package ac.uk.RHUL.Students.EmmaC.CS1822.BehaviourSystems;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Sound;
import java.util.Random;

public class PetTheCat {
	private final EV3TouchSensor pet;
	private final SampleProvider getPets;
	private float[] isPetted = new float[1];
	
	private final Random random = new Random();
	private int meowTimer;
	private boolean isPurring = false;
	
	public PetTheCat(EV3TouchSensor pet) {
		this.pet = pet;
		getPets = this.pet.getTouchMode();
		resetMeowTimer();
	}
	
	private void resetMeowTimer() {
		meowTimer = 50000 + random.nextInt(20000); // Generates random aspect of meows - not perfectly a minute apart, not too random 
	}
	
	public void update(int deltaTime) {
		getPets.fetchSample(isPetted, 0);
		if (isPetted[0] == 1) {
			if (!isPurring) {
				isPurring = true;
				startPurring();
			}
		} else {
			isPurring = false;
		}
		
		// Meow timer
		meowTimer -= deltaTime;
		if (meowTimer <= 0) {
			meow();
			resetMeowTimer();
			
		}
	}
	
	private void startPurring() {
		Sound.playSample(new java.io.File("CatPurringSoundEffect.wav"), 100);
	}
	
	private void meow() {
		Sound.playSample(new java.io.File("MeowSoundEffect.wav"), 100);
	}

}
