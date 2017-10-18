import java.util.Random;

// The Model

public class GameBoard{

	// the Difficulty affects distribution of EGGs and TRASH
	public static enum Difficulty {EASY, MEDIUM, HARD};
	
	// size of the board
	public int size = 100;
	// the GameBoard is an empty 2D array of GridSpace pointers
	public GridSpace[][] board = new GridSpace[size][size];
	// timer is unused so far
	public int timer;
	// remaining clicks
	public int clicks;
	// randConst is used in setting Items in GridSpaces
	public static final int randConst = 100;
	
	// constructor
	GameBoard(Difficulty difficulty) {
		
		timer = 100;
		clicks = 25;
		
		Random rand = new Random();
		// cycle through each GridSpace pointer
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// generate random integer to use for determining contents of GridSpace
				int randomInt = rand.nextInt(randConst);
				board[i][j] = new GridSpace(randomInt, difficulty);
			}
		}
	}
	
	// get GridSpace (x, y)
	public GridSpace getSpace(int xIndex, int yIndex) {
		return board[xIndex][yIndex];
	}
	
}