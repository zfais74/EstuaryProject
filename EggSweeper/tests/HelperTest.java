import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import powerUpModels.Helper;

public class HelperTest {
	
	private Helper testHelper;
	
	@Before
	public void setup() {
		testHelper = new Helper(0,0);
	}

	@Test
	public void testSetXPos() {
		int newXPos = 4;
		testHelper.setXPos(newXPos);
		int result = testHelper.getXPos();
		assertTrue("The result should be the same value as the newXPos", result == newXPos);
	}


	@Test
	public void testSetYPos() {
		int newYPos = 4;
		testHelper.setYPos(newYPos);
		int result = testHelper.getYPos();
		assertTrue("The result should be the same value as the newYPos", result == newYPos);
	}
	
	@Test
	public void testGetName() {
		String result = testHelper.getName();
		assertTrue("Name should be Maggie", result.equals("Maggie"));
	}

}
