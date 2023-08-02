package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

/**
 * This class is used to generate and maintain a database that stores user scores.
 * @author Jay Pyefinch
 */
@Database(entities = {TriviaScoreFragment.Score.class}, version=1)
public abstract class TriviaScoreDatabase extends RoomDatabase {

    /**
     * This method is used to get an instance of the DAO associated with the database.
     */
    public abstract TriviaScoresDAO sDAO();
}
