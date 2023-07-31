package algonquin.cst2335.finalproject.data;

import android.widget.ListView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * The AirportViewModel class represents the ViewModel for an airport.
 */
public class AirportViewModel extends ViewModel {
    /**
     * A MutableLiveData object that Holds the list of airports.
     * Observers to be notified of any changes to the list.
     */
    public MutableLiveData<List<Airport>> airports = new MutableLiveData<>();

    /**
     * A MutableLiveData object that Holds the ListView object.
     * Observers to be notified of any changes.
     */
    public MutableLiveData<ListView> viewInitiated = new MutableLiveData< >();

    /**
     * A MutableLiveData object that Holds the Airport object.
     * Observers to be notified of any changes.
     */
    public MutableLiveData<Airport> airportSelected = new MutableLiveData< >();
}
