package algonquin.cst2335.finalproject.data;

import java.util.ArrayList;
import java.util.HashMap;

public class TriviaGameState {
    private String category;

    private int numberOfQuestions;

    private int currentQuestionNumber = 0;

    private int numberAnsweredCorrectly = 0;

    private String difficulty;

    private ArrayList<Question> questions = new ArrayList<>();

    public TriviaGameState() {

    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) { this.numberOfQuestions = numberOfQuestions; }

    public int getCurrentQuestionNumber() { return this.currentQuestionNumber; }

    public void setCurrentQuestion(int currentQuestionNumber) { this.currentQuestionNumber = currentQuestionNumber; }

    public int getNumberAnsweredCorrectly() {
        return this.numberAnsweredCorrectly;
    }

    public void setNumberAnsweredCorrectly(int numberAnsweredCorrectly) {
        this.numberAnsweredCorrectly = numberAnsweredCorrectly;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Question getCurrentQuestion() {
        return this.questions.get(this.currentQuestionNumber-1);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
        ++this.numberOfQuestions;
    }

    public void resetGameState() {
        this.category = null;
        this.difficulty = null;
        this.questions = new ArrayList<>();
        this.numberOfQuestions = 0;
        this.numberAnsweredCorrectly = 0;
        this.currentQuestionNumber = 0;
    }

    public static class Question {
        private String questionText;
        private String correctAnswer;
        private String[] incorrectAnswers;

        public String getQuestionText() {
            return this.questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getCorrectAnswer() {
            return this.correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String[] getIncorrectAnswers() {
            return this.incorrectAnswers;
        }

        public void setIncorrectAnswers(String[] incorrectAnswers) {
            this.incorrectAnswers = incorrectAnswers;
        }
    }
}
