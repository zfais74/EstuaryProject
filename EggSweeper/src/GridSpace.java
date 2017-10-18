
public class GridSpace{
	
	static enum Item {EMPTY, EGG, TRASH};
	public Item contents;
	public boolean isCovered = true;
	
	public GridSpace(int x) {
		if (x < 5) {
			contents = Item.EMPTY;
		}
		else if (x < 8) {
			contents = Item.EGG;
		}
		else if (x < 10) {
			contents = Item.TRASH;
		}
	}
	
	public Item getItem() {
		this.isCovered = false;
		return this.contents;
	}
	
}