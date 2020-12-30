import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

/**
 * MancalaFrame is the View and Controller for the mancala game.
 * 
 * @author Matrix
 *
 */
public class MancalaFrame extends JFrame implements ChangeListener {
	
	Color primaryColor = new Color(155,155,155);
	Color secondaryColor = new Color(0,0,0);
	private MancalaTheme theme;
	private MancalaModel MancalaModel;
	private static final int ICON_WIDTH = 900;
	private static final int ICON_HEIGHT = 450;
	JPanel topPanel, bottomPanel, middlePanel;
	JButton undoButton, endTurn;
	JLabel turnLabel;
	JLabel announcement = new JLabel("Annoucements");
	JLabel playerA, playerB, playerA_pits, playerB_pits;
	JTextArea messageLabel;
	private int madeMove = 0;
	int[] undosLeft;
	ArrayList<Pit> pits;
	ArrayList<Pit> mancalas;
	boolean start;
	
	/**
	 * Sets the colors based on MancalaTheme
	 * @param theme
	 */
	public void setTheme(MancalaTheme theme) {
		this.theme = theme;
		primaryColor = theme.getPrimaryColor();
		secondaryColor = theme.getSecondaryColor();
		
		this.getContentPane().setBackground(primaryColor);
		topPanel.setBackground(primaryColor);
		bottomPanel.setBackground(primaryColor);
		announcement.setForeground(secondaryColor);
		turnLabel.setForeground(secondaryColor);
		playerA.setForeground(Color.white); 
		playerB.setForeground(Color.white);
		playerA_pits.setForeground(Color.white);
		playerB_pits.setForeground(Color.white);
		repaint();

		for(Pit p: pits) {
			p.setColor(secondaryColor);
			p.repaint();
		}
		for(Pit p: mancalas) {
			p.setColor(secondaryColor);
			p.repaint();
		}
		repaint();
	}
	
	/**
	 * Constructs a MancalaFrame object
	 * @param MancalaModel the model for the game
	 */
	public MancalaFrame(MancalaModel model) {
		this.setLayout(null); // no preselected layout, we choose the positioning
		setSize(ICON_WIDTH, ICON_HEIGHT);
		setTitle("Mancala");
		
		MancalaModel	  = model;
		mancalas 		  = new ArrayList<Pit>();
		pits 			  = new ArrayList<Pit>();
		start 			  = true;
		
		undosLeft = MancalaModel.getUndosLeft();

		//add in the top label
		topPanel = new JPanel(null);
		topPanel.setBounds(0, 0, 900, 50);
		add(topPanel);
		
		//add player turn label 
		turnLabel = new JLabel("Player A's Turn");
		turnLabel.setFont(new Font("Serif", Font.BOLD, 18));
		turnLabel.setForeground(Color.black);
		turnLabel.setBounds(10, 0, 200, 50);
		topPanel.add(turnLabel);

		//add in end turn button
		endTurn = new JButton("End Turn");
		endTurn.setBounds(ICON_WIDTH - 120, 10, 90, 30);
		endTurn.setBackground(Color.RED);
		endTurn.setForeground(Color.WHITE);
		topPanel.add(endTurn);
		
		//add listener to end turn button
		endTurn.addActionListener((e)-> {
			
			if(madeMove == 1) {
				
				//update the pits and mancalas with the new data from the model
				//which contains the new data after the player makes a move
				for(int i = 0; i < pits.size(); i++) {
					
					if(i < 6) {
						
						pits.get(i).setRocks(MancalaModel.getBPits()[i]);
					}
					else {
						
						pits.get(i).setRocks(MancalaModel.getAPits()[i - 6]);
					}
				}
				mancalas.get(0).setRocks(MancalaModel.getBBowl());
				mancalas.get(1).setRocks(MancalaModel.getABowl());
				
				
				//update the visual player label
				if(MancalaModel.getTurn() == 0) {
					
					turnLabel.setText("Player B's Turn");
					messageLabel.append("Player A ended turn \n");
				}
				else {
					
					turnLabel.setText("Player A's Turn");
					messageLabel.append("Player B ended turn \n");
				}
				
				//reset the undos
				undosLeft[0] = 3;
				undosLeft[1] = 3;
				
				//changes the turn
				MancalaModel.changeTurn();
				
				//update the visual undo button
				undoButton.setText("Undo (" + undosLeft[MancalaModel.getTurn()] + " remaining)");
				
				//check if next player has undo's remaining, if not disable the button
				if(undosLeft[MancalaModel.getTurn()] == 0){
					
					undoButton.setEnabled(false); //disable the undo button
				}
				else {
					
					undoButton.setEnabled(true);
				}
				
				madeMove = 0;
			}
		});
		
		// set up bottom panel with undo button and message
		bottomPanel = new JPanel(null);
		this.add(bottomPanel);
		bottomPanel.setBounds(0, 350, 900, 200);
		
		//add in undo button
		undoButton = new JButton("Undo (" + undosLeft[MancalaModel.getTurn()] + " remaining)");
		undoButton.setBackground(Color.orange);
		undoButton.setForeground(Color.black);
		bottomPanel.add(undoButton);
		undoButton.setBounds(20, 0, 180, 60);
		
		// set background color 
		this.getContentPane().setBackground(primaryColor);
		topPanel.setBackground(primaryColor);
		bottomPanel.setBackground(primaryColor);
		
		//add in listener to undo button
		undoButton.addActionListener((e)-> {

			//only works if player has undo's left and made a move
			if(undosLeft[MancalaModel.getTurn()] > 0 && madeMove == 1 || MancalaModel.extraTurn == true) {
				MancalaModel.extraTurn = false;
				// Call undo from model
				MancalaModel.undo();
				
				//update undo count
				undosLeft[MancalaModel.getTurn()] = undosLeft[MancalaModel.getTurn()] - 1;
				MancalaModel.setUndosLeft(undosLeft);
				MancalaModel.update();
				
				undoMove();
				
				//update the visual undo button
				undoButton.setText("Undo (" + undosLeft[MancalaModel.getTurn()] + " remaining)");
				
				if(undosLeft[MancalaModel.getTurn()] == 0) {
					
					undoButton.setEnabled(false);
				}
				
				if(MancalaModel.getTurn() == 0) {
					
					messageLabel.append("Player A undo'ed previous move \n");
				}
				else {
					
					messageLabel.append("Player B undo'ed previous move \n");
				}
			}
		});
		
		// mouse listener for the pit Jlabels
		MouseListener pitListener = new MouseListener() {
			// listener when player clicks on a pit
			/**
			 * Listener when player clicks on a pit
			 */
			public void mousePressed(MouseEvent e) {

				Pit p = (Pit) e.getSource();
				// System.out.println(madeMove + " " + p.getRocks()); // debug
				
				/* can only click one pit per turn, unless player undos or gets extra turn
				   can only click on pits with rocks */
				int rocks = 0;
				if(p.getName().substring(0,1).equals("A")){
					rocks = MancalaModel.aPits[Integer.parseInt(p.getName().substring(1))-1];
				}
				else {
					rocks = MancalaModel.bPits[Integer.parseInt(p.getName().substring(1))-1];
				}
				
				if(madeMove == 0 && rocks != 0) {
					
					//gets player turn
					int turn = MancalaModel.getTurn();
					
					//can only click on pits belonging to player
					if(turn == 0) {
						
						if(p.getName().startsWith("A")) {
							
							//update variable
							madeMove();
							
							// update model that move was made, model will work with data
							MancalaModel.madeMove(1, p.getName());
						
							repaint();
							// System.out.println("Clicked on pit " + p.getName()); // debug
						}
					}
					else {
						
						if(p.getName().startsWith("B")) {

							//update variable
							madeMove();
							
							// update model that move was made, model will work with data
							MancalaModel.madeMove(2, p.getName());

							repaint();
							// System.out.println("Clicked on pit " + p.getName()); debug
						}
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};


		//create announcements
		messageLabel = new JTextArea();
		messageLabel.setEditable(false);
		
		//make scrollable
		JScrollPane pane = new JScrollPane(messageLabel);
		pane.setBounds(220, 0, 650, 60);
		
		//add announcements
		bottomPanel.add(pane);
		
		//this panel incorporates the game board, & pits
		middlePanel = new JPanel(null) {
			
			//JLabel playerA, playerB, playerA_pits, playerB_pits;
			
			/**
			 * Sets up or refreshes the view of the game.
			 */
			public void paintComponent(Graphics g) {
				
				
		        Graphics2D g2 = (Graphics2D) g;
		        g2.setStroke(new BasicStroke(7));//thicken boarder
		        g2.setColor(secondaryColor);

		        int board_x = 65;
		        int board_y = 10;
		        int board_width = 740;
		        int board_height = 250;

		        // Draw the game board rectangle
		        g2.draw(new Rectangle2D.Double(board_x, board_y, board_width, board_height));
		        
		        
		        //set up pits in game board
		        g2.setStroke(new BasicStroke(4));
		        int pit_x = 150;
		        int pit_y = 50;
		        int pit_width = 70;
		        int pit_height = 70;
		        int pit_newX = 150;
		        int pit_newY = 150;
		        int pit_BNum = 6; //label for B pits
		        int pit_ANum = 1; //label for A pits
		        
		        //make 12 pits on board
		        for(int i = 0; i < 12; i++) {
		        	if(i < 6) {
		        		
		        		//set up player B pits
		        		//add label
		        		playerB_pits = new JLabel("B" + pit_BNum);
		        		playerB_pits.setBounds(pit_x + 25, pit_y - 50, pit_width, pit_height);
		        		playerB_pits.setFont(new Font("Serif", Font.BOLD, 18));
		        		playerB_pits.setForeground(Color.WHITE);
		        		middlePanel.add(playerB_pits);
		        	
		        		if(start) {
		        			
		        			// setting up the clickable Pits
			        		Pit B = new Pit(new PitIcon(secondaryColor), "B" + (6 - i));	// construct Pit and give it name
			        		B.setBounds(pit_x, pit_y, 90, 90);			// set the location of the Pit
			        		B.addMouseListener(pitListener);
			        		middlePanel.add(B);		
			        		pits.add(B);
		        		}
		        		
		        		//draws rocks in pits
		        		int rockRow		 = 0;
		        		
		        		for(int r = 0; r < MancalaModel.getBPits()[5-i]; r++) {
		        			
		        			if(r % 4 == 0 && r != 0) {

				        		rockRow++;
		        			}
			        		
		        			g2.draw(new Ellipse2D.Double((pit_x + pit_width/5 + 12*(r%4)), pit_y + (pit_height/5) + 12*rockRow, 10, 10));
		        		}
		        		
		        		pit_x += 100;
		        		pit_BNum -= 1;
		        	}
		        	else {
		        		
		        		//set up player A pits
		        		//add label
		        		playerA_pits = new JLabel("A" + pit_ANum);
		        		playerA_pits.setBounds(pit_newX + 25, pit_newY + 50, pit_width, pit_height);
		        		playerA_pits.setFont(new Font("Serif", Font.BOLD, 18));
		        		playerA_pits.setForeground(Color.WHITE);
		        		middlePanel.add(playerA_pits);
		        		
		        		if(start) {
		        			
		        			//setting up the clickable Pits
			        		Pit A = new Pit(new PitIcon(secondaryColor), "A" + (i - 5));	// construct Pit and give it name
			        		A.setBounds(pit_newX, pit_newY, 90, 90);					// set the location of the Pit
			        		A.addMouseListener(pitListener);
			        		A.setRocks(MancalaModel.getAPits()[i - 6]);
			        		middlePanel.add(A);		
			        		pits.add(A);
		        		}
		        		
		        		//draws rocks in pits
		        		int rockRow		 = 0;
		        		
		        		for(int r = 0; r < MancalaModel.getAPits()[i - 6]; r++) {
		        			
		        			if(r % 4 == 0 && r != 0) {

				        		rockRow++;
		        			}
			        		
		        			g2.draw(new Ellipse2D.Double((pit_newX + pit_width/5 + 12*(r%4)), pit_newY + (pit_height/5) + 12*rockRow, 10, 10));
		        		}
		        		
		        		pit_newX += 100;
		        		pit_ANum += 1;
		        	}
		        }
		        
		        //x-coordinate for player B's mancala
		        int mancala_x = 75;
		        
		        //create mancala for player B
		        g2.draw(new Ellipse2D.Double(mancala_x,20, 65, 225));
		        
		        //draw the visual player label
		        playerB = new JLabel("B");
		        playerB.setBounds(mancala_x + 27, 0, pit_width, pit_height);
		        playerB.setFont(new Font("Serif", Font.BOLD, 18));
        		playerB.setForeground(Color.WHITE);
		        middlePanel.add(playerB);
		        
		        //draw the rocks in player B's mancala
		        int rockRow		 = 0;
		        
        		for(int r = 0; r < MancalaModel.getBBowl(); r++) {
        			
        			if(r % 3 == 0 && r != 0) {

		        		rockRow++;
        			}
	        		
        			g2.draw(new Ellipse2D.Double((mancala_x + 14 + 12*(r%3)), 20 + 45 + 12*rockRow, 10, 10));
        		}
		        
        		if(start) {
        			
        			Pit bMancala = new Pit(new PitIcon(secondaryColor), "bBowl");
            		mancalas.add(bMancala);
        		}
        		
        		
        		//adjust the x coordinate to the opposite mancala
        		mancala_x = mancala_x + 655;
        		
        		//create mancala for player A
		        g2.draw(new Ellipse2D.Double(mancala_x,20, 65, 225));
		        
		        //draw the visual player label
		        playerA = new JLabel("A");
		        playerA.setBounds(mancala_x + 27, 0, pit_width, pit_height);
		        playerA.setFont(new Font("Serif", Font.BOLD, 18));
        		playerA.setForeground(Color.WHITE);
		        middlePanel.add(playerA);
		        
        		//draw in the rocks for Player A's mancala
		        rockRow		 = 0;
        		for(int r = 0; r < MancalaModel.getABowl(); r++) {
        			
        			if(r % 3 == 0 && r != 0) {

		        		rockRow++;
        			}
	        		
        			g2.draw(new Ellipse2D.Double((mancala_x + 14 + 12*(r%3)), 20 + 45 + 12*rockRow, 10, 10));
        		}

        		if(start) {
        			
        			Pit aMancala = new Pit(new PitIcon(secondaryColor), "aBowl");
            		mancalas.add(aMancala);
            		
            		start = false;
        		}
        		
		    }
			
		};
		
		
		
		// announcement label
		//JLabel announcement = new JLabel("Annoucements");
		//announcement.setForeground(secondaryColor);
		announcement.setBounds(450, 280, 150, 20);
        announcement.setFont(new Font("Serif", Font.BOLD, 18));
		middlePanel.add(announcement);
		
		//add the middle panel
		this.add(middlePanel);
		middlePanel.setBounds(0,50,900,500);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // centers the frame to middle of users computer
		setVisible(true);
		setResizable(false);
	}
	
	/**
	 * Updates the madeMove variable.
	 */
	public void madeMove() {
		
		madeMove = 1;
	}
	
	/**
	 * Updates the madeMove variable.
	 */
	public void undoMove() {
		
		madeMove = 0;
	}
	
	
	/**
	 * Calls repaint on the game.
	 */
	public void refresh() {
		repaint();
	}

	/**
	 * Called when the data in the model is changed.
	 * 
	 * @param e the event representing the change
	 */
	public void stateChanged(ChangeEvent e) {
		/*
		 * a = MancalaModel.getData();
		 */
		repaint();
	}

}