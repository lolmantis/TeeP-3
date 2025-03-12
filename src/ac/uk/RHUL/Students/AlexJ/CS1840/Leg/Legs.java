package ac.uk.RHUL.Students.AlexJ.CS1840.Leg;

import ac.uk.RHUL.Students.AlexJ.CS1840.persConsts.WalkerConsts;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.utility.Delay;

public final class Legs {

	private final Leg[] legs; // 0 = FL, 1 = FR, 2 = BL 3 = BR

	public Legs(Leg[] legs) {
		if (legs.length != 4) {
			throw new RuntimeException("Error: must have four legs");
		}
		this.legs = legs;

		// an array of the motors is very useful
		// motor 0 is handled separately to avoid synchronising with itself
		BaseRegulatedMotor[] syncList = new BaseRegulatedMotor[WalkerConsts.NUMBER_OF_LEGS - 1];
		for (int i = 1; i < WalkerConsts.NUMBER_OF_LEGS; i++) {
			syncList[i - 1] = this.legs[i].getMotor();
		}
		this.legs[0].getMotor().synchronizeWith(syncList);
		// sync all the legs
	}

	public Leg[] getLegs() {
		return legs;
	}

	public Leg getLeg(LegID id) {
		switch (id) {
		case FRONT_LEFT:
			return legs[0];
		case FRONT_RIGHT:
			return legs[1];
		case BACK_LEFT:
			return legs[2];
		case BACK_RIGHT:
			return legs[3];
		}
		throw new RuntimeException("No leg ID passed to getLeg");
	}

	// doesn't actually matter which motor we pick, they're all sync'ed
	public void beginSync() {
		legs[0].getMotor().startSynchronization();
	}

	public void endSync() {
		legs[0].getMotor().endSynchronization();
	}

	public void waitToStop() {
		for (Leg leg : legs) {
			leg.waitComplete();
		}
	}

	public void immediateStop() {
		beginSync();
		for (Leg leg : legs) {
			leg.stop();
		}
		endSync();
	}

	public void testLegs() {
		waitToStop();
		beginSync();
		for (Leg leg : legs) {
			leg.forward();
		}
		Delay.msDelay(1000);
		immediateStop();
		endSync();
	}

}
