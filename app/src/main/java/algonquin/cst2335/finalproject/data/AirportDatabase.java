package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//if a column is added or deleted in the database table.
//To make the database rebuild itself, increase the version number
@Database(entities = {Airport.class}, version = 1)
public abstract class AirportDatabase extends RoomDatabase {
    public abstract AirportDAO airportDAO();
}
