/**
 * MancalaTester tests the mancala game.
 * 
 * @author Matrix
 *
 */
public class MancalaTester {

	/**
	 * Creates a MancalaModel and attaches the MancalaFrame
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {

		MancalaModel model = new MancalaModel();

		// MancalaFrame MancalaFrame = new MancalaFrame(model);

		MenuFrame menuFrame = new MenuFrame(model);

		// model.attach(menuFrame);
		// model.attach(gameFrame); //attach frame for allowing ChangeListener in
		// TextFrame
	}
}
