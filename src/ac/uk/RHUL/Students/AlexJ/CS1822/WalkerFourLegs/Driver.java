package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Driver {

	public static void main(String[] args) {

		BaseFrame frame = new BaseFrame();
		
		LCD.clear();
		LCD.drawString("ready.", 0, 4);
		
//		Button.ENTER.waitForPressAndRelease();
//		LCD.clear();
//		frame.standFromCat();
		
		Button.ENTER.waitForPressAndRelease();
		LCD.drawString("about to run arbiter", 0, 3);
		LCD.drawString("arbiter running", 0, 3);
		
		frame.runArbiter();
		// until all the behaviors return false, it keeps going
		LCD.drawString("Arbiter stopped", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		if (!frame.isSitting()) {
			frame.sitToCat();
		}
	}
}
