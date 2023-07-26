package algonquin.cst2335.finalproject.data;

import androidx.annotation.NonNull;

import java.util.HashMap;

import algonquin.cst2335.finalproject.util.Constants;

public class TriviaAPIURLBuilder {

    private static final String URL_START = Constants.TRIVIA_BASE_URL_START;

    private static final String URL_END = Constants.TRIVIA_BASE_URL_END;

    private static final int FIRST_CATEGORY = 9;

    private static final int NUMBER_OF_QUESTIONS = 10;

    private static final int INDEX_OFFSET = FIRST_CATEGORY - 1;

    private static HashMap<String, String> categoryAPIMap;

    private final String numberOfQuestionsSegment = "amount=" + NUMBER_OF_QUESTIONS;

    private String categorySegment = "";

    private String difficultySegment = "";


    public TriviaAPIURLBuilder() {
        if (categoryAPIMap == null) {
            categoryAPIMap = new HashMap<>();
            categoryAPIMap.put("Any Category", "");
            String[] categoryArray = Constants.triviaCategoryArray;
            for (int i = 1; i < categoryArray.length; ++i) {
                categoryAPIMap.put(categoryArray[i], "&category=" + (i + INDEX_OFFSET));
            }
        }
    }

    public TriviaAPIURLBuilder setCategorySegment(@NonNull String category) {
        this.categorySegment = categoryAPIMap.get(category);
        return this;
    }

    public TriviaAPIURLBuilder setDifficultySegment(@NonNull String difficulty) {
        this.difficultySegment = (difficulty.equals("Any Difficulty")) ? "" : "&difficulty=" + difficulty;
        return this;
    }

    public String build() {
        return URL_START + numberOfQuestionsSegment + categorySegment + difficultySegment + URL_END;
    }




}
