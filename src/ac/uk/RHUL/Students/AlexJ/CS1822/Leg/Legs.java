package ac.uk.RHUL.Students.AlexJ.CS1822.Leg;

import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
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

	/**
	 * <b>expects to be called outside a sync block<b>
	 */
	public void waitToStop() {
		for (Leg leg : legs) {
			leg.waitComplete();
		}
	}
	
	public void setSpeed(int speed) {
		for (Leg leg : legs) {
			leg.setSpeed(speed);
		}
	}

	/** 
	 * <b> has its own sync call, for emergencies <b>
	 */
	public void immediateStop() {
		beginSync();
		for (Leg leg : legs) {
			leg.stop();
		}
		endSync();
	}
	
	public void returnToNeutral() {
		beginSync();
		for (Leg leg : legs) {
			leg.returnToNeutral();
		}
		waitToStop();
		endSync();
	}

	public void angleReader() {
		int[] startingAngles = new int[4];
		LCD.clear();
		for (int currentLeg = 0; currentLeg < 4; currentLeg++) {
			int limitAngle = legs[currentLeg].getMotor().getLimitAngle();
			LCD.drawString(String.format("Leg %d Ang %d", currentLeg, limitAngle), 0, currentLeg + 1);
			startingAngles[currentLeg] = limitAngle;
		}
		Button.ENTER.waitForPressAndRelease();
		Delay.msDelay(500);
		beginSync();
		for (Leg leg : legs) {
			leg.rotate(30);
		}
		waitToStop();
		endSync();
		LCD.clear();
		for (int currentLeg = 0; currentLeg < 4; currentLeg++) {
			int limitAngle = legs[currentLeg].getMotor().getLimitAngle();
			LCD.drawString(String.format("%d --> %d", startingAngles[currentLeg], limitAngle), 0, currentLeg+1);
		}
		LCD.drawString("Enter to quit", 0, 7);
		Button.ENTER.waitForPressAndRelease();
	}

	public void testMotors() {
		LCD.drawString("boom", 0, 0);
		beginSync();
		waitToStop();
		for (Leg leg : legs) {
			leg.rotate(30);
		}
		waitToStop();
		endSync();
	}
	
	public void close() {
		for (Leg leg : legs) {
			leg.close();
		}
	}

}
