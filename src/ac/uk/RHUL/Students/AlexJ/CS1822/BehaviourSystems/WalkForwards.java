package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class WalkForwards implements Behavior, endProgram {
	private final Legs legs;
	private boolean walking = false;
	private int legCycleStage = 0;
	private boolean primed = false;
	private boolean suppressor = false;
	private boolean programRunning = true;
	
	public WalkForwards(Legs legs) {
		this.legs = legs;
	}
	
	@Override
	public void setEndProgram() {
		programRunning = false;
		
	}
	
	public final void primeToWalk() {
		walking = true;
		primer();
		LCD.drawString("primed!", 0, 4);
		primed = true;
	}
	
	private final void walkCycle() {
		if (primed) {
			suppressor = false;
			while (walking) {
				Thread.yield();
				switch (legCycleStage++ % 4) {
				case 0:
					moveStageOne();
					break;
				case 1:
					moveStageTwo();
					break;
				case 2:
					moveStageThree();
					break;
				case 3:
					moveStageFour();
					break;
				}
				if (suppressor) {
					break;
				}
				Delay.msDelay(250);
			}
		}
		neutralise();
	}
	
	public boolean getPrimedFlag() {
		return primed;
	}
	
	private final void primer() {
		// FrontLeft & BackRight neutral --> forward
		// FrontRight & BackLeft neutral --> neutral
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.BACK_RIGHT).stepForward(LegState.FORWARD);
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.waitToStop();
		legs.endSync();
	}
	
	private final void moveStageOne() {
		// FrontLeft & BackRight forward --> neutral
		// FrontRight & BackLeft neutral --> Back
		// i.e: rock forward
		LCD.clear();
		LCD.drawString("phase 1", 0, 6);
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).returnToNeutral();
		legs.getLeg(LegID.BACK_RIGHT).returnToNeutral();
		
		legs.getLeg(LegID.FRONT_RIGHT).stepBack(LegState.BACK);
		legs.getLeg(LegID.BACK_LEFT).stepBack(LegState.BACK);
		legs.waitToStop();
		legs.endSync();
	}
	
	private final void moveStageTwo() {
		// FrontLeft & BackRight neutral --> neutral
		// FrontRight & BackLeft back (--> neutral) --> forward
		LCD.clear();
		LCD.drawString("phase 2", 0, 6);
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).returnToNeutral();
		legs.getLeg(LegID.BACK_RIGHT).returnToNeutral();
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.FRONT_RIGHT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).stepForward(LegState.FORWARD);
		legs.waitToStop();
		legs.endSync();
	}
	
	private final void moveStageThree() {
		// FrontLeft & BackRight neutral --> back
		// FrontRight & BackLeft forward --> neutral
		// i.e: rock forward
		LCD.clear();
		LCD.drawString("phase 3", 0, 6);
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).stepBack(LegState.BACK);
		legs.getLeg(LegID.BACK_RIGHT).stepBack(LegState.BACK);
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.waitToStop();
		legs.endSync();
	}
	
	private final void moveStageFour() {
		// FrontLeft & BackRight back (--> neutral) --> forward
		//	FrontRight & BackLeft neutral --> neutral
		// i.e: return to start
		LCD.clear();
		LCD.drawString("phase 4", 0, 6);
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).returnToNeutral();
		legs.getLeg(LegID.FRONT_LEFT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.BACK_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_RIGHT).stepBack(LegState.FORWARD);
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.waitToStop();
		legs.endSync();
	}
	
	public final void stop() {
		walking = false;
	}
	
	public final void neutralise() {
		legs.returnToNeutral();
		primed = false;
	}

	@Override
	public boolean takeControl() {
		// we always want to walk, this is the lowest priority method
		return programRunning;
	}

	@Override
	public void action() {
		// get ready, go, then stop afterwards
		primeToWalk();
		walkCycle();
		
	}

	@Override
	public void suppress() {
		walking = false;
		suppressor = true;		
	}
}
