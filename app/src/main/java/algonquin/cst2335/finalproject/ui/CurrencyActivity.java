package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.BuildConfig;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Currency;
import algonquin.cst2335.finalproject.data.CurrencyDAO;
import algonquin.cst2335.finalproject.data.CurrencyDatabase;
import algonquin.cst2335.finalproject.data.CurrencyViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

/**
 * This is our Main Activity for the Currency App.
 * This Application will allow the user to select which two currencies they would like to convert,
 * their money into. The conversions are then saved into a local database where the user can look,
 * back into to see their results, depending on how many times they have converted. This class will
 * also handle navigation to other applications in the program we have created, will take the user
 * to other apps on the project.
 */
public class CurrencyActivity extends AppCompatActivity {

    /**
     * Binding for the activity view.
     */
    ActivityCurrencyBinding binding;
    /**
     * Variable set up for the Return button.
     */
    private Button rbtn;
    /**
     * Variable set up for the Convert button.
     */
    private Button cBtn;
    /**
     * ViewModel holding data operations for conversion
     */
    private CurrencyViewModel viewModel;

    /**
     * Data Access Object (DAO) from Currency class
     */
    CurrencyDAO myDAO;
    /**
     * Variable set up for Results button
     */
    private Button reBtn;

    /**
     * List storing representations of conversions user has made
     */
    private ArrayList<String> conversions = new ArrayList<>();

    /**
     * Request queue for the Volley library to handle network requests.
     */
    RequestQueue queue = null;

    /**
     * This activity will be initialized and set up the necessary view of the application.
     * It will have a JSON set up to retrieve the data when the user makes a conversion.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        viewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);

        rbtn = binding.btnReset;
        cBtn = binding.btnConvert;
        reBtn = binding.resultBtn;
        queue = Volley.newRequestQueue(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFromCurrency.setAdapter(adapter);
        binding.spToCurrency.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("currencyData", Context.MODE_PRIVATE);
        float xChange = prefs.getFloat("decimalValue", 0.0f);
        binding.inFrom.setText(String.valueOf(xChange));
        float[] oldXChange = new float[1];

        rbtn.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyActivity.this);
            builder.setTitle("Question:");
            builder.setMessage("Would you like to reset?");
            builder.setNegativeButton("No", (dialog, cl) -> {
            });
            builder.setPositiveButton("Yes", (dialog, cl) -> {
                oldXChange[0] = xChange;

                int oldFromSpinner = binding.spFromCurrency.getSelectedItemPosition();
                int oldToSpinner = binding.spToCurrency.getSelectedItemPosition();
                String oldResult = binding.cResult.getText().toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat("decimalValue", 0.0f);
                editor.apply();

                binding.spFromCurrency.setSelection(0);
                binding.spToCurrency.setSelection(0);
                binding.cResult.setText("");

                binding.inFrom.setText(String.valueOf(0.0f));
                Snackbar.make(rbtn, "You have reset the values", Snackbar.LENGTH_LONG)
                        .setAction("Undo", (click2) -> {
                            SharedPreferences.Editor editorUndo = prefs.edit();
                            editorUndo.putFloat("decimalValue", oldXChange[0]);
                            editorUndo.apply();
                            binding.inFrom.setText(String.valueOf(oldXChange[0]));
                            binding.spFromCurrency.setSelection(oldFromSpinner);
                            binding.spToCurrency.setSelection(oldToSpinner);
                            binding.cResult.setText(oldResult);
                        })
                        .show();
            });
            builder.create().show();
        });


        cBtn.setOnClickListener(click -> {
            String sourceCurrency = binding.spFromCurrency.getSelectedItem().toString();
            String targetCurrency = binding.spToCurrency.getSelectedItem().toString();
            String amount = binding.inFrom.getText().toString();

            if (sourceCurrency.isEmpty() || targetCurrency.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please select both currencies and enter an amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = BuildConfig.Currency_URL + "&from=" + sourceCurrency + "&to=" + targetCurrency + "&amount=" + amount + "&api_key=" + BuildConfig.Currency_KEY + "&format=json";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONObject ratesObject = response.getJSONObject("rates");
                            JSONObject targetCurrencyObject = ratesObject.getJSONObject(targetCurrency);
                            String rateForAmount = targetCurrencyObject.getString("rate_for_amount");
                            String resultString = amount + " " + sourceCurrency + " = " + rateForAmount + " " + targetCurrency;
                            conversions.add(resultString);
                            binding.cResult.setText(resultString);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putFloat("decimalValue", Float.parseFloat(rateForAmount));
                            editor.apply();
                            Toast.makeText(this, "Converted!", Toast.LENGTH_SHORT).show();

                            Currency conversion = new Currency();
                            conversion.setFromCountry(sourceCurrency);
                            conversion.setToCountry(targetCurrency);
                            conversion.setAmount(Double.parseDouble(rateForAmount));
                            viewModel.insert(conversion);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(this, "An error occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show());

            queue.add(request);
        });


        reBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * An on click set here, which has a intent set up. When results is clicked, it will move
             * to the Currency Result class with all the information user has converted
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrencyActivity.this, CurrencyResult.class);
                intent.putStringArrayListExtra("conversions", conversions);
                startActivity(intent);
            }
        });

    }

    /**
     * When the user clicks on either of these items set up at the top of the menu bar. They will be
     * sent to either one of the activities we have created in our project.
     * @param item The menu item that was selected.
     *
     * @return user will be sent to whatever activity they have pressed on. false otherwise
     */
        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.currencyHelp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyActivity.this);
            builder.setTitle(R.string.info_title);
            builder.setMessage(R.string.info_message);
            builder.setPositiveButton(R.string.ok_button, (dialog, cl) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (item.getItemId() == R.id.currencyToBear) {
        Intent bear = new Intent(CurrencyActivity.this, BearGeneratorActivity.class);
        CurrencyActivity.this.startActivity(bear);

        }
        else if (item.getItemId() == R.id.currencyToTrivia) {
            Intent trivia = new Intent(CurrencyActivity.this, TriviaActivity.class);
            CurrencyActivity.this.startActivity(trivia);

        } else if (item.getItemId() == R.id.currencyToFlightTracker) {
            Intent airTracker = new Intent(CurrencyActivity.this, AirportDisplayBoardActivity.class);
            CurrencyActivity.this.startActivity(airTracker);
        }
        return true;
    }

    /**
     * This will inflate the toolbar we have created
     * @param menu The options menu in which you place your items.
     *
     * @return true for the menu to display, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.currency_toolbar_menu, menu);

        return true;
    }


    /**
     * Handles actions required when the activity is paused, such as saving the last conversion rate.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("currencyData", Context.MODE_PRIVATE);
        float xChange = prefs.getFloat("decimalValue", 0.0f);
        binding.inFrom.setText(String.valueOf(xChange));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("decimalValue", xChange);
        editor.apply();
    }
}




