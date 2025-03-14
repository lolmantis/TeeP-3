package ac.uk.RHUL.Students.AlexJ.CS1821.persConsts;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public final class WalkerConsts {

	public WalkerConsts() {
	};

	public static final int MOTOR_SPEED_BASE = 70;
	public static final int NUMBER_OF_LEGS = 4;
	public static final float GEAR_RATIO = 3.24f;

	public static final Port GYROSCOPE_PITCH_PORT = SensorPort.S1;

	public static final Port FRONT_LEFT_LEG_PORT = MotorPort.A;
	public static final Port FRONT_RIGHT_LEG_PORT = MotorPort.B;
	public static final Port BACK_RIGHT_LEG_PORT = MotorPort.C;
	public static final Port BACK_LEFT_LEG_PORT = MotorPort.D;

	// no sensors at present, figure out what and how you want to use them later

}
