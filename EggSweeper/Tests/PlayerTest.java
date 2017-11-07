import static org.junit.Assert.*;

import org.junit.Test;

import enums.Bird;

public class PlayerTest {
	
	private Player player = new Player(Bird.REDKNOT);

	@Test
	public void testGetBirdType() {
		Bird playerBird = player.getBirdType();
		assertEquals("playerBird should be the same value as the constructor value", playerBird, Bird.REDKNOT);
	}

	@Test
	public void testSetBirdType() {
		player.setBirdType(Bird.DUNLIN);
		Bird playerBird = player.getBirdType();
		assertEquals("the returned bird should be DUNLIN", playerBird, Bird.DUNLIN);
	}

	@Test
	public void testIncScore() {
		int scoreBeforeInc = player.getScore();
		player.incScore();
		int scoreAfterInc = player.getScore();
		assertTrue("the older score should be less than the newer score", scoreBeforeInc < scoreAfterInc);
	}

	@Test
	public void testDecScore() {
		int scoreBeforeDec = player.getScore();
		player.decScore();
		int scoreAfterDec = player.getScore();
		assertTrue("the older score should be greater than the newer score", scoreBeforeDec > scoreAfterDec);
	}

	@Test
	public void testIncEggs() {
		int eggsBeforeInc = player.getEggs();
		player.incEggs();
		int eggsAfterInc = player.getEggs();
		assertTrue("the amount of eggs should increase", eggsBeforeInc < eggsAfterInc);
	}


	@Test
	public void testIncTrash() {
		int trashBeforeInc = player.getTrash();
		player.incTrash();
		int trashAfterInc = player.getTrash();
		assertTrue("the amount of trash should increase", trashBeforeInc < trashAfterInc);
	}
	
	@Test
	public void gettingAndSettingPlayerXPos() {
		int xPos = 4;
		player.setXLoc(xPos);
		int result = player.getXLoc();
		assertEquals("The result should be the same value held in the xPos variable", result, xPos);
	}
	
	@Test
	public void gettingAndSettingPlayerYPos() {
		int yPos = 4;
		player.setYLoc(yPos);
		int result = player.getYLoc();
		assertEquals("The result should be the same value held in the yPos variable", result, yPos);
	}

}
