package algonquin.cst2335.finalproject.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

@Database(entities = {TriviaScoreFragment.Score.class}, version=1)
public abstract class TriviaScoreDatabase extends RoomDatabase {

    public abstract TriviaScoresDAO sDAO();
}
