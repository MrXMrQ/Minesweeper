import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class Field extends JLabel {
    private JButton button;
    private final boolean isBomb;
    private boolean isFlag;
    private int bombs;
    private final int index;
    private boolean visited;

    public Field(boolean isBomb, boolean isFlag, int bombs, int index, boolean visited) {
        this.isBomb = isBomb;
        this.isFlag = isFlag;
        this.bombs = bombs;
        this.index = index;
        this.visited = visited;

        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        button = new JButton();
        button.setBackground(Color.GREEN);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinesweeperPanel.removeField(index);
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    toggleFlag();
                }
            }
        });
        button.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        add(button);
        setBackgroundAndButtonColor();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize();
            }
        });
    }

    private void setBackgroundAndButtonColor() {
        Color labelBgColor = (index % 2 == 0) ? new Color(180, 180, 184) : new Color(199, 200, 204);
        Color buttonBgColor = (index % 2 == 0) ? new Color(210, 209, 128) : new Color(178, 179, 119);
        setBackground(labelBgColor);
        button.setBackground(buttonBgColor);
    }

    private void adjustFontSize() {
        int labelHeight = getHeight();
        float fontSize = labelHeight * 0.5f;
        Font font = getFont().deriveFont(fontSize);
        setFont(font);
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public void increaseBombs() {
        bombs++;
        if (!isBomb) setText(String.valueOf(bombs));
        Color[] col = {Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN, Color.ORANGE, Color.PINK, Color.BLACK};
        if (bombs >= 1 && bombs <= 8) {
            setForeground(col[bombs - 1]);
        } else {
            setForeground(null);
        }
    }

    public void setButtonVisible(boolean visible) {
        button.setVisible(visible);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setButtonBackground(Color color) {
        this.button.setBackground(color);
    }

    private void toggleFlag() {
        if (isFlag()) {
            setBackgroundAndButtonColor();
            setFlag(false);
            InformationPanel.increaseFlags();
        } else if (InformationPanel.getFlags() > 0){
            button.setBackground(Color.BLUE);
            setFlag(true);
            InformationPanel.decreaseFlags();
        }

        MinesweeperPanel.checkWin();
    }
}