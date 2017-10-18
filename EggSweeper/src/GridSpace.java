
// Support class, is this part of the model?


public class GridSpace{
	
	public enum Item {EMPTY, EGG, TRASH};
	
	// the Item in the GridSpace
	public Item contents;
	// whether the GridSpace has been checked yet, true if it has not been checked
	public boolean isCovered = true;
	
	// ratio for determining distribution of EMPTY spaces in all Difficulties
	public double emptyRatio = GameBoard.randConst * (3./10.);
	// ratios for determining distribution of EGGs and TRASH based on Difficulty
	public double easyEggRatio = GameBoard.randConst * (9./10.);
	public double mediumEggRatio = GameBoard.randConst * (8.5/10.);
	public double hardEggRatio = GameBoard.randConst * (8./10.);
	
	// constructor, takes random integer from GameBoard constructor
	GridSpace(int random, GameBoard.Difficulty difficulty) {
		switch (difficulty) {
			// on EASY Difficulty, 30% EMPTY, 60% EGGs, 10% TRASH
			case EASY:
				if (random < (emptyRatio)) {
					contents = Item.EMPTY;
				}
				else if (random < (easyEggRatio)) {
					contents = Item.EGG;
				}
				else {
					contents = Item.TRASH;
				}
				break;
			case MEDIUM:
				if (random < (emptyRatio)) {
					contents = Item.EMPTY;
				}
				else if (random < (mediumEggRatio)) {
					contents = Item.EGG;
				}
				else {
					contents = Item.TRASH;
				}
				break;
			case HARD:
				if (random < (emptyRatio)) {
					contents = Item.EMPTY;
				}
				else if (random < (hardEggRatio)) {
					contents = Item.EGG;
				}
				else {
					contents = Item.TRASH;
				}
				break;
		}
	}
	
	// get the Item in the space
	public Item getItem() {
		return this.contents;
	}
	
}