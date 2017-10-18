import java.util.Random;

public class GameBoard{
	
	public GridSpace[][] board = new GridSpace[100][100];
	
	public GameBoard() {
		
		Random rand = new Random();
		
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				
				int randomInt = rand.nextInt(10);
				board[i][j] = new GridSpace(randomInt);
				
			}
		}
	}
	
	public static void main(String[] args) {
		GameBoard gameBoard = new GameBoard();
		System.out.println(gameBoard.board[10][10].contents);
		System.out.println(gameBoard.board[30][30].contents);
		System.out.println(gameBoard.board[50][50].contents);
		System.out.println(gameBoard.board[70][70].contents);
		System.out.println(gameBoard.board[90][90].contents);
		System.out.println(gameBoard.board[15][10].contents);
		System.out.println(gameBoard.board[35][30].contents);
		System.out.println(gameBoard.board[55][50].contents);
		System.out.println(gameBoard.board[75][70].contents);
		System.out.println(gameBoard.board[95][90].contents);
	}
	
}