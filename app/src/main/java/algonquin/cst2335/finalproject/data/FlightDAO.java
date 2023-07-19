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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFlights(Flight ... flights);

    @Query("Select * from Flight")
    public List<Flight> getAllFlights();

    @Query("Select * from Flight where fav_date is not null")
    public List<Flight> getAllFavFlights();

    @Query(("Delete from Flight"))
    void deleteAllFlights();

    @Query(("Delete from Flight where fav_date is null"))
    void deleteAllFlightsExceptFav();
}
