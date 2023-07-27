package algonquin.cst2335.finalproject.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an Airport entity with its unique id, IATA code, name, city, and country.
 */
@Entity
public class Airport {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "iata")
    private String iata;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "country")
    private String country;

    /**
     * Gets the unique id.
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique id.
     * @param id the id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the IATA code.
     * @return the IATA code.
     */
    public String getIata() {
        return iata;
    }

    /**
     * Sets the IATA code.
     * @param iata the IATA code to be set.
     */
    public void setIata(String iata) {
        this.iata = iata;
    }

    /**
     * Gets the name.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the cityof the airport.
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the cityof the airport.
     * @param city the city to be set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the countryof the airport.
     * @return the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the countryof the airport.
     * @param country the country to be set.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
