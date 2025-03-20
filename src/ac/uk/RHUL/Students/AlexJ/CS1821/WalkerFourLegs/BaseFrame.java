package ac.uk.RHUL.Students.AlexJ.CS1821.WalkerFourLegs;

import ac.uk.RHUL.Students.AlexJ.CS1821.BehaviourSystems.StandAndSit;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1821.persConsts.WalkerConsts;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;

public class BaseFrame {

	
	private final Legs legs;
	
	private final EV3GyroSensor pitch;
	private final EV3UltrasonicSensor collisionDetector;
	private final NXTSoundSensor listener;
	
	private final StandAndSit riser;

	BaseFrame() {

		// initialise legs
		LCD.clear();
		LCD.drawString("Booting legs...", 0, 0);
		Leg FrontLeft = new Leg(WalkerConsts.FRONT_LEFT_LEG_PORT, LegState.NEUTRAL);
		Leg FrontRight = new Leg(WalkerConsts.FRONT_RIGHT_LEG_PORT, LegState.NEUTRAL);
		Leg BackLeft = new Leg(WalkerConsts.BACK_LEFT_LEG_PORT, LegState.SEATED);
		Leg BackRight = new Leg(WalkerConsts.BACK_RIGHT_LEG_PORT, LegState.SEATED);

		legs = new Legs(new Leg[] { FrontLeft, FrontRight, BackLeft, BackRight });

		LCD.clear();
		LCD.drawString("Booting sensors...", 0, 0);
		// sensors will be booted down here

		pitch = new EV3GyroSensor(WalkerConsts.GYROSCOPE_PITCH_PORT);
		collisionDetector = new EV3UltrasonicSensor(WalkerConsts.DISTANCE_COLLISION_PORT);
		listener = new NXTSoundSensor(WalkerConsts.SOUND_LISTENER_PORT);
		

		// method classes down here

		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 0);
		riser = new StandAndSit(legs);
	}

	public Legs getLegs() {
		return legs;
	}

	/**
	 * @deprecated
	 * @param reversed
	 */
	public void stand(Boolean reversed) {
		riser.stand(reversed);
		// will get more convoluted later
	}

	public void standFromCat() {
		riser.standFromCatMode();
	}
	
	public void scratchPad() {
		riser.temp_backwardsCatMode();
	}
}
