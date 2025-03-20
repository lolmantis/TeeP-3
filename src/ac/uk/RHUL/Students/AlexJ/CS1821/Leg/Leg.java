package ac.uk.RHUL.Students.AlexJ.CS1821.Leg;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import ac.uk.RHUL.Students.AlexJ.CS1821.persConsts.WalkerConsts;
import lejos.hardware.motor.BaseRegulatedMotor;

public final class Leg {

	private final BaseRegulatedMotor power; // ratio 24:40
	private LegState state;
	/*
	 * gears: 24 into 40 twice
	 * ratio 3:5
	 * 3:5 --> 3:5
	 * 1.8x torque
	 * twice
	 * 3.24x the torque power
	 * 
	 * positive angle = anticlockwise, negative angle = clockwise 
	 */
	private int speed;

	public Leg(Port port, LegState state) {
		power = new EV3LargeRegulatedMotor(port);
		this.speed = WalkerConsts.MOTOR_SPEED_BASE;
		power.setSpeed(this.speed);
		this.state = state;
	}

	protected BaseRegulatedMotor getMotor() {
		return power;
	}

	public void forward() {
		power.forward();
	}
	
	public void setSpeed(float speed) {
		power.setSpeed(speed);
	}

	public void rotate(int angle) {
		power.rotate(Math.round(angle*WalkerConsts.GEAR_RATIO), true); // accounting for gear ratios abstractly
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

	public void StepForward() {
		/*
		 * in order: forward 30 if from resting, wait for other steps then wait then
		 * rotate back 60 degrees
		 * 
		 * in essence, 60 up to go forward, then to move the body forward, 60 back
		 */
	}

	public void StepNeutral() {
		/*
		 * after stepping forward, return to neutral point
		 */
	}
}
