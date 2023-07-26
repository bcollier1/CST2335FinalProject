package algonquin.cst2335.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Airport;
import algonquin.cst2335.finalproject.data.AirportAdapter;
import algonquin.cst2335.finalproject.data.AirportDAO;
import algonquin.cst2335.finalproject.data.AirportDatabase;
import algonquin.cst2335.finalproject.data.AirportViewModel;
import algonquin.cst2335.finalproject.data.Flight;
import algonquin.cst2335.finalproject.data.FlightDAO;
import algonquin.cst2335.finalproject.data.FlightDatabase;
import algonquin.cst2335.finalproject.data.FlightPOJO;
import algonquin.cst2335.finalproject.data.FlightViewModel;
import algonquin.cst2335.finalproject.databinding.ActivityAirportDisplayBoardBinding;
import algonquin.cst2335.finalproject.util.Constants;
import algonquin.cst2335.finalproject.util.DataUtils;

/**
 * This Activity is used to manage the display of airport and flight information.
 * The UI is set up with different fragments and RecyclerViews to display data.
 */
public class AirportDisplayBoardActivity extends AppCompatActivity {
    /**
     * Boolean value to indicate testing without API.
     */
    boolean isTestNoAPI = true;
    /**
     * Binding for this activity.
     */
    ActivityAirportDisplayBoardBinding binding;

    /**
     * Flight Data Access Object.
     */
    public static FlightDAO flightDAO;
    /**
     * Airport Data Access Object.
     */
    AirportDAO airportDAO;

    /**
     * List of Flights.
     */
    List<Flight> flights;
    /**
     * Temporary list of flights.
     */
    List<Flight> flightsTemp = new ArrayList<>();

    /**
     * ViewModel for Flight.
     */
    FlightViewModel flightModel;
    /**
     * ViewModel for Airport.
     */
    AirportViewModel airportModel;

    /**
     * Adapter for Flight List.
     */
    public static RecyclerView.Adapter flightListAdapter;
    /**
     * Adapter for Airport.
     */
    AirportAdapter airportAdapter;

    /**
     * Executor for Flight Thread.
     */
    public static Executor flightThread = Executors.newSingleThreadExecutor();

    /**
     * Boolean value to indicate status of star button.
     */
    boolean starBtnOn = false;

    /**
     * Fragment for Airport.
     */
    AirportFragment airportFragment;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState the Bundle contains the data it most recently recovered or it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialization UI
        super.onCreate(savedInstanceState);
        binding = ActivityAirportDisplayBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarAirport);

        SharedPreferences prefs = getSharedPreferences("AirportDisplayBoard", Context.MODE_PRIVATE);
        String searchedCity = prefs.getString("city", "");

        Executor thread = Executors.newSingleThreadExecutor();

        //Init DB for airports from local Json file
        AirportDatabase airportDB = Room.databaseBuilder(getApplicationContext(), AirportDatabase.class, "airport").build();
        airportDAO = airportDB.airportDAO();
        airportModel = new ViewModelProvider(this).get(AirportViewModel.class);

        thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            String data_airport = DataUtils.getJsonFromAsset(getApplicationContext(), Constants.DATA_AIRPORT);
            Airport[] airports = DataUtils.getObjArrayFromJson(null, data_airport, Airport[].class);
            airportDAO.deleteAllAirport();
            airportDAO.insertAirports(airports);
            List<Airport> allAirports = airportDAO.getAllAirports();
            airportModel.airports.postValue(allAirports);
        });

        FlightDatabase flightDB = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "flight").build();
        flightDAO = flightDB.flightDAO();
        flightModel = new ViewModelProvider(this).get(FlightViewModel.class);

        //Init search view for airports
        switchToAirportFrag();

        //Setup observers
        flightModel.selectedFlight.observe(this, (newMessageValue) -> {
            FlightFragment chatFragment = new FlightFragment(newMessageValue);
            FragmentManager supportFragmentManager = getSupportFragmentManager();

            supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    flightThread.execute(() -> {
                        showHideFav(!starBtnOn, false);
                        showHideFav(!starBtnOn, false);
                    });
                }
            });

            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentLocation, chatFragment);
            transaction.addToBackStack("");
            transaction.commit();
        });

        airportModel.viewInitiated.observe(this, (newMessageValue) -> {
            ListView airportListView = newMessageValue;

            binding.searchView.setActivated(true);
            if (StringUtils.isNotBlank(searchedCity)) {
                binding.searchView.setQueryHint("Last Searched: " + searchedCity);
            } else {
                binding.searchView.setQueryHint("Type your keyword here");
            }
            binding.searchView.onActionViewExpanded();
            binding.searchView.setIconified(false);
            binding.searchView.clearFocus();

            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//                airportList = binding.airportListView;
                    // Pass results to ListViewAdapter Class
                    Context context = airportListView.getContext();
                    airportAdapter = new AirportAdapter(context, airportModel.airports.getValue());

                    // Binds the Adapter to the ListView
                    airportListView.setAdapter(airportAdapter);

                    String text = newText;
                    if (text != null && text.trim().length() > 1) {
                        airportAdapter.filter(text);
                    }

                    if (airportFragment == null) {
                        switchToAirportFrag();
                    }
                    return false;
                }
            });

            airportListView.setOnItemClickListener((parent, view, position, id) -> {
                Airport airport = (Airport) parent.getAdapter().getItem(position);
                airportModel.airportSelected.postValue(airport);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("city", airport.getCity());
                editor.apply();

                binding.searchView.setQueryHint(getString(R.string.AirportDisplayBoard_Hint_Last_Searched) + airport.getCity());

                onBackPressed();
                hideAirportFrag();

            });
        });

        airportModel.airportSelected.observe(this, (Airport newMessageValue) -> {
//            Toast.makeText(this, newMessageValue.getCode(), Toast.LENGTH_SHORT).show();
            if (isTestNoAPI) {
                initDbForFlights(null, null, false);
            } else {
                sendAndRequestResponse(newMessageValue.getIata());
            }
        });

        // Set up the flight list and its adapter
        binding.flightListView.setLayoutManager(new LinearLayoutManager(this));

        binding.flightListView.setAdapter(flightListAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            //Creates the view holder for a chat message item based on its view type.
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == -1) {
                    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item, parent, false);
                    return new MyRowHolder(inflate); // view holder for normal items
                } else if (viewType == 1) {
                    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_item, parent, false);
                    return new MyRowHolder(inflate); // view holder for header items
                }
                return null;
            }

            //Binds the chat message data to the view holder.
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                if (flights == null || flights.isEmpty()) {
                    return;
                }
                Flight flight = flights.get(position);
                holder.flight_number.setText(flight.getFlight_number());
                holder.airline.setText(flight.getAirline());
                holder.departure_iata.setText(flight.getDeparture_iata());
                holder.arrival_iata.setText(flight.getArrival_iata());
                String departGate = flight.getDeparture_airport_gate();
                if (StringUtils.isBlank(departGate)) {
                    departGate = "TBD";
                }
                holder.flight_gate.setText(departGate);

                if (flight.getDeparture_delay() > 0) {
                    holder.status_image.setImageResource(R.drawable.flight_delay);
                } else {
                    holder.status_image.setImageResource(R.drawable.flight_on_time);
                }
            }

            //Returns the total number of chat messages.
            @Override
            public int getItemCount() {
                if (flights == null) {
                    return 0;
                }
                return flights.size();
            }

            //Returns the view type of a chat message item.
            @Override
            public int getItemViewType(int position) {
//                Flight message = flights.get(position);
//                boolean isSendBtn = message.isSentButton();
//                if (isSendBtn) {
//                    return 0;
//                } else {
//                    return 1;
//                }
                return -1;
            }


        });

        binding.flightListView.setItemAnimator(null);

        binding.favBtn.setOnClickListener(v -> {
            if (flights == null || flights.isEmpty()) {
                Toast.makeText(this, "Please select an Airport first.", Toast.LENGTH_SHORT).show();
                return;
            }
            flightThread.execute(() -> {
                showHideFav(!starBtnOn, true);
            });
        });
    }

    /**
     * Inflates the options menu for this activity.
     *
     * @param menu The options menu in which you place your items.
     * @return boolean Return true to display the menu.
     */
    /**
     * Inflates the options menu for this activity.
     *
     * @param menu The options menu in which you place your items.
     * @return boolean Return true to display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.airport_toolbar_menu, menu);

        return true;
    }

    /**
     * Handles the selection of an item in the options menu.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_help) {
        }
        Toast.makeText(this, "Version 1.0, created by Tong Chi", Toast.LENGTH_SHORT).show();


        return true;
    }

    /**
     * Shows or hides the list of favorite flights.
     *
     * @param showFav   If true, shows the favorite flights, otherwise shows all flights.
     * @param showToast If true, shows a toast message.
     */
    public void showHideFav(boolean showFav, boolean showToast) {
        if (!showFav) {
            //Show all fav flights
            flights.clear();
            List<Flight> temp = flightDAO.getAllFlights();
            flights = temp;
            runOnUiThread(() -> {
                binding.flightListView.getRecycledViewPool().clear();
                flightListAdapter.notifyDataSetChanged();
//                binding.favBtn.setImageResource(R.drawable.star_on);
                if (showToast)
                    Toast.makeText(getApplicationContext(), "Showing All Flights.", Toast.LENGTH_SHORT).show();
            });
            starBtnOn = false;
        } else {
            //Show Favorite flights
            flights.clear();
            List<Flight> temp = flightDAO.getAllFavFlights();
            flights = temp;
            runOnUiThread(() -> {
                binding.flightListView.getRecycledViewPool().clear();
                flightListAdapter.notifyDataSetChanged();
//                binding.favBtn.setImageResource(R.drawable.star_off);
                if (showToast)
                    Toast.makeText(getApplicationContext(), "Showing Favorite Flights.", Toast.LENGTH_SHORT).show();
            });

            starBtnOn = true;
        }
    }

    /**
     * Sends a GET request to a specified URL and handles the response.
     *
     * @param code The code of the airport.
     */
    private void sendAndRequestResponse(String code) {
        RequestQueue requestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();

        String url = Constants.URL_DEPARTURE_FLIGHT_FROM_CODE + code;

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                initDbForFlights(response, code, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error in requesting data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    /**
     * Initializes the database for flights based on provided data.
     *
     * @param data_flights    The data of flights in JSON format.
     * @param airportIataCode The IATA code of the airport.
     * @param showFavOnly     If true, shows only favorite flights, otherwise shows all flights.
     */
    private void initDbForFlights(String data_flights, String airportIataCode, boolean showFavOnly) {
//        Executor thread = Executors.newSingleThreadExecutor();
        flightThread.execute(() -> {
            String jsonData = null;
            if (data_flights == null) {
                jsonData = DataUtils.getJsonFromAsset(getApplicationContext(), Constants.DATA_FLIGHTS);
            } else {
                jsonData = data_flights;
            }

//            FlightPOJO flightPOJO = DataUtils.getObjFromJson(data_flights, FlightPOJO.class);
            FlightPOJO flightPOJO = DataUtils.getObjFromJson(jsonData, FlightPOJO.class);
            Flight[] flights = DataUtils.getFlightDbObjsFromPojo(flightPOJO);

            flightDAO.deleteAllFlightsExceptFav();
            flightDAO.insertFlights(flights);
        });

        // Initialize the ViewModel for the flight details
        flights = flightModel.flights.getValue();
        if (flights == null || flights.size() == 0) {
            flightModel.flights.setValue(flights = new ArrayList<>());
            //Thread is a must otherwise the main activity crushes
//            thread = Executors.newSingleThreadExecutor();
            flightThread.execute(() -> {
                List<Flight> temp = new ArrayList<>();

                if (showFavOnly) {
                    temp = flightDAO.getAllFavFlights(); //Once you get the data from database
                } else {
                    temp = flightDAO.getAllFlights(); //Once you get the data from database
                }
                flights = temp;
                runOnUiThread(() -> binding.flightListView.setAdapter(flightListAdapter)); //You can then load the RecyclerView
            });
        }
    }

    /**
     * Hides the airport fragment.
     */
    private void hideAirportFrag() {
        if (airportFragment == null) {
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
//        transaction.remove(airportFragment);
        transaction.remove(airportFragment);
//        transaction.addToBackStack("");
        transaction.commit();
        airportFragment = null;
    }

    /**
     * Switches to the airport fragment.
     */
    private void switchToAirportFrag() {
        airportFragment = new AirportFragment(airportModel);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentLocation, airportFragment);
//        transaction.add(R.id.fragmentLocation, airportFragment);
//        transaction.show(airportFragment);
        transaction.addToBackStack("");
        transaction.commit();

//        airportListView.setOnItemClickListener((parent, view, position, id) -> {
//            int a = 0;
//            Airport item = (Airport) parent.getAdapter().getItem(position);
//            String name = item.getName();
//            String code = item.getCode();
//            Toast.makeText(view.getContext(), name,Toast.LENGTH_SHORT).show();
//        });

    }

    /**
     * A ViewHolder class for RecyclerView, used to represent a row in the list.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView flight_number;
        TextView airline;
        TextView departure_iata;
        TextView arrival_iata;
        TextView flight_gate;
        ImageView status_image;

        /**
         * RowHolder instance, also it sets up its views.
         *
         * @param itemView The view that holds the item views.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> {

                int position = getAbsoluteAdapterPosition();
                Flight selected = flights.get(position);

                flightModel.selectedFlight.postValue(selected);
            });

            // Initialize the messageText and timeText TextViews by finding their respective views in the itemView layout.
            flight_number = itemView.findViewById(R.id.flight_number);
            airline = itemView.findViewById(R.id.airline);
            departure_iata = itemView.findViewById(R.id.departure_iata);
            arrival_iata = itemView.findViewById(R.id.arrival_iata);
            flight_gate = itemView.findViewById(R.id.flight_gate);
            status_image = itemView.findViewById(R.id.status_image);
        }
    }
}

