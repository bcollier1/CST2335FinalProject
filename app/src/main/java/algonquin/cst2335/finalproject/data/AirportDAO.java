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
public interface AirportDAO {
    /**
     * Inserts an array of airports.
     * If an airport with the same primary key already exists, it will be replaced.
     *
     * @param airports array of Airport objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAirports(Airport ... airports);

    /**
     * Retrieves all airports.
     *
     * @return List of all Airport objects.
     */
    @Query("Select * from Airport")
    public List<Airport> getAllAirports();

    /**
     * Deletes all airports.
     */
    @Query("Delete from Airport")
    void deleteAllAirport();
}
