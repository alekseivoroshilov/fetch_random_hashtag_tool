package org.voroshiloa;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class Run {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Number Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JTextField numberField = new JTextField(10);
        JButton calculateButton = new JButton("Copy random words");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = numberField.getText();
                try {
                    int number = Integer.parseInt(inputText);
                    List<String> result = calculateResult(number);
                    String resultText = Arrays.toString(result.toArray())
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "");

                    // Copy result to clipboard
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                            new StringSelection(resultText), null);

                    JOptionPane.showMessageDialog(frame,
                            "Ready to paste: " + resultText + " (copied to clipboard)",
                            "Calculation Result",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Invalid input. Please enter a valid number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(numberField);
        panel.add(calculateButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static List<String> calculateResult(int number) {
        String filePath = "./input.txt";

        FileHelper fileHelper = new FileHelper();
        List<String> words = fileHelper.readWordsFromFile(filePath);
        List<String> randomWords = fileHelper.selectRandomWords(words, number);
        fileHelper.writeWordsToFile(randomWords, "./output.txt");
        return randomWords;
    }


}