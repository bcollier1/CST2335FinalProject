package algonquin.cst2335.finalproject.util;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import algonquin.cst2335.finalproject.data.Flight;
import algonquin.cst2335.finalproject.data.FlightPOJO;

public class DataUtils {
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

    public static <T> T[] getObjArrayFromJson(String jsonKey, String data, Class<T[]> clazz) {
        T[] result;
//        Type type = TypeToken.getParameterized(clazz).getType();
        Gson gson = new Gson();
        result = gson.fromJson(data, clazz);
        return result;
    }

    public static <T> T getObjFromJson(String data, Class<T> clazz) {
        T result;
//        Type type = TypeToken.getParameterized(clazz).getType();
        Gson gson = new Gson();
        result = gson.fromJson(data, clazz);
        return result;
    }

    public static Flight[] getFlightDbObjsFromPojo(FlightPOJO pojo){
        if(pojo == null || pojo.data == null || pojo.data.size() == 0){
            return null;
        }
        ArrayList<FlightPOJO.Datum> data = pojo.data;
        Flight[] flights = new Flight[data.size()];
        for (int i = 0; i < data.size(); i++) {
            FlightPOJO.Datum datum = data.get(i);
            Flight flight = new Flight();
            String flightNumber = datum.flight.iata;
            if(StringUtils.isBlank(flightNumber)){
                flightNumber = datum.flight.icao;
            }
            if(StringUtils.isBlank(flightNumber)){
                flightNumber = datum.flight.number;
            }
            if(StringUtils.isBlank(flightNumber)){
                flightNumber = "TBD";
            }
            flight.setFlight_number(flightNumber);

            flight.setAirline(StringUtils.isBlank(datum.airline.name)?"TBD":datum.airline.name);
            if(datum.flight.codeshared != null){
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

    public static java.sql.Date getSqlDateFromUtilDate(java.util.Date from){
        if(from == null){
            return null;
        }
        return new java.sql.Date(from.getTime());
    }
}
