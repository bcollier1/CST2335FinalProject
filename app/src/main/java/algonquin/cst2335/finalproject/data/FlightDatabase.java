package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.finalproject.util.Converters;

/**
 * This is a Room database for storing Flight entities.
 *
 * If a column is added or deleted in the database table, increase the version number
 * to make the database rebuild itself.
 *
 * @see Flight
 * @see FlightDAO
 */
@Database(entities = {Flight.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FlightDatabase extends RoomDatabase {
    /**
     * Gets the Data Access Object for the Flight database.
     *
     * @return the FlightDAO instance.
     * @see FlightDAO
     */
    public abstract FlightDAO flightDAO();
}
