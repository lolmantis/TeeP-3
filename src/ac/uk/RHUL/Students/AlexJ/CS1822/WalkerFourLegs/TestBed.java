package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class TestBed {
	
	BaseFrame frame;
	
	TestBed() {
		throw new RuntimeException("Error: no frame provided to TestBed");
	}
	
	TestBed(BaseFrame frame) {
		this.frame = frame;
	}

	private void testLegs(BaseFrame frame) {
		LCD.clear();
		LCD.drawString("Test legs", 0, 0);
		frame.getLegs().testMotors();
	}

	private void testAngles(BaseFrame frame) {
		LCD.clear();
		LCD.drawString("Measure angles", 0, 0);
		frame.getLegs().angleReader();
	}

	public void runTests() {
		LCD.clear();
		LCD.drawString("Press Enter for",0,0);
		LCD.drawString("each test", 0, 1);
		Button.ENTER.waitForPressAndRelease();

//		testLegs(frame);
		testAngles(frame);

	}

}
