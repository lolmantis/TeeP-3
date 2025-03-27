package ac.uk.RHUL.Students.IbrahimA.BehaviourSystems;


import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.BaseFrame;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Collision implements Behavior, endProgram {

    
    private final EV3UltrasonicSensor ultrasonicSensor;
    private final SampleProvider distanceProvider;
    private final BaseFrame frame;
    private boolean programRunning = true;

    public Collision(EV3UltrasonicSensor distance, BaseFrame frame) {
    	this.frame = frame;
        ultrasonicSensor = distance;
        distanceProvider = ultrasonicSensor.getDistanceMode();
    }
    
		@Override
		public void setEndProgram() {
			programRunning = false;
			
		}

    @Override
    public boolean takeControl() {
        float[] sample = new float[distanceProvider.sampleSize()];
        distanceProvider.fetchSample(sample, 0);
        return sample[0] < WalkerConsts.STOP_DISTANCE && programRunning;
    }

    @Override
    public void action() {
      frame.sitToCat();
      frame.meow();
    }

    @Override
    public void suppress() {
    }
}
