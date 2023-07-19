package algonquin.cst2335.finalproject.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.TriviaCategorySelectionBinding;

public class TriviaSelectorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        TriviaCategorySelectionBinding binding = TriviaCategorySelectionBinding.inflate(inflater);

        // From https://developer.android.com/develop/ui/views/components/spinner#:~:text=In%20the%20default%20state%2C%20a,can%20select%20a%20new%20one.&text=To%20populate%20the%20spinner%20with,Activity%20or%20Fragment%20source%20code.
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.trivia_categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        binding.categorySelector.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.trivia_difficulties_array, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        binding.difficultySelector.setAdapter(difficultyAdapter);

        binding.categoryButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder( this.getContext() );
            builder.setMessage("You have selected the following:\nCategory: " +
                            binding.categorySelector.getSelectedItem().toString() +
                            "\nDifficulty: " + binding.difficultySelector.getSelectedItem().toString())
                    .setTitle("Ready?").setPositiveButton("Yes", (dialog, cl) -> {
                        Fragment thisFragment = getParentFragmentManager().findFragmentByTag("Category Selection");
                        getParentFragmentManager().beginTransaction().remove(thisFragment).commit();
                    })
                    .setNegativeButton("No", (dialog, cl) -> { })
                    .create().show();
        });

        return binding.getRoot();
    }
}
