package algonquin.cst2335.finalproject.ui;

/*
 * Filename: BearGeneratorActivity.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: July 31, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Bear;
import algonquin.cst2335.finalproject.data.BearDAO;
import algonquin.cst2335.finalproject.data.BearDatabase;
import algonquin.cst2335.finalproject.data.BearViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityBearLoaderBinding;
import algonquin.cst2335.finalproject.databinding.ActivityBearGeneratorUiBinding;

/**
 * This class represents the main activity for the Bear Image Generator app.
 * It allows users to generate bear images with specified width and height,
 * view the generated images, and manage the images in the gallery.
 *
 * @author bradycollier
 */
public class BearGeneratorActivity extends AppCompatActivity {

    /**
     * Adapter for the RecyclerView that displays bear images.
     */
    protected RecyclerView.Adapter myAdapter;
    /**
     * RecyclerView used to display bear images.
     */
    protected RecyclerView recyclerView;
    /**
     * Binding object for the layout of the BearGeneratorActivity.
     */
    ActivityBearGeneratorUiBinding binding;
    /**
     * Button to upload a new bear image.
     */
    protected ImageButton uploadBTN;
    /**
     * Button to open the bear image gallery.
     */
    protected ImageButton galleryBTN;
    /**
     * ImageView used to display a bear image.
     */
    protected ImageView bear;

    /**
     * EditText for entering the width of the bear image.
     */
    protected EditText width;

    /**
     * EditText for entering the height of the bear image.
     */
    protected EditText height;

    /**
     * List of Bear objects representing the image URLs and dimensions.
     */
    protected ArrayList<Bear> imageUrls;

    /**
     * Database instance for bear images.
     */
    BearDatabase bearDB;

    /**
     * Data Access Object for accessing bear images in the database.
     */
    BearDAO bearDAO;

    /**
     * ViewModel for managing bear image data and UI interactions.
     */
    BearViewModel bearModel;
    List<Bear> deletedImageBackup = new ArrayList<>();

    /**
     * Called when the activity is first created. This method sets up the user interface,
     * initializes database and ViewModel, handles user interactions, and manages the image gallery.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Main
        super.onCreate(savedInstanceState);
        binding = ActivityBearGeneratorUiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // DB setup
        bearDB = Room.databaseBuilder(getApplicationContext(), BearDatabase.class, "bear").build();
        bearDAO = bearDB.bearDAO();
        bearModel = new ViewModelProvider(this).get(BearViewModel.class);

        // SharedPrefs
        SharedPreferences prefs = getSharedPreferences("Bear_SharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Context context = getApplicationContext();
        binding.widthInput.setText(prefs.getString("Width",""));
        binding.heightInput.setText(prefs.getString("Height",""));

        // ToolBar
        setSupportActionBar(binding.toolBar);

        // Variables
        recyclerView = binding.recyclerView;
        width = binding.widthInput;
        height = binding.heightInput;
        uploadBTN = binding.newImage;
        galleryBTN = binding.imageLibrary;
        imageUrls = new ArrayList<>();

        // Load stored image URLs from sharedPrefs
        Set<String> storedImageUrls = prefs.getStringSet("ImageUrls", new HashSet<>());
        for (String imageUrl : storedImageUrls) {
            Bear bear = new Bear(imageUrl, binding.widthInput.getText().toString(), binding.heightInput.getText().toString()); //
            imageUrls.add(bear);
        }

        // Observe changes in the selectedImage LiveData within the ViewModel
        bearModel.selectedImage.observe(this, newImage ->{
            // Check if a new image has been selected
            if(newImage != null){
                // Create a new instance of BearGeneratorFragment with the selected image data
                BearGeneratorFragment detailsFragment = new BearGeneratorFragment(newImage);

                // Get the FragmentManager to manage fragment transactions
                FragmentManager fgmt = getSupportFragmentManager();

                // Begin a new FragmentTransaction
                FragmentTransaction tx = fgmt.beginTransaction();

                // Add the transaction to the back stack for navigation
                tx.addToBackStack("");

                // Replace the existing fragment with the detailsFragment
                tx.replace(R.id.frameView, detailsFragment);

                // Commit the transaction to apply the changes
                tx.commit();
            }
        });

        // Retrieve the list of image URLs from the ViewModel
        imageUrls = bearModel.bearImages.getValue();

        // Check if the list of image URLs is null
        if(imageUrls == null) {

            // Initialize a new ArrayList for image URLs and set it in the ViewModel
            bearModel.bearImages.setValue(imageUrls = new ArrayList<>());

            // Create a background thread to fetch all images from the database
            Executor thread = Executors.newSingleThreadExecutor();

            // Fetch all images from the database and add them to the imageUrls list
            thread.execute(() -> imageUrls.addAll(bearDAO.getAllImages()));

            // Update the RecyclerView on the UI thread and set the adapter for the RecyclerView
            runOnUiThread(() -> binding.recyclerView.setAdapter(myAdapter));
        }
        // Set a click listener for the upload button
        uploadBTN.setOnClickListener(clk ->{
            // Get input information from the EditText fields
            String widthStr = binding.widthInput.getText().toString();
            String heightStr = binding.heightInput.getText().toString();

            // Construct the image URL based on the input dimensions
            String imageUrl = "https://placebear.com/" + widthStr + "/" + heightStr;

            // Update SharedPreferences editor with width and height values
            editor.putString("Width", widthStr);
            editor.putString("Height", heightStr);

            // Create a new ImageView and load the image from the URL
            bear = new ImageView(this);
            loadImage(imageUrl, bear);

            // Create a new Bear instance to store image details
            Bear bearImage = new Bear(imageUrl, widthStr, heightStr);
            bearImage.name = imageUrl;

            // Add the new Bear instance to the list of image URLs
            imageUrls.add(bearImage);

            // Update local cache by converting Bear instances to a string set
            Set<String> updatedImageUrls = new HashSet<>();
            for (Bear bear : imageUrls){
                updatedImageUrls.add(bear.getName());
            }
            editor.putStringSet("ImageUrls", updatedImageUrls);
            editor.apply();

            // Insert the new Bear instance into the database using a background thread
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> bearImage.id = bearDAO.insertImage(bearImage));

            // Notify the adapter to update the RecyclerView
            myAdapter.notifyDataSetChanged();

            // Show a Toast message indicating upload success or failure
            if(bear != null){
                Toast toast = Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        galleryBTN.setOnClickListener(clk -> {
            // Create a new intent
            Intent gallery = new Intent(BearGeneratorActivity.this, BearGeneratorGallery.class);

            // Go-to the image gallery for all locally saved images
            startActivity(gallery);
        });

        //Setup recycleview
        recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             * Called when the RecyclerView needs a new ViewHolder to represent an item.
             *
             * @param parent   The parent ViewGroup that the ViewHolder will be attached to.
             * @param viewType The type of the new View.
             * @return A new instance of MyRowHolder that holds the item View.
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ActivityBearLoaderBinding bearLoaderBinding = ActivityBearLoaderBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(bearLoaderBinding.getRoot());
            }

            /**
             * Returns the type of the view at the specified position for the purposes of view recycling.
             *
             * @param position The position of the item.
             * @return The view type of the item at the given position.
             */
            public int getItemViewType(int position){
                return 1;
            }

            /**
             * Called by the RecyclerView to display data at a specified position.
             *
             * @param holder   The ViewHolder to update for the contents of the item at the given position.
             * @param position The position of the item in the data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                Bear bear = imageUrls.get(position);
                loadImage(bear.getName(), holder.imageView);
            }

            /**
             * Returns the total number of items in the data set held by the adapter.
             *
             * @return The total number of items.
             */
            @Override
            public int getItemCount() {
                return imageUrls.size();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    /**
     * ViewHolder class to hold and display each bear image item in the RecyclerView.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        /**
         * Constructor to initialize the ViewHolder with the item View.
         *
         * @param itemView The item View to display.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            // Set a click listener on the item View to handle selection
            itemView.setOnClickListener(clk -> {
                // Get the absolute adapter position of the clicked item
                int index = getAbsoluteAdapterPosition();
                // Post the selected image URL using LiveData
                bearModel.selectedImage.postValue(imageUrls.get(index));
            });
            // Find and initialize the ImageView within the item View
            imageView = itemView.findViewById(R.id.bear);
        }
    }
    /**
     * Loads an image from the given URL and sets it into the specified ImageView.
     *
     * @param imageUrl The URL of the image to load.
     * @param bear     The ImageView in which to display the loaded image.
     */
    public void loadImage(String imageUrl, ImageView bear) {
        // Create a RequestQueue using Volley for managing network requests
        RequestQueue queue = Volley.newRequestQueue(this);
        // Create an ImageRequest for loading the image
        ImageRequest request = new ImageRequest(imageUrl,
                bear::setImageBitmap,
                0, 0, ImageView.ScaleType.CENTER_INSIDE, null,
                error -> {
                    // Display a Toast message in case of loading failure
                    Toast.makeText(BearGeneratorActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                });
        // Add the ImageRequest to the RequestQueue to initiate loading
        queue.add(request);
    }

    /**
     * Handles the selection of options from the toolbar menu.
     *
     * @param item The selected menu item.
     * @return True if the menu item selection is handled successfully, false otherwise.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

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

        else if (item.getItemId() == R.id.trashBin) {
            // Create an AlertDialog to confirm image deletion
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Delete all images?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // Backup the image URLs to be deleted
                        List<Bear> deletedImages = new ArrayList<>(imageUrls);

                        // Backup the deleted images before clearing
                        deletedImageBackup.addAll(deletedImages);

                        // Clear the local data source and update the RecyclerView
                        imageUrls.clear();
                        myAdapter.notifyDataSetChanged();

                        // Remove the SharedPreferences entries
                        SharedPreferences prefs = getSharedPreferences("Bear_SharedPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove("ImageUrls");
                        editor.remove("Width");
                        editor.remove("Height");
                        editor.apply();

                        // Delete all images from the database
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            bearDAO.deleteAllImages();

                            // Show a Snackbar with an "Undo" action
                            Snackbar.make(recyclerView, "Deleted all images from Database and Local Cache", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        // Restore the deleted images to the database
                                        Executor restoreThread = Executors.newSingleThreadExecutor();
                                        restoreThread.execute(() -> {
                                            try {
                                                for (Bear deletedImage : deletedImageBackup) {
                                                    bearDAO.insertImage(deletedImage);
                                                }

                                                // Update the UI with the restored data
                                                runOnUiThread(() -> {
                                                    imageUrls.addAll(deletedImageBackup);
                                                    myAdapter.notifyDataSetChanged();

                                                    // Clear the deleted image backup
                                                    deletedImageBackup.clear();

                                                    // Indicate restoration with a Toast
                                                    Toast.makeText(this, "Images restored", Toast.LENGTH_SHORT).show();
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                    })
                                    .show();
                        });
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        }

        return true;
    }

    /**
     * Inflates the options menu for the activity.
     *
     * @param menu The menu to inflate.
     * @return True if the menu is successfully inflated, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.bear_generator_menu, menu);

        return true;
    }
}