package algonquin.cst2335.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.TriviaScoresDAO;
import algonquin.cst2335.finalproject.databinding.TriviaScoreDetailsBinding;

/**
 * This class displays details about a selected player score.
 * @author Jay Pyefinch
 */
public class TriviaScoreDetailFragment extends Fragment {

    /**
     * This field stores the binding associated with the fragment.
     */
    private TriviaScoreDetailsBinding binding;

    /**
     * This field stores an ArrayList of scores currently displayed.
     */
    private List<TriviaScoreFragment.Score> displayedScores;

    /**
     * This field stores the adapter associated with the list of displayed scores.
     */
    private RecyclerView.Adapter<TriviaScoreFragment.ScoreHolder> listAdapter;

    /**
     * This field stores the position of the selected score.
     */
    private int position;

    /**
     * This field stores the DAO that is used to access the score database
     */
    private TriviaScoresDAO scoreDAO;


    /**
     * This constructor initializes the object with references to the currently displayed scores,
     * the position of the selected score, the adpater to notify of the change if deleted, and the
     * DAO with which to delete the score.
     * @param displayedScores the currently displayed score
     * @param position the position of this score in the displayed scores
     * @param listAdapter the adapter of the list of scores
     * @param scoreDAO the DAO used to delete the score
     */
    public TriviaScoreDetailFragment(List<TriviaScoreFragment.Score> displayedScores, int position, RecyclerView.Adapter<TriviaScoreFragment.ScoreHolder> listAdapter, TriviaScoresDAO scoreDAO) {
        this.displayedScores = displayedScores;
        this.listAdapter = listAdapter;
        this.position = position;
        this.scoreDAO = scoreDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = TriviaScoreDetailsBinding.inflate(inflater);

        binding.scoreDetailName.setText(displayedScores.get(position).getName() + " ID:" + displayedScores.get(position).id);

        binding.scoreDetailScore.setText(displayedScores.get(position).getScore() + "/" + displayedScores.get(position).getTriviaLength());

        binding.scoreDetailcategory.setText(displayedScores.get(position).getCategory());

        binding.scoreDetailDifficulty.setText(displayedScores.get(position).getDifficulty());


        binding.scoreDetailBackButton.setOnClickListener(backButton -> {
            Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Score Details");
            getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
        });

        binding.scoreDetailDeleteButton.setOnClickListener(deleteButton -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            builder.setMessage("Do you want to delete this score?")
                    .setTitle("Question").setPositiveButton("Yes", (dialog, cl) -> {
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            TriviaScoreFragment.Score removedScore = displayedScores.remove(position);
                            scoreDAO.deleteScore(removedScore);

                            getActivity().runOnUiThread(() -> {
                                listAdapter.notifyItemRemoved(position);

                                Snackbar.make(binding.scoreTitleView, "You deleted score #" + position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo", clk -> {
                                            displayedScores.add(position, removedScore);
                                            listAdapter.notifyItemInserted(position);
                                        }).show();
                                binding.scoreDetailDeleteButton.setVisibility(View.INVISIBLE);
                            });
                        });
                    })
                    .setNegativeButton("No", (dialog, cl) -> {
                    })
                    .create().show();
        });


        return binding.getRoot();
    }
}
