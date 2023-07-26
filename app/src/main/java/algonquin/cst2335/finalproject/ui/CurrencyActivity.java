package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

public class CurrencyActivity extends AppCompatActivity {

    ActivityCurrencyBinding binding;
    private Button rbtn;
    private Button cBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        rbtn = binding.btnReset;
        cBtn = binding.btnConvert;


        SharedPreferences prefs = getSharedPreferences("currencyData", Context.MODE_PRIVATE);
        float xChange = prefs.getFloat("decimalValue", 0.0f);
        binding.inFrom.setText(String.valueOf(xChange));

        rbtn.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyActivity.this);
            builder.setTitle("Question:");
            builder.setMessage("Would you like to reset?");
            builder.setNegativeButton("No", (dialog, cl) -> {
            });
            builder.setPositiveButton("Yes", (dialog,cl) ->{
                Snackbar.make(rbtn,"You have reset the values",Snackbar.LENGTH_LONG)
                        .setAction("Undo",(click2)->{

                        })
                        .show();
            });
            builder.create().show();
        });

        cBtn.setOnClickListener(click ->{
            Toast.makeText(CurrencyActivity.this, "Converted!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("decimalValue", Float.parseFloat(binding.inFrom.getText().toString()));
            editor.apply();
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.item_1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyActivity.this);
            builder.setTitle(R.string.info_title);
            builder.setMessage(R.string.info_message);
            builder.setPositiveButton(R.string.ok_button, (dialog, cl) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my_menu, menu);

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




