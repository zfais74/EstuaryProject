package powerUpModels;

public class Cleaner {
	private int xPos;
	private int yPos;
	
	public Cleaner() {
		this.setXPos(0);
		this.setYPos(0);
	}
	
	public Cleaner(int xPos, int yPos) {
		this.setXPos(xPos);
		this.setYPos(yPos);
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Someone has cleaned some trash at this position: ")
		.append("(" + this.xPos + ",")
		.append(this.yPos + ")");
		return sb.toString();
	}

}
