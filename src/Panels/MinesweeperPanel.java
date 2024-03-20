package Panels;

import javax.swing.*;
import java.awt.*;
import Game.Game;

public class MinesweeperPanel extends JPanel {
    public static Field[] fieldsArray;

    public MinesweeperPanel(GridLayout gridLayout, Field[] fieldsArray) {
        MinesweeperPanel.fieldsArray = fieldsArray;

        setLayout(gridLayout);
        count(fieldsArray);

        for (Field field : fieldsArray) {
            if (field.isBomb()) field.setBombs(0);
            add(field);
        }
    }

    private void count(Field[] fieldsArray) {
        int row = (int) Math.sqrt(fieldsArray.length);

        for (int i = 0; i <= fieldsArray.length - row; i += row) {
            for (int j = 0; j <= row - 1; j++) {
                if (fieldsArray[i + j].isBomb()) {

                    // Access to the field left and right
                    if (j < row - 1) fieldsArray[i + j + 1].increaseBombs();
                    if (j > 0) fieldsArray[i + j - 1].increaseBombs();

                    // Access to the row above the bomb
                    if (i >= row) {
                        fieldsArray[i + j - row].increaseBombs();
                        if (j < row - 1) fieldsArray[i + j + 1 - row].increaseBombs();
                        if (j > 0) fieldsArray[i + j - 1 - row].increaseBombs();
                    }

                    // Access to the row below the bomb
                    if (i <= fieldsArray.length - row - 1) {
                        fieldsArray[i + j + row].increaseBombs();
                        if (j < row - 1) fieldsArray[i + j + 1 + row].increaseBombs();
                        if (j > 0) fieldsArray[i + j - 1 + row].increaseBombs();
                    }
                }
            }
        }
    }

    public static void removeField(int index) {
        if (fieldsArray[index].isBomb() && !fieldsArray[index].isFlag()) {
            for (Field field : fieldsArray) {
                if (field.isBomb() && field.isFlag()) field.setButtonBackground(Color.GREEN);
                else if(field.isBomb())field.setButtonBackground(Color.RED);
            }
            int result = JOptionPane.showConfirmDialog(Game.myFrame, "GAME OVER! Play again?", "Confirmation", JOptionPane.YES_NO_OPTION);
            Game.restart(result);
        } else if (index >= 0 && index < fieldsArray.length && !fieldsArray[index].isBomb() && fieldsArray[index].isNotVisited() && !fieldsArray[index].isFlag()) {
            fieldsArray[index].setVisited(true);
            fieldsArray[index].setButtonVisible(false);

            int rowLength = (int) Math.sqrt(fieldsArray.length);

            int column = index % rowLength;
            int row = index / rowLength;

            if (fieldsArray[index].getBombs() > 0) return;

            if (column > 0) removeField(index - 1);
            if (column < rowLength - 1) removeField(index + 1);
            if (row > 0) removeField(index - rowLength);
            if (row < fieldsArray.length / rowLength - 1) removeField(index + rowLength);

            if (row > 0 && column > 0) removeField(index - rowLength - 1);
            if (row > 0 && column < rowLength - 1) removeField(index - rowLength + 1);
            if (row < fieldsArray.length / rowLength - 1 && column > 0) removeField(index + rowLength - 1);
            if (row < fieldsArray.length / rowLength - 1 && column < rowLength - 1) removeField(index + rowLength + 1);
        }
    }

    public static void checkWin() {
        int remainingBombs = 0;
        int unvisitedFields = 0;

        for (Field field : fieldsArray) {
            if (field.isBomb() && field.isNotVisited()) {
                remainingBombs++;
            }
            if(field.isNotVisited()) {
                unvisitedFields++;
            }
        }

        if(remainingBombs == unvisitedFields) {
            int result = JOptionPane.showConfirmDialog(Game.myFrame, "You won! Play again?", "Confirmation", JOptionPane.YES_NO_OPTION);
            Game.restart(result);
        }
    }
}