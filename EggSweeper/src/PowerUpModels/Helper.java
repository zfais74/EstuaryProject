package PowerUpModels;

public class Helper {
	private String name;
	private int xPos;
	private int yPos;
	
	public Helper() {
		this.xPos = 0;
		this.yPos = 0;
		this.name = "Maggie";
	}
	
	public Helper(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.name = "Maggie";
	}
	
	public Helper(int xPos, int yPos, String name) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.name = name;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " is at this position: ")
		.append("(" + this.xPos + ",")
		.append(this.yPos + ")");
		return sb.toString();
	}

}
