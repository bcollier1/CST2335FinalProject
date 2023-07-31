package algonquin.cst2335.finalproject.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.TriviaAPIURLBuilder;
import algonquin.cst2335.finalproject.data.TriviaGameState;
import algonquin.cst2335.finalproject.data.TriviaViewModel;
import algonquin.cst2335.finalproject.databinding.TriviaCategorySelectionBinding;
import algonquin.cst2335.finalproject.util.Constants;

public class TriviaSelectorFragment extends Fragment {

    private final TriviaGameState game;

    private TriviaAPIURLBuilder urlBuilder;

    private TriviaViewModel triviaModel;

    public TriviaSelectorFragment(TriviaGameState game, TriviaAPIURLBuilder urlBuilder, TriviaViewModel triviaModel) {
        super();
        this.game = game;
        this.urlBuilder = urlBuilder;
        this.triviaModel = triviaModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        TriviaCategorySelectionBinding binding = TriviaCategorySelectionBinding.inflate(inflater);

        // From https://developer.android.com/develop/ui/views/components/spinner#:~:text=In%20the%20default%20state%2C%20a,can%20select%20a%20new%20one.&text=To%20populate%20the%20spinner%20with,Activity%20or%20Fragment%20source%20code.
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.trivia_categories_array, R.layout.trivia_spinner_item);
        categoryAdapter.setDropDownViewResource(R.layout.trivia_spinner_item);

        binding.categorySelector.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.trivia_difficulties_array, R.layout.trivia_spinner_item);
        difficultyAdapter.setDropDownViewResource(R.layout.trivia_spinner_item);

        binding.difficultySelector.setAdapter(difficultyAdapter);

        binding.categoryButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder( this.getContext() );
            builder.setMessage("You have selected the following:\nCategory: " +
                            binding.categorySelector.getSelectedItem().toString() +
                            "\nDifficulty: " + binding.difficultySelector.getSelectedItem().toString())
                    .setTitle("Ready?").setPositiveButton("Yes", (dialog, cl) -> {

                        String category = binding.categorySelector.getSelectedItem().toString();
                        String difficulty = binding.difficultySelector.getSelectedItem().toString();
                        urlBuilder.setCategorySegment(category)
                                .setDifficultySegment(difficulty);

                        String builtURL = urlBuilder.build();
                        triviaModel.url.postValue(builtURL);

                        game.setCategory(category);
                        game.setDifficulty(difficulty);

                        Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Category Selection");
                        getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
                    })
                    .setNegativeButton("No", (dialog, cl) -> { })
                    .create().show();
        });

        return binding.getRoot();
    }
}
