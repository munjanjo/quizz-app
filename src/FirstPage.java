import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FirstPage {

    public static void main(String[] args) {
        String path="src/resources/questions.txt";
        List<Question> questions = Question.loadFromFile(path);
        QuizEngine engine = new QuizEngine(questions);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setTitle("Quiz Game");
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Welcome Quiz Game");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        frame.add(titleLabel, BorderLayout.NORTH);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 30, 0));


        JLabel questionLabel = new JLabel("How many questions do you want to answer? (Max:"+engine.getTotalQuestions()+")");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(questionLabel);


        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.X_AXIS));
        JTextArea questionTextArea = new JTextArea(1, 15);
        questionTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 20));
        questionTextArea.setMaximumSize(questionTextArea.getPreferredSize());

        answerPanel.add(questionTextArea);
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        answerPanel.add(nextButton);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setHorizontalAlignment(JButton.CENTER);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        wrapper.add(mainPanel);
        wrapper.add(answerPanel);


        frame.add(wrapper, BorderLayout.CENTER);

        nextButton.addActionListener(e -> {
            String Text = questionTextArea.getText().trim();
            int numOfQuestions = Integer.valueOf(Text);

            if (numOfQuestions > engine.getTotalQuestions()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "You need to enter a number less than or equal to " + engine.getTotalQuestions(),
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            QuizEngine limitedEngine = new QuizEngine(questions.subList(0,numOfQuestions));



            new MainPage(limitedEngine);
            frame.dispose();


        });

        frame.setVisible(true);

    }

}