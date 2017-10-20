import java.util.Random;

// The Model

public class Board{
	
	// the GameBoard is an empty 2D array of GridSpace pointers
	private GridSpace[][] board = new GridSpace[boardSize][boardSize];
	
	// the Difficulty affects distribution of EGGs and TRASH
	public static enum Difficulty {EASY, MEDIUM, HARD};
	
	// Board data
	private int timer;
	private int clicks;
	private Difficulty difficulty;
	
	// size of the board
	public static int boardSize = 20;
	
	// randConst is used in setting Items in GridSpaces
	private static final int randConst = 100;
	// ratio for determining distribution of EMPTY spaces in all Difficulties
	private double emptyRatio = randConst * (3./10.);
	// ratios for determining distribution of EGGs and TRASH based on Difficulty
	private double easyEggRatio = randConst * (9./10.);
	private double mediumEggRatio = randConst * (8.5/10.);
	private double hardEggRatio = randConst * (8./10.);
	
	// sets EMPTY, TRASH and EGG spaces in the board
	Board(Difficulty newDifficulty) {
		
		difficulty = newDifficulty;
		//timer is currently unused
		timer = 100;
		// max number of clicks
		clicks = 10;
		
		Random rand = new Random();
		// cycle through each GridSpace pointer
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				// generate random integer to use for determining contents of GridSpace
				int randomInt = rand.nextInt(randConst);
				GridSpace.Item spaceItem = GridSpace.Item.EMPTY;
				switch (difficulty) {
					// on EASY Difficulty, 30% EMPTY, 60% EGGs, 10% TRASH
					case EASY:
						if (randomInt < (emptyRatio)) {
							spaceItem = GridSpace.Item.EMPTY;
						}
						else if (randomInt < (easyEggRatio)) {
							spaceItem = GridSpace.Item.EGG;
						}
						else {
							spaceItem = GridSpace.Item.TRASH;
						}
						break;
					case MEDIUM:
						if (randomInt < (emptyRatio)) {
							spaceItem = GridSpace.Item.EMPTY;
						}
						else if (randomInt < (mediumEggRatio)) {
							spaceItem = GridSpace.Item.EGG;
						}
						else {
							spaceItem = GridSpace.Item.TRASH;
						}
						break;
					case HARD:
						if (randomInt < (emptyRatio)) {
							spaceItem = GridSpace.Item.EMPTY;
						}
						else if (randomInt < (hardEggRatio)) {
							spaceItem = GridSpace.Item.EGG;
						}
						else {
							spaceItem = GridSpace.Item.TRASH;
						}
						break;
				}
				board[i][j] = new GridSpace(spaceItem);
			}
		}
	}
	
	// get GridSpace at index (x, y)
	public GridSpace getSpace(int xIndex, int yIndex) {
		return board[xIndex][yIndex];
	}
	
	// set GridSpace at index (x, y)
	public void setSpace(int xIndex, int yIndex, GridSpace space) {
		board[xIndex][yIndex] = space;
	}
	
	public int getClicks() {
		return clicks;
	}
	
	public void setClicks(int newClicks) {
		clicks = newClicks;
	}
	
	public void decClicks() {
		clicks--;
	}
	
	public int getTime() {
		return timer;
	}
	
	public void setTime(int newTime) {
		timer = newTime;
	}
	
}