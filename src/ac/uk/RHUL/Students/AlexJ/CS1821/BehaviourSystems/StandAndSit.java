package ac.uk.RHUL.Students.AlexJ.CS1821.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Legs;

public class StandAndSit {

	private boolean Standing = false;

	private Legs legs;

	public StandAndSit(Legs legs) {
		this.legs = legs;
	}

	/**
	 * @deprecated
	 * 
	 * @param reversed
	 */
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
		legs.getLeg(LegID.FRONT_LEFT).rotate(-17);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(-17);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();
		// arbitrary rotate val, will be fine tuned later

		legs.waitToStop();
		legs.endSync();
	}
	
	public void temp_backwardsCatMode() {
		legs.beginSync();
		// all angles are backwards
		legs.getLeg(LegID.BACK_LEFT).rotate(20);
		legs.getLeg(LegID.BACK_RIGHT).rotate(20);
		legs.getLeg(LegID.FRONT_LEFT).rotate(80);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(80);
		
		legs.waitToStop();
		legs.endSync();
	}

}
