package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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

    private final Context triviaContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

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