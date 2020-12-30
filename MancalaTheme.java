import java.awt.Color;
/**
 * Interface for MancalaTheme strategy pattern.
 * @author Matrix
 *
 */
public interface MancalaTheme {
	/**
	 * Returns the primary color.
	 * @return - primary color
	 */
	Color getPrimaryColor();
	
	/**
	 * Returns the secondary color.
	 * @return - secondary color
	 */
	Color getSecondaryColor();
}
