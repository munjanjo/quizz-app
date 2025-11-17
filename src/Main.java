import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int numberOfQuestions = 0;
        Scanner input = new Scanner(System.in);
        List<Question> questions = new ArrayList<>();
        String filePath="src/resources/questions.txt";
        questions=Question.loadFromFile(filePath);
        System.out.print("Number of questions(Max is "+questions.size() +"):");
       numberOfQuestions = input.nextInt();
       if(numberOfQuestions<=questions.size() && numberOfQuestions>0) {
           QuizEngine engine = new QuizEngine(questions.subList(0,numberOfQuestions));
           play(engine);
       }
       else if(numberOfQuestions>questions.size()) {
           System.out.println("Number is too large");
       }
       else{
           System.out.println("Number has to be above 0");
       }



    }
    private static void play(QuizEngine engine){
        int questionNumber = 1;
        Scanner input = new Scanner(System.in);
        String answer = "";
        while(!engine.isFinished()){
            Question q=engine.getCurrentQuestion();
            System.out.println("Question #"+questionNumber+":"+q.getText());
            String[] options=q.getOptions();
            System.out.println("1) "+options[0]);
            System.out.println("2) "+options[1]);
            System.out.println("3) "+options[2]);
            System.out.print("Your answer is: ");
            answer = input.nextLine().trim();
            Integer correctAnswer = Integer.parseInt(answer);
            engine.submitAnswer(correctAnswer);
            questionNumber++;
        }
        System.out.println("Total score is "+ engine.getTotalScore()+"/"+engine.getTotalQuestions()+"("+ (float)engine.getTotalScore() / (float)engine.getTotalQuestions()*100+"%)");
        System.out.println("Thank you for playing!");


    }
}
