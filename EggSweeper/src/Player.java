import enums.Bird;
import enums.Item;
import enums.PowerUps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The Model

/**
 * @author Will Ransom
 *
 */
/**
 * @author ThisMac
 *
 */
public class Player implements Serializable {
	
	// Player data
	private Bird bird;
	private int score;
	private int eggs;
	private int trash;
	private boolean hasPowerUp = false;
	private int xLoc;
	private int yLoc;
	private PowerUps currentPowerUp = null;
	private int totalCorrectAnswers = 0;
	private int eggMultiplier = 1;
	private int eggsFound = 0;
	
	// constructor
	
	/**
	 * Player constructor to create a new Player object
	 * @param newBird The bird representing the player
	 * @return Player
	 */
	Player(Bird newBird) {
		bird = newBird;
		score = 0;
		eggs = 0;
		trash = 0;
	}
	
	// method to check contents of a space on the board
	/**
	 * This method checks the x and y co-ordinate of the board and returns what item is there
	 * @param xIndex  The xPosition
	 * @param yIndex  The yPosition
	 * @param board  The Board object
	 * @return Item
	 */
	public Item checkSpace(int xIndex, int yIndex, Board board) {
		System.out.println(" ");
		System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
		System.out.println(String.format("Found %d items", board.countAdjacentItems(xIndex, yIndex)));
		GridSpace space = board.getSpace(xIndex, yIndex);
		// if space has already been checked do not continue
		if (space.getIsCovered() == false) {
			System.out.println("Already checked there!");
			System.out.println("Score: " + Integer.toString(score));
			return Item.ALREADYCHECKED;
		}
		else {
			// take a turn
			space.setIsCovered(false);
		}
		Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				score = score + eggMultiplier;
				eggs = eggs + eggMultiplier;
				System.out.println("Found an egg!!!");
				System.out.println("Score: " + Integer.toString(score));
				space.setItem(Item.ALREADYCHECKED);
				this.eggsFound++;
				return Item.EGG;
			case TRASH:
				// reduce player score
				score--;
				trash++;
				System.out.println("Ate some trash :(");
				System.out.println("Score: " + Integer.toString(score));
				space.setItem(Item.ALREADYCHECKED);
				return Item.TRASH;
			case EMPTY:
				System.out.println("Nothing.");
				System.out.println("Score: " + Integer.toString(score));
				space.setItem(Item.ALREADYCHECKED);
				return Item.EMPTY;
		}
		return null;
	}
	
	public int getEggsFound() {
		return this.eggsFound;
	}
	
	public boolean hasPowerUp(){
		return this.hasPowerUp;
	}
	
	/**
	 * Bird getter
	 * @return bird
	 */
	public Bird getBirdType() {
		return bird;
	}
	
	/**
	 * Bird setter
	 * @param newBird  The new Bird
	 */
	public void setBirdType(Bird newBird) {
		bird = newBird;
	}
	
	/**
	 * Increments the score
	 */
	public void incScore() {
		score+=eggMultiplier ;
	}
	
	/**
	 * Decrements the score
	 */
	public void decScore() {
		score--;
	}
	
	/**
	 * Returns the score
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Increments the eggs
	 */
	public void incEggs() {
		eggs++;
	}
	
	/**
	 * Returns the eggs
	 * @return eggs
	 */
	public int getEggs() {
		return eggs;
	}
	
	/**
	 * Increments the amount of trash
	 */
	public void incTrash() {
		trash++;
	}
	
	/**
	 * Returns the amount of trash
	 * @return trash
	 */
	public int getTrash() {
		return trash;
	}
	
	/**
	 * Setter for the xLocation
	 * @param xPos  The new xPosition
	 */
	public void setXLoc(int xPos) {
		this.xLoc = xPos;
	}
	
	/**
	 * Getter for the xLocation
	 * @return xLoc
	 */
	public int getXLoc() {
		return this.xLoc;
	}
	
	
	/**
	 * Setter for the yPosition
	 * @param yPos  The new yPosition
	 */
	public void setYLoc(int yPos) {
		this.yLoc = yPos;
	}
	
	/**
	 * Getter for the yPosition
	 * @return yLoc
	 */
	public int getYLoc() {
		return this.yLoc;
	}
	
	public void incTotalCorrectAnswers() {
		this.totalCorrectAnswers++;
	}
	
	public int gettotalCorrectAnswers() {
		return this.totalCorrectAnswers;
	}
	
	public void setCurrentPowerUp(PowerUps powerUp) {
		this.currentPowerUp = powerUp;
		System.out.println("Current powerUp is: " + powerUp);
		if(powerUp == PowerUps.BONUS) {
			this.setEggMultiplier(2);
//			int newMultiplier = NumberManipulation.generateNum(5);
//			if(newMultiplier == 1) {
//				newMultiplier++;
//			}
//			System.out.println("Bonus!\n" + "You now have a multiplier of " + newMultiplier);
//			this.setMultiplier(newMultiplier);
		}
	}
	
	public PowerUps getCurrentPowerUp() {
		return this.currentPowerUp;
	}
	
	public void setPowerupStatus(boolean playerHasPowerup) {
		this.hasPowerUp = playerHasPowerup;
		if(playerHasPowerup) {
			PowerUps obtainedPowerUp = this.generatePowerUp();
			this.setCurrentPowerUp(obtainedPowerUp);
		} else {
			this.currentPowerUp = null;
			this.setEggMultiplier(1);
		}
	}
	
	public void setEggMultiplier(int adjustment) {
		this.eggMultiplier = adjustment;
	}
	
	public int getEggMultiplier() {
		return this.eggMultiplier;
	}
	
	private PowerUps generatePowerUp() {
		List<PowerUps> powerUps = new ArrayList<>();
		for(PowerUps choice: PowerUps.values()) {
			if(choice != PowerUps.DEVOUR) {
				powerUps.add(choice);
			}
		}
		Collections.shuffle(powerUps);
		Collections.shuffle(powerUps);
		PowerUps selectedPowerUp = powerUps.get(0);
		return selectedPowerUp;
	}
}