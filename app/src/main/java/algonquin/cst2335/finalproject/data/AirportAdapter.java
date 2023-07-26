package algonquin.cst2335.finalproject.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import algonquin.cst2335.finalproject.R;

public class AirportAdapter extends BaseAdapter {
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Airport> airportList;
    private ArrayList<Airport> sourceList;

    public AirportAdapter(Context context, List<Airport> airportList) {
        mContext = context;
        this.sourceList = new ArrayList<>();
        this.airportList = new ArrayList<>();
        if (airportList == null || airportList.isEmpty()) {

        } else {
            this.airportList.addAll(airportList);
            this.sourceList.addAll(airportList);
        }
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return airportList.size();
    }

    @Override
    public Airport getItem(int position) {
        return airportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.airport_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(airportList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        airportList.clear();
        if (charText.length() == 0) {
            airportList.addAll(sourceList);
        } else {
            for (Airport airport : sourceList) {
                String iata = airport.getIata();
                String city = airport.getCity();
                if (iata == null) iata = "";
                if (city == null) city = "";
                if (city.toLowerCase().contains(charText) || iata.toLowerCase().contains(charText)) {
                    airportList.add(airport);
                }
            }
        }

        notifyDataSetChanged();
    }

}