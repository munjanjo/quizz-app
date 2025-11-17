import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Question {

    private final String text;
    private final String[] options;
    private final int correctIndex;
    public Question(String text, String[] options, int correctIndex) {
        if(options.length != 3){throw new IllegalArgumentException("need exactly 3 options");}
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }
    public String getText() {
        return text;
    }
    public String[] getOptions() {
        return options;
    }
    public int getCorrectIndex() {return correctIndex;}


    public static List<Question> loadFromFile(String filePath) {
        List<Question> questions = new ArrayList<>();
        int lineNumber=0;
        try( BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                lineNumber++;
                String trimmedLine = line.trim();
                String[] parts = trimmedLine.split(";");
                String optA = parts[1];
                String optB = parts[2];
                String optC = parts[3];
                int correctAnswer = Integer.parseInt(parts[4]);
                questions.add(new Question(parts[0],new String[]{optA,optB,optC},correctAnswer));

            }
        }catch (IOException e){
            System.out.println("Error opening file" +e.getMessage());
        }
        return questions;
    }
}
