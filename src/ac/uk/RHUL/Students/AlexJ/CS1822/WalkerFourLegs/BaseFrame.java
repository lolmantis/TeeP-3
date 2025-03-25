package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems.PetTheCat;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.StandAndSit;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.WalkForwards;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BaseFrame implements Runnable{

	
	private final Legs legs;
	
	private final EV3GyroSensor pitch;
	private final EV3UltrasonicSensor collisionDetector;
	private final EV3TouchSensor purr;
	
	private final StandAndSit riser;
	private final PetTheCat pet;
	private final WalkForwards movement;
	
	private final Arbitrator arbiter;

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
		LCD.drawString("Booting sensors...", 0, 1);
		// sensors will be booted down here

		pitch = new EV3GyroSensor(WalkerConsts.GYROSCOPE_PITCH_PORT); // this is unused, change that
		collisionDetector = new EV3UltrasonicSensor(WalkerConsts.DISTANCE_COLLISION_PORT);
		purr = new EV3TouchSensor(WalkerConsts.TOUCH_PURR_PORT); 
		

		// method classes down here

		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 2);
		riser = new StandAndSit(legs);
		pet = new PetTheCat(purr);
		movement = new WalkForwards(legs);
		
		// placeholders
		LCD.clear();
		LCD.drawString("Booting arbiter...", 0, 3);
		Behavior[] priorities = new Behavior[] {
			movement,riser,pet
		};
		arbiter = new Arbitrator(priorities);
	}
	
	public void runArbiter() {
		arbiter.go();
	}
	
	public void stopArbiter() {
		arbiter.stop();
	}
	
	public void test() {
		System.out.println("Hello world");
	}

	public Legs getLegs() {
		return legs;
	}

	public void standFromCat() {
		riser.standFromCatMode();
	}
	
	public void sitToCat() {
		movement.neutralise();
		riser.sit();
	}
	
	public void waitToMove() {
		Delay.msDelay(50);
		legs.beginSync();
		legs.waitToStop();
		legs.endSync();
	}
	
	/**
	 * <a>runs indefinitely until ENTER is pushed, that can be changed later<a>
	 * needs fixing!
	 */
	public void petTheCat() {
		int timeStep = 100;
		LCD.clear();
		LCD.drawString("Ready to purr", 0, 0);
		while (!Button.ENTER.isDown()) {
			pet.update(timeStep);
			Delay.msDelay(timeStep);
		}
		return;
	}
	
	
	public int timeToMeow() {
		return pet.timeToMeow();
	}
	
	public void walkForwards() {
		movement.primeToWalk();
	}
	
	public boolean walkPrimed() {
		return movement.getPrimedFlag();
	}
	
	public void stopWalking() {
		movement.stop();
	}

	@Override
	public void run() {
		runArbiter();
		
	}
}
