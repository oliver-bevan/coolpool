package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic_classes.Player;

public class StartPanel extends JComponent {
	Menu menu;
	Player player;		//Instance variables
	Manager gui;

	public StartPanel(Manager gui) {
		this.gui = gui;
		menu = new Menu();

		JPanel filler1 = new JPanel();
		JPanel filler2 = new JPanel();
		JPanel filler3 = new JPanel();		//Define filler JPanels
		JPanel filler4 = new JPanel();
		JPanel filler5 = new JPanel();
		JPanel filler6 = new JPanel();
		JPanel filler7 = new JPanel();
		JPanel filler8 = new JPanel();
		JPanel filler9 = new JPanel();
		JPanel filler10 = new JPanel();
		JPanel filler11 = new JPanel();
		JPanel filler12 = new JPanel();
		
		filler1.setBackground(new Color(0x1300bd));
		filler2.setBackground(new Color(0x1300bd));
		filler3.setBackground(new Color(0x1300bd));
		filler4.setBackground(new Color(0x1300bd));		//Set their colour to blue
		filler5.setBackground(new Color(0x1300bd));
		filler6.setBackground(new Color(0x1300bd));
		filler7.setBackground(new Color(0x1300bd));
		filler8.setBackground(new Color(0x1300bd));
		filler9.setBackground(new Color(0x1300bd));
		filler10.setBackground(new Color(0x1300bd));
		filler11.setBackground(new Color(0x1300bd));
		filler12.setBackground(new Color(0x1300bd));

		this.setLayout(new BorderLayout());
		JPanel main = new JPanel(new GridLayout(2, 1));
		JPanel buttonsPanel = new JPanel(new GridLayout(3, 2));		//Set layouts
		JPanel textPanel = new JPanel(new GridLayout(3, 3));

		main.setBackground(new Color(0x1300bd));	//Set this background colour
		buttonsPanel.setBackground(new Color(0x1300bd));
		textPanel.setBackground(new Color(0x1300bd));

		JLabel programNameLabel = new JLabel("Cool");
		programNameLabel.setFont(new Font("Calibri", Font.BOLD, 60));		//Set label text and font
		JLabel programNameLabelLine2 = new JLabel("Pool!");
		programNameLabelLine2.setFont(new Font("Calibri", Font.BOLD, 60));

		programNameLabel.setForeground(new Color(0xffffff));		//Set text colour to black
		programNameLabelLine2.setForeground(new Color(0xffffff));

		JButton startButton = new JButton("Start!");
		startButton.setFont(new Font("Calibri", Font.BOLD, 30));	//Set startButton text and font
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	//If the button has been pressed then:
				String input;
				do {
					input = JOptionPane.showInputDialog(main, "Name:");	//Create an option pane
					if(input == null) {		//If null returned, i.e. cancel button pressed, return and quit dialog
						return;
					}
				} while ( input == null || input.length() < 2 || input.length() > 26);	//If the input name is smaller than 3 or bigger then 26 then show input dialog again
				player = new Player(input);		//Create a new player
				gui.swapPanels();	//Swap to the gamePanel panel
			}

		});

		JButton viewScores = new JButton("View Leaderboard");	//Create button for view leaderboard
		viewScores.setFont(new Font("Calibri", Font.BOLD, 25));
		viewScores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					gui.swapToLeaderboard();
				} catch (NumberFormatException e1) {
					showErrorDialog("Error: NumberFormatException", e1);	//Show error dialog if swapToLeaderboard returns exception from accessing file
					e1.printStackTrace();
				} catch (IOException e1) {
					showErrorDialog("Error: IOException", e1);
					e1.printStackTrace();
				}

			}
		});

		textPanel.add(filler1);

		textPanel.add(filler2);
		textPanel.add(programNameLabel);
		textPanel.add(filler3);

		textPanel.add(programNameLabelLine2);

		textPanel.add(filler4);

		buttonsPanel.add(filler5);
		buttonsPanel.add(filler6);
		buttonsPanel.add(filler7);

		buttonsPanel.add(filler8);		//Add all the components
		buttonsPanel.add(startButton);
		buttonsPanel.add(filler9);

		buttonsPanel.add(filler10);
		buttonsPanel.add(viewScores);
		buttonsPanel.add(filler12);

		main.add(textPanel);
		main.add(buttonsPanel);

		this.add(main, BorderLayout.CENTER);	//Add main panel to this JComponent

	}
	
	public Player getPlayer() {
		return player;	//Getter for newly created player
	}
	
	public void showErrorDialog(String title, Exception e) {
		JOptionPane.showMessageDialog(this, e.getStackTrace(), title, JOptionPane.ERROR_MESSAGE);		//Show message dialog with stack trace
	}
}
