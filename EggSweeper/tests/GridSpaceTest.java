import static org.junit.Assert.*;

import org.junit.Test;

import enums.Item;

public class GridSpaceTest {
	
	private GridSpace gridSpaceTest = new GridSpace(Item.EGG);
	private boolean isCovered = false;

	@Test
	public void testGetItem() {
		Item gridSpaceItem = gridSpaceTest.getItem();
		assertEquals("Should be an egg", gridSpaceItem, Item.EGG);
	}

}
