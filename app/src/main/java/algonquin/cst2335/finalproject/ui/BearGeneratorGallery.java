package algonquin.cst2335.finalproject.ui;

/*
 * Filename: BearGeneratorGallery.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: August 7, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityBearGalleryBinding;
import algonquin.cst2335.finalproject.databinding.ActivityBearLoaderBinding;

/**
 * This activity displays a gallery of saved bear images from the app's internal storage.
 * Users can view and delete the saved images using a RecyclerView.
 *
 * @author bradycollier
 */
public class BearGeneratorGallery extends AppCompatActivity {
    /**
     * The binding instance for the layout of the Bear Generator Gallery activity.
     */
    ActivityBearGalleryBinding binding;

    /**
     * The custom adapter used to populate and manage the images in the RecyclerView.
     */
    protected MyRowHolder imageAdapter;

    /**
     * The RecyclerView that displays a list of images in the Bear Generator Gallery.
     */
    protected RecyclerView recyclerView;

    /**
     * A list that stores the file paths of the images to be displayed in the RecyclerView.
     * Initially, it's initialized as an empty ArrayList.
     */
    ArrayList<String> imagePaths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBearGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);

        // Initialize RecyclerView and adapter
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageAdapter = new MyRowHolder(this, imagePaths);
        recyclerView.setAdapter(imageAdapter);

        // Load saved image paths
        loadSavedImagePaths();
    }

    /**
     * Loads the list of saved image file names from the app's internal storage.
     * Populates the {@code imagePaths} list with the saved image paths.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void loadSavedImagePaths() {
        // Get the list of files in the app's private storage
        String[] fileList = fileList();

        // Iterate through the list of files
        for (String filename : fileList) {
            // Check if the filename starts with the specified prefix
            if (filename.startsWith("bear_image_")) {
                // Add the filename (image path) to the imagePaths list
                imagePaths.add(filename);
            }
        }
        // Notify the imageAdapter to update the RecyclerView
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * Adapter for managing the image items in the RecyclerView.
     */
    class MyRowHolder extends RecyclerView.Adapter<MyRowHolder.ImageViewHolder> {
        /**
         * The application context, providing access to application-specific resources and methods.
         */
        Context context;
        /**
         * A list that stores the file paths of the images displayed in the RecyclerView.
         */
        ArrayList<String> imagePaths;

        /**
         * Constructs a MyRowHolder instance.
         *
         * @param context    The context.
         * @param imagePaths List of image paths.
         */
        public MyRowHolder(Context context, ArrayList<String> imagePaths) {
            this.context = context;
            this.imagePaths = imagePaths;
        }

        /**
         * Deletes the image at the specified position from internal storage and updates the adapter.
         *
         * @param position The position of the image to be deleted.
         */
        public void deleteImage(int position) {
            // Get the image name (path) at the specified position
            String imageName = imagePaths.get(position);

            // Attempt to delete the file corresponding to the image name
            if (context.deleteFile(imageName)) {
                // If the file deletion was successful:
                // Remove the image path from the list
                imagePaths.remove(position);

                // Notify the adapter that an item has been removed at the specified position
                notifyItemRemoved(position);

                // Notify the adapter that the data set has changed in the range after the removed item
                notifyItemRangeChanged(position, imagePaths.size() - position);
            }
        }

        /**
         * Creates a new ImageViewHolder.
         *
         * @param parent   The parent view group.
         * @param viewType The view type.
         * @return The created ImageViewHolder.
         */
        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ActivityBearLoaderBinding bearLoaderBinding = ActivityBearLoaderBinding.inflate(getLayoutInflater(), parent, false);
            return new ImageViewHolder(bearLoaderBinding.getRoot());
        }

        /**
         * Binds data to the ImageViewHolder.
         *
         * @param holder   The ImageViewHolder.
         * @param position The position of the item.
         */
        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            // Get the image name (path) at the current position
            String imageName = imagePaths.get(position);

            // Create the complete image path by combining the files directory and image name
            String imagePath = getFilesDir() + "/" + imageName;

            // Decode the image file into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            // Set the Bitmap to the ImageView in the ViewHolder
            holder.imageView.setImageBitmap(bitmap);

            // Set a click listener on the ImageView to handle deletion confirmation
            holder.imageView.setOnClickListener(view -> {
                // Create an AlertDialog to confirm image deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete this image?")
                        .setPositiveButton("Delete", (dialog, which) -> deleteImage(position))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
            });
        }

        /**
         * Returns the number of images.
         *
         * @return The number of images.
         */
        @Override
        public int getItemCount() {
            return imagePaths.size();
        }

        /**
         * ViewHolder class for holding image views.
         */
        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            /**
             * Constructs an ImageViewHolder instance.
             *
             * @param itemView The item view.
             */
            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.bear);
            }
        }
    }

    /**
     * Handles the selection of options in the options menu.
     *
     * @param item The selected menu item.
     * @return {@code true} if the menu item selection is handled successfully, otherwise {@code false}.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String words;

        if(item.getItemId() == R.id.airportPage){
            Intent airTracker = new Intent(BearGeneratorGallery.this, AirportDisplayBoardActivity.class);
            BearGeneratorGallery.this.startActivity(airTracker);
        }

        else if(item.getItemId() == R.id.currencyPage){
            Intent airTracker = new Intent(BearGeneratorGallery.this, CurrencyActivity.class);
            BearGeneratorGallery.this.startActivity(airTracker);
        }

        else if(item.getItemId() == R.id.triviaPage){
            Intent airTracker = new Intent(BearGeneratorGallery.this, TriviaActivity.class);
            BearGeneratorGallery.this.startActivity(airTracker);
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
            AlertDialog.Builder infoBuilder = new AlertDialog.Builder(BearGeneratorGallery.this);
            infoBuilder.setMessage(message)
                    .setTitle("Welcome to CST2335 Final Project!")
                    .setNegativeButton("Close", ((dialog, cl) -> {
                    }))
                    .create().show();
        }
        else if(item.getItemId() == R.id.trashBin) {
            words = "Deleted all images";
            // Delete image files from app's internal storage
            for (String imagePath : imagePaths) {
                this.deleteFile(imagePath);
            }
            // Clear the local image paths list and notify adapter
            imagePaths.clear();
            imageAdapter.notifyDataSetChanged();

            Toast.makeText(this, words, Toast.LENGTH_LONG).show();
        }
        return true;
    }

    /**
     * Creates the options menu.
     *
     * @param menu The menu to be created.
     * @return {@code true} if the menu is successfully created, otherwise {@code false}.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bear_generator_menu, menu);
        return true;
    }
}
