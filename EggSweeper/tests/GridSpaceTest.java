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

	@Test
	public void gridSpaceIsNotCovered() {
		gridSpaceTest.setIsCovered(isCovered);
		boolean result = gridSpaceTest.getIsCovered();
		assertFalse("Should be false", result);
	}
	
	@Test
	public void gridSpaceIsCovered() {
		isCovered = true;
		gridSpaceTest.setIsCovered(isCovered);
		boolean result = gridSpaceTest.getIsCovered();
		assertTrue("Should be false", true);
	}

}
