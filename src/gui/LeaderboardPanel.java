package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic_classes.Player;
import quicksort.QuickSort;

public class LeaderboardPanel {

	ArrayList<Player> players;
	JPanel mainPanel;
	JPanel leaderboardPanel; // Define instance variables
	JPanel exitPanel;
	Manager gui;
	QuickSort<Player> sorter;

	public LeaderboardPanel(Manager gui) {
		this.gui = gui;
		exitPanel = new JPanel();
		exitPanel.setLayout(new GridLayout(1, 1)); // Set instance variables
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		players = new ArrayList<Player>();

		JButton exitButton = new JButton("Back to start"); // Add exit button
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.swapToStart(); // Swap back to start when button pressed
			}
		});

		leaderboardPanel = new JPanel();
		leaderboardPanel.setLayout(new GridLayout(2, 4));
		exitPanel.setPreferredSize(new Dimension(600, 100)); // Add components to panel
		exitPanel.add(exitButton);
		mainPanel.add(exitPanel, BorderLayout.NORTH);
		mainPanel.add(leaderboardPanel);
	}

	public void saveScores(ArrayList<Player> newPlayers) throws IOException {
		FileWriter fileWriter = new FileWriter("PlayerTimes.csv", true); // Create FileWriter and BufferedWriter to
																			// access file
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < newPlayers.size(); i++) {
			bufferedWriter.write((newPlayers.get(i)).getName() + "," + newPlayers.get(i).getTime()); // Run through new
																										// players
																										// parsed
																										// through and
																										// write them to
																										// file
			bufferedWriter.newLine();
		}

		bufferedWriter.close(); // Close FileWriter and BufferedWriter
		fileWriter.close();
	}

	public void loadScores() throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader("PlayerTimes.csv"); // Create FileReader and BufferedReader to access and
																	// read file
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		players = new ArrayList<Player>();
		String line;
		String[] values;

		while ((line = bufferedReader.readLine()) != null) { // While the current line in flie is not null, get the
																// values and create new player with time and name
			values = line.split(",");
			players.add(new Player(values[0], Integer.parseInt(values[1]))); // Add player to players list
		}
		bufferedReader.close(); // Close FileReader and BufferedReader
		fileReader.close();
	}

	public void displayScores() {
		try {
			loadScores(); // Try to load scores
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(leaderboardPanel, e.getStackTrace(), "Error: NumberFormatException",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) { // If there's a failure then print the stack trace in a JOptionPane error
									// message
			JOptionPane.showMessageDialog(leaderboardPanel, e.getStackTrace(), "Error: IOException",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		sortPlayersByTime(); // Sort the players
		for (int i = 0; i < players.size(); i++) {
			leaderboardPanel.add(new JLabel(players.get(i).getName() + " " + players.get(i).getTime())); // Add the
																											// values to
																											// leaderboard
																											// panel
		}
	}

	public void sortPlayersByTime() {
		sorter = new QuickSort<Player>(); // Use QuickSort to sort players
		sorter.sort(players);
	}

	public JPanel getPanel() { // Getters and setters
		return mainPanel;
	}

	public void setGuiManager(Manager gui) {
		this.gui = gui;
	}
}
