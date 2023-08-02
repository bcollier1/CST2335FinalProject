package algonquin.cst2335.finalproject.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to track the current state of the trivia game. It holds the number of
 * questions, the question the user is currently on, the current category, the number of questions
 * the user has answered correctly, the difficulty of the trivia, and the list of questions (and
 * their corresponding answers).
 * @author Jay Pyefinch
 */
public class TriviaGameState {
    /**
     * This field stores the current category of the trivia.
     */
    private String category;

    /**
     * This field stores the number of questions in this trivia.
     */
    private int numberOfQuestions;

    /**
     * This field stores the question number that the user is currently on.
     */
    private int currentQuestionNumber = 0;

    /**
     * This field stores the number of questions the user has answered correctly.
     */
    private int numberAnsweredCorrectly = 0;

    /**
     * This field stores the selected difficulty for this trivia.
     */
    private String difficulty;

    /**
     * This field stores an {@code ArrayList} of {@link Question} instances associated with the
     * trivia
     */
    private ArrayList<Question> questions = new ArrayList<>();

    /**
     * This constructor initializes the object with default values.
     */
    public TriviaGameState() {

    }

    /**
     * This method returns the category of the current trivia.
     * @return {@code String} representation of the category
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * This method sets the category of the current trivia
     * @param category {@code String} representation of the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * This method gets the number of questions in this trivia
     * @return the number of questions in this trivia
     */
    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    /**
     * This method sets the number of questions in this trivia
     * @param numberOfQuestions the number of questions in this trivia
     */
    public void setNumberOfQuestions(int numberOfQuestions) { this.numberOfQuestions = numberOfQuestions; }

    /**
     * This method gets the current question number
     * @return the current question number (starts from 1)
     */
    public int getCurrentQuestionNumber() { return this.currentQuestionNumber; }

    /**
     * This method sets the current question number
     * @param currentQuestionNumber the current question number (starts from 1)
     */
    public void setCurrentQuestion(int currentQuestionNumber) { this.currentQuestionNumber = currentQuestionNumber; }

    /**
     * This method gets the number of questions in this trivia.
     * @return the number of questions in the trivia
     */
    public int getNumberAnsweredCorrectly() {
        return this.numberAnsweredCorrectly;
    }

    /**
     * This method sets the number of questions in this trivia.
     * @param numberAnsweredCorrectly the number of questions in the trivia
     */
    public void setNumberAnsweredCorrectly(int numberAnsweredCorrectly) {
        this.numberAnsweredCorrectly = numberAnsweredCorrectly;
    }

    /**
     * This method gets the difficulty of this trivia.
     * @return the difficulty of the trivia
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * This method sets the difficulty of this trivia.
     * @param difficulty the difficulty of the trivia
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This method gets the current {@link Question} in the trivia.
     * @return the associated {@link Question} of the current question number
     */
    public Question getCurrentQuestion() {
        return this.questions.get(this.currentQuestionNumber-1);
    }

    /**
     * This method is used to load a question into the list of questions associated with this trivia
     * @param question the {@link Question} to add to this trivia
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
        ++this.numberOfQuestions;
    }

    /**
     * This method resets the object to its original state for use in another game of trivia.
     */
    public void resetGameState() {
        this.category = null;
        this.difficulty = null;
        this.questions = new ArrayList<>();
        this.numberOfQuestions = 0;
        this.numberAnsweredCorrectly = 0;
        this.currentQuestionNumber = 0;
    }

    /**
     * This class stores the information associated with a trivia question, with the question,
     * correct answer, and incorrect answers.
     */
    public static class Question {
        /**
         * This field stores the question to be displayed to the user.
         */
        private String questionText;

        /**
         * This field stores the correct answer.
         */
        private String correctAnswer;

        /**
         * This field stores an array of incorrect answers.
         */
        private String[] incorrectAnswers;

        /**
         * This method gets the question text.
         * @return the text associated with the question
         */
        public String getQuestionText() {
            return this.questionText;
        }

        /**
         * This method set the question text.
         * @param questionText the text associated with the question
         */
        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        /**
         * This method gets the correct answer for the question.
         * @return the correct answer for the question
         */
        public String getCorrectAnswer() {
            return this.correctAnswer;
        }

        /**
         * This method sets the correct answer for the question.
         * @param correctAnswer the correct answer for the question
         */
        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        /**
         * This method gets the array of incorrect answers.
         * @return the incorrect answers
         */
        public String[] getIncorrectAnswers() {
            return this.incorrectAnswers;
        }

        /**
         * This method sets the array of incorrect answers.
         * @param incorrectAnswers  the incorrect answers
         */
        public void setIncorrectAnswers(String[] incorrectAnswers) {
            this.incorrectAnswers = incorrectAnswers;
        }
    }
}
