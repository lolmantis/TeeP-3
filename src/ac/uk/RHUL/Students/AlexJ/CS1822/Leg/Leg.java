package ac.uk.RHUL.Students.AlexJ.CS1822.Leg;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.motor.BaseRegulatedMotor;

public final class Leg {

	private final BaseRegulatedMotor power; // ratio 24:40
	private LegState state;
	private LegID identifier;
	private int reversed;
	/*
	 * gears: 24 into 40 twice
	 * ratio 3:5
	 * 3:5 --> 3:5
	 * 1.8x torque
	 * twice
	 * 2.79x the torque power
	 * 
	 * positive angle = clockwise, negative angle = anticlockwise 
	 */
	private int speed;

	public Leg(Port port, LegState state, LegID identifier) {
		this.state = state;
		this.identifier = identifier;
		// the front motors are upside down
		reversed = ((this.identifier == LegID.FRONT_LEFT || this.identifier == LegID.FRONT_RIGHT) ? -1:1);
		
		power = new EV3LargeRegulatedMotor(port);
		this.speed = WalkerConsts.MOTOR_SPEED_BASE;
		power.setSpeed(this.speed);
		
	}

	protected BaseRegulatedMotor getMotor() {
		return power;
	}
	
	public LegID getID() {
		return identifier;
	}
	
	public void setSpeed(float speed) {
		power.setSpeed(speed);
	}

	/**
	 * 
	 * does not account for the stability bars, do not attempt to rotate legs too far
	 * 
	 * @param angle
	 * angle to be rotated
	 */
	public void rotate(int angle) {
		power.rotate(Math.round(angle*WalkerConsts.GEAR_RATIO)*reversed, true); 
		// accounting for gear ratios abstractly, this ratio is 25/9 but it'll still be slightly wrong
	}

	public void stop() {
		power.stop(true);
	}

	public void waitComplete() {
		power.waitComplete();
	}
	
	public void stand() {
		rotate(-WalkerConsts.STAND_REAR_ANGLE);
		// only used from catmode
	}
	
	public void sit() {
		rotate(WalkerConsts.STAND_REAR_ANGLE);
	}

	public void stepForward(LegState newState) {
		// rotate forward 20 degrees
		rotate(WalkerConsts.STEP_ANGLE);
		state = newState;
	}
	
	public void doubleStepForward() {
		if (state == LegState.BACK) {			
		rotate(WalkerConsts.STEP_ANGLE*2);
		state = LegState.FORWARD;
		}
	}
	
	public void doubleStepBack() {
		if (state == LegState.FORWARD) {
			rotate(-WalkerConsts.STEP_ANGLE*2);
			state = LegState.BACK;
		}
	}
	
	public void stepBack(LegState newState) {
		// rotate back 20 degrees
		rotate(-WalkerConsts.STEP_ANGLE);
		state = newState;
	}

	public void returnToNeutral() {
		switch (state) {
		case NEUTRAL:
			break;
		case FORWARD:
			stepBack(LegState.NEUTRAL);
			break;
		case BACK:
			stepForward(LegState.NEUTRAL);
			break;
		case SEATED:
			break;
		}
	}
	
	public void close() {
		power.close();
	}
}
