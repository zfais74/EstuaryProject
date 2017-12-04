import static org.junit.Assert.*;

import org.junit.Test;

import enums.Bird;
import enums.Difficulty;
import enums.Item;
import enums.PowerUps;

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
	
	@Test
	public void testPlayerHasPowerUp(){
		player.setPowerupStatus(true);
		boolean result = player.hasPowerUp();
		assertTrue("result should be true", result);
	}
	
	@Test
	public void testPlayerDoesNotHavePowerUp (){
		boolean result = player.hasPowerUp();
		assertFalse("result should be false", result);
	}
	
	
	@Test
	public void testPlayerHasNullPowerUp(){
		PowerUps powerUp = player.getCurrentPowerUp();
		assertEquals("The powerUp variable should be null",powerUp, null);
	}
	
	@Test
	public void testPlayerTotalCorrectAnswers(){
		int result = player.gettotalCorrectAnswers();
		assertEquals("result should be 0", result, 0);
	}
	
	@Test
	public void testIncreaseTotalCorrectAnswers(){
		player.incTotalCorrectAnswers();
		int result = player.gettotalCorrectAnswers();
		assertEquals("result should be 1", result, 1);
	}
	
	@Test
	public void playerCheckSpaceEmpty(){
		GridSpace space1 = new GridSpace(Item.EMPTY);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be EMPTY", result, Item.EMPTY);
	}
	
	@Test
	public void playerCheckSpaceEgg(){
		GridSpace space1 = new GridSpace(Item.EGG);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be EGG", result, Item.EGG);
	}
	
	@Test
	public void playerCheckSpaceTrash(){
		GridSpace space1 = new GridSpace(Item.TRASH);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be TRASH", result, Item.TRASH);
	}
	
	@Test
	public void playerCheckSpaceAlreadyChecked(){
		GridSpace space1 = new GridSpace(Item.ALREADYCHECKED);
		space1.setIsCovered(false);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be ALREADYCHECKED", result, Item.ALREADYCHECKED);
	}
	
	@Test
	public void playerLosesBonusPowerUp(){
		player.setCurrentPowerUp(PowerUps.BONUS);
		player.setPowerupStatus(false);
	}
	

}
