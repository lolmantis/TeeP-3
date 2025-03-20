package ac.uk.RHUL.Students.AlexJ.CS1821.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Driver {

	public static void main(String[] args) {

		BaseFrame frame = new BaseFrame();
		
//		TestBed tester = new TestBed(frame);
//		LCD.clear();
//		LCD.drawString("Enter: begin tests", 0, 1);
//		Button.ENTER.waitForPressAndRelease();
//		tester.testLegs();
//		Button.ENTER.waitForPressAndRelease();
//		tester.runTests();

		LCD.clear();
		LCD.drawString("Enter to Stand", 0, 1);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
//		frame.stand(false);
		frame.standFromCat();
//		frame.scratchPad();
		Button.ENTER.waitForPressAndRelease();
	}

}
