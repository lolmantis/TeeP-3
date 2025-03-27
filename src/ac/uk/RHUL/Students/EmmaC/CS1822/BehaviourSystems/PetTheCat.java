package ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

import java.util.Random;

import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;

public class PetTheCat implements Behavior, endProgram {
	private final EV3TouchSensor pet;
	private final SampleProvider getPets;
	private float[] isPetted = new float[1];
	
	private final Random random = new Random();
	private long meowTimer;
	private long oldTime = System.currentTimeMillis();
	
	private boolean programRunning = true; // for closing up
	
	public PetTheCat(EV3TouchSensor pet) {
		this.pet = pet;
		getPets = this.pet.getTouchMode();
		resetMeowTimer();
	}
	
	@Override
	public void setEndProgram() {
		programRunning = false;
	}
	
	private synchronized void resetMeowTimer() {
		meowTimer = WalkerConsts.MIN_MEOW_DELAY_MS + Long.valueOf(random.nextInt(WalkerConsts.MEOW_VARIANCE_MS)); 
		// Generates random aspect of meows - not perfectly a minute apart, not too random 
	}
	/**
	 * both updates the counter & purrs as required
	 */
	public synchronized void update() {
		long nowTime = System.currentTimeMillis();
		long deltaTime = nowTime - oldTime; 
		meowTimer -= deltaTime;
		oldTime = nowTime;

		LCD.drawString(String.format("%d", meowTimer), 0, 7);
		
		// Meow timer
		if (meowTimer <= 0) {
			meow();
			resetMeowTimer();
		}
	}
	
	/**
	 * 
	 * @return time to next meow
	 */
	public synchronized long timeToMeow() {
		return meowTimer;
	}
	
	public synchronized void meow() {
		Sound.playSample(WalkerConsts.meow, 100);
	}

	@Override
	public synchronized boolean takeControl() {
		getPets.fetchSample(isPetted, 0);
		update();
		return isPetted[0]==1 && programRunning;
	}

	@Override
	public synchronized void action() {
		meow();
	}

	@Override
	public void suppress() {}
}
