package algonquin.cst2335.finalproject.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * The ChatRoomViewModel class represents the ViewModel for a chat room.
 * It holds the MutableLiveData object for storing and observing the list of chat messages.
 */
public class FlightViewModel extends ViewModel {
    /**
     * The MutableLiveData object that holds the list of chat messages.
     * It allows observers to be notified of any changes to the list.
     */
    public MutableLiveData<List<Flight>> flights = new MutableLiveData<>();

    public MutableLiveData<Flight> selectedFlight = new MutableLiveData<>();
}
