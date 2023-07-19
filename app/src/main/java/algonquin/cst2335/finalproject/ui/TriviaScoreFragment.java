package algonquin.cst2335.finalproject.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.TriviaScoreRecordBinding;
import algonquin.cst2335.finalproject.databinding.TriviaTopScoresBinding;


public class TriviaScoreFragment extends Fragment {

    private ArrayList<Score> scores = new ArrayList<>();

    private TriviaTopScoresBinding binding;

    private boolean scoreSubmitted = false;

    private RecyclerView.Adapter<ScoreHolder> scoreListAdapter;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = TriviaTopScoresBinding.inflate(inflater);

        sharedPreferences = getActivity().getSharedPreferences("PlayerData",
                Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("PlayerName", "");
        binding.nameInput.setText(savedName);

        binding.scoresList.setAdapter(scoreListAdapter = new RecyclerView.Adapter<ScoreHolder>() {

            @NonNull
            @Override
            public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ViewBinding binding = TriviaScoreRecordBinding.inflate(getLayoutInflater());
                return new ScoreHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
                holder.playerName.setText(scores.get(position).name);
            }

            @Override
            public int getItemCount() {
                return scores.size();
            }

            public int getItemViewType(int position){
                return 0;
            }
        });

        binding.submitScoreButton.setOnClickListener(v -> {
            if (!scoreSubmitted) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("PlayerName", binding.nameInput.getText().toString()).apply();
                // Add score to database.
                Score playerScore = new Score(binding.nameInput.getText().toString(), 0); // Put actual score here.
                Snackbar.make(binding.nameInput, "Score added", Snackbar.LENGTH_LONG)
                        .setAction("Undo", clk -> {
                            this.scoreSubmitted = false;
                            int position = scores.indexOf(playerScore);
                            scores.remove(playerScore);
                            scoreListAdapter.notifyItemRemoved(position);
                        }).show();
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
        Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Top Scores");
        getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
        TriviaSelectorFragment categorySelection = new TriviaSelectorFragment();
        getParentFragmentManager().beginTransaction()
                .add(R.id.fragmentLocation, categorySelection, "Category Selection")
                .commit();
    }

    public static class Score {
        private String name;
        private int score;

        public Score() {}

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
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

    }

    private class ScoreHolder extends RecyclerView.ViewHolder {

        private TextView playerName;

        private TextView score;

        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            TriviaScoreRecordBinding binding = TriviaScoreRecordBinding.bind(itemView);
            this.playerName = binding.playerName;
            this.score = binding.score;
        }
    }
}
