import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InformationPanel extends JPanel {
    private static int totalBombs;
    private static int flags;
    private static JLabel flagsLabel;
    private static JLabel info;


    public InformationPanel(Field[] fieldsArray) {
        setLayout(new GridLayout());
        setBackground(Color.LIGHT_GRAY);
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        flags = 0;
        for (Field field : fieldsArray) {
            if (field.isBomb()) flags++;
        }
        totalBombs = flags;

        flagsLabel = new JLabel("Flags " + flags, SwingConstants.CENTER);
        flagsLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                flagsLabel.setFont(adjustFontSize());
            }
        });
        add(flagsLabel);

        info = new JLabel("Press i for tutorial ", SwingConstants.CENTER);
        info.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                info.setFont(adjustFontSize());
            }
        });
        add(info);
    }

    public static void decreaseFlags() {
        if(flags > 0) {
            flags--;
            flagsLabel.setText("Flags " + flags);
        }
    }

    public static void increaseFlags() {
        flags++;
        flagsLabel.setText("Flags " + flags);
    }

    public static int getFlags() {
        return flags;
    }

    public static int getTotalBombs() {
        return totalBombs;
    }

    private Font adjustFontSize() {
        int labelHeight = getHeight();
        float fontSize = labelHeight * 0.5f;
        return getFont().deriveFont(fontSize);
    }
}