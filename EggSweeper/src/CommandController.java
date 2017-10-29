import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class CommandController{
	
	// The Model
	Player player;
	Board gameBoard;
	
	public void startGame(Board.Difficulty difficulty) {
		gameBoard = new Board(difficulty);
    	player = new Player(Player.Bird.SANDPIPER); 
    	try {
            AniObject bird = new AniObject("bird", (int) Math.round(1000*(3./5.)), (int) Math.round(1000*(4./5.)), 100, 150, ImageIO.read(new File("bird.png")));
            for (int step = 0; step < 50; step ++) {
        		bird.setY(bird.getY() - 10);
        		System.out.println("Bird Image Location = " + Integer.toString(bird.getY()));
        		Thread.sleep(40);
        	}
    	} catch (IOException e1) {
			System.out.println("failed to load US or Bird");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void playGame() {
		while (gameBoard.getClicks() != 0) {
			System.out.println(" ");
			System.out.println("Pick Row 0 to 19:");
			Scanner scan = new Scanner(System.in);
			int row = scan.nextInt();
			System.out.println("Pick column 0 to 19:");
			int col = scan.nextInt();
			player.checkSpace(col, row, gameBoard);
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		CommandController cont = new CommandController();
		System.out.println("Pick Difficulty, 1 for easy , 2 for medium, 3 for hard:");
		Scanner scanner = new Scanner(System.in);
		int diff = scanner.nextInt();
		if (diff == 1) {
			cont.startGame(Board.Difficulty.EASY);
		}
		else if (diff == 2) {
			cont.startGame(Board.Difficulty.MEDIUM);
		}
		else if (diff == 3) {
			cont.startGame(Board.Difficulty.HARD);
		}
		
		cont.playGame();
		
	}
	
}