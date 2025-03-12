package ac.uk.RHUL.Students.AlexJ.CS1840.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class TestBed {
	
	private void testLegs(BaseFrame frame) {
		LCD.clear();
		LCD.drawString("Press Enter to Test legs", 0, 1);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		frame.legTest();
	}
	
	public static void main(String[] args) {
		BaseFrame frame = new BaseFrame();
		TestBed tester = new TestBed();
		
		tester.testLegs(frame);
		
	}

}
