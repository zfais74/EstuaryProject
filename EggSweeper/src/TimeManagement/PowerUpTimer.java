package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PowerUpTimer implements ActionListener {
	
	private int timeRemaining;
	private Timer timer;
	
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
	}
	
	public int getTimeRemaining () {
		return this.timeRemaining;
	}
	
	public Timer getTimer() {
		return this.timer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Called");
		this.decTime();
		System.out.println(this.timeRemaining);
	}

}
