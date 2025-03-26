package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class StandAndSit implements Behavior, endProgram {

	private Legs legs;
	private boolean programRunning = true;
	private boolean sitting = true;

	public StandAndSit(Legs legs) {
		this.legs = legs;
	}
	
	@Override
	public synchronized void setEndProgram() {
		programRunning = false;
	}

	public synchronized void sit() {
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
		Delay.msDelay(WalkerConsts.STAND_AND_SIT_DELAY_MS);
		Thread.yield();
	}

	public synchronized void stand() {
		if (!sitting) {
			return;
		}
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.FRONT_RIGHT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();

		legs.waitToStop();
		legs.endSync();
		sitting = false;
		Delay.msDelay(WalkerConsts.STAND_AND_SIT_DELAY_MS);
		Thread.yield();
	}
	
	public boolean isSitting() {
		return sitting;
	}

	@Override
	public boolean takeControl() {
		// either we're saying sit, or the program is ending
//		return programRunning ? (Button.ENTER.isDown() || sitting) : false;
		LCD.drawString("2", 1, 3);
		return sitting && programRunning;
	}

	@Override
	public synchronized void action() {
		LCD.drawString("stnding", 0, 4);
		if (sitting) {
			stand();
		}
	}

	@Override
	public void suppress() {
		// if we're mid-sit, we can't stop
		
	}
}
