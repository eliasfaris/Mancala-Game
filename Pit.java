import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
/**
 * Pit is the JLabel that contains the PitIcon. It is the clickable pit in the game. 
 * 
 * @author Matrix
 *
 */
public class Pit extends JLabel{
	
	private String name; // e.g A1, B6
	private int rocks;
	PitIcon icon;
	
	/**
	 * Constructs the pit.
	 * @param icon - Icon to draw the circle
	 * @param name - name of the pit
	 */
	public Pit(PitIcon icon, String name) {
		super(icon);
		this.icon = icon;
		this.name = name;
		rocks = 0;
	}
	
	/**
	 * Returns name of the pit so MancalaModel knows which pit was clicked.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets amount of rocks in pit
	 * @param num the amount of rocks
	 */
	public void setRocks(int num) {
		rocks = num;
	}
	
	/**
	 * gets amount of rocks in pit
	 * @return the amount of rocks
	 */
	public int getRocks() {
		
		return rocks;
	}
	
	/**
	 * Sets color of pit icon.
	 * @param color
	 */
	public void setColor(Color color) {
		icon.setColor1(color);
	}
	
}
