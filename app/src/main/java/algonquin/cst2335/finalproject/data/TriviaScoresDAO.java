package algonquin.cst2335.finalproject.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

/**
 * This class uses the Room class to create a DAO that is used to access scores for users of the
 * trivia activity.
 * @author Jay Pyefinch
 */
@Dao
public interface TriviaScoresDAO {

    /**
     * This method inserts a {@link algonquin.cst2335.finalproject.ui.TriviaScoreFragment.Score}
     * into the database.
     * @param score the {@link algonquin.cst2335.finalproject.ui.TriviaScoreFragment.Score} to add
     * @return the id assigned to this instance
     */
    @Insert
    long insertScore(TriviaScoreFragment.Score score);

    /**
     * This method gets all of the scores associated with the trivia's category and difficulty from
     * highest-scoring to lowest-scoring.
     * @param category the trivia's category
     * @param difficulty the trivia's difficulty
     * @return the list of scores with the same category and difficulty as those specified
     */
    @Query("Select * FROM Score WHERE category = :category AND difficulty = :difficulty ORDER BY Score.score DESC")
    List<TriviaScoreFragment.Score> getScores(String category, String difficulty);

    /**
     * This method deletes the {@link algonquin.cst2335.finalproject.ui.TriviaScoreFragment.Score}
     * from the database.
     * @param score the {@link algonquin.cst2335.finalproject.ui.TriviaScoreFragment.Score} to
     * delete
     */
    @Delete
    void deleteScore(TriviaScoreFragment.Score score);

}
