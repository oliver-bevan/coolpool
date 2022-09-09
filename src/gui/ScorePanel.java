package gui;

import java.awt.GridLayout; //Import necessary libraries
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic_classes.Player;

public class ScorePanel {
	private Player player; //Create instances required for ScorePanel
	private Manager gui; //Reference GUIManager gui to swap JPanels
	private long startTime;
	private long endTime;
	private long score;
	private JButton start;
	private JPanel scorePanel;
	private ArrayList<Player> players;

	public ScorePanel(Player player, Manager gui) {
		this.gui = gui; //Set instance variable
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(4, 3)); //Prepare scorePanel
		
		JButton exitButton = new JButton("Exit");
		start = new JButton("Play");	// Prepare JButton
		
		this.player = player;	//Get instance of player parsed in
		players = new ArrayList<Player>();

		exitButton.addActionListener(new ActionListener() { //Add an action listener to the exit button

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(scorePanel, "Are you sure you want to close the game?", //Check confirmation of game closure
						"Close game?", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					System.exit(0); //Exit the program
				}
			}
		});

		start.addActionListener(new ActionListener() {	//Add an action listener to the start button
			@Override
			public void actionPerformed(ActionEvent e) {
				play();	//When button pressed run play function
			}
		});
	
		scorePanel.add(start);
		scorePanel.add(exitButton); //Add buttons and label to the scorePanel
	}

	public JPanel getScorePanel() {
		return scorePanel;	//Return scorePanel
	}

	public void disqualify(String string) {
		JOptionPane.showMessageDialog(scorePanel, string, "You were disqualified", JOptionPane.ERROR_MESSAGE);	//Message user disqualified
		int choice = JOptionPane.showConfirmDialog(scorePanel, "Play again?", "Restart?", JOptionPane.YES_NO_OPTION); //Ask user to play again or exit
		if (choice == 0) {
			gui.update(); //Reset gamePanel
		} else {
			System.exit(0); //Exit program
		}
		gui.getGamePanel().lock(); //After reseting game panel lock game panel until game starts
	}

	public void won() {
		endTime = System.currentTimeMillis(); //End timer
		score = startTime - endTime; //Calculate score/time from start and end times
		score = score / 1000; //Get time in seconds.
		score = score*-1;
		gui.getGamePanel().lock();
		
		JOptionPane.showMessageDialog(scorePanel, "Your score was: " + score, "Your Score", JOptionPane.PLAIN_MESSAGE); //Message user score
		
		player.setTime((int) score);	//Set the instance of player class' score
		players.add(player); //Add to list of players
		
		JOptionPane.showMessageDialog(scorePanel, "Saving scores and exiting", "End", JOptionPane.INFORMATION_MESSAGE); // Message user end game and save score
		try {
			gui.getLeaderboardPanel().saveScores(players);	//Attempt to save scores
		} catch (IOException e) {
			JOptionPane.showMessageDialog(scorePanel, e.getStackTrace(), "Error: IOException", JOptionPane.ERROR_MESSAGE); //Notify user of IOException error
			e.printStackTrace(); //Print stack trace
		}
		System.exit(0); //Upon saving scores exit program
	}

	public void play() {	//Start playing the game
		gui.getGamePanel().unlock(); //Allow user to access game panel
		startTime = System.currentTimeMillis(); //Begin score timer
	}
}
