package algonquin.cst2335.finalproject.data;

import android.widget.ListView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * The ChatRoomViewModel class represents the ViewModel for a chat room.
 * It holds the MutableLiveData object for storing and observing the list of chat messages.
 */
public class AirportViewModel extends ViewModel {
    /**
     * The MutableLiveData object that holds the list of chat messages.
     * It allows observers to be notified of any changes to the list.
     */
    public MutableLiveData<List<Airport>> airports = new MutableLiveData<>();

    public MutableLiveData<ListView> viewInitiated = new MutableLiveData< >();
    public MutableLiveData<Airport> airportSelected = new MutableLiveData< >();
}
