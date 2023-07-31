package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Represents the SQLite database for the application using Room.
 *
 * <p>Note: If a column is added or deleted in the database table,
 * to make the database rebuild itself, increase the version number.</p>
 */
@Database(entities = {Airport.class}, version = 1)
public abstract class AirportDatabase extends RoomDatabase {
    /**
     * Provides access to the DAO to the Airport table.
     *
     * @return The DAO object.
     */
    public abstract AirportDAO airportDAO();
}
