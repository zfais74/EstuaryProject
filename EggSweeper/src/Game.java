import java.util.Random;

// The Controller

public class Game{
	
	// the Difficulty affects distribution of EGGs and TRASH
	public static enum Difficulty {EASY, MEDIUM, HARD};
	
	// Game data
	Player player;
	Board gameBoard;
	GUI gameGUI;
	Difficulty difficulty;
	// size of the board
	public static int boardSize = 50;
	
	// randConst is used in setting Items in GridSpaces
	public static final int randConst = 100;
	// ratio for determining distribution of EMPTY spaces in all Difficulties
	public double emptyRatio = randConst * (3./10.);
	// ratios for determining distribution of EGGs and TRASH based on Difficulty
	public double easyEggRatio = randConst * (9./10.);
	public double mediumEggRatio = randConst * (8.5/10.);
	public double hardEggRatio = randConst * (8./10.);

	// constructor
	Game() {
		// gameGUI is initialized first, gameBoard and player are set after difficulty is chosen in GUI
		gameGUI = new GUI(this);
	}
	
	// starts the game
	public void play() {
		gameGUI.startScreen();
	}
	
	// create the Player and Board objects based on difficulty
	public void easyGame() {
		difficulty = Difficulty.EASY;
		player = new Player(Player.Bird.DUNLIN);
		gameBoard = new Board();
		populateBoard();
	}
	
	public void mediumGame() {
		difficulty = Difficulty.MEDIUM;
		player = new Player(Player.Bird.SANDPIPER);
		gameBoard = new Board();
		populateBoard();
	}
	
	public void hardGame() {
		difficulty = Difficulty.HARD;
		player = new Player(Player.Bird.REDKNOT);
		gameBoard = new Board();
		populateBoard();
	}
	
	// sets EMPTY, TRASH and EGG spaces in the board
	public void populateBoard() {
		Random rand = new Random();
		// cycle through each GridSpace pointer
		for (int i = 0; i < Game.boardSize; i++) {
			for (int j = 0; j < Game.boardSize; j++) {
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
				gameBoard.setSpace(i, j, new GridSpace(spaceItem));
			}
		}
	}
	
	// method to check contents of a space on the board
	public void checkSpace(int i, int j) {
		GridSpace space = gameBoard.getSpace(i, j);
		// if space has already been checked do not continue
		if (space.getIsCovered() == false) {
			System.out.println("Already checked there!");
			return;
		}
		else {
			// take a turn
			space.setIsCovered(false);
			gameBoard.decClicks();
		}
		GridSpace.Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				player.incScore();
				player.incEggs();
				System.out.println("Found an Egg!!!");
				break;
			case TRASH:
				// reduce player score
				player.decScore();
				player.incTrash();
				System.out.println("Ate some trash...");
				break;
			case EMPTY:
				System.out.println("Nothing.");
				break;
		}
		// when all clicks have been used, switch to end screen
		if (gameBoard.getClicks() == 0){
			gameGUI.endScreen(player.getScore());
		}
	}
	
	public int getScore() {
		return player.getScore();
	}
	
	public int getEggs() {
		return player.getEggs();
	}
	
	public int getTrash() {
		return player.getTrash();
	}
	
	// general test
	public static void main(String[] args) {
		// the main thread with an anonymous implementation of the Runnable class
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Game game = new Game();
            	game.play();
            }
        });
	}
	
}