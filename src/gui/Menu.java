package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import logic_classes.Player;

public class Menu extends JComponent{

	GamePanel gamePanel;
	JPanel main;
	Menu(){
		main = new JPanel(new GridLayout(2, 1));
		JPanel filler1 = new JPanel();
		JPanel filler2 = new JPanel();	
		JPanel filler3 = new JPanel();
		JPanel filler4 = new JPanel();
		JPanel filler5 = new JPanel();
		JPanel filler6 = new JPanel();
		JPanel filler7 = new JPanel();
		JPanel filler8 = new JPanel();
		
		filler1.setBackground(new Color(0x1300bd));
		filler2.setBackground(new Color(0x1300bd));
		filler3.setBackground(new Color(0x1300bd));
		filler4.setBackground(new Color(0x1300bd));
		filler5.setBackground(new Color(0x1300bd));
		filler6.setBackground(new Color(0x1300bd));
		filler7.setBackground(new Color(0x1300bd));
		filler8.setBackground(new Color(0x1300bd));
		
		JPanel filler9 = new JPanel();
		JPanel filler10 = new JPanel();
		JPanel filler11 = new JPanel();
		JPanel filler12 = new JPanel();
		
		filler9.setBackground(new Color(0x1300bd));
		filler10.setBackground(new Color(0x1300bd));
		filler11.setBackground(new Color(0x1300bd));
		filler12.setBackground(new Color(0x1300bd));
		
		this.setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel(new GridLayout(3, 2));
		JPanel textPanel = new JPanel(new GridLayout(3, 3));

		main.setBackground(new Color(0x1300bd));
		buttonsPanel.setBackground(new Color(0x1300bd));
		textPanel.setBackground(new Color(0x1300bd));

		JTextArea playerName = new JTextArea("Enter your name");
		playerName.setFont(new Font("Calibri", Font.BOLD, 60));
		JLabel programNameLabelLine2 = new JLabel("Game!");
		programNameLabelLine2.setFont(new Font("Calibri", Font.BOLD, 60));

		JButton startButton = new JButton("Start!");
		startButton.setFont(new Font("Calibri", Font.BOLD, 30));
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player player = new Player(playerName.getText());
			}
		});
		
		textPanel.add(filler1);
		
		textPanel.add(filler2);
		textPanel.add(playerName);
		textPanel.add(filler3);
		
		textPanel.add(programNameLabelLine2);
		
		textPanel.add(filler4);
		
		buttonsPanel.add(filler5);
		buttonsPanel.add(filler6);
		buttonsPanel.add(filler7);
		
		buttonsPanel.add(filler8);
		buttonsPanel.add(startButton);
		buttonsPanel.add(filler9);
		
		buttonsPanel.add(filler10);
		buttonsPanel.add(filler11);
		buttonsPanel.add(filler12);
		
		
		main.add(textPanel);
		main.add(buttonsPanel);
		
		
		
		this.add(main);
	}
	public JPanel getMain() {
		return main;
	}
}
