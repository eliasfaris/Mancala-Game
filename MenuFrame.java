import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
/**
 * MenuFrame is the JFrame for the menu of the game.
 * 
 * @author Matrix
 */
public class MenuFrame extends JFrame implements ChangeListener {
	MancalaModel MancalaModel;

	/**
	 * Constructs the menu frame.
	 * 
	 * @param d the model to display
	 */
	public MenuFrame(MancalaModel d) {

		MancalaModel = d;
		JPanel panel;
		JButton retro, classic, play;
		JLabel welcomeMsg, boardDesignMsg, numPitsMsg;
		JTextField numOfPits;

		Color green = new Color(0, 100, 0); // green background for retro
		Color brown = new Color(180,130,24); // brown background for classic
		Color white = new Color(255, 255, 255); // white color for font
		Color cream = new Color(253,237,203);
		Color grey = new Color(56,56,56);
		Color neongreen = new Color(131,255,17);


		setTitle("Menu");
		setSize(500, 500); // Set size to 400x400 pixels

		// Create panel, buttons, labels, & text field
		panel = new JPanel(null);
		retro = new JButton("Retro");
		classic = new JButton("Classic");
		play = new JButton("Play");

		welcomeMsg = new JLabel("Mancala Game");
		boardDesignMsg = new JLabel("Choose board design");
		numPitsMsg = new JLabel("How many starting mancalas in each pit?");

		numOfPits = new JTextField(8);

		// add panel to frame
		add(panel);
		panel.setBounds(0, 0, 320, 335);

		// add & design retro button
		panel.add(retro);
		retro.setBounds(250, 150, 175, 75);
		retro.setBackground(grey);
		retro.setForeground(neongreen);
		retro.setFont(new Font("Serif", Font.BOLD, 20));

		// add & design classic button
		panel.add(classic);
		classic.setBounds(50, 150, 175, 75);
		classic.setBackground(brown);
		classic.setForeground(cream);
		classic.setFont(new Font("Serif", Font.BOLD, 20));

		// add & design play button
		panel.add(play);
		play.setBounds(150, 350, 175, 75);
		play.setBackground(white);
		play.setForeground(Color.BLACK);
		play.setFont(new Font("Serif", Font.BOLD, 20));

		// add & design welcome label
		panel.add(welcomeMsg);
		welcomeMsg.setFont(new Font("Serif", Font.BOLD, 28));
		welcomeMsg.setBounds(150, 5, 400, 75);

		// add & design board design label
		panel.add(boardDesignMsg);
		boardDesignMsg.setFont(new Font("Serif", Font.BOLD, 16));
		boardDesignMsg.setBounds(25, 50, 150, 150);

		// add & design number of pits label
		panel.add(numPitsMsg);
		numPitsMsg.setFont(new Font("Serif", Font.BOLD, 16));
		numPitsMsg.setBounds(25, 135, 300, 300);

		// add & design number of pits text field
		panel.add(numOfPits);
		numOfPits.setBounds(325, 275, 40, 25);

		/*
		 * Action Listener Zone
		 */

		// action listener for clicking the classic button
		classic.addActionListener((e) -> {
			MancalaModel.setTheme(new ClassicMancala());

		});

		retro.addActionListener((e) -> {
			MancalaModel.setTheme(new RetroMancala());
		});

		// action listener for clicking play button, it will start game with the given
		// parameters
		play.addActionListener((e) -> {
			
			int rocksPerPit = Integer.parseInt(numOfPits.getText());
			if(rocksPerPit >= 3 && rocksPerPit <= 4) {
				
				MancalaModel.setRocksPerPit(rocksPerPit); // set rocks per pit from JTextField
				MancalaModel.startGame(); // call start game from model
				this.setVisible(false); // hide menu for the rest of the game
			}
			else {
				
				JDialog error = new JDialog(this, "Error");
				JLabel msg = new JLabel("Only 3 or 4 initial rocks per pit allowed");
				error.add(msg);
				error.setLocationRelativeTo(null);
				error.pack();
				error.setVisible(true);
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // centers the frame to middle of users computer
		setVisible(true);
	}

	/**
	 * Called when state is changed.
	 * 
	 * @param e - event representing change
	 */
	public void stateChanged(ChangeEvent e) {

	}

}