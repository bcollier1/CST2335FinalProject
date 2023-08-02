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


public class TriviaScoreFragment extends Fragment {

    private TriviaGameState game;

    private TriviaAPIURLBuilder urlBuilder;

    private TriviaViewModel triviaModel;

    private ArrayList<Score> displayedScores = new ArrayList<>();

    private TriviaScoresDAO scoreDAO;

    private TriviaTopScoresBinding binding;

    private boolean scoreSubmitted = false;

    private RecyclerView.Adapter<ScoreHolder> scoreListAdapter;

    private SharedPreferences sharedPreferences;

    private Button scoreButton;

    public TriviaScoreFragment(TriviaGameState game, TriviaAPIURLBuilder urlBuilder, TriviaViewModel triviaModel) {
        super();
        this.game = game;
        this.urlBuilder = urlBuilder;
        this.triviaModel = triviaModel;
        this.displayedScores = new ArrayList<>();
    }

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
                    thread.execute(() -> {
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

    private void returnToCategorySelection() {
        this.game.resetGameState();
        Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Top Scores");
        getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
        TriviaSelectorFragment categorySelection = new TriviaSelectorFragment(this.game, this.urlBuilder, this.triviaModel);
        getParentFragmentManager().beginTransaction()
                .add(R.id.fragmentLocation, categorySelection, "Category Selection")
                .commit();
    }

    @Entity
    public static class Score {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        public int id;
        @ColumnInfo(name = "name")
        private String name;
        @ColumnInfo(name = "score")
        private int score;

        @ColumnInfo(name = "category")
        private String category;

        @ColumnInfo(name = "difficulty")
        private String difficulty;

        @ColumnInfo(name = "length")
        private int triviaLength;

        public Score() {}

        public Score(String name, int score, String category, String difficulty, int triviaLength) {
            this.name = name;
            this.score = score;
            this.category = category;
            this.difficulty = difficulty;
            this.triviaLength = triviaLength;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getCategory() { return this.category; }

        public void setCategory(String category) { this.category = category; }

        public String getDifficulty() { return this.difficulty; }

        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        public int getTriviaLength() { return this.triviaLength; }

        public void setTriviaLength(int triviaLength) { this.triviaLength = triviaLength; }

    }

    public class ScoreHolder extends RecyclerView.ViewHolder {

        private final TextView playerName;

        private final TextView score;

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
