package logic_classes;

public class Player implements Comparable<Player> {
	String name;	//Instance variables constructor, getters and setters
	int time;
	public Player(String name) {
		this.name = name;
	}
	public Player(String name, long time) {
		this.name = name;
		this.time = (int) time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public int compareTo(Player player) {	//Override the super method compareTo
		if(time > player.getTime()) {	//Compare time between parsed in player and this player
			return 1;
		} else if (time < player.getTime()) {	
			return -1;
		} else {
			return 0;
		}
	}
	
}
