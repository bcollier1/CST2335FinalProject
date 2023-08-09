package algonquin.cst2335.finalproject.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Currency;
import algonquin.cst2335.finalproject.data.CurrencyDAO;
import algonquin.cst2335.finalproject.data.CurrencyDatabase;
import algonquin.cst2335.finalproject.data.CurrencyViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyListBinding;

/**
 * Our CurrencyResult class. This class will be the second window that opens when user wants to see
 * the results of all of the conversions they have made. At the top of the class we have our toolbar
 * which will give the user an option to delete the conversion they have made. It will also give the
 * User a bit of information at the bottom upon selecting the conversion.
 */
public class CurrencyResult extends AppCompatActivity {

    /**
     * Variable set up for when the user selects the currency
     */
    private Currency selectedCurrency;
    /**
     * Variable set up for when user selects on position of display
     */
    private int selectedPosition;
    /**
     * ViewModel variable which hold data operations for conversions
     */
    private CurrencyViewModel viewModel;
    /**
     * Adapter set up for the RecyclerView to display the conversions into a list
     */
    private RecyclerView.Adapter<MyRowHolder> adapter;
    /**
     * List of the conversions which will be displayed
     */
    private ArrayList<Currency> conversions = new ArrayList<>();
    /**
     * Binding set up for the view
     */
    ActivityCurrencyListBinding binding;

    /**
     * The onCreate will initialize the entire activity. It will set up how the View should look when
     * user goes into Result. It will show the user their conversions and when they click on them,
     * the fragment will appear showing them more information.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);


        viewModel.getSelectedCurrency().observe(this, new Observer<Currency>() {
            /**
             * This fragment has been set up so that when user clicks on the conversion they have
             * made it will show them the results of the conversion: ID in database, From and To
             * nations and the amount.
             * @param currency  The new data
             */
            @Override
            public void onChanged(Currency currency) {
                CurrencyResultsFragement fragment = new CurrencyResultsFragement(currency);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragL, fragment);
                fragmentTransaction.commit();

                Log.d("CurrencyResult", "Fragment transaction committed");
            }
        });


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             * Called when the RecyclerView needs a new {@link MyRowHolder} of the given type to represent an item.
             *
             * @param parent The ViewGroup into which the new View will be added after it is bound to
             *               an adapter position.
             * @param viewType The view type of the new View.
             * @return A new MyRowHolder that holds a View of the given view type.
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_currency_results, parent, false);
                return new MyRowHolder(view);
            }
            /**
             * Called by RecyclerView to display the data at the specified position. This method updates the
             * contents of the itemView to reflect the item at the given position.
             *
             * <p>
             * Note: The 'position' argument is used to index the current item in the 'conversions' ArrayList
             * and bind this data to the ViewHolder. This method also sets an OnClickListener for each item,
             * allowing interaction with individual items in the list.
             * </p>
             *
             * @param holder The ViewHolder which should be updated to represent the contents of the item at the
             *               given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                Currency conversion = conversions.get(position);
                String conversionResult = conversion.getFromCountry() + " = " + conversion.getToCountry() + " " + conversion.getAmount();
                Log.d("CurrencyAdapter", "Displaying conversion: " + conversionResult);
                holder.currencyRow.setText(conversionResult);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    /**
                     * When user clicks on currency they can inspect what it is as the bottom fragment
                     * will appear showing them more info on it.
                     * @param view The view that was clicked.
                     */
                    @Override
                    public void onClick(View view) {
                        selectedCurrency = conversions.get(position);
                        selectedPosition = position;

                        viewModel.selectCurrency(selectedCurrency);
                    }

                });
            }
            /**
             * This method returns the size of the dataset (invoked by the layout manager).
             *
             * @return The total number of items in the 'conversions' ArrayList representing the number
             *         of items in the dataset being used by the adapter.
             */
            @Override
            public int getItemCount() {
                return conversions.size();
            }
        };

        binding.recycleView.setAdapter(adapter);
        viewModel.getCurrencies().observe(this, newCurrencies -> {
            conversions = new ArrayList<>(newCurrencies);
            Log.d("CurrencyResult", "Fetched currencies: " + conversions);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * MyRowHolder class to hold each row in the RecyclerView. Each row represents a currency conversion.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView currencyRow;

        /**
         * Will initialize the holder and bind the currencyRow ID
         * @param itemView this is the view of the holder
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            currencyRow = itemView.findViewById(R.id.currencyRow);
        }
    }

    /**
     * This will handle the selection of menu items when selected on the tool bar. It will allow user
     * to delete the conversion they have made if they would like, also removing it from the database
     * @param item The menu item that was selected.
     *
     * @return if item it selected it will be true, false if nothing if clicked
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.currencyDel) {
            if (selectedCurrency != null) {
                conversions.remove(selectedPosition);
                viewModel.delete(selectedCurrency);
                adapter.notifyDataSetChanged();

                Snackbar.make(binding.getRoot(), "Item removed", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            viewModel.insert(selectedCurrency);
                            conversions.add(selectedPosition, selectedCurrency);
                            adapter.notifyDataSetChanged();
                        }).show();
            } else {
                Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
            }

            return true;
        } else if(item.getItemId() == R.id.currencyHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyResult.this);
            builder.setTitle(R.string.info_title);
            builder.setMessage(R.string.info_message);
            builder.setPositiveButton(R.string.ok_button, (dialog, cl) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (item.getItemId() == R.id.currencyToBear) {
            Intent bear = new Intent(CurrencyResult.this, BearGeneratorActivity.class);
            CurrencyResult.this.startActivity(bear);

        }
        else if (item.getItemId() == R.id.currencyToTrivia) {
            Intent trivia = new Intent(CurrencyResult.this, TriviaActivity.class);
            CurrencyResult.this.startActivity(trivia);

        } else if (item.getItemId() == R.id.currencyToFlightTracker) {
            Intent airTracker = new Intent(CurrencyResult.this, AirportDisplayBoardActivity.class);
            CurrencyResult.this.startActivity(airTracker);
        }
        return false;
    }


    /**
     * This will inflate the toolbar
     * @param menu The options menu in which you place your items.
     *
     * @return true if displaying the menu, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.currency_toolbar_menu, menu);

        return true;
    }
}


