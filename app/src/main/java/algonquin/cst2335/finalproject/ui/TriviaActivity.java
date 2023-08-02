package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.TriviaAPIURLBuilder;
import algonquin.cst2335.finalproject.data.TriviaGameState;
import algonquin.cst2335.finalproject.data.TriviaViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerABinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerBBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerCBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerDBinding;

/**
 * This class facilitates the playing of a trivia using OpenTDB's API.
 * @author Jay Pyefinch
 */
public class TriviaActivity extends AppCompatActivity {

    /**
     * This field stores the current {@link TriviaGameState game state} of the trivia.
     */
    private TriviaGameState game = new TriviaGameState();

    /**
     * This field stores the {@link TriviaAPIURLBuilder URL Builder} used to generate the API URL.
     */
    private TriviaAPIURLBuilder urlBuilder = new TriviaAPIURLBuilder();

    /**
     * This field stores the queue for sending requests to the OpenTDB API.
     */
    private RequestQueue queue;

    /**
     * This field stores the {@link TriviaViewModel} associated with this activity.
     */
    TriviaViewModel triviaModel;

    /**
     * This field stores the list of currently displayed answers.
     */
    private ArrayList<Answer> displayedAnswers = new ArrayList<>();

    /**
     * This field stores the binding of this activity.
     */
    private ActivityTriviaBinding binding;

    /**
     * This field stores the adapter attached to the list of displayed answers.
     */
    private RecyclerView.Adapter<AnswerHolder> answerListAdapter;

    /**
     * This field stores the {@link Random} instance used to generate the correct answer slot.
     */
    private Random randomNumber = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.trivia_toolbar_menu, menu);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.triviaToFlightTracker) {
            Intent airTracker = new Intent(TriviaActivity.this, AirportDisplayBoardActivity.class);
            TriviaActivity.this.startActivity(airTracker);
        } else if (item.getItemId() == R.id.triviaToBear) {
            Intent bear = new Intent(TriviaActivity.this, BearGeneratorActivity.class);
            TriviaActivity.this.startActivity(bear);
        } else if (item.getItemId() == R.id.triviaToCurrency) {
            Intent currencyConverter = new Intent(TriviaActivity.this, CurrencyActivity.class);
            TriviaActivity.this.startActivity(currencyConverter);
        } else if (item.getItemId() == R.id.triviaHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage("Game Selector:\nSelect a topic from the Categories dropdown, then select a "
                    + "difficulty level from the difficulty level dropdown. When you tap the Select button, "
                    + "you will be prompted to check your selection. If it is satisfactory, hit 'Yes' to "
                    + "the selected game of Trivia.\n\n"
                    + "Trivia Game:\nBased on the previous selection, you will be put through a sequence of "
                    + "questions. Tap on the answer that you think is best for each question. After you have "
                    + "selected an answer, you will be told whether you got it right or not. Good Luck!\n\n"
                    + "Top Scores:\nFinally, you will find yourself on the top scores page. Here, you will "
                    + "find the top scorers in the trivia game. You will have the option to add your score "
                    + "to the list, and examine previous games.\n\nHave Fun!")
                    .setTitle("How to use the app.").setPositiveButton("Ok", (dialog, cl) -> {
                    }).create().show();
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.triviaToolbar);

        queue = Volley.newRequestQueue(this);

        triviaModel = new ViewModelProvider(this).get(TriviaViewModel.class);

        TriviaSelectorFragment categorySelection = new TriviaSelectorFragment(game, urlBuilder, triviaModel);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentLocation, categorySelection, "Category Selection")
                .commit();
        binding.answerList.setLayoutManager(new LinearLayoutManager(this));

        Answer answerA = new Answer("Loading...", 0);
        Answer answerB = new Answer("Loading...", 2);
        Answer answerC = new Answer("Loading...", 4);
        Answer answerD = new Answer("Loading...", 6);
        displayedAnswers.add(answerA);
        displayedAnswers.add(answerB);
        displayedAnswers.add(answerC);
        displayedAnswers.add(answerD);

        binding.answerList.setAdapter(answerListAdapter = new RecyclerView.Adapter<AnswerHolder>() {

            @NonNull
            @Override
            public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ViewBinding binding = null;
                switch (viewType) {
                    case 0:
                    case 1:
                        binding = TriviaAnswerABinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                        break;
                    case 2:
                    case 3:
                        binding = TriviaAnswerBBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                        break;
                    case 4:
                    case 5:
                        binding = TriviaAnswerCBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                        break;
                    case 6:
                    case 7:
                        binding = TriviaAnswerDBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                        break;
                    default:
                        break;
                }
                if (binding != null) {
                    return new AnswerHolder(binding.getRoot(), viewType);
                } else {
                    return null;
                }
            }

            @Override
            public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
                holder.answerText.setText(Html.fromHtml(displayedAnswers.get(position).getAnswerText(), Html.FROM_HTML_MODE_LEGACY).toString());
            }

            @Override
            public int getItemCount() {
                return displayedAnswers.size();
            }

            public int getItemViewType(int position){
                return displayedAnswers.get(position).getViewType();
            }
        });

        triviaModel.url.observe(this, (newURL) -> {
            if (!(newURL == null || newURL.equals(""))) {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newURL, null, (response) -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); ++i) {

                            JSONObject JSONQuestion = results.getJSONObject(i);

                            TriviaGameState.Question question = new TriviaGameState.Question();

                            question.setQuestionText(Html.fromHtml(JSONQuestion.getString("question"), Html.FROM_HTML_MODE_LEGACY).toString());
                            question.setCorrectAnswer(JSONQuestion.getString("correct_answer"));

                            JSONArray JSONIncorrectAnswers = JSONQuestion.getJSONArray("incorrect_answers");

                            String[] incorrectAnswers = new String[3];
                            for (int j = 0; j < JSONIncorrectAnswers.length(); ++j) {
                                incorrectAnswers[j] = JSONIncorrectAnswers.getString(j);
                            }

                            question.setIncorrectAnswers(incorrectAnswers);

                            game.addQuestion(question);
                        }

                        triviaModel.game.postValue(game);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, (error) -> {

                });
                queue.add(request);
            }
        });

        triviaModel.game.observe(this, (newGameState) -> {
            this.game = newGameState;
            if (this.game.getNumberOfQuestions() != 0) {
                goToNextQuestion();
            }
        });

    }

    /**
     * This class associates an answer with the viewType it was assigned to.
     * @author Jay Pyefinch
     */
    public static class Answer {
        /**
         * This field stores the text of the answer to be displayed to the user.
         */
        private String answerText;

        /**
         * This field stores the viewType associated with the answer.
         */
        private int viewType;

        /**
         * This constructor initializes the object with default values.
         */
        public Answer() {}

        /**
         * This constructor initializes the fields with the indicated values
         * @param answerText the text of the answer
         * @param viewType the viewType the answer is in
         */
        public Answer(String answerText, int viewType) {
            this.answerText = answerText;
            this.viewType = viewType;
        }

        /**
         * This method gets the answer text
         * @return the answer text
         */
        public String getAnswerText() {
            return this.answerText;
        }

        /**
         * This method sets the answer text
         * @param answerText the text associated with the answer.
         */
        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        /**
         * This method gets the viewType associated with the answer
         * @return the viewType
         */
        public int getViewType() {
            return this.viewType;
        }

        /**
         * This method sets the viewType associated with the answer
         * @param viewType the viewType
         */
        public void setViewType(int viewType) {
            this.viewType = viewType;
        }
    }

    /**
     * This class holds an answer in the associated recyclerView.
     * @author Jay Pyefinch
     */
    private class AnswerHolder extends RecyclerView.ViewHolder {

        /**
         * This field stores the view that holds the answer text.
         */
        TextView answerText;

        /**
         * This field stores the boolean determining if this answer is correct.
         */
        boolean isCorrect;

        /**
         * This field stores the viewType of this holder
         */
        int viewType;

        /**
         * This Constructor inflates the holder and gives it an on-click listener
         * @param itemView the holder's associated view
         * @param viewType the holder's viewType
         */
        public AnswerHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.isCorrect = false;
            this.viewType = viewType;
            switch (viewType) {
                case 0:
                    this.isCorrect = true;
                case 1:
                    TriviaAnswerABinding aAnswerBinding = TriviaAnswerABinding.bind(itemView);
                    answerText = aAnswerBinding.answerText;
                    break;
                case 2:
                    this.isCorrect = true;
                case 3:
                    TriviaAnswerBBinding bAnswerBinding = TriviaAnswerBBinding.bind(itemView);
                    answerText = bAnswerBinding.answerText;
                    break;
                case 4:
                    this.isCorrect = true;
                case 5:
                    TriviaAnswerCBinding cAnswerBinding = TriviaAnswerCBinding.bind(itemView);
                    answerText = cAnswerBinding.answerText;
                    break;
                case 6:
                    this.isCorrect = true;
                case 7:
                    TriviaAnswerDBinding dAnswerBinding = TriviaAnswerDBinding.bind(itemView);
                    answerText = dAnswerBinding.answerText;
                    break;
                default:
                    break;
            }

            itemView.setOnClickListener((v) -> {
                if (viewType % 2 == 0) {
                    reportCorrectIncorrect(true);
                    game.setNumberAnsweredCorrectly(game.getNumberAnsweredCorrectly()+1);
                } else {
                    reportCorrectIncorrect(false);
                }
                if (game.getCurrentQuestionNumber()+1 > game.getNumberOfQuestions()) {
                    TriviaScoreFragment scoreFragment = new TriviaScoreFragment(game, urlBuilder, triviaModel);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentLocation, scoreFragment, "Top Scores")
                            .commit();
                } else {
                    goToNextQuestion();
                }
            });
        }
    }

    /**
     * This method displays a Toast whether the answer was right or wrong. Will say the right answer
     * if wrong.
     * @param isCorrect whether the question is right or wrong.
     */
    private void reportCorrectIncorrect(boolean isCorrect) {
        if (isCorrect) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect: Answer was '" + Html.fromHtml(this.game.getCurrentQuestion().getCorrectAnswer(), Html.FROM_HTML_MODE_LEGACY).toString() + "'", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method transitions the game-state to the next question.
     */
    private void goToNextQuestion() {
        while (!displayedAnswers.isEmpty()) {
            displayedAnswers.remove(displayedAnswers.size()-1);
        }
        game.setCurrentQuestion(game.getCurrentQuestionNumber()+1);

        TriviaGameState.Question question = game.getCurrentQuestion();

        binding.questionProgress.setText("Question " + game.getCurrentQuestionNumber() + "/" + game.getNumberOfQuestions());

        binding.questionText.setText(Html.fromHtml(question.getQuestionText(), Html.FROM_HTML_MODE_LEGACY).toString());
        int correctAnswerSlot = 0;
        do {
             correctAnswerSlot = getRandomCorrectAnswerSlot();
        } while (correctAnswerSlot >= question.getIncorrectAnswers().length);
        int incorrectAnswerIndexCounter = 0;
        for (int i = 0; i <= 6; i += 2) {
            if (i == (correctAnswerSlot*2)) {
                displayedAnswers.add(new Answer(question.getCorrectAnswer(), i));
            } else {
                displayedAnswers.add(new Answer(question.getIncorrectAnswers()[incorrectAnswerIndexCounter++], i+1));
            }
        }
        binding.answerList.getAdapter().notifyDataSetChanged();

    }

    /**
     * This method generates a random number for the correct answer slot.
     * @return the correct answer slot to use.
     */
    private int getRandomCorrectAnswerSlot() {
        return randomNumber.nextInt(4);
    }
}