import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
/**
 * PitIcon is the icon of the Pit, this class draws the circle.
 * 
 * @author Matrix
 *
 */
public class PitIcon implements Icon{
	
	final private int width = 70;
	final private int height = 70;
	private Color color;
	
	/**
	 * Constructs a pit icon
	 */
	public PitIcon(Color color) {
		this.color = color;
	}
	
	/**
	 * Draws a circle of width 70px
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(color);
		g2.draw(new Ellipse2D.Double(1, 1, width, width));
	}

	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
	
	/**
	 * Sets color of pit icon.
	 * @param color
	 */
	public void setColor1(Color color) {
		this.color = color;
	}

}
