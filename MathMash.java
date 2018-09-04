/**
 * 
 */
package edu.ics111.ExtraCreditProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class that uses GUI to create a quiz that automatically generates simple math problems.
 * 
 * @author Kathleen Dy
 *
 */
public class MathMash extends JPanel {

  /**
   * Variables a and b are the 1st and second numbers in the question. Variable sign is the index that of the string in
   * the signList that coordinates the sign for all operations.
   */
  private int a;
  private int b;
  private int questionCount;
  private int multiplier;
  private int sign;
  private String[] signList;
  private int correctSum;
  private double incorrectSum;
  private int[] correct;
  private int[] incorrect;
  private int[] skipped;

  private JLabel qLabel;
  private JTextField aInput;
  private JLabel XLabel;
  private JLabel scoreLabel;
  private JLabel timerLabel;
  private JButton startButton;
  private JButton skipButton;
  private JPanel mainPanel;
  private JPanel topPanel;
  private JPanel questionPanel;
  private JPanel bottomPanel;

  private JPanel scorePanel;
  private JPanel mainScorePanel;
  private JPanel labelScorePanel;
  private JLabel[] signLabels;
  private JLabel[] correctLabels;
  private JLabel[] incorrectLabels;
  private JLabel[] skippedLabels;

  private Timer gameTimer;
  private Timer checkTimer;
  private Timer incorrectTimer;
  private int mins;
  private int secs;
  private int minsFin;
  private int secsFin;
  private JTextField minsInput;
  private JTextField secsInput;
  private JLabel timerL;
  private JPanel timerPanel;
  private JTextArea report;


  /**
   * Main method that creates a new window that allows the program to run A JOptionPane will pop up to explain the
   * instructions to the user. After the user closes the instructions, a new window will appear and the user can start
   * playing.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    JFrame window = new JFrame("Math Mash");
    JOptionPane instructions = new JOptionPane();
    JPanel mathPanel = new MathMash();
    // instructions.showMessageDialog(window,
    // "The default time is 1:00, but you can use the text boxes to set the timer. \nThe game will end when time runs
    // out.",
    // "Instructions", JOptionPane.PLAIN_MESSAGE);
    window.setContentPane(mathPanel);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(new Dimension(300, 260));
    window.setResizable(false);
    window.setVisible(true);
  }


  /**
   * Default constructor that creates a new JPanel of type MathMash(). Initializes all variables. Timers: gameTimer =
   * keeps track of the time left; counts down every second checkTimer = continuously checks the user's input
   * incorrectTimer = starts when the user inputs an incorrect answer; if the answer is still wrong 2 seconds later, it
   * is marked incorrect Sets the background color, preferred size, borders, and layouts of the various panels. Adds
   * everything to the correct areas and formats everything.
   */
  public MathMash() {
    super();
    this.correct = new int[4];
    this.incorrect = new int[4];
    this.skipped = new int[4];
    this.signLabels = new JLabel[4];
    this.correctLabels = new JLabel[5];
    this.incorrectLabels = new JLabel[5];
    this.skippedLabels = new JLabel[5];
    this.signList = new String[] { "+", "-", "*", "/" };

    qLabel = new JLabel("", JLabel.RIGHT);
    aInput = new JTextField(3);
    XLabel = new JLabel("X", JLabel.CENTER);
    scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
    timerLabel = new JLabel("1 : 00", JLabel.CENTER);
    startButton = new JButton("Start");
    skipButton = new JButton("Skip");
    mainPanel = new JPanel();
    topPanel = new JPanel();
    questionPanel = new JPanel();
    scorePanel = new JPanel();
    mainScorePanel = new JPanel();
    labelScorePanel = new JPanel();
    timerPanel = new JPanel();
    bottomPanel = new JPanel();

    minsInput = new JTextField(2);
    secsInput = new JTextField(2);
    timerL = new JLabel(" : ", JLabel.CENTER);
    report = new JTextArea("", 3, 20);
    report.setEditable(false);

    for (int i = 0; i < 4; i++) {
      signLabels[i] = new JLabel("", JLabel.CENTER);
      correctLabels[i] = new JLabel("", JLabel.CENTER);
      incorrectLabels[i] = new JLabel("", JLabel.CENTER);
      skippedLabels[i] = new JLabel("", JLabel.CENTER);
    }
    correctLabels[4] = new JLabel("Correct: ", JLabel.CENTER);
    incorrectLabels[4] = new JLabel("Incorrect: ", JLabel.CENTER);
    skippedLabels[4] = new JLabel("Skipped: ", JLabel.CENTER);

    this.skipButton.addActionListener(new ButtonListener());
    this.startButton.addActionListener(new ButtonListener());

    Color c = new Color(200, 200, 255);
    Color c1 = new Color(120, 30, 255);
    this.setBackground(c);
    mainPanel.setBackground(c);
    topPanel.setBackground(Color.DARK_GRAY);
    timerPanel.setBackground(null);
    questionPanel.setBackground(c);
    scorePanel.setBackground(c);
    bottomPanel.setBackground(c);
    scoreLabel.setForeground(Color.WHITE);
    timerL.setForeground(Color.WHITE);
    timerLabel.setForeground(Color.WHITE);

    topPanel.setPreferredSize(new Dimension(300, 35));
    questionPanel.setPreferredSize(new Dimension(300, 35));
    scorePanel.setPreferredSize(new Dimension(300, 100));
    // mainScorePanel.setPreferredSize(new Dimension(60, 100));
    bottomPanel.setPreferredSize(new Dimension(300, 35));

    mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7));
    topPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
    questionPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
    labelScorePanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 0));
    mainScorePanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
    report.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    topPanel.setLayout(new GridLayout(1, 3, 40, 0));
    questionPanel.setLayout(new GridLayout(1, 0));
    scorePanel.setLayout(new BorderLayout());
    mainScorePanel.setLayout(new GridLayout(4, 4));
    labelScorePanel.setLayout(new GridLayout(4, 1));
    bottomPanel.setLayout(new BorderLayout());

    this.add(mainPanel);

    mainPanel.add(topPanel);
    mainPanel.add(questionPanel);
    mainPanel.add(scorePanel);
    mainPanel.add(report);
    mainPanel.add(bottomPanel);
    // skipButton.setSize(new Dimension(300, 35));

    topPanel.add(scoreLabel);
    topPanel.add(timerPanel);
    topPanel.add(startButton);

    timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.X_AXIS));
    timerPanel.add(minsInput);
    timerPanel.add(timerL);
    timerPanel.add(secsInput);

    questionPanel.add(qLabel);
    questionPanel.add(aInput);
    questionPanel.add(XLabel);

    scorePanel.add(mainScorePanel, BorderLayout.CENTER);
    scorePanel.add(labelScorePanel, BorderLayout.WEST);
    labelScorePanel.add(new JLabel());
    labelScorePanel.add(correctLabels[4]);
    labelScorePanel.add(incorrectLabels[4]);
    labelScorePanel.add(skippedLabels[4]);

    fillScorePanel(signLabels);
    fillScorePanel(correctLabels);
    fillScorePanel(incorrectLabels);
    fillScorePanel(skippedLabels);

    bottomPanel.add(skipButton, BorderLayout.SOUTH);

    ActionListener check = new ActionListener() {
      /*
       * (non-Javadoc)
       * 
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      public void actionPerformed(ActionEvent evt) {
        checkAnswer();
      }
    };
    checkTimer = new Timer(100, check);

    ActionListener incorrectCheck = new ActionListener() {
      /*
       * (non-Javadoc)
       * 
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      public void actionPerformed(ActionEvent evt) {
        incorrect[sign]++;
        incorrectSum++;
        XLabel.setVisible(true);
        incorrectTimer.stop();
      }
    };
    incorrectTimer = new Timer(1000, incorrectCheck); // gives the user 1 secs before counting their answer incorrect

    ActionListener timer = new ActionListener() {
      /*
       * (non-Javadoc)
       * 
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      public void actionPerformed(ActionEvent evt) {
        // update timerLabel
        if (mins == 0 && secs == 0) {
          endGame();
        } else {
          if (secs == 0) {
            mins -= 1;
            secs = 59;
          } else {
            secs -= 1;
          }
        }

        if (mins == 0 && secs <= 10) {
          timerLabel.setForeground(Color.RED);
        }
        timerLabel.setText(String.format("%d : %02d", mins, secs));

      }
    };
    gameTimer = new Timer(1000, timer);

    reset();

  }

  /**
   * Class that is used to create a ButtonListener to respond when the user clicks any of the buttons
   * 
   * @author Kathleen Dy
   *
   */
  private class ButtonListener implements ActionListener {
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand() == "Skip") {
        skipped[sign]++;
        newQuestion();
        aInput.requestFocus();
      }

      else if (e.getActionCommand().equalsIgnoreCase("Pause")) {
        questionPanel.setVisible(false);
        gameTimer.stop();
        checkTimer.stop();
        startButton.setText("Resume");
        skipButton.setText("Restart");
      } else if (e.getActionCommand().equalsIgnoreCase("Restart")) {
        reset();
        gameTimer.stop();
        checkTimer.stop();
        startButton.setText("Start");
        skipButton.setText("Skip");
        minsInput.requestFocus();

      } else { // Start or Resume
        if (e.getActionCommand().equalsIgnoreCase("Start")) {
          skipButton.setEnabled(true);
          XLabel.setVisible(false);
          setTimer();
          topPanel.remove(timerPanel);
          topPanel.add(timerLabel, 1);
        }
        questionPanel.setVisible(true);
        gameTimer.restart();
        checkTimer.restart();
        startButton.setText("Pause");
        skipButton.setText("Skip");
        aInput.requestFocus();
      }

    }
  }


  /**
   * reset() resets the variables to the initial values of the game, so that the user can play again
   */
  private void reset() {
    for (int i = 0; i < 4; i++) {
      correct[i] = 0;
      incorrect[i] = 0;
      skipped[i] = 0;
    }

    this.questionCount = 0;
    this.correctSum = 0;
    this.incorrectSum = 0;

    scoreLabel.setText("Score: 0");
    minsInput.setText(null);
    secsInput.setText(null);

    XLabel.setVisible(false);
    report.setVisible(false);
    startButton.setEnabled(true);
    skipButton.setEnabled(false);
    questionPanel.setVisible(false);
    scorePanel.setVisible(false);
    // mainPanel.setPreferredSize(new Dimension(300, 115));
    topPanel.remove(timerLabel);
    topPanel.add(timerPanel, 1);
    timerLabel.setVisible(false);
    timerPanel.setVisible(true);
    timerLabel.setForeground(Color.WHITE);
    newQuestion();
  }


  /**
   * newQuestion() displays a new question to the user Uses Math.random to pick the sign and to generate variables a and
   * b. Displays the new question to the user and clears the answer text box. Adds 1 to the questionCount
   */
  public void newQuestion() {
    this.sign = (int) (Math.random() * 4);
    if (sign <= 1) {
      this.multiplier = 30;
    } else {
      this.multiplier = 12;
    }

    // set a, b
    this.a = (int) (Math.random() * multiplier) + 1;
    if (sign == 0 || sign == 2) { // addition, multiplication
      this.b = (int) (Math.random() * multiplier) + 1;
    } else if (sign == 1) { // subtraction
      this.b = (int) (Math.random() * a);
    } else { // division
      do {
        this.b = (int) (Math.random() * a) + 1;
      }
      while (a % b != 0);
    }

    // display question
    this.qLabel.setText(String.format("%2d  %s %2d = ", a, signList[sign], b));
    this.aInput.setText(null);
    this.XLabel.setVisible(false);

    questionCount++;
  }


  /**
   * checkAnswer() checks if the user's answer is correct If the user's answer is correct, the scoreLabel will be
   * updated, the incorrectTimer will stop, and a newQuestion will be displayed.
   */
  private void checkAnswer() {
    String answer;
    if (sign == 0) {
      answer = a + b + "";
    } else if (sign == 1) {
      answer = a - b + "";
    } else if (sign == 2) {
      answer = a * b + "";
    } else {
      answer = a / b + "";
    }
    if (answer.equalsIgnoreCase(aInput.getText().trim())) {
      correctSum++;
      correct[sign]++;
      scoreLabel.setText(String.format("Score: %2d", correctSum));
      incorrectTimer.stop();
      newQuestion();

    } else {
      if (aInput.getText().isEmpty()) {
        incorrectTimer.restart();
      }

    }

  }


  /**
   * fillScorePanel() adds the components in the JLabel[] to mainScorePanel row by row
   * 
   * @param list, type JLabel[], the array of JLabels will be added
   */
  public void fillScorePanel(JLabel[] list) {
    for (int i = 0; i < 4; i++) {
      mainScorePanel.add(list[i]);
    }
  }


  /**
   * endGame() executes all of the procedures that end the game when time is up. Stops the timers. Updates the text of
   * the score table Grades the quiz using the formula: ((correct - (0.5 * incorrect)) / questionCount) * 100
   */
  public void endGame() {
    gameTimer.stop();
    checkTimer.stop();

    for (int i = 0; i < 4; i++) {
      signLabels[i].setText(signList[i]);
      correctLabels[i].setText(correct[i] + "");
      incorrectLabels[i].setText(incorrect[i] + "");
      skippedLabels[i].setText(skipped[i] + "");
    }

    report.setVisible(true);
    questionPanel.setVisible(false);
    scorePanel.setVisible(true);
    startButton.setEnabled(false);
    skipButton.setText("Restart");
    incorrectTimer.stop();
    // mainPanel.setPreferredSize(new Dimension(300, 230));

    double grade;
    if (questionCount == 1) {
      grade = 0.0;
    } else {
      // subtract 1 from questionCount because it doesn't count the last question
      // every incorrect answer subtracts 1/2 point from correctSum
      grade = (((double) correctSum - (incorrectSum * 0.5)) / (questionCount - 1)) * 100;
      // System.out.println(correctSum + ", " + incorrectSum + ", " + questionCount);
    }
    report.setText(String.format("Total Time: %d:%02d %nTotal Correct Answers: %d %nGrade: %1.2f%%", minsFin, secsFin,
        correctSum, grade));

  }


  /**
   * setTimer() sets mins and secs to the user's input from the JTextFields If a JTF is empty, the default is mins = 1
   * and secs = 0. Updates the timer text and sets the minsFin and secsFin variables equal to the mins and secs
   * variables.
   */
  public void setTimer() {
    String minsStr = minsInput.getText().trim();
    String secsStr = secsInput.getText().trim();

    if (minsStr.isEmpty() && secsStr.isEmpty()) {
      mins = 1;
      secs = 0;
    } else {
      if (minsStr.isEmpty()) {
        mins = 0;
      } else if (testInt(minsStr) == false) {
        mins = 1;
      } else {
        if (Integer.parseInt(minsStr) >= 0 && Integer.parseInt(minsStr) <= 59) {
          mins = Integer.parseInt(minsStr);
        } else {
          mins = 1;
        }
      }

      if (secsStr.isEmpty() || testInt(secsStr) == false) {
        secs = 0;
      } else {
        if (Integer.parseInt(secsStr) >= 0 && Integer.parseInt(secsStr) <= 59) {
          secs = Integer.parseInt(secsStr);
        } else {
          secs = 0;
        }
      }
    }
    timerLabel.setText(String.format("%d : %02d", mins, secs));
    timerLabel.setVisible(true);
    timerPanel.setVisible(false);
    minsFin = mins;
    secsFin = secs;

  }


  /**
   * testInt() tests whether the String parameter is an int or not
   * 
   * @param s, type String, the String to test
   * @return true if the String is an int, false otherwise
   */
  public boolean testInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
