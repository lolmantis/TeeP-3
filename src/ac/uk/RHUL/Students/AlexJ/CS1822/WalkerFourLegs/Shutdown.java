package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Shutdown implements Runnable {
	private final BaseFrame frame; // a reference to the container
	
	public Shutdown(BaseFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void run() {
		// wait indefinitely
		LCD.drawString("watching", 0, 7);
		Button.ESCAPE.waitForPressAndRelease();
		LCD.clear();
		LCD.drawString("pressed!", 0, 7);
		frame.stopProgram();
	}

}
