package algonquin.cst2335.finalproject.data;

import android.text.Html;

import androidx.annotation.NonNull;

import java.util.HashMap;

import algonquin.cst2335.finalproject.util.Constants;

/**
 * This class is used to take the {@code String}s of both the category and difficulty of an OpenTDB
 * trivia game and produce a URL that will retrieve the desired game type.
 * @author Jay Pyefinch
 */
public class TriviaAPIURLBuilder {

    /**
     * This constant stores the first, unchanging component of the OpenTDB API address.
     */
    private static final String URL_START = Constants.TRIVIA_BASE_URL_START;

    /**
     * This constant stores the concluding portion of the OpenTDB API address. In this case, it is
     * only requesting the data from multiple-choice questions.
     */
    private static final String URL_END = Constants.TRIVIA_BASE_URL_END;

    /**
     * This constant stores the first category number of their API. (Each category is mapped to a
     * category number)
     */
    private static final int FIRST_CATEGORY = 9;

    /**
     * This constant stores the number of questions for the trivia. In this case, is requests 10
     * questions for each trivia play-through.
     */
    private static final int NUMBER_OF_QUESTIONS = 10;

    /**
     * This constant stores the index offset to be used when generating the category mapping. For
     * example, if the index is 1, the first category will be 1 (index) + 8 (index offset) = 9
     * (first category number).
     */
    private static final int INDEX_OFFSET = FIRST_CATEGORY - 1;

    /**
     * This Hashmap contains the category name-to-number mapping in order to communicate with the
     * OpenTDB API. It is populated on the first instantiation of an instance of this class.
     */
    private static HashMap<String, String> categoryAPIMap;

    /**
     * This constant uses the {@link #NUMBER_OF_QUESTIONS} constant to create the amount segment of
     * the OpenTDB API address.
     */
    private final String numberOfQuestionsSegment = "amount=" + NUMBER_OF_QUESTIONS;

    /**
     * This field stores the category segment. It will be populated with the URL segment of the
     * desired category, blank if 'Any Category' is selected.
     */
    private String categorySegment = "";

    /**
     * This field stores the difficulty segment. It will be populated with the URL segment of the
     * desired difficulty, blank if 'Any Difficulty' is selected.
     */
    private String difficultySegment = "";

    /**
     * This constructor builds the object with default values, but also checks if the {@code static}
     * {@link #categoryAPIMap} has been initialized. If not, it generates the key-value pairs for
     * the category mapping for future {@link TriviaAPIURLBuilder} instances to use.
     */
    public TriviaAPIURLBuilder() {
        if (categoryAPIMap == null) {
            categoryAPIMap = new HashMap<>();
            categoryAPIMap.put("Any Category", "");
            String[] categoryArray = Constants.triviaCategoryArray;
            for (int i = 1; i < categoryArray.length; ++i) {
                categoryAPIMap.put(Html.fromHtml(categoryArray[i], Html.FROM_HTML_MODE_LEGACY).toString(), "&category=" + (i + INDEX_OFFSET));
            }
        }
    }

    /**
     * This method sets the category for this URL builder instance.
     * @param category the {@code String} representation of the category to play
     * @return {@link TriviaAPIURLBuilder this}
     */
    public TriviaAPIURLBuilder setCategorySegment(@NonNull String category) {
        this.categorySegment = categoryAPIMap.get(category);
        return this;
    }

    /**
     * This method sets the diificulty for this URL builder instance.
     * @param difficulty the {@code String} representation of the difficulty to play
     * @return {@link TriviaAPIURLBuilder this}
     */
    public TriviaAPIURLBuilder setDifficultySegment(@NonNull String difficulty) {
        this.difficultySegment = (difficulty.equals("Any Difficulty")) ? "" : "&difficulty=" + difficulty.toLowerCase();
        return this;
    }

    /**
     * This method uses the different segments to create a completed URL {@code String} for the
     * OpenTDB API.
     * @return {@code String } of the URL to access the OpenTDB API with the specified category and
     * difficulty
     */
    public String build() {
        return URL_START + numberOfQuestionsSegment + categorySegment + difficultySegment + URL_END;
    }

}
