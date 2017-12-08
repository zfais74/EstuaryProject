package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

/**
 * This class is a swing timer used to time the power ups for 5 seconds
 * @author Zeke Faison
 *
 */
public class PowerUpTimer implements ActionListener, Serializable {
	
	private int timeRemaining;
	private Timer timer;
	private boolean timesUp = false;
	
	/**
	 * Constructor sets the timer for 5 seconds
	 */
	public PowerUpTimer() {
		this.timeRemaining = 5;
		this.timer = new Timer(1000, this);
	}

	/**
	 * Constructor used for testing without full 5 second delay
	 * @param time the test time
	 */
	public PowerUpTimer(int time) {
		this.timeRemaining = time;
		this.timer = new Timer(1000, this);
	}
	
	/**
	 * Reduces the time left and checks if time is up
	 */
	private void decTime() {
		timeRemaining--;
	}
	
	/**
	 * Gets the time remaining
	 * @return time remaining
	 */
	public int getTimeRemaining () {
		return this.timeRemaining;
	}
	
	/**
	 * Gets the Timer object used to start and stop timer
	 * @return Timer object
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Sets whether time is up
	 * @param isTimeUp if time is up or not
	 */
	private void setTimesUp(boolean isTimeUp) {
		this.timesUp = isTimeUp;
	}
	
	/**
	 * Gets if time is up
	 * @return if time is up or not
	 */
	public boolean isTimesUp() {
		return this.timesUp;
	}

	/**
	 * The tick of the swing timer, reduces time each time the swing timer produces an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.decTime();
		if(timeRemaining <= 0) {
			setTimesUp(true);
		}
	}

}
