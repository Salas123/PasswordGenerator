import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton  extends JButton
{
    public RoundButton(String label)
    {
        super(label);

        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
// You might want to make the highlight color
            // a property of the RoundButton class.
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width-1,
                getSize().height-1);

// This call will paint the label and the
        // focus rectangle.
        super.paintComponent(g);
    }

    // Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(0, 0, getSize().width-1,
                getSize().height-1);
    }

    // Hit detection.
    Shape shape;
    public boolean contains(int x, int y) {
// If the button has changed size,
        // make a new shape object.
        if (shape == null ||
                !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0,
                    getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}
