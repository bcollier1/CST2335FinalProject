package algonquin.cst2335.finalproject.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

/**
 * This class is used to track the state of the different objects associated with the trivia
 * activity.
 * @author Jay Pyefinch
 */
public class TriviaViewModel extends ViewModel {
    /**
     * This field stores the url used to access the OpenTDB API.
     */
    public MutableLiveData<String> url = new MutableLiveData<>();

    /**
     * This field stores the game state of the current trivia game.
     */
    public MutableLiveData<TriviaGameState> game = new MutableLiveData<>();

    /**
     * This field stores the currently displayed scores in the scores screen.
     */
    public MutableLiveData<ArrayList<TriviaScoreFragment.Score>> scores = new MutableLiveData<>();
}
