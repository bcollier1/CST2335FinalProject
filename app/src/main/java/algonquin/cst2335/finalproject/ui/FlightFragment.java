package algonquin.cst2335.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Date;

import algonquin.cst2335.finalproject.data.Flight;
import algonquin.cst2335.finalproject.databinding.FlightFragmentBinding;
import algonquin.cst2335.finalproject.util.DataUtils;

public class FlightFragment extends Fragment {
    Flight selected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FlightFragmentBinding binding = FlightFragmentBinding.inflate(inflater);

        binding.flightNumber.setText(selected.getFlight_number());
        binding.airline.setText(selected.getAirline());
        binding.departureIata.setText(selected.getDeparture_iata());
        binding.departureAirportName.setText(selected.getDeparture_airport_name());
        if (selected.getFav_date() == null) {
            binding.saveBtn.setText("Save Favorite");
        } else {
            binding.saveBtn.setText("Remove Favorite");
        }

        Date fav_date = selected.getFav_date();
        binding.saveBtn.setOnClickListener(v -> {
            if (selected.getFav_date() == null) {
                selected.setFav_date(DataUtils.getSqlDateFromUtilDate(new java.util.Date()));
                binding.saveBtn.setText("Remove Favorite");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Questions").setMessage("Do you want to delete the favorite: " + selected.getFlight_number()).setPositiveButton("Yes", ((dialog, which) -> {
                    Snackbar.make(binding.saveBtn, "You removed favorite flight #" + selected.getFlight_number(), Snackbar.LENGTH_LONG).setAction("Undo", click -> {
                        selected.setFav_date(fav_date);
                        binding.saveBtn.setText("Remove Favorite");
                        updateFlight();
                    }).show();
                    selected.setFav_date(null);
                    binding.saveBtn.setText("Save Favorite");
                    updateFlight();
                })).setNegativeButton("No", ((dialog, which) -> {
                })).create().show();

            }
            updateFlight();
        });
        return binding.getRoot();
    }

    private void updateFlight(){
        AirportDisplayBoardActivity.flightThread.execute(() -> {
            AirportDisplayBoardActivity.flightDAO.insertFlights(selected);
            getActivity().runOnUiThread(() -> {
                AirportDisplayBoardActivity.flightListAdapter.notifyDataSetChanged();
            });
        });
    }

    public FlightFragment(Flight msg) {
        selected = msg;
    }
}
