package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Driver {

	public static void main(String[] args) {

		BaseFrame frame = null;
		while (frame == null) {
			try {
				frame = new BaseFrame();
			} catch (IllegalArgumentException e) {
				LCD.drawString("Check all cables", 0, 4);
				LCD.drawString("Enter to retry.", 0, 5);
				Button.ENTER.waitForPressAndRelease();
				LCD.clear();
			}
		}
		
//		LCD.clear();
//		LCD.drawString("ready.", 0, 4);
		
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
//		frame.runArbiter();
		frame.standFromCat();
		frame.walkForwards();
		Button.ENTER.waitForPressAndRelease();
	}
}
