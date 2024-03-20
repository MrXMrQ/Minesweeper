package Game;

import Panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Game {
    public static final MyFrame myFrame = new MyFrame("Minesweeper", 500, 500);
    public static int GRID_SIZE = size(); // 55 should be the maximum

    public Game() {
        init();
    }

    private static void init() {
        Field[] fieldsArray = createFieldsArray();

        InformationPanel informationPanel = new InformationPanel(fieldsArray);
        MinesweeperPanel minesweeperPanel = new MinesweeperPanel(new GridLayout(GRID_SIZE, GRID_SIZE), fieldsArray);

        informationPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                informationPanel.setPreferredSize(new Dimension(0, (int) (myFrame.getHeight() * 0.1)));
            }
        });
        myFrame.add(informationPanel, BorderLayout.NORTH);
        myFrame.add(minesweeperPanel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }

    private static Field[] createFieldsArray() {
        Field[] fieldsArray = new Field[GRID_SIZE * GRID_SIZE];
        int mid = fieldsArray.length / 2;

        for (int i = 0; i < fieldsArray.length; i++) {
            fieldsArray[i] = new Field(generateBoolean(), false, 0, i, false);
        }

        fieldsArray[mid].setBomb(false);
        fieldsArray[mid + 1].setBomb(false);
        fieldsArray[mid - 1].setBomb(false);

        fieldsArray[mid - (int) Math.sqrt(fieldsArray.length)].setBomb(false);
        fieldsArray[mid - (int) Math.sqrt(fieldsArray.length) - 1].setBomb(false);
        fieldsArray[mid - (int) Math.sqrt(fieldsArray.length) + 1].setBomb(false);

        fieldsArray[mid + (int) Math.sqrt(fieldsArray.length)].setBomb(false);
        fieldsArray[mid + (int) Math.sqrt(fieldsArray.length) - 1].setBomb(false);
        fieldsArray[mid + (int) Math.sqrt(fieldsArray.length) + 1].setBomb(false);

        return fieldsArray;
    }

    private static boolean generateBoolean() {
        int random = (int) (Math.random() * 100);
        return random <= 15; // 10 - 20
    }

    public static void restart(int result) {
        if (result == JOptionPane.YES_OPTION) {
            myFrame.getContentPane().removeAll();
            GRID_SIZE = size();
            init();
        } else {
            System.exit(0);
        }
    }

    private static int size() {
        String userInput = JOptionPane.showInputDialog(myFrame, "Enter how many fields you want in your game to explore (25 - 2025)", "Fields", JOptionPane.QUESTION_MESSAGE);

        if (userInput == null) System.exit(0);

        try {
            int input = Integer.parseInt(userInput);
            if (input >= 25 && input <= 2025) {
                return (int) Math.sqrt(input);
            } else {
                JOptionPane.showMessageDialog(myFrame, "Invalid input! Please enter an integer between 25 and 2055.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(myFrame, "Invalid input! Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return size();
    }
}
