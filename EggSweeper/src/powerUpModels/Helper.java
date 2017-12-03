package powerUpModels;

import java.io.Serializable;
/**
 * This is a model which is useful for placing the helper sprite
 * @author zeke
 *
 */
public class Helper implements Serializable {
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
	/**
	 * Sets the x position of the helper
	 * @param xPos The x position of the helper
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	/**
	 * Get the x position of the helper
	 * @return The helper's x position
	 */
	public int getXPos() {
		return this.xPos;
	}
	/**
	 * Sets the y position of the helper 
	 * @param yPos The y position of the helper
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	/**
	 * Get the y position of the helper
	 * @return The helper's y position
	 */
	public int getYPos() {
		return this.yPos;
	}
	/**
	 * Get the Helper's name
	 * @return The helper's name
	 */
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
