
// The Model

public class Player{
	
	// the bird type
	static enum Bird {REDKNOT, SANDPIPER, SANDERLING, DUNLIN, RUDDYTURNSTONE};
	
	// Player data
	private Bird bird;
	private int score;
	private int eggs;
	private int trash;
	
	// constructor
	Player(Bird newBird) {
		bird = newBird;
		score = 0;
		eggs = 0;
		trash = 0;
	}
	
	// method to check contents of a space on the board
	public GridSpace.Item checkSpace(int xIndex, int yIndex, Board board) {
		GridSpace space = board.getSpace(xIndex, yIndex);
		// if space has already been checked do not continue
		if (space.getIsCovered() == false) {
			System.out.println("Already checked there!");
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
				System.out.println("Found an Egg!!!");
				return GridSpace.Item.EGG;
			case TRASH:
				// reduce player score
				score--;
				trash++;
				System.out.println("Ate some trash...");
				return GridSpace.Item.TRASH;
			case EMPTY:
				System.out.println("Nothing.");
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
	
}