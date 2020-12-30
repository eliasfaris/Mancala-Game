import java.awt.Color;

/**
 * ClassicMancala is a theme of Mancala, it follows the strategy pattern.
 * 
 * @author Matrix
 *
 */
public class ClassicMancala implements MancalaTheme {
	
	Color primary = new Color(180,130,24);
	Color secondary = new Color(253,237,203);
	@Override
	public Color getPrimaryColor() {
		return primary;
	}

	@Override
	public Color getSecondaryColor() {
		return secondary;
	}

}