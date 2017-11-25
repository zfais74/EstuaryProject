import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import PowerUpModels.Cleaner;

public class CleanerTest {
	
	private Cleaner cleanerTest;
	
	@Before
	public void setup() {
		cleanerTest = new Cleaner();
	}

	@Test
	public void testSetXPos() {
		int newXPos = 4;
		cleanerTest.setXPos(newXPos);
		int result = cleanerTest.getXPos();
		assertTrue("The result should be the same value as the newXPos", result == newXPos);
	}


	@Test
	public void testSetYPos() {
		int newYPos = 4;
		cleanerTest.setYPos(newYPos);
		int result = cleanerTest.getYPos();
		assertTrue("The result should be the same value as the newYPos", result == newYPos);
	}

}
