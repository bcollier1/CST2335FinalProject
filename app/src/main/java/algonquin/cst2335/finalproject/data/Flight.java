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

    /**
     * Gets the ID of the flight.
     *
     * @return the ID of the flight.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the flight.
     *
     * @param id the new ID of the flight.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the flight number.
     *
     * @return the flight number.
     */
    public String getFlight_number() {
        return flight_number;
    }

    /**
     * Sets the flight number.
     *
     * @param flight_number the new flight number.
     */
    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    /**
     * Gets the airline.
     *
     * @return the airline.
     */
    public String getAirline() {
        return airline;
    }

    /**
     * Sets the airline.
     *
     * @param airline the new airline.
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * Gets the codeshared flight number.
     *
     * @return the codeshared flight number.
     */
    public String getFlight_number_codeshared() {
        return flight_number_codeshared;
    }

    /**
     * Sets the codeshared flight number.
     *
     * @param flight_number_codeshared the new codeshared flight number.
     */
    public void setFlight_number_codeshared(String flight_number_codeshared) {
        this.flight_number_codeshared = flight_number_codeshared;
    }

    /**
     * Gets the codeshared airline.
     *
     * @return the codeshared airline.
     */
    public String getAirline_codeshared() {
        return airline_codeshared;
    }

    /**
     * Sets the codeshared airline.
     *
     * @param airline_codeshared the new codeshared airline.
     */
    public void setAirline_codeshared(String airline_codeshared) {
        this.airline_codeshared = airline_codeshared;
    }

    /**
     * Gets the departure IATA code.
     *
     * @return the departure IATA code.
     */
    public String getDeparture_iata() {
        return departure_iata;
    }

    /**
     * Sets the departure IATA code.
     *
     * @param departure_iata the new departure IATA code.
     */
    public void setDeparture_iata(String departure_iata) {
        this.departure_iata = departure_iata;
    }

    /**
     * Gets the departure airport name.
     *
     * @return the departure airport name.
     */
    public String getDeparture_airport_name() {
        return departure_airport_name;
    }

    /**
     * Sets the departure airport name.
     *
     * @param departure_airport_name the new departure airport name.
     */
    public void setDeparture_airport_name(String departure_airport_name) {
        this.departure_airport_name = departure_airport_name;
    }

    /**
     * Gets the departure airport gate.
     *
     * @return the departure airport gate.
     */
    public String getDeparture_airport_gate() {
        return departure_airport_gate;
    }

    /**
     * Sets the departure airport gate.
     *
     * @param departure_airport_gate the new departure airport gate.
     */
    public void setDeparture_airport_gate(String departure_airport_gate) {
        this.departure_airport_gate = departure_airport_gate;
    }

    /**
     * Gets the departure airport terminal.
     *
     * @return the departure airport terminal.
     */
    public String getDeparture_airport_terminal() {
        return departure_airport_terminal;
    }

    /**
     * Sets the departure airport terminal.
     *
     * @param departure_airport_terminal the new departure airport terminal.
     */
    public void setDeparture_airport_terminal(String departure_airport_terminal) {
        this.departure_airport_terminal = departure_airport_terminal;
    }

    /**
     * Gets the departure delay.
     *
     * @return the departure delay.
     */
    public int getDeparture_delay() {
        return departure_delay;
    }

    /**
     * Sets the departure delay.
     *
     * @param departure_delay the new departure delay.
     */
    public void setDeparture_delay(int departure_delay) {
        this.departure_delay = departure_delay;
    }

    /**
     * Obtain departure scheduled date
     *
     * @return departure_scheduled_date
     */
    public Date getDeparture_scheduled_date() {
        return departure_scheduled_date;
    }

    /**
     * Set the departure scheduled date
     *
     * @param departure_scheduled_date date to be set
     */
    public void setDeparture_scheduled_date(Date departure_scheduled_date) {
        this.departure_scheduled_date = departure_scheduled_date;
    }

    /**
     * Obtain departure estimated date
     *
     * @return departure_estimated_date
     */
    public Date getDeparture_estimated_date() {
        return departure_estimated_date;
    }

    /**
     * Set the departure estimated date
     *
     * @param departure_estimated_date date to be set
     */
    public void setDeparture_estimated_date(Date departure_estimated_date) {
        this.departure_estimated_date = departure_estimated_date;
    }

    /**
     * Obtain departure actual date
     *
     * @return departure_actual_date
     */
    public Date getDeparture_actual_date() {
        return departure_actual_date;
    }

    /**
     * Set the departure actual date
     *
     * @param departure_actual_date date to be set
     */
    public void setDeparture_actual_date(Date departure_actual_date) {
        this.departure_actual_date = departure_actual_date;
    }

    /**
     * Obtain departure estimated runway date
     *
     * @return departure_estimated_runway_date
     */
    public Date getDeparture_estimated_runway_date() {
        return departure_estimated_runway_date;
    }

    /**
     * Set the departure estimated runway date
     *
     * @param departure_estimated_runway_date date to be set
     */
    public void setDeparture_estimated_runway_date(Date departure_estimated_runway_date) {
        this.departure_estimated_runway_date = departure_estimated_runway_date;
    }

    /**
     * Obtain departure actual runway date
     *
     * @return departure_actual_runway_date
     */
    public Date getDeparture_actual_runway_date() {
        return departure_actual_runway_date;
    }

    /**
     * Set the departure actual runway date
     *
     * @param departure_actual_runway_date date to be set
     */
    public void setDeparture_actual_runway_date(Date departure_actual_runway_date) {
        this.departure_actual_runway_date = departure_actual_runway_date;
    }

    /**
     * Obtain arrival airport's IATA code
     *
     * @return arrival_iata as a String
     */
    public String getArrival_iata() {
        return arrival_iata;
    }

    /**
     * Set the arrival airport's IATA code
     *
     * @param arrival_iata IATA code to be set
     */
    public void setArrival_iata(String arrival_iata) {
        this.arrival_iata = arrival_iata;
    }

    /**
     * Obtain arrival airport's name
     *
     * @return arrival_airport_name as a String
     */
    public String getArrival_airport_name() {
        return arrival_airport_name;
    }

    /**
     * Set the arrival airport's name
     *
     * @param arrival_airport_name Name to be set
     */
    public void setArrival_airport_name(String arrival_airport_name) {
        this.arrival_airport_name = arrival_airport_name;
    }

    /**
     * Obtain arrival airport's gate
     *
     * @return arrival_airport_gate as a String
     */
    public String getArrival_airport_gate() {
        return arrival_airport_gate;
    }

    /**
     * Set the arrival airport's gate
     *
     * @param arrival_airport_gate Gate to be set
     */
    public void setArrival_airport_gate(String arrival_airport_gate) {
        this.arrival_airport_gate = arrival_airport_gate;
    }

    /**
     * Obtain arrival airport's terminal
     *
     * @return arrival_airport_terminal as a String
     */
    public String getArrival_airport_terminal() {
        return arrival_airport_terminal;
    }

    /**
     * Set the arrival airport's terminal
     *
     * @param arrival_airport_terminal Terminal to be set
     */
    public void setArrival_airport_terminal(String arrival_airport_terminal) {
        this.arrival_airport_terminal = arrival_airport_terminal;
    }

    /**
     * Obtain arrival delay time in minutes
     *
     * @return arrival_delay as an integer
     */
    public int getArrival_delay() {
        return arrival_delay;
    }

    /**
     * Set the arrival delay time in minutes
     *
     * @param arrival_delay Delay time to be set
     */
    public void setArrival_delay(int arrival_delay) {
        this.arrival_delay = arrival_delay;
    }

    /**
     * Obtain arrival scheduled date
     *
     * @return arrival_scheduled_date
     */
    public Date getArrival_scheduled_date() {
        return arrival_scheduled_date;
    }

    /**
     * Set the arrival scheduled date
     *
     * @param arrival_scheduled_date date to be set
     */
    public void setArrival_scheduled_date(Date arrival_scheduled_date) {
        this.arrival_scheduled_date = arrival_scheduled_date;
    }

    /**
     * Obtain arrival estimated date
     *
     * @return arrival_estimated_date
     */
    public Date getArrival_estimated_date() {
        return arrival_estimated_date;
    }

    /**
     * Set the arrival estimated date
     *
     * @param arrival_estimated_date date to be set
     */
    public void setArrival_estimated_date(Date arrival_estimated_date) {
        this.arrival_estimated_date = arrival_estimated_date;
    }

    /**
     * Obtain arrival actual date
     *
     * @return arrival_actual_date
     */
    public Date getArrival_actual_date() {
        return arrival_actual_date;
    }

    /**
     * Set the arrival actual date
     *
     * @param arrival_actual_date date to be set
     */
    public void setArrival_actual_date(Date arrival_actual_date) {
        this.arrival_actual_date = arrival_actual_date;
    }

    /**
     * Obtain arrival estimated runway date
     *
     * @return arrival_estimated_runway_date
     */
    public Date getArrival_estimated_runway_date() {
        return arrival_estimated_runway_date;
    }

    /**
     * Set the arrival estimated runway date
     *
     * @param arrival_estimated_runway_date date to be set
     */
    public void setArrival_estimated_runway_date(Date arrival_estimated_runway_date) {
        this.arrival_estimated_runway_date = arrival_estimated_runway_date;
    }

    /**
     * Obtain arrival actual runway date
     *
     * @return arrival_actual_runway_date
     */
    public Date getArrival_actual_runway_date() {
        return arrival_actual_runway_date;
    }

    /**
     * Set the arrival actual runway date
     *
     * @param arrival_actual_runway_date date to be set
     */
    public void setArrival_actual_runway_date(Date arrival_actual_runway_date) {
        this.arrival_actual_runway_date = arrival_actual_runway_date;
    }

    /**
     * Obtain favourite date
     *
     * @return fav_date
     */
    public Date getFav_date() {
        return fav_date;
    }

    /**
     * Set the favourite date
     *
     * @param fav_date date to be set
     */
    public void setFav_date(Date fav_date) {
        this.fav_date = fav_date;
    }
}