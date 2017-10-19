
// The Model

public class Board{
	
	// the GameBoard is an empty 2D array of GridSpace pointers
	private GridSpace[][] board = new GridSpace[Game.boardSize][Game.boardSize];
	
	// Board data
	private int timer;
	private int clicks;
	
	// constructor
	Board() {
		//timer is currently unused
		timer = 100;
		// max number of clicks
		clicks = 10;
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