package ac.uk.RHUL.Students.AlexJ.CS1822.persConsts;

import java.io.File;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public final class WalkerConsts {

	public WalkerConsts() {
	};

	public static final int MOTOR_SPEED_BASE = 540;
	public static final int NUMBER_OF_LEGS = 4;
	public static final float GEAR_RATIO = 25f/9f; // 2.778 rec.
	public static final int STEP_ANGLE = 20;
	public static final int STEP_PAUSE_DURATION_MS = 2000;
	public static final int STAND_AND_SIT_DELAY_MS = 5000;
	public static final float STOP_DISTANCE = 0.50f; // 30 cm in meters
	
	public static final int STAND_FRONT_ANGLE = 17;
	public static final int STAND_REAR_ANGLE = 90;

	public static final Port TOUCH_GROUND_PORT = SensorPort.S1;
	public static final Port DISTANCE_COLLISION_PORT = SensorPort.S2;
	public static final Port TOUCH_PURR_PORT = SensorPort.S3;

	public static final Port FRONT_LEFT_LEG_PORT = MotorPort.A;
	public static final Port FRONT_RIGHT_LEG_PORT = MotorPort.B;
	public static final Port BACK_RIGHT_LEG_PORT = MotorPort.C;
	public static final Port BACK_LEFT_LEG_PORT = MotorPort.D;
	
	public static final File meow = new File("MeowSoundEffectLoudCrunched.wav");
	public static final long MIN_MEOW_DELAY_MS = 50000L;
	public static final int MEOW_VARIANCE_MS = 20000;

}
