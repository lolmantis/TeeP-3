package ac.uk.RHUL.Students.AlexJ.CS1840.Leg;

import ac.uk.RHUL.Students.AlexJ.CS1840.persConsts.WalkerConsts;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.motor.BaseRegulatedMotor;

public final class Leg {
	
	private final BaseRegulatedMotor power;
	private int speed;
	
	public Leg(Port port) {
		this(port, WalkerConsts.MOTOR_SPEED_MEANDER);
		
	}
	
	public Leg(Port port, int speed) {
		power = new EV3LargeRegulatedMotor(port);
		this.speed = speed;
		power.setSpeed(this.speed);
	}
	
	protected BaseRegulatedMotor getMotor() {
		return power;
	}
	
	public void forward() {
		power.forward();
	}
	
	public void rotate(int angle) {
		power.rotate(angle, true);
	}
	
	public void stop() {
		power.stop(true);
	}
	
	public void waitComplete() {
		power.waitComplete();
	}
	
	
	public void Stand() {
		power.rotate(80); 
		// in case this gets more complex, I'm abstracting standing
		// basic principle is to rotate 90 degrees
		// note that the angle is reversed, because the motors are installed upside down
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
