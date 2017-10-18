
// The Controller

public class Game{
	
	Player player;
	GameBoard board;
	GUI gameGUI;

	// constructor
	Game(Player.Bird gameBird, GameBoard.Difficulty gameDifficulty) {
		player = new Player(gameBird);
		board = new GameBoard(gameDifficulty);
		connectGUI();
	}
	
	public void connectGUI() {
		gameGUI = new GUI(this);
	}
	
	// method to check contents of a space on the board
	public void checkSpace(int i, int j) {
		GridSpace space = board.getSpace(i, j);
		// if space has already been checked do not continue
		if (space.isCovered == false) {
			System.out.println("Already checked there!");
			return;
		}
		else {
			// take a turn
			space.isCovered = false;
			board.clicks--;
		}
		GridSpace.Item item = space.getItem();
		switch (item) {
			case EGG:
				// up player score
				player.score++;
				System.out.println("Found an Egg!!!");
				break;
			case TRASH:
				// reduce player score
				player.score--;
				System.out.println("Ate some trash...");
				break;
			case EMPTY:
				System.out.println("Nothing.");
				break;
		}
		if (board.clicks == 0){
			System.out.println(" ");
			System.out.println("Score = " + player.getScore());
			System.exit(0);
		}
	}
	
	// general test
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Game game = new Game(Player.Bird.DUNLIN, GameBoard.Difficulty.EASY);
            }
        });
	}
	
}