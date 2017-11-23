import enums.Bird;
import enums.Item;

import java.io.Serializable;

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
			System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
			return Item.ALREADYCHECKED;
		}
		else {
			// take a turn
			space.setIsCovered(false);
			board.decClicks();
		}
		Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				score = score + 100;;
				eggs++;
				if (board.getClicks() == 0) {
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Found an egg!!!");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.EGG;
			/*case TRASH:
				// reduce player score
				score--;
				trash++;
				if (board.getClicks() == 0) {
					System.out.println(" ");
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Ate some trash :(");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.TRASH;*/
			case TWIG:
				// reduce player score
				score = score - 10;
				trash++;
				if (board.getClicks() == 0) {
					System.out.println(" ");
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Ate some twig :(");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.TWIG;
			case BOTTLE:
				// reduce player score
				score = score - 25;
				trash++;
				if (board.getClicks() == 0) {
					System.out.println(" ");
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Ate some bottle :(");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.BOTTLE;
			case PESTICIDE:
				// reduce player score
				score = score - 50;;
				trash++;
				if (board.getClicks() == 0) {
					System.out.println(" ");
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Ate some pesticide :(");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.PESTICIDE;
			case EMPTY:
				if (board.getClicks() == 0) {
					System.out.println("Out of clicks!");
					System.out.println("Your score is: " + Integer.toString(score));
					
				}
				else {
					System.out.println("Nothing.");
					System.out.println("Score: " + Integer.toString(score));
					System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
				}
				return Item.EMPTY;
		}
		return null;
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
		score++;
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
	
}