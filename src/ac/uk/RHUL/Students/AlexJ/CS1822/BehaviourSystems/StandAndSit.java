package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;

public class StandAndSit {

	private boolean Standing = false;

	private Legs legs;

	public StandAndSit(Legs legs) {
		this.legs = legs;
	}

	public void sit() {
		if (!Standing) {
			return;
		}
		Standing = false;
		// coming soon
	}

	public void standFromCatMode() {
		if (Standing) {
			return;
		}
		// front legs are straight down, hind legs are straight forward
		legs.beginSync();
		// hind legs
		legs.getLeg(LegID.FRONT_LEFT).rotate(-17);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(-17);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
	}
}
