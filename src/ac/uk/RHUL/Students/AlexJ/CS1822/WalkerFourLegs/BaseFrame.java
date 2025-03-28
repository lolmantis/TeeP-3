package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems.PetTheCat;
import ac.uk.RHUL.Students.IbrahimA.BehaviourSystems.Collision;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.StandAndSit;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.WalkForwards;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.BatteryChecker;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.Shutdown;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BaseFrame {
	
	
	private final Legs legs;
	
	private boolean programRunning = true;
	
	private final EV3TouchSensor ground;
	private final EV3UltrasonicSensor collisionDetector;
	private final EV3TouchSensor purr;
	
	private final Shutdown exitTask;
	private final BatteryChecker batteryTask;
	private final StandAndSit riser;
	private final PetTheCat pet;
	private final WalkForwards movement;
	private final Collision collision;
	
	private final Arbitrator arbiter;

	BaseFrame() {

		

		LCD.clear();
		LCD.drawString("Booting sensors...", 0, 1);
		
		// sensors will be booted down here
		try {
		collisionDetector = new EV3UltrasonicSensor(WalkerConsts.DISTANCE_COLLISION_PORT);
		} catch (IllegalArgumentException e) {
			LCD.drawString("collision sensors", 0, 1);
			LCD.drawString("crashed.", 0, 2);
			LCD.drawString("restarting...", 0, 3);
			throw new IllegalArgumentException("Sensors failed to int. Restart");
		}
		ground = new EV3TouchSensor(WalkerConsts.TOUCH_GROUND_PORT);
		purr = new EV3TouchSensor(WalkerConsts.TOUCH_PURR_PORT); 
		
		// initialise legs
		LCD.clear();
		LCD.drawString("Booting legs...", 0, 1);
		Leg FrontLeft = new Leg(WalkerConsts.FRONT_LEFT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_LEFT);
		Leg FrontRight = new Leg(WalkerConsts.FRONT_RIGHT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_RIGHT);
		Leg BackLeft = new Leg(WalkerConsts.BACK_LEFT_LEG_PORT, LegState.SEATED, LegID.BACK_LEFT);
		Leg BackRight = new Leg(WalkerConsts.BACK_RIGHT_LEG_PORT, LegState.SEATED, LegID.BACK_RIGHT);

		legs = new Legs(new Leg[] { FrontLeft, FrontRight, BackLeft, BackRight });

		// method classes down here
		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 1);
		riser = new StandAndSit(legs, ground);
		pet = new PetTheCat(purr);
		movement = new WalkForwards(legs);
		collision = new Collision(collisionDetector, this);
		batteryTask = new BatteryChecker(this);
		exitTask = new Shutdown(this);
		
		// arbiter
		LCD.clear();
		LCD.drawString("Booting arbiter...", 0, 1);
		Behavior[] priorities = new Behavior[] {movement, riser, collision, pet, batteryTask, exitTask};
		
		// quickly hiding the text the arbiter throws into the console
		
		PrintStream console = System.out;
		ByteArrayOutputStream throwAway = new ByteArrayOutputStream();
		PrintStream empty = new PrintStream(throwAway);
		System.setOut(empty);
		arbiter = new Arbitrator(priorities);
		System.setOut(console);
		
		LCD.clear();
		LCD.drawString("TP3 v15.2.1.1.2", 0, 0);
		LCD.drawString("Made by:", 0, 1);
		LCD.drawString("Alex Jacob", 0, 2);
		LCD.drawString("Emma Chaudheri", 0, 3);
		LCD.drawString("Ibrahim Animasahun", 0, 4);
		LCD.drawString("Gilbert Awuah", 0, 5);
	}
	
	public void runArbiter() {
		// start the threads before we begin!
		arbiter.go();
	}
	
	public synchronized void stopProgram() {
		programRunning = false;
		riser.setEndProgram();
		pet.setEndProgram();
		movement.setEndProgram();
		collision.setEndProgram();
		arbiter.stop();
		movement.neutralise();
		riser.sit();
		legs.close();
		ground.close();
		collisionDetector.close();
		purr.close();
		// fuck it we ball
		System.exit(0);
	}
	
	public boolean isRunning() {
		return programRunning;
	}
	
	public boolean isSitting() {
		return riser.isSitting();
	}
	
	public float groundCheck() {
		return riser.groundSample();
	}

	public Legs getLegs() {
		return legs;
	}

	public void standFromCat() {
		riser.stand();
	}
	
	public void sitToCat() {
		movement.neutralise();
		riser.sit();
	}
	
	public void waitToMove() {
		Delay.msDelay(50);
		legs.waitToStop();
	}
	
	/**
	 * calls a single update to the petting subsystem
	 */
	public void updateMeow() {
		pet.update();
	}
	
	public long timeToMeow() {
		return pet.timeToMeow();
	}
	
	public void meow() {
		pet.meow();
	}
	
	public boolean walkPrimed() {
		return movement.getPrimedFlag();
	}
	
	public void walkForwards() {
		movement.action();
	}
	
	public void stopWalking() {
		movement.stop();
	}
}
