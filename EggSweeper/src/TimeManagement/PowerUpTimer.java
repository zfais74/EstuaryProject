package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

public class PowerUpTimer implements ActionListener, Serializable {
	
	private int timeRemaining;
	private Timer timer;
	private boolean timesUp = false;
	
	public PowerUpTimer() {
		this.timeRemaining = 5;
		this.setTimer();
	}

	public PowerUpTimer(int time) {
		this.timeRemaining = time;
		this.setTimer();
	}
	
	public void setTimer() {
		this.timer = new Timer(1000, this);
	}
	
	private void decTime() {
		timeRemaining--;
		if(timeRemaining <= 0) {
			setTimesUp(true);
		}
	}
	
	public int getTimeRemaining () {
		return this.timeRemaining;
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	private void setTimesUp(boolean isTimeUp) {
		this.timesUp = isTimeUp;
	}
	
	public boolean isTimesUp() {
		return this.timesUp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.decTime();
		System.out.println("Powerup:" + this.timeRemaining);
	}

}
