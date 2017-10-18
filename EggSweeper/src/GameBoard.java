import java.util.Random;


public class GameBoard{
	
	public GridSpace[][] board = new GridSpace[100][100];
	public int timer;
	public int clicks;
	
	public GameBoard() {
		
		timer = 100;
		clicks = 25;
		
		Random rand = new Random();
		
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				
				int randomInt = rand.nextInt(10);
				board[i][j] = new GridSpace(randomInt);
				
			}
		}
	}
	
	public GridSpace.Item checkSpace(int i, int j) {
		this.clicks--;
		return this.board[i][j].getItem();
	}
	
	public static void main(String[] args) {
		GameBoard gameBoard = new GameBoard();
		for (int i = 0; i < 10; i++) {
			System.out.println(gameBoard.checkSpace(i, 10));
		}
	}
	
}