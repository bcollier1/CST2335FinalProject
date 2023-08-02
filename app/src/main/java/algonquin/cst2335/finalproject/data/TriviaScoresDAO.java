package algonquin.cst2335.finalproject.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

@Dao
public interface TriviaScoresDAO {

    @Insert
    long insertScore(TriviaScoreFragment.Score score);

    @Query("Select * FROM Score WHERE category = :category AND difficulty = :difficulty ORDER BY Score.score DESC")
    List<TriviaScoreFragment.Score> getScores(String category, String difficulty);

    @Delete
    void deleteScore(TriviaScoreFragment.Score score);

}
