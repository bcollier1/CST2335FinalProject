package algonquin.cst2335.finalproject.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Our Database class is set up so that this is the main access point for all the information is
 * stored here. Using SQLiteDatabase and Room to build the library.
 *
 * As an abstract class, we defined all of our entities here. We only have Currency as of right now
 * and its version number. The entity will then end up being a table in the SQLite Database.
 *
 * This class also has an abstract method with no body, it returns a CurrencyDAO. Room automatically
 * implements this method and creates a CurrencyDAO object when the app asks for it.
 *
 */
@Database(entities = {Currency.class}, version = 1, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {

    /**
     * We will be returning the DAO for the database
     * @return DAO for the database
     */
    public abstract CurrencyDAO DAO();
}
