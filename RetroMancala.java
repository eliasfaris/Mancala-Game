import java.awt.Color;

/**
 * RetroMancala is a theme of MancalaFrame, it follows the strategy pattern.
 * 
 * @author Matrix
 *
 */
public class RetroMancala implements MancalaTheme {
	
	Color primary = new Color(56,56,56);
	Color secondary = new Color(131,255,17);
	
	@Override
	public Color getPrimaryColor() {
		return primary;
	}

	@Override
	public Color getSecondaryColor() {
		return secondary;
	}
}