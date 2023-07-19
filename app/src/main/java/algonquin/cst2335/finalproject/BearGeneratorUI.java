package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.databinding.ActivityBearGeneratorUiBinding;

public class BearGeneratorUI extends AppCompatActivity {

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
        recyclerView = binding.recyclerView;

        binding.widthInput.setText(prefs.getString("Width",""));
        binding.heightInput.setText(prefs.getString("Height",""));

        uploadBTN = binding.newImage;
        deleteBTN = binding.garbageButton;

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

        deleteBTN.setOnClickListener(clk ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(BearGeneratorUI.this);
            builder.setMessage("Do you want to delete this image?")
                    .setTitle("Question:")
                    .setNegativeButton("No", (((dialog, which) -> {})))
                    .setPositiveButton("Yes", ((dialog, which) -> {
                        Snackbar.make(imageView,"Image deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Undo", cl->{})
                                .show();
                    }))
                    .create().show();
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

    protected static class MyRowHolder extends RecyclerView.ViewHolder {

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}