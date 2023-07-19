package algonquin.cst2335.finalproject.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

/**
 * Represents a chat message with its content, timestamp, and a flag indicating if it is a sent button.
 */
@Entity
public class Flight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    //General Info
    @ColumnInfo(name = "flight_number")
    private String flight_number;

    @ColumnInfo(name = "airline")
    private String airline;

    @ColumnInfo(name = "flight_number_codeshared")
    private String flight_number_codeshared;

    @ColumnInfo(name = "airline_codeshared")
    private String airline_codeshared;

    @ColumnInfo(name = "fav_date")
    private Date fav_date;

    //Departure Info
    @ColumnInfo(name = "departure_iata")
    private String departure_iata;

    @ColumnInfo(name = "departure_airport_name")
    private String departure_airport_name;

    @ColumnInfo(name = "departure_airport_gate")
    private String departure_airport_gate;

    @ColumnInfo(name = "departure_airport_terminal")
    private String departure_airport_terminal;
    
    @ColumnInfo(name = "departure_delay")
    private int departure_delay;

    @ColumnInfo(name = "departure_scheduled_date")
    private Date departure_scheduled_date;

    @ColumnInfo(name = "departure_estimated_date")
    private Date departure_estimated_date;

    @ColumnInfo(name = "departure_actual_date")
    private Date departure_actual_date;

    @ColumnInfo(name = "departure_estimated_runway_date")
    private Date departure_estimated_runway_date;

    @ColumnInfo(name = "departure_actual_runway_date")
    private Date departure_actual_runway_date;

    //Arrival Info
    @ColumnInfo(name = "arrival_iata")
    private String arrival_iata;

    @ColumnInfo(name = "arrival_airport_name")
    private String arrival_airport_name;

    @ColumnInfo(name = "arrival_airport_gate")
    private String arrival_airport_gate;

    @ColumnInfo(name = "arrival_airport_terminal")
    private String arrival_airport_terminal;

    @ColumnInfo(name = "arrival_delay")
    private int arrival_delay;

    @ColumnInfo(name = "arrival_scheduled_date")
    private Date arrival_scheduled_date;

    @ColumnInfo(name = "arrival_estimated_date")
    private Date arrival_estimated_date;

    @ColumnInfo(name = "arrival_actual_date")
    private Date arrival_actual_date;

    @ColumnInfo(name = "arrival_estimated_runway_date")
    private Date arrival_estimated_runway_date;

    @ColumnInfo(name = "arrival_actual_runway_date")
    private Date arrival_actual_runway_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlight_number_codeshared() {
        return flight_number_codeshared;
    }

    public void setFlight_number_codeshared(String flight_number_codeshared) {
        this.flight_number_codeshared = flight_number_codeshared;
    }

    public String getAirline_codeshared() {
        return airline_codeshared;
    }

    public void setAirline_codeshared(String airline_codeshared) {
        this.airline_codeshared = airline_codeshared;
    }

    public String getDeparture_iata() {
        return departure_iata;
    }

    public void setDeparture_iata(String departure_iata) {
        this.departure_iata = departure_iata;
    }

    public String getDeparture_airport_name() {
        return departure_airport_name;
    }

    public void setDeparture_airport_name(String departure_airport_name) {
        this.departure_airport_name = departure_airport_name;
    }

    public String getDeparture_airport_gate() {
        return departure_airport_gate;
    }

    public void setDeparture_airport_gate(String departure_airport_gate) {
        this.departure_airport_gate = departure_airport_gate;
    }

    public String getDeparture_airport_terminal() {
        return departure_airport_terminal;
    }

    public void setDeparture_airport_terminal(String departure_airport_terminal) {
        this.departure_airport_terminal = departure_airport_terminal;
    }

    public int getDeparture_delay() {
        return departure_delay;
    }

    public void setDeparture_delay(int departure_delay) {
        this.departure_delay = departure_delay;
    }

    public Date getDeparture_scheduled_date() {
        return departure_scheduled_date;
    }

    public void setDeparture_scheduled_date(Date departure_scheduled_date) {
        this.departure_scheduled_date = departure_scheduled_date;
    }

    public Date getDeparture_estimated_date() {
        return departure_estimated_date;
    }

    public void setDeparture_estimated_date(Date departure_estimated_date) {
        this.departure_estimated_date = departure_estimated_date;
    }

    public Date getDeparture_actual_date() {
        return departure_actual_date;
    }

    public void setDeparture_actual_date(Date departure_actual_date) {
        this.departure_actual_date = departure_actual_date;
    }

    public Date getDeparture_estimated_runway_date() {
        return departure_estimated_runway_date;
    }

    public void setDeparture_estimated_runway_date(Date departure_estimated_runway_date) {
        this.departure_estimated_runway_date = departure_estimated_runway_date;
    }

    public Date getDeparture_actual_runway_date() {
        return departure_actual_runway_date;
    }

    public void setDeparture_actual_runway_date(Date departure_actual_runway_date) {
        this.departure_actual_runway_date = departure_actual_runway_date;
    }

    public String getArrival_iata() {
        return arrival_iata;
    }

    public void setArrival_iata(String arrival_iata) {
        this.arrival_iata = arrival_iata;
    }

    public String getArrival_airport_name() {
        return arrival_airport_name;
    }

    public void setArrival_airport_name(String arrival_airport_name) {
        this.arrival_airport_name = arrival_airport_name;
    }

    public String getArrival_airport_gate() {
        return arrival_airport_gate;
    }

    public void setArrival_airport_gate(String arrival_airport_gate) {
        this.arrival_airport_gate = arrival_airport_gate;
    }

    public String getArrival_airport_terminal() {
        return arrival_airport_terminal;
    }

    public void setArrival_airport_terminal(String arrival_airport_terminal) {
        this.arrival_airport_terminal = arrival_airport_terminal;
    }

    public int getArrival_delay() {
        return arrival_delay;
    }

    public void setArrival_delay(int arrival_delay) {
        this.arrival_delay = arrival_delay;
    }

    public Date getArrival_scheduled_date() {
        return arrival_scheduled_date;
    }

    public void setArrival_scheduled_date(Date arrival_scheduled_date) {
        this.arrival_scheduled_date = arrival_scheduled_date;
    }

    public Date getArrival_estimated_date() {
        return arrival_estimated_date;
    }

    public void setArrival_estimated_date(Date arrival_estimated_date) {
        this.arrival_estimated_date = arrival_estimated_date;
    }

    public Date getArrival_actual_date() {
        return arrival_actual_date;
    }

    public void setArrival_actual_date(Date arrival_actual_date) {
        this.arrival_actual_date = arrival_actual_date;
    }

    public Date getArrival_estimated_runway_date() {
        return arrival_estimated_runway_date;
    }

    public void setArrival_estimated_runway_date(Date arrival_estimated_runway_date) {
        this.arrival_estimated_runway_date = arrival_estimated_runway_date;
    }

    public Date getArrival_actual_runway_date() {
        return arrival_actual_runway_date;
    }

    public void setArrival_actual_runway_date(Date arrival_actual_runway_date) {
        this.arrival_actual_runway_date = arrival_actual_runway_date;
    }

    public Date getFav_date() {
        return fav_date;
    }

    public void setFav_date(Date fav_date) {
        this.fav_date = fav_date;
    }
}