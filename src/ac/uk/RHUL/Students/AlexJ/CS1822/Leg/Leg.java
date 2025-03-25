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

	/**
	 * @deprecated
	 * spins motor forwards, dangerous
	 * as bars will snap, this is debug only
	 */
	public void forward() {
		power.forward();
	}
	
	public void setSpeed(float speed) {
		power.setSpeed(speed);
	}

	public void rotate(int angle) {
		power.rotate(Math.round(angle*WalkerConsts.GEAR_RATIO)*reversed, true); // accounting for gear ratios abstractly
	}

	public void stop() {
		power.stop(true);
	}

	public void waitComplete() {
		power.waitComplete();
	}
	
	public void stand() {
		rotate(-90);
		// only used from catmode
	}
	
	public void sit() {
		rotate(90);
	}

	public void stepForward(LegState newState) {
		// rotate forward 20 degrees
		rotate(20);
		state = newState;
	}
	
	public void stepBack(LegState newState) {
		// rotate back 20 degrees
		rotate(-20);
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
}
