package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class StandAndSit implements Behavior, endProgram {

	private Legs legs;
	private boolean programRunning = true;
	private final SampleProvider groundSampleProvider;
	private float[] sample = new float[1];
	

	public StandAndSit(Legs legs, EV3TouchSensor groundCheck) {
		groundSampleProvider = groundCheck.getTouchMode();
		this.legs = legs;
	}
	
	@Override
	public synchronized void setEndProgram() {
		programRunning = false;
	}

	public synchronized void sit() {
		if (isSitting()) {
			return;
		}
		legs.beginSync();
//		legs.getLeg(LegID.FRONT_LEFT).rotate(-WalkerConsts.STAND_FRONT_ANGLE);
//		legs.getLeg(LegID.FRONT_RIGHT).rotate(-WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.BACK_LEFT).sit();
		legs.getLeg(LegID.BACK_RIGHT).sit();
		legs.endSync();
		legs.waitToStop();
		Thread.yield();
	}

	public synchronized void stand() {
		if (!isSitting()) {
			return;
		}
		legs.beginSync();
//		legs.getLeg(LegID.FRONT_LEFT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
//		legs.getLeg(LegID.FRONT_RIGHT).rotate(WalkerConsts.STAND_FRONT_ANGLE);
		legs.getLeg(LegID.BACK_LEFT).stand();
		legs.getLeg(LegID.BACK_RIGHT).stand();

		legs.endSync();
		legs.waitToStop();
		Thread.yield();
	}
	
	/**
	 * easy simple check, relies on groundSample()
	 * @return if we're currently sitting
	 */
	public boolean isSitting() {
		return groundSample()==1;
	}
	
	public float groundSample() {
		groundSampleProvider.fetchSample(sample, 0);
		return sample[0];
	}

	@Override
	public boolean takeControl() {
		// either we're saying sit, or the program is ending
//		return programRunning ? (Button.ENTER.isDown() || sitting) : false;
		return isSitting() && programRunning;
	}

	@Override
	public synchronized void action() {
		if (isSitting()) {
			stand();
		}
	}

	@Override
	public void suppress() {
		// if we're mid-sit, we can't stop
		
	}
}
