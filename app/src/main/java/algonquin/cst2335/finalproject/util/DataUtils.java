package algonquin.cst2335.finalproject.util;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import algonquin.cst2335.finalproject.data.Flight;
import algonquin.cst2335.finalproject.data.FlightPOJO;

/**
 * This class contains utility methods.
 */
public class DataUtils {
    /**
     * Reads a JSON file from assets and returns its content as a string.
     *
     * @param context   the context to access the assets.
     * @param assetName the name of the asset JSON file to read.
     * @return the content of the JSON file as a string.
     *
     * @throws RuntimeException if a runtime error occurs.
     */
    public static String getJsonFromAsset(Context context, String assetName) {
        String result = null;
        try (InputStream is = context.getAssets().open(assetName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Parses a JSON string and returns its content as an array of objects of type T.
     *
     * @param <T>     the type of the objects.
     * @param jsonKey the key to the array in the JSON data.
     * @param data    the JSON data.
     * @param clazz   the class of type T.
     * @return an array of objects of type T.
     */
    public static <T> T[] getObjArrayFromJson(String jsonKey, String data, Class<T[]> clazz) {
        T[] result;
//        Type type = TypeToken.getParameterized(clazz).getType();
        Gson gson = new Gson();
        result = gson.fromJson(data, clazz);
        return result;
    }

    /**
     * Parses a JSON string and returns its content as an object of type T.
     *
     * @param <T>   the type of the object.
     * @param data  the JSON data.
     * @param clazz the class of type T.
     * @return an object of type T.
     */
    public static <T> T getObjFromJson(String data, Class<T> clazz) {
        T result;
//        Type type = TypeToken.getParameterized(clazz).getType();
        Gson gson = new Gson();
        result = gson.fromJson(data, clazz);
        return result;
    }

    /**
     * Converts a FlightPOJO object into an array of Flight objects.
     *
     * @param pojo the FlightPOJO object.
     * @return an array of Flight objects.
     */
    public static Flight[] getFlightDbObjsFromPojo(FlightPOJO pojo) {
        if (pojo == null || pojo.data == null || pojo.data.size() == 0) {
            return null;
        }
        ArrayList<FlightPOJO.Datum> data = pojo.data;
        Flight[] flights = new Flight[data.size()];
        for (int i = 0; i < data.size(); i++) {
            FlightPOJO.Datum datum = data.get(i);
            Flight flight = new Flight();
            String flightNumber = datum.flight.iata;
            if (StringUtils.isBlank(flightNumber)) {
                flightNumber = datum.flight.icao;
            }
            if (StringUtils.isBlank(flightNumber)) {
                flightNumber = datum.flight.number;
            }
            if (StringUtils.isBlank(flightNumber)) {
                flightNumber = "TBD";
            }
            flight.setFlight_number(flightNumber);

            flight.setAirline(StringUtils.isBlank(datum.airline.name) ? "TBD" : datum.airline.name);
            if (datum.flight.codeshared != null) {
                flight.setFlight_number_codeshared(datum.flight.codeshared.airline_iata);
                flight.setAirline_codeshared(datum.flight.codeshared.airline_name);
            }

            //departure info
            flight.setDeparture_iata(datum.departure.iata);
            flight.setDeparture_airport_name(datum.departure.airport);
            flight.setDeparture_airport_gate(datum.departure.gate);
            flight.setDeparture_airport_terminal(datum.departure.terminal);
            flight.setDeparture_delay(datum.departure.delay);
            flight.setDeparture_scheduled_date(getSqlDateFromUtilDate(datum.departure.scheduled));
            flight.setDeparture_estimated_date(getSqlDateFromUtilDate(datum.departure.estimated));
            flight.setDeparture_actual_date(getSqlDateFromUtilDate(datum.departure.actual));
            flight.setDeparture_estimated_runway_date(getSqlDateFromUtilDate(datum.departure.estimated_runway));
            flight.setDeparture_actual_runway_date(getSqlDateFromUtilDate(datum.departure.actual_runway));

            //arrival info
            flight.setArrival_iata(datum.arrival.iata);
            flight.setArrival_airport_name(datum.arrival.airport);
            flight.setArrival_airport_gate(datum.arrival.gate);
            flight.setArrival_airport_terminal(datum.arrival.terminal);
            flight.setArrival_delay(datum.arrival.delay);
            flight.setArrival_scheduled_date(getSqlDateFromUtilDate(datum.arrival.scheduled));
            flight.setArrival_estimated_date(getSqlDateFromUtilDate(datum.arrival.estimated));
            flight.setArrival_actual_date(getSqlDateFromUtilDate(datum.arrival.actual));
            flight.setArrival_estimated_runway_date(getSqlDateFromUtilDate(datum.arrival.estimated_runway));
            flight.setArrival_actual_runway_date(getSqlDateFromUtilDate(datum.arrival.actual_runway));

            //For test purpose only
//            if(i < 20 && i > 15){
//                flight.setFav_date(getSqlDateFromUtilDate(new java.util.Date()));
//            }

            flights[i] = flight;
        }
        return flights;
    }

    /**
     * Converts a java.util.Date object into a java.sql.Date object.
     *
     * @param from the java.util.Date object.
     * @return a java.sql.Date object with the same time as the input, or null if the input is null.
     */
    public static java.sql.Date getSqlDateFromUtilDate(java.util.Date from) {
        if (from == null) {
            return null;
        }
        return new java.sql.Date(from.getTime());
    }
}
