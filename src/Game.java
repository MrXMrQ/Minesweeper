import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Game {
    private static final MyFrame myFrame = new MyFrame("Minesweeper", 500, 500);
    private static final int GRID_SIZE = 15; // 55 should be the maximum

    public Game() {
        init();
    }

    private static void init() {
        Field[] fieldsArray = createFieldsArray();

        InformationPanel informationPanel = new InformationPanel(fieldsArray);
        informationPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                informationPanel.setPreferredSize(new Dimension(0, (int) (myFrame.getHeight() * 0.1)));
            }
        });
        MinesweeperPanel minesweeperPanel = new MinesweeperPanel(new GridLayout(GRID_SIZE, GRID_SIZE), fieldsArray);

        myFrame.add(informationPanel, BorderLayout.NORTH);
        myFrame.add(minesweeperPanel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }

    private static Field[] createFieldsArray() {
        Field[] fieldsArray = new Field[GRID_SIZE * GRID_SIZE];
        for (int i = 0; i < fieldsArray.length; i++) {
            fieldsArray[i] = new Field(generateBoolean(), false, 0, i, false);
        }
        return fieldsArray;
    }

    private static boolean generateBoolean() {
        int random = (int) (Math.random() * 100);
        return random <= 10; // 10 - 20
    }

    public static void restart(int result) {
        if (result == JOptionPane.YES_OPTION) {
            myFrame.getContentPane().removeAll();
            init();
        } else {
            System.exit(0);
        }
    }
}
