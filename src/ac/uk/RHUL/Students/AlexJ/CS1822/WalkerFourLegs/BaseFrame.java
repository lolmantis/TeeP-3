package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems.PetTheCat;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.StandAndSit;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.WalkForwards;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class BaseFrame {

	
	private final Legs legs;
	
	private final EV3GyroSensor pitch;
	private final EV3UltrasonicSensor collisionDetector;
	private final EV3TouchSensor purr;
	
	private final StandAndSit riser;
	private final PetTheCat pet;
	private final WalkForwards movement;

	BaseFrame() {

		// initialise legs
		LCD.clear();
		LCD.drawString("Booting legs...", 0, 0);
		Leg FrontLeft = new Leg(WalkerConsts.FRONT_LEFT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_LEFT);
		Leg FrontRight = new Leg(WalkerConsts.FRONT_RIGHT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_RIGHT);
		Leg BackLeft = new Leg(WalkerConsts.BACK_LEFT_LEG_PORT, LegState.SEATED, LegID.BACK_LEFT);
		Leg BackRight = new Leg(WalkerConsts.BACK_RIGHT_LEG_PORT, LegState.SEATED, LegID.BACK_RIGHT);

		legs = new Legs(new Leg[] { FrontLeft, FrontRight, BackLeft, BackRight });

		LCD.clear();
		LCD.drawString("Booting sensors...", 0, 0);
		// sensors will be booted down here

		pitch = new EV3GyroSensor(WalkerConsts.GYROSCOPE_PITCH_PORT);
		collisionDetector = new EV3UltrasonicSensor(WalkerConsts.DISTANCE_COLLISION_PORT);
		purr = new EV3TouchSensor(WalkerConsts.TOUCH_PURR_PORT); 
		

		// method classes down here

		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 0);
		riser = new StandAndSit(legs);
		pet = new PetTheCat(purr);
		movement = new WalkForwards(legs);
		
	}

	public Legs getLegs() {
		return legs;
	}

	public void standFromCat() {
		riser.standFromCatMode();
	}
	
	public void petTheCat() {
		pet.update(1);
	}
	
	public void walkForwards() {
		movement.walk();
	}
	
	public void stopWalking() {
		movement.stop();
	}
}
