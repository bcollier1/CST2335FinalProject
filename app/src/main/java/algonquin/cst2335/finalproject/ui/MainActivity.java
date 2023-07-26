package algonquin.cst2335.finalproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.flightTrackerButton.setOnClickListener( v -> {
            Intent airTracker = new Intent(MainActivity.this, AirportDisplayBoardActivity.class);
            MainActivity.this.startActivity(airTracker);
        });

        binding.bearButton.setOnClickListener( v -> {
            Intent bear = new Intent(MainActivity.this, BearGeneratorActivity.class);
            MainActivity.this.startActivity(bear);
        });

        binding.currencyExchangeButton.setOnClickListener( v -> {
            Intent currencyConverter = new Intent(MainActivity.this, CurrencyActivity.class);
            MainActivity.this.startActivity(currencyConverter);
        });

        binding.triviaButton.setOnClickListener( v -> {
            Intent trivia = new Intent(MainActivity.this, TriviaActivity.class);
            MainActivity.this.startActivity(trivia);
        });

    }
}