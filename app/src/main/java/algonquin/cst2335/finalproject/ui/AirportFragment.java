package algonquin.cst2335.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Airport;
import algonquin.cst2335.finalproject.data.AirportViewModel;
import algonquin.cst2335.finalproject.databinding.AirportFragmentBinding;

/**
 * Fragment for displaying information related to airports.
 */
public class AirportFragment extends Fragment {
    /**
     * The selected airport.
     */
    Airport selected;

    /**
     * The ListView for displaying airports.
     */
    public ListView airportListView;

    /**
     * The ViewModel for managing airport data.
     */
    AirportViewModel airportModel;

    /**
     * Constructs a new AirportFragment
     *
     * @param airportModel The ViewModel to be used.
     */
    public AirportFragment(AirportViewModel airportModel) {
        this.airportModel = airportModel;
    }

    /**
     * Default method when the default view is created
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     *
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        AirportFragmentBinding binding = AirportFragmentBinding.inflate(inflater);

        airportListView = binding.airportListView;
//        binding.msgFrag.setText(selected.getMessage());
//        binding.msgTimeFrag.setText(selected.getTimeSent());
//        binding.msgIdFrag.setText("ID = " + selected.id);
        return binding.getRoot();
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * Assigns the ListView to the view identified by the id, then posts the ListView to the ViewModel's LiveData.
     *
     * @param view               The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        airportListView = view.findViewById(R.id.airport_list_view);
        airportModel.viewInitiated.postValue(airportListView);
    }


//    public AirportFragment(Airport msg){
//        selected = msg;
//    }
}
