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

public class AirportFragment extends Fragment {
    Airport selected;
    public ListView airportListView;
    AirportViewModel airportModel;

    public AirportFragment(AirportViewModel airportModel) {
        this.airportModel = airportModel;
    }

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
