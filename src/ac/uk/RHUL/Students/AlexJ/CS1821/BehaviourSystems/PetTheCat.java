package ac.uk.RHUL.Students.AlexJ.CS1821.BehaviourSystems;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class PetTheCat {
	private final EV3TouchSensor pet;
	private final SampleProvider getPets;
	private float[] isPetted = new float[1];
	
	public PetTheCat(EV3TouchSensor pet) {
		this.pet = pet;
		getPets = this.pet.getTouchMode();
		
	}
	
	public void theCat() {
		getPets.fetchSample(isPetted, 0);
		if (isPetted[0]==1) {
			LCD.drawString("meow", 0, 6);			
		}
	}

}
