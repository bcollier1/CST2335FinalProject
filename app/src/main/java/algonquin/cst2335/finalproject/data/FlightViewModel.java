package algonquin.cst2335.finalproject.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * The FlightViewModel class represents the Flight ViewModel.
 */
public class FlightViewModel extends ViewModel {
    /**
     * A MutableLiveData object that Holds the list of Flight.
     * Observers to be notified of any changes to the list.
     */
    public MutableLiveData<List<Flight>> flights = new MutableLiveData<>();

    /**
     * A MutableLiveData object that holds the Flight object.
     * Observers to be notified of any changes.
     */
    public MutableLiveData<Flight> selectedFlight = new MutableLiveData<>();
}
