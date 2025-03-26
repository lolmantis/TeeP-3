package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import lejos.hardware.Button;

public class shutdownThread extends Thread {
	private BaseFrame frame; // a reference to the container
	
	public shutdownThread(BaseFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void run() {
		// wait indefinitely
		Button.ESCAPE.waitForPressAndRelease();
		frame.stopProgram();
	}

}
