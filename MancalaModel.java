import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The model class for mancala game.
 * 
 * @author Matrix
 *  
 */
public class MancalaModel {
	
	boolean extraTurn = false;
	MancalaFrame game;
	ArrayList<ChangeListener> listeners;
	int theme;
	int rocksPerPit;
	int[] aPits = new int[7];
	int[] bPits = new int[7];
	int[] undosLeft = {3, 3};
	int aBowl = 0;
	int bBowl = 0;
	int turn;
	
	// saved data for UNDO
	int[] aPitsUndo= new int[7];
	int[] bPitsUndo = new int[7];
	int aBowlUndo = 0;
	int bBowlUndo = 0;
	
	/**
	 * Constructs a MancalaModel object
	 * @param d the data to model
	 */
	public MancalaModel() {

		listeners = new ArrayList<ChangeListener>();
		turn = 0;
		
		game = new MancalaFrame(this); // instantiate the game here
		
		this.attach(game); // attach game to changeListeners
		game.refresh();
	}

	/**
	 * Sets the theme for choosing the look of the game
	 * 
	 * @param theme the choosen theme for the game
	 */
	public void setTheme(MancalaTheme theme) {
		game.setTheme(theme);
	}

	/**
	 * Switches the turn between the players
	 */
	public void changeTurn() {

		if(turn == 0) {
			
			turn = 1;
		}
		else {
			turn = 0;
		}
	}

	/**
	 * Gets the amount of rocks in each of player A's pits
	 * @return the amount of rocks in each of player A's pits
	 */
	public int[] getAPits() {
		
		return aPits;
	}
	
	/**
	 * Gets the amount of rocks in each of player B's pits
	 * @return the amount of rocks in each of player B's pits
	 */
	public int[] getBPits() {
		
		return bPits;
	}
	
	/**
	 * Gets the amount of rocks in player A's mancala
	 * @return the amount of rocks in player A's mancala
	 */
	public int getABowl() {
		
		return aBowl;
	}
	
	/**
	 * Gets the amount of rocks in player B's mancala
	 * @return the amount of rocks in player B's mancala
	 */
	public int getBBowl() {
		
		return bBowl;
	}
	
	/**
	 * returns which player's turn it is
	 * @return the current player's turn
	 */
	public int getTurn() {
		
		return turn;
	}
	
	/**
	 * returns the initial rocks per pit
	 * @return the initial rocks per pit
	 */
	public int getRocksPerPit() {
		
		return rocksPerPit;
	}
	
	/**
	 * returns the amount of undos the players have
	 * @return the arraylist storing the data
	 */
	public int[] getUndosLeft() {
		
		return undosLeft;
	}
	
	/**
	 * Sets the amount of rocks in player A's pits
	 * @param pits the array with amount of rocks in player A's pits
	 */
	public void setAPits(int[] pits) {
		
		aPits = pits;
	}
	
	/**
	 * Sets the amount of rocks in player B's pits
	 * @param pits the array with amount of rocks in player B's pits
	 */
	public void setBPits(int[] pits) {
		
		bPits = pits;
	}
	
	/**
	 * Sets the amount of rocks in player A's mancala
	 * @param rocks the amount of rocks in player A's mancala
	 */
	public void setABowl(int rocks) {
		
		aBowl = rocks;
	}
	
	/**
	 * Sets the amount of rocks in player B's mancala
	 * @param rocks the amount of rocks in player B's mancala
	 */
	public void setBBowl(int rocks) {
		
		bBowl = rocks;
	}
	
	/**
	 * Sets the undos left for each player
	 * @param undosLeft the updated amount of undos each player has
	 */
	public void setUndosLeft(int[] undosLeft) {
		
		this.undosLeft = undosLeft;
	}
	
	/**
	 * Sets the initial rocks per pit
	 * @param n the amount of initial rocks per pit
	 */
	public void setRocksPerPit(int n) {
		rocksPerPit = n;
		
		// set all pits to n rocks
		for(int i = 0; i < 6; i++) {
			aPits[i] = n;
			bPits[i] = n;
		}
	}

	/**
	 * Refreshes game to update rocks in pits
	 */
	public void startGame() {
		game.refresh();
	}
	
	/**
	 * Model handling of move, distributes the mancalas from the selected pit
	 * @param player - 1 for A, 2 for B
	 * @param pit - which pit number was clicked
	 * @throws InterruptedException 
	 */
	public void madeMove(int player, String pitName) {
		
		// boolean to keep track of extra turn
		boolean getsExtraTurn = false;
		
		// last pit player's rock landed on
		int lastPitNumber = 7;
		int lastPitSide = 0;
		
		// save the previous state for undo
		aPitsUndo = Arrays.copyOf(aPits,7);
		bPitsUndo = Arrays.copyOf(bPits,7);
		aBowlUndo = aBowl;
		bBowlUndo = bBowl;
		
		// set pitIndex and rocks
		int pitIndex = Integer.parseInt(pitName.substring(1))-1;
		int rocks = 0;
		if(player == 1) {
			rocks = aPits[pitIndex];
			aPits[pitIndex] = 0;
			pitIndex++;
			}
		else if(player == 2) {
			rocks = bPits[pitIndex];
			bPits[pitIndex] = 0;
			pitIndex ++;
		}
		// System.out.println("there are " + rocks + " rocks"); //undo
		
		// if player 1 made a move
		if(player == 1) {
			int i = pitIndex;
			boolean pitA = true;
			while(rocks != 0) {
				
				if(i == 6 && pitA == false) {
					pitA = !pitA;
					i = 0;
				}
				if(i == 7) {
					pitA = !pitA;
					i = 0;
				}
				if(pitA) {
					getsExtraTurn = false; // turn off extraTurn when hits regular pit
					aPits[i]++;
				}
				else if(pitA == false) {
					bPits[i]++;
				}
				i++;
				rocks--;
				aBowl = aPits[6];
			}
			if(i == 7) {
				getsExtraTurn = true;
			}
			lastPitNumber = i-1;
			if(pitA == true) {
				lastPitSide = 1;
			}
			else {
				lastPitSide = 2;
			}
		}
	    // if player 2 made a move
		else if(player == 2) {
			int i = pitIndex;
			boolean pitB = true;
			while(rocks != 0) {
				
				if(i == 6 && pitB == false) {
					pitB = !pitB;
					i = 0;
				}
				if(i == 7) {
					pitB = !pitB;
					i = 0;
				}
				if(pitB) {
					getsExtraTurn = false;  // turn off extraTurn when hits regular pit
					bPits[i]++;
				}
				else if(pitB == false) {
					aPits[i]++;
				}
				i++;
				rocks--;
				bBowl = bPits[6];
			}
			if(i == 7) {
				getsExtraTurn = true;
			}
			lastPitNumber = i-1;
			if(pitB == true) {
				lastPitSide = 2;
			}
			else {
				lastPitSide = 1;
			}
		}
		
		// check if player steals everything from both pits
		//check for player A
		// System.out.println("last pit number is " + lastPitNumber + " and it is player " + player); // debug
		if(player == 1 && lastPitSide == 1 && lastPitNumber < 6) {
			if(aPits[lastPitNumber] == 1 && bPits[5-lastPitNumber] > 0) {
				game.messageLabel.append("Player A steals from both pits since they landed on their own empty \n");
				aBowl = aBowl + 1+bPits[5-lastPitNumber]; // add everything to A's bowl
				
				// clear both pits
				aPits[lastPitNumber] = 0;
				bPits[5-lastPitNumber] = 0;
				aPits[6] = aBowl; // update the a array
				game.refresh();		
			}
		}
		//check for player B
		else if(player == 2 && lastPitSide == 2 && lastPitNumber < 6) {
			if(bPits[lastPitNumber] == 1 && aPits[5-lastPitNumber] > 0) {
				game.messageLabel.append("Player B steals from both pits since they landed on their own empty \n");
				bBowl = bBowl + 1+aPits[5-lastPitNumber]; // add everything to A's bowl
				
				// clear both pits
				aPits[5-lastPitNumber] = 0;
				bPits[lastPitNumber] = 0;
				bPits[6] = bBowl; // update the b array
				game.refresh();		
			}
		}
		
		// check if a player won
		if(checkWinner()) {
			return;
		}
		if(getsExtraTurn == false) {
			extraTurn = false;
		}
		// check if player gets an extra turn
		if(getsExtraTurn) {
			extraTurn = true;
			String name = "A";
			
			if(turn == 1) {
				
				name = "B";
			}
			
			//System.out.println("player " + name + " landed their last rock into their bowl and gets an extra turn"); // undo
			
			//sets variable to 0 so player can make move again
			game.undoMove();
			
			game.messageLabel.append("Player " + name + " gets an extra turn \n");
		}
		
		game.refresh();		
	}
	
	/**
	 * Check if someone won
	 */
	public boolean checkWinner() {
		// check if all 6 of a player's pits are empty
		int aSum = 0;
		int bSum = 0;
		for(int i = 0; i < 6; i++) {
			aSum += aPits[i];
			bSum += bPits[i];
		}
		
		if(aSum == 0 || bSum == 0) {
			// System.out.println("game ended");
		}
		else {
			return false;
		}
		
		// clears rocks in the pits
		for(int i = 0; i < 6; i++) {
			aPits[i] = 0;
			bPits[i] = 0;
		}
		
		// adds all the rocks from the pits to the bowls 
		aBowl += aSum;
		bBowl += bSum;
		
		// updates messageLabel to print out who won
		if(aBowl > bBowl) {
			//System.out.println("Player A won!");
			game.messageLabel.append("Player A won with " + aBowl + " rocks compared to Player B's " + bBowl + " rocks");
		}
		else if(aBowl == bBowl) {
			//System.out.println("It's a Draw!");
			game.messageLabel.append("Player A and Player B both ended with " + bBowl + " rocks");
		}
		else {
			//System.out.println("PLayer B won!");
			game.messageLabel.append("Player B won with " + bBowl + " rocks compared to Player A's " + aBowl + " rocks");
		}
		//System.out.println("Player A: " + aBowl + " Player B: " + bBowl);
		
		game.repaint();
		
		return true;
	}
	
	
	/**
	 * Attach a listener to the Model
	 * @param c the listener
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * Change the data in the model and notifies listeners
	 */
	public void update() {

		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Sets the data to the state before move, and refreshes game
	 */
	public void undo(){
		aPits = aPitsUndo;
		bPits = bPitsUndo;
		aBowl = aBowlUndo;
		bBowl = bBowlUndo;
		game.refresh();		
	}
}