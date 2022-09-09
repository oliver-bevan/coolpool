package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.JComponent;
import logic_classes.Ball;
import logic_classes.ConstantObjects;
import logic_classes.Player;

public class GamePanel extends JComponent
		implements MouseListener, MouseMotionListener, MouseWheelListener, EventListener {	//Implement listeners for mouse motion and extend JComponent for game panel
	private ConstantObjects co;	//Define instance variables
	private ArrayList<Ball> balls;
	private ArrayList<Integer> collisions;
	
	private Player player;
	
	private ScorePanel scorePanel;
	private boolean lock;

	public GamePanel(Player player, ScorePanel scorePanel) {
		lock = true;
		this.scorePanel = scorePanel;	//Instantiate variables
		
		this.setPreferredSize(new Dimension(800, 600));		//Set the preferred size
		
		addMouseMotionListener(this);	//Add the mouse listeners to this
		addMouseWheelListener(this);
		addMouseListener(this);
		this.player = player;
		co = new ConstantObjects();
		
		balls = new ArrayList<Ball>();	//Create the list of balls and add the pre instantiated balls from Constant Objects class
		balls.add(co.getBall1());
		balls.add(co.getBall2());
		balls.add(co.getBall3());
		balls.add(co.getBall4());
		balls.add(co.getBall5());
		balls.add(co.getBall6());
		

		co.getBall1().setPos(new int[] {((co.getTable1().getPaddingSizeX() + ((co.getTable1().getMainRectSizeX() - co.getTable1().getTurfSizeX())/2) + co.getTable1().getTurfSizeX()/2 + co.getBall1().getSizeX()/2)*2), co.getTable1().getPaddingSizeY() + ((co.getTable1().getMainRectSizeY() - co.getTable1().getTurfSizeY())/2) + co.getTable1().getTurfSizeY()/2 + co.getBall1().getSizeY()/2});
		//Set ball 1 to a position 2/4 of length and width of table + padding size etc.
		co.getBall6().setPos(new int[] {(int) (co.getBall1().getPosX() + co.getBall6().getSizeX()*1.25), co.getBall1().getPosY()});
		co.getBall3().setPos(new int[] {(int) (co.getBall1().getPosX() + co.getBall3().getSizeX()*1.25), (int) (co.getBall1().getPosY() - co.getBall3().getSizeY()*1.25)});		//Place balls in a plus shape based on already calculated position of ball1
		co.getBall4().setPos(new int[] {(int) (co.getBall1().getPosX() + co.getBall4().getSizeX()*1.25), (int) (co.getBall1().getPosY() + co.getBall3().getSizeY()*1.25)});
		co.getBall5().setPos(new int[] {(int) (co.getBall1().getPosX() - co.getBall2().getSizeX()*1.25), co.getBall1().getPosY()});
		co.getBall2().setPos(new int[] {(int) (co.getBall1().getPosX() - (co.getTable1().getTurfSizeX()*0.75)), co.getBall1().getPosY()});	//Set cue ball to position 1/4 of table size
		co.getCue1().setPosX(400);	// set cue starting position
		co.getCue1().setPosY(150);
		co.getCue1().setRotation(180);
		
		collisions = new ArrayList<Integer>();
		lock();	//Lock the GamePanel
	}

	@Override
	public void paintComponent(Graphics g) {	
		if (lock == false) {
			co.getTable1().render(g, 800, 500, 100, 25);	//Render the table at these positions

			co.getCue1().render(g);		//Render the cue

			for (int i = 0; i < balls.size(); i++) {
				if (balls.get(i).isPotted() == false) {
					balls.get(i).render(g);		//Render each ball from the balls list
				}
			}
		}
	}

	public void hideCursor() {
		//Create blank image
		BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		//Create blank cursor
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "blank");
		//Set blankCursor as current cursor
		this.setCursor(blankCursor);
	}

	public void showCursor() {
		//Reset default cursor
		this.setCursor(null);
	}

	public void updateUI() {
		if (lock == false) {	//If the GamePanel is not locked
			for (int i = 0; i < balls.size(); i++) {
				boolean[] results = balls.get(i).updatePositions(collisions, balls);	//For each of the balls in the balls list, update the positions
				if(results[1]) {	//If Cue ball potted is returned from ball then disqualify player
					scorePanel.disqualify("Cue ball potted.");
				} else if(results[2]) {
					if(balls.size() != 1) {		//If Black ball potted is returned from ball then disqualify player
						scorePanel.disqualify("Black ball potted.");
					}
				}
			}
			if (balls.size() == 1) {
				if (balls.get(0).isCue()) {	//If the only ball remaining in the list then finish game
					scorePanel.won();
				}
			}
			collisions.clear();
		}
		try {
			Thread.sleep(10);	//Update every 10ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkColision(int x1, int y1, int x2, int y2, int circleRadii1, int circleRadii2) {
		int xSide = x1 - x2;	//Work out the two sides of right angled triangle from parsed in positions
		int ySide = y1 - y2;

		double hypotenuse = Math.sqrt((int) Math.pow(xSide, 2) + (int) Math.pow(ySide, 2));	//Calculate hypotenuse from pythagoras' theorem
		if (circleRadii1 + circleRadii2 > hypotenuse) {	//If the ball radii is bigger than the hypotenuse then a collision has occurred
			return true;
		} else {
			return false;
		}
	}

	public void lock() {
		showCursor();
		lock = true;	//Show the cursor in GamePanel region and set lock flag to true
	}

	public void unlock() {
		hideCursor();	//Show the cursor in GamePanel region and set lock flag to true
		lock = false;
	}

	public ConstantObjects getco() {
		return co;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		moveCue(e);

	}
												//If the mouse has been moved then run the move cue passing in the MouseEvent
	@Override
	public void mouseMoved(MouseEvent e) {
		moveCue(e);

	}

	public void moveCue(MouseEvent e) {
		co.getCue1().setPosX(e.getX());		//Move the cue to the postions of the mouse
		co.getCue1().setPosY(e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		co.getCue1().setRotation(co.getCue1().getRotation() + (e.getWheelRotation() * 6));		//Add the degrees of motion to the cue position
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(co.getCue1().fire(balls, co.getTable1(), collisions)) {	//If the mouse wheel has been clicked then run fire method moving nearby ball
			scorePanel.disqualify("Other ball was hit before Cue ball");	//If cue ball not hit returned then disqualify
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	//Define methods to satisfy required implementations

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	public ScorePanel getScorePanel() {
		return scorePanel;
	}
}
