package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import lejos.robotics.subsumption.Behavior;

public class StandAndSit implements Behavior {

	private Legs legs;

	public StandAndSit(Legs legs) {
		this.legs = legs;
	}

	public void sit() {
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).rotate(-17);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(-17);
		legs.getLeg(LegID.BACK_LEFT).sit();
		legs.getLeg(LegID.BACK_RIGHT).sit();
		legs.waitToStop();
		legs.endSync();
	}

	public void standFromCatMode() {
		// front legs are straight down, hind legs are straight forward
		legs.beginSync();
		// hind legs
		legs.getLeg(LegID.FRONT_LEFT).rotate(17);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(17);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
}
