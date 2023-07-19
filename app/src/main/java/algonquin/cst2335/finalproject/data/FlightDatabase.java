package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.finalproject.util.Converters;

//if a column is added or deleted in the database table.
//To make the database rebuild itself, increase the version number
@Database(entities = {Flight.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FlightDatabase extends RoomDatabase {
    public abstract FlightDAO flightDAO();
}
