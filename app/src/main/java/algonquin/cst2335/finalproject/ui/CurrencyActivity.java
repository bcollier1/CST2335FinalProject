package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.finalproject.BuildConfig;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

public class CurrencyActivity extends AppCompatActivity {

    ActivityCurrencyBinding binding;
    private Button rbtn;
    private Button cBtn;

    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        rbtn = binding.btnReset;
        cBtn = binding.btnConvert;
        queue = Volley.newRequestQueue(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFromCurrency.setAdapter(adapter);
        binding.spToCurrency.setAdapter(adapter);



        /*cBtn.setOnClickListener(click ->{

            String sourceCurrency = binding.spFromCurrency.getSelectedItem().toString();
            String targetCurrency = binding.spToCurrency.getSelectedItem().toString();
            String amount = binding.inFrom.getText().toString();

            if (sourceCurrency.isEmpty() || targetCurrency.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please select both currencies and enter an amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "BuildConfig.API_URL + \"&from=\" + sourceCurrency + \"&to=\" + targetCurrency + \"&amount=\" + amount + \"&api_key=\" + BuildConfig.API_KEY + \"&format=json\";";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {  try {
                        JSONObject ratesObject = response.getJSONObject("rates");
                        JSONObject targetCurrencyObject = ratesObject.getJSONObject(targetCurrency);
                        String rateForAmount = targetCurrencyObject.getString("rate_for_amount");
                        binding.cResult.setText(rateForAmount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }  },
                    (error) -> {
                Toast.makeText(this, "An error occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            });
            queue.add(request);

        });

     */

        SharedPreferences prefs = getSharedPreferences("currencyData", Context.MODE_PRIVATE);
        float xChange = prefs.getFloat("decimalValue", 0.0f);
        binding.inFrom.setText(String.valueOf(xChange));

        rbtn.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyActivity.this);
            builder.setTitle("Question:");
            builder.setMessage("Would you like to reset?");
            builder.setNegativeButton("No", (dialog, cl) -> {
            });
            builder.setPositiveButton("Yes", (dialog, cl) -> {
                Snackbar.make(rbtn, "You have reset the values", Snackbar.LENGTH_LONG)
                        .setAction("Undo", (click2) -> {

                        })
                        .show();
            });
            builder.create().show();
        });

        /*
        cBtn.setOnClickListener(click ->{
            Toast.makeText(CurrencyActivity.this, "Converted!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("decimalValue", Float.parseFloat(binding.inFrom.getText().toString()));
            editor.apply();
        });
    }

  */

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
                            binding.cResult.setText(resultString);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putFloat("decimalValue", Float.parseFloat(rateForAmount));
                            editor.apply();
                            Toast.makeText(this, "Converted!", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(this, "An error occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show());

            queue.add(request);
        });
    }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.currency_toolbar_menu, menu);

        return true;
    }


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




