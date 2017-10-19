
// The Model

public class Player{
	
	// the bird type
	static enum Bird {REDKNOT, SANDPIPER, SANDERLING, DUNLIN, RUDDYTURNSTONE};
	
	// Player data
	private Bird birdType;
	private int score;
	private int eggs;
	private int trash;
	
	// constructor
	Player (Bird bird) {
		birdType = bird;
		score = 0;
		eggs = 0;
		trash = 0;
	}
	
	public Bird getBirdType() {
		return birdType;
	}
	
	public void incScore() {
		score++;
	}
	
	public void decScore() {
		score--;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incEggs() {
		eggs++;
	}
	
	public int getEggs() {
		return eggs;
	}
	
	public void incTrash() {
		trash++;
	}
	
	public int getTrash() {
		return trash;
	}
	
}