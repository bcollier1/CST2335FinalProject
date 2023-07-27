package algonquin.cst2335.finalproject.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.Flight;
import algonquin.cst2335.finalproject.databinding.FlightFragmentBinding;
import algonquin.cst2335.finalproject.util.DataUtils;

/**
 * Fragment used to display details of a specific flight.
 */
public class FlightFragment extends Fragment {
    /**
     * Selected flight data
     */
    private Flight selected;

    /**
     * Inflates the view for the FlightFragment.
     *
     * @param inflater           used to inflate any views in the fragment
     * @param container          container to be attached to
     * @param savedInstanceState previous state of the fragment
     *
     * @return view for the fragment's UI, or null
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FlightFragmentBinding binding = FlightFragmentBinding.inflate(inflater);
        String departGate = selected.getDeparture_airport_gate();
        if (StringUtils.isBlank(departGate)) {
            departGate = getString(R.string.AirportDisplayBoard_Text_TBD);
        }

        binding.flightNumber     .setText(getString(R.string.AirportDisplayBoard_Hint_Flight_Number) + ":" +  selected.getFlight_number());
        binding.airline          .setText(getString(R.string.AirportDisplayBoard_Hint_Airline_Name) +  ":"+selected.getAirline());
        binding.departureGate    .setText(getString(R.string.AirportDisplayBoard_Hint_Gate) + departGate);
        binding.delayTime        .setText(getString(R.string.AirportDisplayBoard_Hint_Delayed_Depart) + selected.getDeparture_delay());
        binding.departureIata    .setText(getString(R.string.AirportDisplayBoard_Hint_IATA_Depart) + selected.getDeparture_iata());
        binding.arrivalIata      .setText(getString(R.string.AirportDisplayBoard_Hint_IATA_Arrival) + selected.getArrival_iata());
        binding.departureAirport .setText(getString(R.string.AirportDisplayBoard_Hint_Airport_Depart) + selected.getDeparture_airport_name());
        binding.arrivalAirport   .setText(getString(R.string.AirportDisplayBoard_Hint_Airport_Arrival) + selected.getArrival_airport_name());


        if (selected.getFav_date() == null) {
            binding.saveBtn.setText(getString(R.string.AirportDisplayBoard_Btn_Save_Fav));
        } else {
            binding.saveBtn.setText(getString(R.string.AirportDisplayBoard_Btn_Remove_Fav));
        }

        Date fav_date = selected.getFav_date();
        binding.saveBtn.setOnClickListener(v -> {
            if (selected.getFav_date() == null) {
                selected.setFav_date(DataUtils.getSqlDateFromUtilDate(new java.util.Date()));
                binding.saveBtn.setText(getString(R.string.AirportDisplayBoard_Btn_Remove_Fav));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.AirportDisplayBoard_Confirm))
                        .setMessage(getString(R.string.AirportDisplayBoard_Btn_Remove_Fav_Text)
                                + selected.getFlight_number()).setPositiveButton(getString(R.string.AirportDisplayBoard_Yes),
                                ((dialog, which) -> {
                    Snackbar.make(binding.saveBtn, getString(R.string.AirportDisplayBoard_Btn_Remove_Fav_Result) + selected.getFlight_number(), Snackbar.LENGTH_LONG).setAction(getString(R.string.AirportDisplayBoard_Undo), click -> {
                        selected.setFav_date(fav_date);
                        binding.saveBtn.setText(getString(R.string.AirportDisplayBoard_Btn_Remove_Fav));
                        updateFlight();
                    }).show();
                    selected.setFav_date(null);
                    binding.saveBtn.setText(getString(R.string.AirportDisplayBoard_Btn_Save_Fav));
                    updateFlight();
                })).setNegativeButton(getString(R.string.AirportDisplayBoard_No), ((dialog, which) -> {
                })).create().show();

            }
            updateFlight();
        });
        return binding.getRoot();
    }

    /**
     * Updates flight information in the database and refreshes the UI.
     */
    private void updateFlight() {
        AirportDisplayBoardActivity.flightThread.execute(() -> {
            AirportDisplayBoardActivity.flightDAO.insertFlights(selected);
            getActivity().runOnUiThread(() -> {
                AirportDisplayBoardActivity.flightListAdapter.notifyDataSetChanged();
            });
        });
    }

    /**
     * Constructor of the FlightFragment.
     *
     * @param msg flight data to be displayed
     */
    public FlightFragment(Flight msg) {
        selected = msg;
    }
}
