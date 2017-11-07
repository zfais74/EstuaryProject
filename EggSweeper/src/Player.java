
// The Model

public class Player{
	
	// the bird type
	static enum Bird {REDKNOT, SANDPIPER, SANDERLING, DUNLIN, RUDDYTURNSTONE};
	
	// Player data
	private Bird bird;
	private int score;
	private int eggs;
	private int trash;
	private boolean hasPowerUp = false;
	private int xLoc;
	private int yLoc;
	
	
	// constructor
	Player(Bird newBird) {
		bird = newBird;
		score = 0;
		eggs = 0;
		trash = 0;
	}
	
	// method to check contents of a space on the board
	public GridSpace.Item checkSpace(int xIndex, int yIndex, Board board) {
		System.out.println(" ");
		System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
		GridSpace space = board.getSpace(xIndex, yIndex);
		// if space has already been checked do not continue
		if (space.getIsCovered() == false) {
			System.out.println("Already checked there!");
			System.out.println("Score: " + Integer.toString(score));
			System.out.println("Remaining clicks: " + Integer.toString(board.getClicks()));
			return GridSpace.Item.ALREADYCHECKED;
		}
		else {
			// take a turn
			space.setIsCovered(false);
			board.decClicks();
		}
		GridSpace.Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				score++;;
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
				return GridSpace.Item.EGG;
			case TRASH:
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
				return GridSpace.Item.TRASH;
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
				return GridSpace.Item.EMPTY;
		}
		return null;
	}
	
	public Bird getBirdType() {
		return bird;
	}
	
	public void setBirdType(Bird newBird) {
		bird = newBird;
	}
	
	public void incScore() {
		score++;
	}
	
	public void decScore() {
		score--;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incEggs() {
		eggs++;
	}
	
	public int getEggs() {
		return eggs;
	}
	
	public void incTrash() {
		trash++;
	}
	
	public int getTrash() {
		return trash;
	}
	
	public void setXLoc(int xPos) {
		this.xLoc = xPos;
	}
	
	public int getXLoc() {
		return this.xLoc;
	}
	
	public void setYLoc(int yPos) {
		this.yLoc = yPos;
	}
	
	public int getYLoc() {
		return this.yLoc;
	}
	
}