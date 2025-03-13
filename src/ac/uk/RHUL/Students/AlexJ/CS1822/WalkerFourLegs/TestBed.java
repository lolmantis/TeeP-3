package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class TestBed {

	private void testLegs(BaseFrame frame) {
		LCD.clear();
		LCD.drawString("Test legs", 0, 1);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		frame.legTest();
	}

	private void testAngles(BaseFrame frame) {
		LCD.drawString("Measure angles", 0, 2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		frame.getLegs().angleReader();
	}

	public static void main(String[] args) {
		LCD.clear();
		LCD.drawString("Press Enter for each test", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		BaseFrame frame = new BaseFrame();
		TestBed tester = new TestBed();

		tester.testLegs(frame);
		tester.testAngles(frame);

	}

}
