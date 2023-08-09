package algonquin.cst2335.finalproject.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.TriviaAPIURLBuilder;
import algonquin.cst2335.finalproject.data.TriviaGameState;
import algonquin.cst2335.finalproject.data.TriviaScoreDatabase;
import algonquin.cst2335.finalproject.data.TriviaScoresDAO;
import algonquin.cst2335.finalproject.data.TriviaViewModel;
import algonquin.cst2335.finalproject.databinding.TriviaScoreRecordBinding;
import algonquin.cst2335.finalproject.databinding.TriviaTopScoresBinding;

/**
 * This class hosts the player scores associated with the last-played trivia's category and
 * difficulty.
 * @author Jay Pyefinch
 */
public class TriviaScoreFragment extends Fragment {

    /**
     * This field stores the last trivia's game-state
     */
    private TriviaGameState game;

    /**
     * This field stores the URL builder to send to the category selector fragment.
     */
    private TriviaAPIURLBuilder urlBuilder;

    /**
     * This field stores the model associated with the trivia activity.
     */
    private TriviaViewModel triviaModel;

    /**
     * This field stores an ArrayList of displayed Scores.
     */
    private ArrayList<Score> displayedScores = new ArrayList<>();

    /**
     * This field stores the DAO used to add and delete scores, as well as retrieve them for the
     * list.
     */
    private TriviaScoresDAO scoreDAO;

    /**
     * This field stores the binding associated with this fragment.
     */
    private TriviaTopScoresBinding binding;

    /**
     * This field stores a boolean that checks if a score has been added.
     */
    private boolean scoreSubmitted = false;

    /**
     * This field stores the adapter of the list of displayed answers.
     */
    private RecyclerView.Adapter<ScoreHolder> scoreListAdapter;

    /**
     * This field stores the shared preferences of the previously entered name.
     */
    private SharedPreferences sharedPreferences;

    /**
     * This field stores a reference to the add score button.
     */
    private Button scoreButton;

    /**
     * This constructor initializes the object with the game state, the url builder for the selector
     * fragment, and the model associated with the activity.
     * @param game the game state of the played trivia
     * @param urlBuilder the url builder
     * @param triviaModel the viewModel
     */
    public TriviaScoreFragment(TriviaGameState game, TriviaAPIURLBuilder urlBuilder, TriviaViewModel triviaModel) {
        super();
        this.game = game;
        this.urlBuilder = urlBuilder;
        this.triviaModel = triviaModel;
        this.displayedScores = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = TriviaTopScoresBinding.inflate(inflater);

        scoreButton = binding.submitScoreButton;

        sharedPreferences = getActivity().getSharedPreferences("PlayerData",
                Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("PlayerName", "");
        binding.nameInput.setText(savedName);

        binding.scoresList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        binding.scoresList.setAdapter(scoreListAdapter = new RecyclerView.Adapter<ScoreHolder>() {

            @NonNull
            @Override
            public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ViewBinding binding = TriviaScoreRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ScoreHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
                holder.playerName.setText(displayedScores.get(position).name);
                holder.score.setText(String.valueOf(displayedScores.get(position).score) + "/" + String.valueOf(displayedScores.get(position).triviaLength));
            }

            @Override
            public int getItemCount() {
                return displayedScores.size();
            }

            public int getItemViewType(int position){
                return 0;
            }
        });

        TriviaScoreDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), TriviaScoreDatabase.class, "scores-database").build();
        this.scoreDAO = db.sDAO();

        displayedScores = triviaModel.scores.getValue();
        if (displayedScores == null) {
            displayedScores = new ArrayList<>();
            triviaModel.scores.postValue(displayedScores);
        }

        Executor firstThread = Executors.newSingleThreadExecutor();
        firstThread.execute(() -> {
            displayedScores = new ArrayList<>();
            displayedScores.addAll( scoreDAO.getScores(game.getCategory(), game.getDifficulty()) );
            triviaModel.scores.postValue(displayedScores);
        });

        triviaModel.scores.observe(getViewLifecycleOwner(), (newScores) -> {
            binding.scoresList.getAdapter().notifyDataSetChanged();
        });

        binding.submitScoreButton.setOnClickListener(v -> {
            if (!scoreSubmitted) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("PlayerName", binding.nameInput.getText().toString()).apply();

                Score playerScore = new Score(binding.nameInput.getText().toString(),
                        game.getNumberAnsweredCorrectly(),
                        game.getCategory(),
                        game.getDifficulty(),
                        game.getNumberOfQuestions());
                displayedScores.add(playerScore);
                // Add score to database.
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    scoreDAO.insertScore(playerScore);
                    Executor thread2 = Executors.newSingleThreadExecutor();
                    thread2.execute(() -> {
                        displayedScores = new ArrayList<>();
                        displayedScores.addAll( scoreDAO.getScores(game.getCategory(), game.getDifficulty()) );
                        triviaModel.scores.postValue(displayedScores);
                    });
                });
                this.scoreSubmitted = true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder( this.getContext() );
                builder.setMessage("You have already submitted your score.")
                        .setTitle("Warning").setPositiveButton("Back", (dialog, cl) -> { })
                        .create().show();
            }
        });

        binding.newGameButton.setOnClickListener(v -> {
            if (scoreSubmitted) {
                returnToCategorySelection();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder( this.getContext() );
                builder.setMessage("You have not submitted your score. Continue?")
                        .setTitle("Warning").setPositiveButton("Yes", (dialog, cl) -> {
                            returnToCategorySelection();
                        })
                        .setNegativeButton("No", (dialog, cl) -> { })
                        .create().show();
            }
        });

        return binding.getRoot();
    }

    /**
     * This method returns the user to the category selection using the game-state, the url builder,
     * and the viewModel.
     */
    private void returnToCategorySelection() {
        this.game.resetGameState();
        Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Top Scores");
        getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
        TriviaSelectorFragment categorySelection = new TriviaSelectorFragment(this.game, this.urlBuilder, this.triviaModel);
        getParentFragmentManager().beginTransaction()
                .add(R.id.fragmentLocation, categorySelection, "Category Selection")
                .commit();
    }

    /**
     * This class stores the score of the user in a trivia game, and allows it to be added to a
     * database.
     * @author Jay Pyefinch
     */
    @Entity
    public static class Score {

        /**
         * This field stores the database-assigned id.
         */
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        public int id;

        /**
         * This field stores the player's name.
         */
        @ColumnInfo(name = "name")
        private String name;

        /**
         * This field stores the number of questions answered correctly.
         */
        @ColumnInfo(name = "score")
        private int score;

        /**
         * This field stores the category of the trivia played.
         */
        @ColumnInfo(name = "category")
        private String category;

        /**
         * This field stores the difficulty of the played trivia.
         */
        @ColumnInfo(name = "difficulty")
        private String difficulty;

        /**
         * This field stores the number of questions answered.
         */
        @ColumnInfo(name = "length")
        private int triviaLength;

        /**
         * This Constructor initializes the object with default values.
         */
        public Score() {}

        /**
         * This contructor initializes the object with specified values.
         * @param name the player name
         * @param score the amount answered correctly
         * @param category the category of trivia
         * @param difficulty the difficulty of trivia
         * @param triviaLength the number of questions answered
         */
        public Score(String name, int score, String category, String difficulty, int triviaLength) {
            this.name = name;
            this.score = score;
            this.category = category;
            this.difficulty = difficulty;
            this.triviaLength = triviaLength;
        }

        /**
         * This method gets the player name.
         * @return the player name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * This method sets the player name.
         * @param name the player name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * This method gets the score
         * @return the score
         */
        public int getScore() {
            return this.score;
        }

        /**
         * This method sets the score
         * @param score the score
         */
        public void setScore(int score) {
            this.score = score;
        }

        /**
         * This method gets the score's category
         * @return the category
         */
        public String getCategory() { return this.category; }

        /**
         * This method sets the score's category
         * @param category the category
         */
        public void setCategory(String category) { this.category = category; }

        /**
         * This method gets the score's difficulty
         * @return the difficulty
         */
        public String getDifficulty() { return this.difficulty; }

        /**
         * This method sets the score's difficulty
         * @param difficulty  the difficulty
         */
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        /**
         * This method gets the number of questions in the completed trivia
         * @return the number of questions answered
         */
        public int getTriviaLength() { return this.triviaLength; }

        /**
         * This method sets the number of questions in the completed trivia
         * @param triviaLength the number of questions answered
         */
        public void setTriviaLength(int triviaLength) { this.triviaLength = triviaLength; }

    }

    /**
     * This class is the holder for a score instance.
     * @author Jay Pyefinch
     */
    public class ScoreHolder extends RecyclerView.ViewHolder {

        /**
         * This field stores the player name textView
         */
        private final TextView playerName;

        /**
         * This field stores the score value's textView
         */
        private final TextView score;

        /**
         * This constructor binds the holder
         * @param itemView the associated view
         */
        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            TriviaScoreRecordBinding binding = TriviaScoreRecordBinding.bind(itemView);
            this.playerName = binding.playerName;
            this.score = binding.score;

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();

                TriviaScoreDetailFragment scoreDetailFragment = new TriviaScoreDetailFragment(displayedScores, position, scoreListAdapter, scoreDAO);
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragmentLocation, scoreDetailFragment, "Score Details")
                        .commit();
            });
        }
    }
}
