package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

public class MyFrame extends JFrame {
    boolean open = false;

    public MyFrame(String TITLE, int WIDTH, int HEIGHT) {
        setVisible(true);
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFocusable(true);

        ImageIcon icon = loadIcon("Sprites/icon.jpg");
        if (icon != null) {
            setIconImage(icon.getImage());
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'i' && !open) {
                    open = true;

                    JFrame optionScreen = new JFrame();
                    optionScreen.setVisible(true);
                    optionScreen.setTitle("explanation screen");
                    optionScreen.setSize(WIDTH, HEIGHT);
                    optionScreen.setLayout(new GridLayout(3, 1));
                    optionScreen.setFocusable(true);
                    optionScreen.setResizable(false);

                    JPanel flagsPanel = new JPanel();
                    flagsPanel.setPreferredSize(new Dimension(WIDTH, 100)); // Set the preferred size
                    flagsPanel.setLayout(new FlowLayout());

                    JPanel flags = new JPanel();
                    flags.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    flags.setBackground(Color.BLUE);
                    flags.setPreferredSize(new Dimension(50, 50)); // Set the preferred size
                    flagsPanel.add(flags);

                    JLabel flagsExplanation = new JLabel("<html><div style='text-align: justify;'>In Minesweeper, the blue field can be flagged by right-clicking, and can also be removed. These flags are crucial for identifying potential mines and navigating the game board safely.</div></html>");
                    flagsExplanation.setPreferredSize(new Dimension(200, 150)); // Set the preferred size

                    flagsPanel.add(flagsExplanation);

                    optionScreen.add(flagsPanel);

                    JPanel bombsPanel = new JPanel();
                    bombsPanel.setPreferredSize(new Dimension(WIDTH, 100)); // Set the preferred size
                    bombsPanel.setLayout(new FlowLayout());

                    JPanel bombs = new JPanel();
                    bombs.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    bombs.setBackground(Color.RED);
                    bombs.setPreferredSize(new Dimension(50, 50)); // Set the preferred size
                    bombsPanel.add(bombs);

                    JLabel bombsExplanation = new JLabel("<html><div style='text-align: justify;'>The red field in Minesweeper represents bombs. Upon triggering a loss condition, all bombs are highlighted in red. Players lose the game by left-clicking on a bomb.</div></html>");
                    bombsExplanation.setPreferredSize(new Dimension(200,150));
                    bombsPanel.add(bombsExplanation);

                    optionScreen.add(bombsPanel);

                    JPanel markedBombsPanel = new JPanel();
                    markedBombsPanel.setPreferredSize(new Dimension(WIDTH, 100)); // Set the preferred size
                    markedBombsPanel.setLayout(new FlowLayout());

                    JPanel markedBombs = new JPanel();
                    markedBombs.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    markedBombs.setBackground(Color.GREEN);
                    markedBombs.setPreferredSize(new Dimension(50, 50)); // Set the preferred size
                    markedBombsPanel.add(markedBombs);

                    JLabel markedBombsExplanation = new JLabel("<html><div style='text-align: justify;'>The green fields in Minesweeper indicate whether a flag was correctly placed or not after a round. They serve to show the player whether their flag placements were accurate or if they need to adjust their strategy.</div></html>");
                    markedBombsExplanation.setPreferredSize(new Dimension(200,150));
                    markedBombsPanel.add(markedBombsExplanation);

                    optionScreen.add(markedBombsPanel);
                    optionScreen.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            open = false;
                        }
                    });
                }
            }
        });
    }

    private ImageIcon loadIcon(String path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                return new ImageIcon(ImageIO.read(inputStream));
            } else {
                System.err.println("Icon " + path + " not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
