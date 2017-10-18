
// Support class, is this part of the model?

public class Player{
	
	// the bird type
	static enum Bird {REDKNOT, SANDPIPER, SANDERLING, DUNLIN, RUDDYTURNSTONE};
	
	// player's bird and score
	Bird birdType;
	int score;
	int eggs;
	int trash;
	
	// constructor
	Player (Bird bird) {
		birdType = bird;
		score = 0;
		eggs = 0;
		trash = 0;
	}
	
	// get the score
	public int getScore() {
		return score;
	}
	
	public int getEggs() {
		return eggs;
	}
	
	public int getTrash() {
		return trash;
	}
	
}