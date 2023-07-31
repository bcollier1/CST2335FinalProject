package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bear.class}, version = 1)
public abstract class BearDatabase extends RoomDatabase {
    public abstract BearDAO bearDAO();
}

