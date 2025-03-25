package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Driver {

	public static void main(String[] args) {

		BaseFrame frame = new BaseFrame();

//		frame.standFromCat();
//		frame.waitToMove();
		
//		Button.ENTER.waitForPressAndRelease();
		
//		frame.walkForwards();
//		if (frame.walkPrimed()) {
//			frame.stopWalking();
//		}
		
		LCD.clear();
		LCD.drawString("ready.", 0, 4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		frame.standFromCat();
//		LCD.drawString("ready for step 1", 0, 0);
//		Button.ENTER.waitForPressAndRelease();
//		LCD.clear();
//		frame.walkForwards();
//		LCD.drawString("ready to sit.", 0, 4);
//		Button.ENTER.waitForPressAndRelease();
//		LCD.clear(); 
//		frame.sitToCat();
//		LCD.drawString("Done.", 0, 4);
//		Button.ENTER.waitForPressAndRelease();
		
		Button.ENTER.waitForPressAndRelease();
		Thread mainthread = new Thread(frame);
		LCD.drawString("about to run arbiter", 0, 3);
		mainthread.run();
		LCD.drawString("arbiter running", 0, 3);
//		while (!Button.ENTER.isDown()) {
//			// wait
//			Delay.msDelay(500);
//		}
		frame.stopArbiter();
		LCD.drawString("Arbiter stopped", 0, 3);
		Button.ENTER.waitForPressAndRelease();
//		try {
//			mainthread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
