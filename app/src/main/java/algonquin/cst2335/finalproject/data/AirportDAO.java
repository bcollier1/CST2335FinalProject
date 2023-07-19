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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAirports(Airport ... airports);

    @Query("Select * from Airport")
    public List<Airport> getAllAirports();

    @Query("Delete from Airport")
    void deleteAllAirport();
}
