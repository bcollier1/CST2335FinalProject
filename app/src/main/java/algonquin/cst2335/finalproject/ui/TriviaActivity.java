package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerABinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerBBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerCBinding;
import algonquin.cst2335.finalproject.databinding.TriviaAnswerDBinding;
import algonquin.cst2335.finalproject.databinding.TriviaTopScoresBinding;

public class TriviaActivity extends AppCompatActivity {

    private ArrayList<Answer> answers = new ArrayList<>();

    private int currentQuestion = 10; // Set to 1
    private ActivityTriviaBinding binding;

    private RecyclerView.Adapter<AnswerHolder> answerListAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.trivia_toolbar_menu, menu);

        return true;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.triviaToolbar);


        TriviaSelectorFragment categorySelection = new TriviaSelectorFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentLocation, categorySelection, "Category Selection")
                .commit();

        binding.questionList.setLayoutManager(new LinearLayoutManager(this));

        Answer answerA = new Answer("Placeholder Text", 0);
        Answer answerB = new Answer("Placeholder Text", 2);
        Answer answerC = new Answer("Placeholder Text", 4);
        Answer answerD = new Answer("Placeholder Text", 6);
        answers.add(answerA);
        answers.add(answerB);
        answers.add(answerC);
        answers.add(answerD);

        binding.questionList.setAdapter(answerListAdapter = new RecyclerView.Adapter<AnswerHolder>() {

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
                holder.answerText.setText(answers.get(position).getAnswerText());
            }

            @Override
            public int getItemCount() {
                return answers.size();
            }

            public int getItemViewType(int position){
                return answers.get(position).getViewType();
            }
        });
        /*
        Answer answerA = new Answer("Placeholder Text", 0);
        Answer answerB = new Answer("Placeholder Text", 2);
        Answer answerC = new Answer("Placeholder Text", 4);
        Answer answerD = new Answer("Placeholder Text", 6);
        answers.add(answerA);
        answerListAdapter.notifyItemInserted(answers.size() - 1);
        answers.add(answerB);
        answerListAdapter.notifyItemInserted(answers.size() - 1);
        answers.add(answerC);
        answerListAdapter.notifyItemInserted(answers.size() - 1);
        answers.add(answerD);

         */
    }

    public static class Answer {
        private String answerText;

        private int viewType;

        public Answer() {}
        public Answer(String answerText, int viewType) {
            this.answerText = answerText;
            this.viewType = viewType;
        }

        public String getAnswerText() {
            return this.answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public int getViewType() {
            return this.viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }
    }

    private class AnswerHolder extends RecyclerView.ViewHolder {

        TextView answerText;
        boolean isCorrect;

        int viewType;
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
                } else {
                    reportCorrectIncorrect(false);
                }
                if (++currentQuestion > 10) {
                    currentQuestion = 10; // Set to 1
                    TriviaScoreFragment scoreFragment = new TriviaScoreFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentLocation, scoreFragment, "Top Scores")
                            .commit();
                }
            });
        }
    }

    private void reportCorrectIncorrect(boolean isCorrect) {
        if (isCorrect) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}