package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.BaseFrame;
import lejos.hardware.Battery;
import lejos.robotics.subsumption.Behavior;

public class BatteryChecker implements Behavior {
	
	private BaseFrame frame;
	
	public BatteryChecker(BaseFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean takeControl() {
		return Battery.getBatteryCurrent() < 0.1f;
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
