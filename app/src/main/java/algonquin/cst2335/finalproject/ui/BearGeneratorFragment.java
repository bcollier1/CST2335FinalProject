package algonquin.cst2335.finalproject.ui;

/*
 * Filename: BearGeneratorActivity.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: August 2, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import algonquin.cst2335.finalproject.data.Bear;
import algonquin.cst2335.finalproject.databinding.ActivityBearFragmentBinding;

/**
 * A fragment to display details about a specific bear image and provide an option to save it to the gallery.
 *
 * @author bradycollier
 */
public class BearGeneratorFragment extends Fragment {

    /**
     * Represents a bear image with its associated properties.
     */
    Bear image;

    /**
     * Constructs a new BearGeneratorFragment instance.
     *
     * @param toShow The bear image details to display.
     */
    public BearGeneratorFragment(Bear toShow){
        image = toShow;
    }

    /**
     * Called when the fragment's view is being created. Inflates the layout, sets up UI elements,
     * and loads the bear image details and image from the provided {@link Bear} object.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param parent             The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment.
     * @return The root view of the fragment's layout.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        ActivityBearFragmentBinding binding = ActivityBearFragmentBinding.inflate(inflater, parent, false);

        // Set the text views with image details
        binding.imageID.setText("ID: " + image.id);
        binding.imageName.setText("Image URL: " + image.name);
        binding.imageWidth.setText("Width: " + image.width + "px");
        binding.imageHeight.setText("Height: " + image.height + "px");

        // Load the image preview using the provided URL and ImageView
        loadImageFromUrl(binding.imagePreview, image.name);

        // Get the save button from the binding
        Button saveButton = binding.saveButton;

        // Set a click listener on the save button and save the image preview to the device's gallery
        saveButton.setOnClickListener(v -> saveImageToGallery(requireContext(), binding.imagePreview));

        // Return the root view of the inflated layout
        return binding.getRoot();
    }

    /**
     * Loads an image from a URL and displays it in the specified ImageView.
     *
     * @param imageView The ImageView to display the loaded image.
     * @param imageUrl  The URL of the image to load.
     */
    private void loadImageFromUrl(ImageView imageView, String imageUrl) {
        // Start a new background thread for image loading
        new Thread(() -> {
            try {
                // Create a URL object from the provided image URL string
                URL url = new URL(imageUrl);

                // Open an input stream to fetch the image data
                InputStream inputStream = url.openStream();

                // Decode the input stream into a Bitmap
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Update the UI on the main thread using a Handler
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Check if the Bitmap was successfully decoded
                    if (bitmap != null) {
                        // Set the Bitmap to the ImageView
                        imageView.setImageBitmap(bitmap);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Saves the image displayed in the ImageView to the device's gallery.
     *
     * @param context   The application context.
     * @param imageView The ImageView containing the image to be saved.
     */
    private void saveImageToGallery(Context context, ImageView imageView) {
        // Enable drawing cache
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        // Create a Bitmap for the drawing cache
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Create a Canvas object to draw the view onto the bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the contents of the ImageView onto the canvas
        imageView.draw(canvas);

        // Generate a unique display name for the saved image
        String displayName = "bear_image_" + System.currentTimeMillis() + ".jpg";
        try {
            // Create a FileOutputStream to write the image to the app's private storage
            FileOutputStream fOut = context.openFileOutput(displayName, Context.MODE_PRIVATE);

            // Compress and save the bitmap as a PNG image
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            // Show a Toast message indicating successful or failed saving
            if(bitmap != null){
                Toast toast = Toast.makeText(context, "Saved " + displayName + " Successfuly!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(context, "Failed to save " + displayName, Toast.LENGTH_SHORT);
                toast.show();
            }

            // Flush and close the FileOutputStream
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
