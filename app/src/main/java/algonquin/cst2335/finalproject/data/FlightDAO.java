package algonquin.cst2335.finalproject.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Represents a chat message with its content, timestamp, and a flag indicating if it is a sent button.
 */
@Dao
public interface FlightDAO {
    /**
     * Insert flights into the Flight database.
     * Flight with the same ID will be replaced.
     *
     * @param flights A variable-length list of Flight objects.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFlights(Flight... flights);

    /**
     * Query the database and return all Flight objects.
     *
     * @return A list of all Flight objects from the database.
     */
    @Query("Select * from Flight")
    public List<Flight> getAllFlights();

    /**
     * Query the database and return all Flight objects where favorite date is not null.
     *
     * @return A list of all favorited Flight objects from the database.
     */
    @Query("Select * from Flight where fav_date is not null")
    public List<Flight> getAllFavFlights();

    /**
     * Delete all Flight objects from the database.
     */
    @Query(("Delete from Flight"))
    void deleteAllFlights();

    /**
     * Delete all Flight objects from the database except those which favorite date is null.
     */
    @Query(("Delete from Flight where fav_date is null"))
    void deleteAllFlightsExceptFav();
}
