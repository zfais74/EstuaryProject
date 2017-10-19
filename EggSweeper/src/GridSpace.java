
// The Model


public class GridSpace{
	
	// possible contents of a GridSpace
	public enum Item {EMPTY, EGG, TRASH};
	
	// GridSpace data
	private Item contents;
	// whether the GridSpace has been checked yet, true if it has not been checked
	private boolean isCovered = true;
	
	// constructor
	GridSpace(Item item) {
		contents = item;
	}
	
	public Item getItem() {
		return this.contents;
	}
	
	public boolean getIsCovered() {
		return isCovered;
	}
	
	public void setIsCovered(boolean bool) {
		isCovered = bool;
	}
	
}