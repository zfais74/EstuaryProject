import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import enums.Bird;

public class CommandController {
	
	// The Model
	Player player;
	Board gameBoard;
	
	public void startGame(Board.Difficulty difficulty) {
		gameBoard = new Board(difficulty);
    	player = new Player(Bird.SANDPIPER); 
    	try {
    		BufferedImage bird = ImageIO.read(new File("images/bird.png"));
			List<BufferedImage> birdList = new ArrayList<BufferedImage>();
			birdList.add(bird);
            AniObject birdObject = new AniObject("bird", (int) Math.round(1000*(3./5.)), (int) Math.round(1000*(4./5.)), 100, 150, birdList);
            for (int step = 0; step < 50; step ++) {
        		birdObject.setY(birdObject.getY() - 10);
        		System.out.println("Bird Image Location = " + Integer.toString(birdObject.getY()));
        		Thread.sleep(40);
        	}
    	} catch (IOException e1) {
			System.out.println("failed to load US or Bird");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void playGame() {
			System.out.println(" ");
			System.out.println("Pick Row 0 to 19:");
			Scanner scan = new Scanner(System.in);
			int row = scan.nextInt();
			System.out.println("Pick column 0 to 19:");
			int col = scan.nextInt();
			player.checkSpace(col, row, gameBoard);
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