package ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

import java.util.Random;

import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;

public class PetTheCat implements Behavior, endProgram {
	private final EV3TouchSensor pet;
	private final SampleProvider getPets;
	private float[] isPetted = new float[1];
	
	private final Random random = new Random();
	private int meowTimer;
	private boolean isPurring = false;
	
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
	
	private void resetMeowTimer() {
		meowTimer = 50000 + random.nextInt(20000); 
		// Generates random aspect of meows - not perfectly a minute apart, not too random 
	}
	/**
	 * 
	 * @param deltaTime
	 * @return time delay for the sound file
	 */
	public void update(int deltaTime) {
		getPets.fetchSample(isPetted, 0);
		if (isPetted[0] == 1) {
			if (!isPurring) {
				isPurring = true;
				startPurring();
			}
		}
		
		// Meow timer
		meowTimer -= deltaTime;
		if (meowTimer <= 0) {
			meow();
			resetMeowTimer();
		}
		LCD.drawString(String.format("Time to meow: %d", timeToMeow()), 0, 2);
	}
	
	public int timeToMeow() {
		return meowTimer;
	}
	
	private void startPurring() {
		int delaytime = Sound.playSample(new java.io.File("CatPurringSoundEffectLoud.wav"), 100);
		LCD.drawInt(delaytime,0,6);
		meowTimer -= delaytime;
		Delay.msDelay(delaytime);
		isPurring = false;
	}
	
	private void meow() {
		int delaytime = Sound.playSample(new java.io.File("MeowSoundEffectLoud.wav"), 100);
		LCD.drawInt(delaytime,0,7);
		meowTimer -= delaytime;
		Delay.msDelay(delaytime);
	}

	@Override
	public boolean takeControl() {
		getPets.fetchSample(isPetted, 0);
		return programRunning && isPetted[0]==1;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
