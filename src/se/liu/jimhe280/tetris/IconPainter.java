package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.*;

public class IconPainter extends JComponent
{
    private final ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/kyckling.png"));
    private static final int IMAGE_OFFSET = 50;
    private static final int FRAME_SIZE = 300;

    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        image.paintIcon(this, g, IMAGE_OFFSET, IMAGE_OFFSET);
    }

    public static JFrame showStartScreen() {
        final JFrame frame = new JFrame("Graphics2D Test");
        frame.setLayout(new GridLayout(1,1));
        frame.add(new IconPainter());
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setVisible(true);
        return frame;
    }
}
