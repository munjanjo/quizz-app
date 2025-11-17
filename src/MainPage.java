import javax.swing.*;
import java.awt.*;

public class MainPage {
    private JFrame frame;
    private final QuizEngine engine;
    private Timer timer;
    private int timeLeft=10;
    private JLabel timeLabel;
    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JButton option1;
    private JButton option2;
    private JButton option3;

    MainPage(QuizEngine engine) {
        this.engine = engine;
        frame = new JFrame("QUIZ GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        createUIComponents();
        timer=new Timer(1000,e -> {
            timeLeft--;
            timeLabel.setText("Time: "+timeLeft+ "s");
            if(timeLeft<=0){
                timer.stop();
                engine.submitAnswer(0);
                showCurrentQuestion();
            }
        });
        showCurrentQuestion();
        timer.start();


        frame.setVisible(true);
    }

    private void createUIComponents() {
        frame.setLayout(new BorderLayout());


        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Quiz game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        titlePanel.add(titleLabel);
        titlePanel.add(questionLabel);


        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new FlowLayout());

        option1 = new JButton();
        option2 = new JButton();
        option3 = new JButton();

        option1.setFont(new Font("Arial", Font.BOLD, 20));
        option2.setFont(new Font("Arial", Font.BOLD, 20));
        option3.setFont(new Font("Arial", Font.BOLD, 20));

        answerPanel.add(option1);
        answerPanel.add(option2);
        answerPanel.add(option3);


        JPanel scorePanel = new JPanel();
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(scoreLabel, BorderLayout.WEST);
        timeLabel = new JLabel("Time: "+timeLeft);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(timeLabel,BorderLayout.EAST );

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(answerPanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.SOUTH);



        option1.addActionListener(e -> handleAnswer(1));
        option2.addActionListener(e -> handleAnswer(2));
        option3.addActionListener(e -> handleAnswer(3));
    }



    private void showCurrentQuestion() {

        if (engine.isFinished()) {

            int answered = engine.getNumberOfAnsweredQuestions();
            int score = engine.getTotalScore();
            float percentage = (score / (float) answered) * 100f;

            JFrame finishedFrame = new JFrame("Result");
            finishedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            finishedFrame.setSize(800, 600);
            finishedFrame.setLocationRelativeTo(null);
            finishedFrame.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            JLabel titleLabel = new JLabel("Quiz Finished!");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
            titleLabel.setForeground(new Color(50, 90, 200));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel scoreLabel = new JLabel(
                    "Your score is " + score + "/" + answered + " (" + (int)percentage + "%)"
            );
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 26));
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

            JButton closeButton = new JButton("Close");
            closeButton.setFont(new Font("Arial", Font.BOLD, 22));
            closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            closeButton.setPreferredSize(new Dimension(150, 50));
            closeButton.addActionListener(e -> System.exit(0));

            centerPanel.add(titleLabel);
            centerPanel.add(scoreLabel);
            centerPanel.add(closeButton);

            finishedFrame.add(centerPanel, BorderLayout.CENTER);

            frame.dispose();
            finishedFrame.setVisible(true);
            return;
        }
        timeLeft = 10;
        timeLabel.setText("Time: " + timeLeft +"s");
        if (!timer.isRunning()) {
            timer.start();
        }

        Question q = engine.getCurrentQuestion();
        questionLabel.setText(q.getText());
        String[] options = q.getOptions();
        option1.setText(options[0]);
        option2.setText(options[1]);
        option3.setText(options[2]);

        scoreLabel.setText("Score: " + engine.getTotalScore());
    }


    private void handleAnswer(int answerIndex) {
        engine.submitAnswer(answerIndex);
        showCurrentQuestion();
    }
}
