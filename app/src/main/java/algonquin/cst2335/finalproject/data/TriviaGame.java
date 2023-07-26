package algonquin.cst2335.finalproject.data;

import java.util.ArrayList;

public class TriviaGame {
    private String category;

    private int numberOfQuestions;

    private String difficulty;

    private ArrayList<Question> questions = new ArrayList<>();

    public TriviaGame() {
        this.numberOfQuestions = 10;
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

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
