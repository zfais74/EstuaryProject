
// Support class, is this part of the model?

public class Player{
	
	// the bird type
	static enum Bird {REDKNOT, SANDPIPER, SANDERLING, DUNLIN, RUDDYTURNSTONE};
	
	// player's bird and score
	Bird birdType;
	int score;
	
	// constructor
	Player (Bird bird) {
		birdType = bird;
		score = 0;
	}
	
	// get the score
	public int getScore() {
		return score;
	}
	
}