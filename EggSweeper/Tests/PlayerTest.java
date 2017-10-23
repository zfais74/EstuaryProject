import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class PlayerTest {

    private Player player = new Player(Player.Bird.DUNLIN);

    @Test
    public void checkSpace() {
    }

    @Test
    public void getBirdType() {
        Enum result = player.getBirdType();
        assertEquals("should return dunlin",result, Player.Bird.DUNLIN);
    }

    @Test
    public void setBirdType() {
        player.setBirdType(Player.Bird.REDKNOT);
        Enum result = player.getBirdType();
        assertEquals("should return redknot", result, Player.Bird.REDKNOT);
    }

    @Test
    public void incScore() {
        int initialScore = player.getScore();
        player.incScore();
        int changedScore = player.getScore();
        assertTrue("the new score should be higher", initialScore < changedScore);
    }

    @Test
    public void decScore() {
        int initialScore = player.getScore();
        player.decScore();
        int changedScore = player.getScore();
        assertTrue("the new score should be lower", initialScore > changedScore);
    }

    @Test
    public void incEggs() {
        int initialEggs = player.getEggs();
        player.incEggs();
        int changedEggs = player.getEggs();
        assertTrue("should have more eggs", initialEggs < changedEggs);
    }

    @Test
    public void incTrash() {
        int initialTrash = player.getTrash();
        player.incTrash();
        int changedTrash = player.getTrash();
        assertTrue("should have more trash", initialTrash < changedTrash);
    }

}