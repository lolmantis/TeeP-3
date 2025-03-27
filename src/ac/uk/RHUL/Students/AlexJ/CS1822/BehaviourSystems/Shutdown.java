package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.BaseFrame;
import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class Shutdown implements Behavior {
	private final BaseFrame frame; // a reference to the container
	
	public Shutdown(BaseFrame frame) {
		this.frame = frame;
	}

	@Override
	public boolean takeControl() {
		return Button.ESCAPE.isDown();
	}

	@Override
	public void action() {
		frame.stopProgram();
	}

	@Override
	public void suppress() {
		// nu uh
	}

}
