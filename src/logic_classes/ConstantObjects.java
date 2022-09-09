package logic_classes;

import java.awt.Color;
import java.util.Random;

public class ConstantObjects {
	private Table table1;
	private Ball ball1;
	private Ball ball2;	//Define 'final' instance varibles
	private Ball ball3;
	private Ball ball4;
	private Ball ball5;
	private Ball ball6;
	private final Pocket pocket;
	private Cue cue1;

	public ConstantObjects() {
		Random rhg = new Random();
		pocket = new Pocket(40, 40, new Color(0x000000));
		table1 = new Table( 2, 0.2, new Color(0x552c14), new Color(0x754c24), new Color(0x005e20), 600, 250,
				550, 200, 40, 40, 3, pocket);
		ball1 = new Ball(0, 20, 20, new Color(0x000000), new Color(0x000000), new Color(0x000000), 200, 5, true,	//Define set values for balls, table, pocket, and cue
				false);	
		ball2 = new Ball(1, 20, 20, new Color(0xfffffff), new Color(0xfffffff), new Color(0xfffffff), 200, 5, false, true);
		ball3 = new Ball(2, 20, 20, new Color(0xfffffff), new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), 200, 5, false,
				false);
		ball4 = new Ball(3, 20, 20, new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), 200, 5, false, false);
		ball5 = new Ball(4, 20, 20, new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), 200, 5, false,
				false);
		ball6 = new Ball(5, 20, 20, new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), new Color(rhg.nextInt(0xffffff)), 200, 5, false, false);
		cue1 = new Cue(80, 10, 10, new Color(0x332100), null, new Color(0x000000), null);
	}

	public Ball getBall1() {	//Define getters and setters
		return ball1;
	}

	public Ball getBall2() {
		return ball2;
	}

	public void setBall1(Ball ball1) {
		this.ball1 = ball1;
	}

	public Pocket getPocket() {
		return pocket;
	}

	public Table getTable1() {
		return table1;
	}

	public Cue getCue1() {
		return cue1;
	}

	public Ball getBall3() {
		return ball3;
	}

	public void setBall3(Ball ball3) {
		this.ball3 = ball3;
	}

	public Ball getBall4() {
		return ball4;
	}

	public void setBall4(Ball ball4) {
		this.ball4 = ball4;
	}

	public Ball getBall5() {
		return ball5;
	}

	public void setBall5(Ball ball5) {
		this.ball5 = ball5;
	}

	public Ball getBall6() {
		return ball6;
	}

	public void setBall6(Ball ball6) {
		this.ball6 = ball6;

	}
}
