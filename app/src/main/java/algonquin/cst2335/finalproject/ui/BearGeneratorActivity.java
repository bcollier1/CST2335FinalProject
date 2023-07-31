package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityBearGeneratorUiBinding;
import algonquin.cst2335.finalproject.databinding.ActivityBearLoaderBinding;

public class BearGeneratorActivity extends AppCompatActivity {

    protected RecyclerView.Adapter myAdapter;
    protected RecyclerView recyclerView;
    ActivityBearGeneratorUiBinding binding;
    protected ImageButton uploadBTN;
    protected ImageView bear;
    protected EditText width;
    protected EditText height;
    final List<String> imageUrls = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
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
        width = binding.widthInput;
        height = binding.heightInput;
        uploadBTN = binding.newImage;

        uploadBTN.setOnClickListener(clk ->{
            String widthStr = binding.widthInput.getText().toString();
            String heightStr = binding.heightInput.getText().toString();
            editor.putString("Width", widthStr);
            editor.putString("Height", heightStr);
            editor.apply();
            String imageUrl = "https://placebear.com/" + widthStr + "/" + heightStr;
            bear = new ImageView(this);
            loadImage(imageUrl, bear);
            imageUrls.add(imageUrl);
            myAdapter.notifyDataSetChanged();
            if(bear != null){
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
                ActivityBearLoaderBinding bearLoaderBinding = ActivityBearLoaderBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(bearLoaderBinding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String imageUrl = imageUrls.get(position);
                loadImage(imageUrl, holder.imageView);
            }

            @Override
            public int getItemCount() {
                return imageUrls.size();
            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadImage(String imageUrl, ImageView bear) {
        // Load the image into the ImageView using the Volley response
        RequestQueue queue = Volley.newRequestQueue(this);
        // Set the loaded image bitmap to the ImageView
        ImageRequest request = new ImageRequest(imageUrl,
                response -> {
                    // Set the loaded image to the ImageView
                    bear.setImageBitmap(response);
                },
                0, 0, ImageView.ScaleType.CENTER_INSIDE, null,
                error -> {
                    // Handle error if image retrieval fails
                    Toast.makeText(BearGeneratorActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                });
        queue.add(request);
    }


    @SuppressLint("NotifyDataSetChanged")
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
            imageUrls.clear();
            myAdapter.notifyDataSetChanged();
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
        ImageView imageView;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bear);
        }
    }
}