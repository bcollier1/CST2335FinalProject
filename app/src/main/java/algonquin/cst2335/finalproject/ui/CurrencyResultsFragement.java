package algonquin.cst2335.finalproject.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Currency;
import algonquin.cst2335.finalproject.data.CurrencyViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyListBinding;
import algonquin.cst2335.finalproject.databinding.CurrencyFragmentBinding;

/**
 * This is our fragement class. Basically the class will be shown in a small little box in the bottom
 * of the results page. When a user clicks on the converted item they have made, the box will show the
 * user the ID, Amount, To and From country they have converted it too
 */
public class CurrencyResultsFragement extends Fragment {

    /**
     * Variable to set up for currency selected
     */
    Currency thisCurrency;

    /**
     * Constructor built for the currency fragement. it will display what is below when the conversion
     * is clicked on
     * @param toShow Currency object this fragement will show.
     */
    public CurrencyResultsFragement(Currency toShow){
        thisCurrency = toShow;
    }

    /**
     * This method is called when you want to view how the fragement will look like in the results
     * page. We give it the currency object and will display all of the information below in that
     * fragement window we created in the XML
     *
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view of the fragement or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CurrencyFragmentBinding binding = CurrencyFragmentBinding.inflate(inflater);

        String fromCountry = thisCurrency.getFromCountry();
        String toCountry = thisCurrency.getToCountry();
        double amount = thisCurrency.getAmount();


            binding.fromCountryMessage.setText("From Country: " + fromCountry);
            binding.toCountryMessage.setText("To Country: " + toCountry);
            binding.amountMessage.setText("Amount: " + amount);
            binding.DatabaseIDGoesHere.setText("Database ID: " + String.valueOf(thisCurrency.getId()));

            return binding.getRoot();
    }

}
