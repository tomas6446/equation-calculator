package org.equations;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
public class Window extends JFrame implements ActionListener {
    // range of random numbers
    private static final int min = 10;
    private static final int max = 10000;
    // create a frame
    private static JFrame jFrame;
    // create a random operands
    private static JTextField operand1;
    private static JTextField operand2;
    private static JTextField operator;
    private static JTextField solution;
    private static JTextField stateOfEquation;
    private static Button helpButton;
    private final String[] operators = {
            "+", "-", "*", "/"
    };
    private int chosenOperator;

    public Window() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        Font font = new JTextField().getFont().deriveFont(65f);
        stateOfEquation = createTextField("", font.deriveFont(40f), new Rectangle(0, 310, 310, 50), false, SwingConstants.LEFT);
        operand1 = createTextField(String.valueOf((int) (Math.random() * (max - min) + min)), font, new Rectangle(50, 0, 310, 100), false, SwingConstants.RIGHT);
        operand2 = createTextField(String.valueOf((int) (Math.random() * (max - min) + min)), font, new Rectangle(50, 100, 310, 100), false, SwingConstants.RIGHT);
        operator = createTextField(operators[chosenOperator], font, new Rectangle(0, 50, 50, 100), false, SwingConstants.RIGHT);
        solution = createTextField("", font, new Rectangle(50, 200, 310, 100), true, SwingConstants.RIGHT);
        solution.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (formatter.format(Float.parseFloat(solution.getText())).equals(formatter.format(calculate(operators[chosenOperator])))) {
                        correctSolution();
                    } else {
                        incorrectSolution();
                    }
                }
            }
        });

        helpButton = new Button("help");
        helpButton.setBounds(310, 310, 100, 50);
        helpButton.setFont(font.deriveFont(15f));
        helpButton.addActionListener(
                e -> solution.setText(formatter.format(calculate(operators[chosenOperator])))
        );

        // create a panel
        JPanel panel = new JPanel(null) {

        };
        // add elements
        panel.add(operator);
        panel.add(operand1);
        panel.add(operand2);
        panel.add(solution);
        panel.add(stateOfEquation);
        panel.add(helpButton);
        // set Background of panel
        panel.setBackground(Color.white);

        // create a frame
        jFrame = new JFrame("calculator");
        // add panel to frame
        jFrame.add(panel);
        jFrame.setLocation(600, 600);
        jFrame.setSize(400, 400);
        jFrame.show();
    }

    private void correctSolution() {
        chosenOperator++;
        if (chosenOperator == operators.length) {
            chosenOperator = 0;
        }

        operand1.setText(String.valueOf((int) (Math.random() * (max - min) + min)));
        operand2.setText(String.valueOf((int) (Math.random() * (max - min) + min)));
        solution.setText("");
        operator.setText(operators[chosenOperator]);
        stateOfEquation.setText("Correct");
    }

    private void incorrectSolution() {
        stateOfEquation.setText("Incorrect");
    }

    private JTextField createTextField(String text, Font font, Rectangle bounds, boolean editable, int alignment) {
        JTextField newTextField = new JTextField(text);
        newTextField.setFont(font);
        newTextField.setBounds(bounds);
        newTextField.setEditable(editable);
        newTextField.setHorizontalAlignment(alignment);
        return newTextField;
    }

    private Float calculate(String op) {
        return switch (op) {
            case "+" -> Float.parseFloat(operand1.getText()) + Float.parseFloat(operand2.getText());
            case "-" -> Float.parseFloat(operand1.getText()) - Float.parseFloat(operand2.getText());
            case "*" -> Float.parseFloat(operand1.getText()) * Float.parseFloat(operand2.getText());
            case "/" -> Float.parseFloat(operand1.getText()) / Float.parseFloat(operand2.getText());
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        // if the value is a number
        if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.charAt(0) == '.') {
            solution.setText(solution.getText() + s);
        }
    }
}
