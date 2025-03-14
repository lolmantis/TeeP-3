package ac.uk.RHUL.Students.AlexJ.CS1821.WalkerFourLegs;

import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Legs;

public class StandAndSit {

	private boolean Standing = false;

	private Legs legs;

	StandAndSit(Legs legs) {
		this.legs = legs;
	}

	public void stand(Boolean reversed) {
		if (Standing) {
			return;
		}
		Standing = true;
		legs.waitToStop();
		legs.beginSync();
		for (Leg leg : legs.getLegs()) {
			leg.stand(reversed);
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
		legs.getLeg(LegID.FRONT_LEFT).rotate(-15);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(-15);
		legs.getLeg(LegID.BACK_LEFT).stand(false);
		legs.getLeg(LegID.BACK_RIGHT).stand(false);
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
	}

}
