package algonquin.cst2335.finalproject.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.ui.TriviaScoreFragment;

public class TriviaViewModel extends ViewModel {
    public MutableLiveData<String> url = new MutableLiveData<>();
    public MutableLiveData<TriviaGameState> game = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TriviaScoreFragment.Score>> scores = new MutableLiveData<>();
}
