package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Manager {

	private StartPanel startPanel; // Define instance variables
	private JPanel gameWindow;

	private ScorePanel scorePanel;
	private GamePanel gamePanel;
	private LeaderboardPanel leaderboardPanel;

	private JFrame mainFrame;
	private Thread updateUIThread;

	public Manager() {
		leaderboardPanel = new LeaderboardPanel(this); // Instantiate instance variables
		mainFrame = new JFrame("Cool Pool");

		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Do nothing when close button is pressed
		mainFrame.addWindowListener(new WindowAdapter() { // Add windowListener to close button
			@Override
			public void windowClosing(WindowEvent event) {
				int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to close the game?",
						"Close game?", JOptionPane.YES_NO_OPTION);
				if (choice == 0) { // confirm with user close window
					mainFrame.dispose(); // Dispose of mainFrame and exit program
					System.exit(0);
				}
			}
		});

		JMenuBar menuBar = new JMenuBar(); // Create a menu bar
		JMenu optionMenu = new JMenu("Other");

		JMenuItem viewVersion = new JMenuItem("View Version");

		viewVersion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showVersion(); // Run show version when menu item is pressed
			}
		});

		JMenuItem exit = new JMenuItem("Exit");

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showExitConformation(); // Rum show exit confirmation when menu item is pressed
			}
		});

		optionMenu.add(viewVersion);
		optionMenu.add(exit); // Add menu items to menu

		menuBar.add(optionMenu);

		mainFrame.setJMenuBar(menuBar);

		mainFrame.setResizable(false); // Finish configuring mainFrame
		startPanel = new StartPanel(this);
		startPanel.gui = this;
		gameWindow = new JPanel();
		mainFrame.add(startPanel); // Add the relevant panels

		updateUIThread = new Thread(new Runnable() { // Prepare thread with endless loop to update UI
			@Override
			public void run() {
				while (true) {
					gamePanel.updateUI();
					mainFrame.repaint();
				}
			}
		});

		mainFrame.setVisible(true);
		mainFrame.pack(); // Display window

	}

	public void swapPanels() {
		mainFrame.remove(startPanel); // Remove startPanel
		scorePanel = new ScorePanel(startPanel.getPlayer(), this); // Reset scorePanel

		gamePanel = new GamePanel(startPanel.getPlayer(), scorePanel);
		gameWindow.setLayout(new GridLayout(2, 1));
		gameWindow.add(gamePanel); // Reinstasiate gameWindow
		gameWindow.add(scorePanel.getScorePanel());

		mainFrame.add(gameWindow); // Add new gameWindow to mainFrame
		mainFrame.pack(); // Update mainframe
		updateUIThread.start(); // Start endless thread
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	} // Setters for instance variables

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void update() {
		mainFrame.remove(gameWindow);
		gamePanel = new GamePanel(startPanel.getPlayer(), scorePanel);
		gameWindow.removeAll();
		gameWindow.add(gamePanel); // Update gameWindow by reinstantiate
		gameWindow.add(scorePanel.getScorePanel());
		mainFrame.add(gameWindow);
		mainFrame.pack();
	}

	public void swapToStart() {
		mainFrame.remove(leaderboardPanel.getPanel());
		startPanel = new StartPanel(this);
		mainFrame.add(startPanel);
		mainFrame.pack(); // Swap to start panel following previous method
		leaderboardPanel = new LeaderboardPanel(this);
	}

	public void swapToLeaderboard() throws NumberFormatException, IOException {
		mainFrame.remove(startPanel);
		leaderboardPanel = new LeaderboardPanel(this);
		leaderboardPanel.displayScores(); // Swap to leaderboardPanel following previous method
		mainFrame.add(leaderboardPanel.getPanel());
		mainFrame.pack();
		startPanel = new StartPanel(this);
	}

	public LeaderboardPanel getLeaderboardPanel() {
		return leaderboardPanel;

	}

	public void showVersion() {
		JOptionPane.showMessageDialog(mainFrame, "Cool Pool Release V5.0\nCopyright © Oliver Bevan MMXVIII",
				"Information", JOptionPane.PLAIN_MESSAGE);
	} // Show version in messageDialog

	public void showExitConformation() {
		int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to close the game?", "Close game?",
				JOptionPane.YES_NO_OPTION);
		if (choice == 0) { // Confirm exit with confirmation dialog
			System.exit(0); // Exit program following user input
		}
	}
}
