
// The Controller

public class Game{
	
	Player player;
	GameBoard board;

	// constructor
	Game(Player.Bird gameBird, GameBoard.Difficulty gameDifficulty) {
		player = new Player(gameBird);
		board = new GameBoard(gameDifficulty);
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
	}
	
	// general test
	public static void main(String[] args) {
		Game game = new Game(Player.Bird.DUNLIN, GameBoard.Difficulty.HARD);
		GUI gameGUI = new GUI();
		int x = 0;
		int y = 0;
		while (game.board.clicks != 0) {
			game.checkSpace(x, y);
			if (x < 99){
				x++;
			}
			else if (y < 99){
				x = 0;
				y++;
			}
			else {
				System.out.println("Checked all space");
			}
		}
		System.out.println(" ");
		System.out.println("Score = " + game.player.getScore());
	}
	
}