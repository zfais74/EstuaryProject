import enums.Item;
import enums.PowerUps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The Model

/**
 * This class holds all player data, power up data and is responsible for checking spaces on the board.
 * @author Will Ransom, Zeke Faison
 *
 */
public class Player implements Serializable {
	
	// Player data
	private int score;
	private boolean hasPowerUp = false;
	private PowerUps currentPowerUp = null;
	// used for the unimplemented devour power up
	private int totalCorrectAnswers = 0;
	// used for the double eggs power up
	private int eggMultiplier = 1;
	// keeps track of the number of eggs the player has found so that the game
	//   will end if the player finds all eggs on the board
	private int eggsFound = 0;

	/**
	 * Player constructor to create a new Player object
	 * @param newBird The bird representing the player
	 * @return a Player object
	 */
	Player() {
		score = 0;
	}
	
	/**
	 * This method checks an x and y coordinate of the board and returns what item is there
	 * @param xIndex  the x index of the space to be checked
	 * @param yIndex  the y index of the space to be checked
	 * @param board  the Board object
	 * @return the item at the specified location
	 */
	public Item checkSpace(int xIndex, int yIndex, Board board) {
		GridSpace space = board.getSpace(xIndex, yIndex);
		// check space's item
		Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				score = score + eggMultiplier;
				space.setItem(Item.ALREADYCHECKED);
				eggsFound++;
				return Item.EGG;
			case TRASH:
				// reduce player score
				score--;
				space.setItem(Item.ALREADYCHECKED);
				return Item.TRASH;
			case EMPTY:
				space.setItem(Item.ALREADYCHECKED);
				return Item.EMPTY;
			case ALREADYCHECKED:
				return Item.ALREADYCHECKED;
				
		}
		return null;
	}
	
	/**
	 * Get the number of eggs found
	 * 
	 * @return number of eggs player has found
	 */
	public int getEggsFound() {
		return eggsFound;
	}

	/**
	 * Gets whether or not the player has a power up
	 * 
	 * @return whether or not player has power up
	 */
	public boolean hasPowerUp(){
		return hasPowerUp;
	}
	
	/**
	 * Increments the score by eggMuliplier
	 */
	public void incScore() {
		score+=eggMultiplier ;
	}
	
	/**
	 * Decrements the score by 1
	 */
	public void decScore() {
		score--;
	}
	
	/**
	 * Getter for the player's score
	 * @return the player's score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Increment the totalCorrectAnswer by 1
	 */
	public void incTotalCorrectAnswers() {
		totalCorrectAnswers++;
	}
	
	/**
	 * Get the total correct answers
	 * 
	 * @return total correct answers
	 */
	public int gettotalCorrectAnswers() {
		return totalCorrectAnswers;
	}
	
	/**
	 * Set the current power up
	 * 
	 * @param powerUp the power up to be set
	 */
	public void setCurrentPowerUp(PowerUps powerUp) {
		this.currentPowerUp = powerUp;
		if(powerUp == PowerUps.BONUS) {
			setEggMultiplier(2);
		}
	}
	
	/**
	 * Get the current power up
	 * 
	 * @return current power up
	 */
	public PowerUps getCurrentPowerUp() {
		return currentPowerUp;
	}
	
	/**
	 * Set the current power up status
	 * 
	 * @param playerHasPowerup whether or not the player has a power up
	 */
	public void setPowerupStatus(boolean playerHasPowerup) {
		this.hasPowerUp = playerHasPowerup;
		if(playerHasPowerup) {
			PowerUps obtainedPowerUp = this.generatePowerUp();
			setCurrentPowerUp(obtainedPowerUp);
		} else {
			currentPowerUp = null;
			setEggMultiplier(1);
		}
	}
	
	/**
	 * Set eggMulitplier to the desired value
	 * 
	 * @param adjustment the desired egg multiplier
	 */
	public void setEggMultiplier(int adjustment) {
		eggMultiplier = adjustment;
	}
	
	/**
	 * Get the eggMultiplier
	 * 
	 * @return eggMultiplier the multiplier used for scoring eggs
	 */
	public int getEggMultiplier() {
		return eggMultiplier;
	}
	
	/**
	 * Shuffles the power ups and returns a random power up
	 * 
	 * @return selectedPowerUp a random power up
	 */
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