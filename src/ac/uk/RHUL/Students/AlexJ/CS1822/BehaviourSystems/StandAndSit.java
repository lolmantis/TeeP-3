package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class StandAndSit implements Behavior, endProgram {

	private Legs legs;
	private boolean programRunning = true;
	private boolean sitting = true;

	public StandAndSit(Legs legs) {
		this.legs = legs;
	}
	
	@Override
	public void setEndProgram() {
		programRunning = false;
	}

	public void sit() {
		if (sitting) {
			return;
		}
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).rotate(-WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(-WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.BACK_LEFT).sit();
		legs.getLeg(LegID.BACK_RIGHT).sit();
		legs.waitToStop();
		legs.endSync();
		sitting = true;
	}

	public void stand() {
		if (!sitting) {
			return;
		}
		// front legs are straight down, hind legs are straight forward
		legs.beginSync();
		// hind legs
		legs.getLeg(LegID.FRONT_LEFT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
		sitting = false;
	}
	
	public boolean isSitting() {
		return sitting;
	}

	@Override
	public boolean takeControl() {
		return programRunning ? (Button.ENTER.isDown() || sitting) : false;
		// either we're saying sit, or the program is ending
	}

	@Override
	public void action() {
		if (sitting) {
			stand();
		} else {
			sit();
		}
	}

	@Override
	public void suppress() {
		// if we're mid-sit, we can't stop
		
	}
}
