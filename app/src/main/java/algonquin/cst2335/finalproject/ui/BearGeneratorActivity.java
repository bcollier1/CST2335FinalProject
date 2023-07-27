package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityBearGeneratorUiBinding;

public class BearGeneratorActivity extends AppCompatActivity {

    protected RecyclerView.Adapter myAdapter;
    protected RecyclerView recyclerView;
    ActivityBearGeneratorUiBinding binding;
    protected ImageButton uploadBTN;
    protected ImageView imageView;
    protected ImageButton deleteBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBearGeneratorUiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Context context = getApplicationContext();
        setSupportActionBar(binding.toolBar);
        recyclerView = binding.recyclerView;

        binding.widthInput.setText(prefs.getString("Width",""));
        binding.heightInput.setText(prefs.getString("Height",""));

        uploadBTN = binding.newImage;

        uploadBTN.setOnClickListener(clk ->{
            String width = binding.widthInput.getText().toString();
            String height = binding.heightInput.getText().toString();
            editor.putString("Width", width);
            editor.putString("Height", height);
            editor.apply();
            if(imageView != null){
                Toast toast = Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            public int getItemViewType(int position){
                return position;
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String words = "";

        if(item.getItemId() == R.id.airportPage){
            Intent airTracker = new Intent(BearGeneratorActivity.this, AirportDisplayBoardActivity.class);
            BearGeneratorActivity.this.startActivity(airTracker);
        }

        else if(item.getItemId() == R.id.currencyPage){
            Intent airTracker = new Intent(BearGeneratorActivity.this, CurrencyActivity.class);
            BearGeneratorActivity.this.startActivity(airTracker);
        }

        else if(item.getItemId() == R.id.triviaPage){
            Intent airTracker = new Intent(BearGeneratorActivity.this, TriviaActivity.class);
            BearGeneratorActivity.this.startActivity(airTracker);
        }

        else if(item.getItemId() == R.id.aboutBear){
            String message = "Let's get started! In the toolbar you will see five different icons.\n" +
                    "Airplane - This button is linked to the Airplane Tracker. It will switch you from this" +
                    "current page to there, which you can track the current status of your airline.\n" +
                    "Currency - This button is linked to the Currency Converter. This page will allow you to" +
                    "input a currency of choice and convert to another countries local currency.\n" +
                    "Trivia - This button is linked to the Trivia Game. You will be able to test your knowledge" +
                    "with very skilled questions from a various of difficulty.\nTrash Can - Any image you "+
                    "generated will be deleted.\nInfo - Provides information of how the interface of this " +
                    "application works. On this page you will find two inputs; 'Width' and 'Height'. Enter "+
                    "a desired resolution size, and the application will retrieve a bear image from <html page>. "+
                    "The two buttons; 'Add' and 'Library' allows you to generate a new image and retrieve an "+
                    "that was already generated.";
            AlertDialog.Builder infoBuilder = new AlertDialog.Builder(BearGeneratorActivity.this);
            infoBuilder.setMessage(message)
                    .setTitle("Welcome to CST2335 Final Project!")
                    .setNegativeButton("Close", ((dialog, cl) -> {
                    }))

                    .create().show();
        }

        else if(item.getItemId() == R.id.trashBin) {
            words = "Test 2";
        }

        Toast.makeText(this, words, Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.bear_generator_menu, menu);

        return true;
    }

    protected static class MyRowHolder extends RecyclerView.ViewHolder {

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}