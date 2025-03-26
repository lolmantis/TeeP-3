package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.BaseFrame;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class MeowTimer implements Runnable {
	
	private final BaseFrame frame;
	
	
	public MeowTimer(BaseFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void run() {
		while (frame.isRunning()) {
			frame.updateMeow();
			LCD.drawString(String.format("time to Meow: %d", frame.timeToMeow()), 0, 6);
			Delay.msDelay(5000);
		}
		// turns itself off when we stop working
	}
}
