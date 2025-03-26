package ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs;

import ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems.PetTheCat;
import ac.uk.RHUL.Students.IbrahimA.BehaviourSystems.Collision;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.StandAndSit;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.WalkForwards;
import ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems.MeowTimer;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BaseFrame {
	
	private final Shutdown exitTask;
	private final MeowTimer meowTask;
	
	private final Legs legs;
	
	private boolean programRunning = true;
	
	private final EV3GyroSensor pitch;
	private final EV3UltrasonicSensor collisionDetector;
	private final EV3TouchSensor purr;
	
	private final StandAndSit riser;
	private final PetTheCat pet;
	private final WalkForwards movement;
	private final Collision collision;
	
	private final Arbitrator arbiter;
	private final ExecutorService manager = Executors.newCachedThreadPool();

	BaseFrame() {

		// initialise legs
		LCD.clear();
		LCD.drawString("Booting legs...", 0, 1);
		Leg FrontLeft = new Leg(WalkerConsts.FRONT_LEFT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_LEFT);
		Leg FrontRight = new Leg(WalkerConsts.FRONT_RIGHT_LEG_PORT, LegState.NEUTRAL, LegID.FRONT_RIGHT);
		Leg BackLeft = new Leg(WalkerConsts.BACK_LEFT_LEG_PORT, LegState.SEATED, LegID.BACK_LEFT);
		Leg BackRight = new Leg(WalkerConsts.BACK_RIGHT_LEG_PORT, LegState.SEATED, LegID.BACK_RIGHT);

		legs = new Legs(new Leg[] { FrontLeft, FrontRight, BackLeft, BackRight });

		LCD.clear();
		LCD.drawString("Booting sensors...", 0, 1);
		
		// sensors will be booted down here

		pitch = new EV3GyroSensor(WalkerConsts.GYROSCOPE_PITCH_PORT); // this is unused, change that
		try {
		collisionDetector = new EV3UltrasonicSensor(WalkerConsts.DISTANCE_COLLISION_PORT);
		} catch (IllegalArgumentException e) {
			LCD.drawString("collision sensors", 0, 1);
			LCD.drawString("crashed.", 0, 2);
			LCD.drawString("restarting...", 0, 3);
			legs.close();
			pitch.close();
			throw new IllegalArgumentException("Sensors failed to int. Restart");
		}
		purr = new EV3TouchSensor(WalkerConsts.TOUCH_PURR_PORT); 
		

		// method classes down here
		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 1);
		riser = new StandAndSit(legs);
		pet = new PetTheCat(purr);
		movement = new WalkForwards(legs);
		collision = new Collision(collisionDetector);
		
		LCD.clear();
		LCD.drawString("Booting watchers...", 0, 1);
		meowTask = new MeowTimer(this);
		exitTask = new Shutdown(this);
		
		
		// placeholders
		LCD.clear();
		LCD.drawString("Booting arbiter...", 0, 1);
		Behavior[] priorities = new Behavior[] {movement, riser, collision, pet};
		
		// quickly hiding the text the arbiter throws into the console
		
		PrintStream console = System.out;
		ByteArrayOutputStream throwAway = new ByteArrayOutputStream();
		PrintStream empty = new PrintStream(throwAway);
		System.setOut(empty);
		arbiter = new Arbitrator(priorities, true);
		System.setOut(console);
		
		LCD.clear();
		LCD.drawString("TP3 v7.6.9.X", 0, 0);
		LCD.drawString("Made by:", 0, 1);
		LCD.drawString("Alex Jacob", 0, 2);
		LCD.drawString("Emma Chaudheri", 0, 3);
		LCD.drawString("Ibrahim Animasahun", 0, 4);
		LCD.drawString("Gilbert Awuah", 0, 5);
	}
	
	public void runArbiter() {
		// start the threads before we begin!
		manager.execute(meowTask);
		manager.execute(exitTask);
		arbiter.go();
	}
	
	public synchronized void stopProgram() {
		LCD.drawString("F", 0, 2);
		programRunning = false;
		riser.setEndProgram();
		pet.setEndProgram();
		movement.setEndProgram();
		collision.setEndProgram();
		arbiter.stop();
		movement.neutralise();
		Delay.msDelay(500);
		riser.sit();
		Delay.msDelay(2000);
		legs.close();
		pitch.close();
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
		legs.beginSync();
		legs.waitToStop();
		legs.endSync();
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
