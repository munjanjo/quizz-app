import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizEngine {
    private final List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    public QuizEngine(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
        Collections.shuffle(this.questions);
    }
    public int getNumberOfAnsweredQuestions(){
        return currentIndex;
    }
    public boolean isFinished() {
        return currentIndex >= questions.size();
    }
    public int getTotalQuestions() {
        return questions.size();
    }
    public int getTotalScore() {
        return score;
    }
    public Question getCurrentQuestion() {
        if(isFinished()){return null;}
        return questions.get(currentIndex);
    }
    public boolean submitAnswer(int chosenIndex) {
        if (isFinished()) {
            return false;
        }
        Question q=questions.get(currentIndex);
        boolean correct = (q.getCorrectIndex() == chosenIndex);
        if(correct) {
            System.out.println("CORRECT!");score++;}
        else{System.out.println("INCORRECT!");}
        currentIndex++;
        return correct;
    }

}
