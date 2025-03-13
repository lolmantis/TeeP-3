package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;

public class StandAndSit {

	private boolean Standing = false;

	private Legs legs;

	StandAndSit(Legs legs) {
		this.legs = legs;
	}

	public void stand() {
		if (Standing) {
			return;
		}
		Standing = true;
		legs.waitToStop();
		legs.beginSync();
		for (Leg leg : legs.getLegs()) {
			leg.Stand();
		}
		legs.waitToStop();
		legs.endSync();
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
		legs.getLeg(LegID.BACK_LEFT).rotate(90);
		legs.getLeg(LegID.BACK_RIGHT).rotate(90);
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
	}

}
