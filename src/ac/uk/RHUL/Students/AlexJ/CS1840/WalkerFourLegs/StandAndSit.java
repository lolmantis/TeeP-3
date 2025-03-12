package ac.uk.RHUL.Students.AlexJ.CS1840.WalkerFourLegs;

import ac.uk.RHUL.Students.AlexJ.CS1840.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1840.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1840.Leg.LegID;

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
		Leg[] hindLegs = new Leg[] { legs.getLeg(LegID.BACK_LEFT), legs.getLeg(LegID.BACK_RIGHT) };
		for (Leg leg : hindLegs) {
			leg.rotate(90);
		}

		legs.waitToStop();
		legs.endSync();
	}

}
