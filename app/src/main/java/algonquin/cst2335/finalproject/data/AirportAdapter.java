package algonquin.cst2335.finalproject.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * This is a custom adapter class for displaying list of Airports.
 */
public class AirportAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Airport> airportList;
    private ArrayList<Airport> sourceList;

    /**
     * This is the constructor method for AirportAdapter.
     *
     * @param context     the context of the current state of the application.
     * @param airportList the list of airports.
     */
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

    /**
     * ViewHolder class to describe item view and metadata about its place within the RecyclerView.
     */
    public class ViewHolder {
        TextView name;
    }

    /**
     * Get the count of the airports list.
     *
     * @return the size of the airports list.
     */
    @Override
    public int getCount() {
        return airportList.size();
    }


    /**
     * Get the Airport at the specified position in the list.
     *
     * @param position the position of the item within the adapter's data set.
     * @return the Airport at the specified position.
     */
    @Override
    public Airport getItem(int position) {
        return airportList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position the position of the item within the adapter's data set.
     * @return the id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position the position of the item within the adapter's data set.
     * @param view     the old view to reuse, if possible.
     * @param parent   the parent that this view will eventually be attached to.
     * @return a View corresponding to the data at the specified position.
     */
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

    /**
     * Filter the airport list based on a character sequence.
     *
     * @param charText the character sequence to match
     */
    public void filter(String charText) {
        airportList.clear();
        if (charText.length() == 0) {
            airportList.addAll(sourceList);
        } else {
            //Filter by city
            charText = charText.toLowerCase();
            for (Airport airport : sourceList) {
                String iata = airport.getIata();
                String city = airport.getCity();
                String name = airport.getName();
                iata = iata == null ? "" : iata;
                city = city == null ? "" : city;
                name = name == null ? "" : name;
                if (city.toLowerCase().contains(charText)
                        || iata.toLowerCase().contains(charText)
                        || name.toLowerCase().contains(charText)
                ) {
                    airportList.add(airport);
                }
            }
        }

        notifyDataSetChanged();
    }

}