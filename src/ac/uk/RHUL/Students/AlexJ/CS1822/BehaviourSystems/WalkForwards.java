package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1822.WalkerFourLegs.endProgram;
import ac.uk.RHUL.Students.AlexJ.CS1822.persConsts.WalkerConsts;
import lejos.hardware.Button;
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
	public synchronized void setEndProgram() {
		programRunning = false;
		suppressor = true;
		walking = false;
		hardStop();
		neutralise();
		primed = false;
	}
	
	public synchronized final void primeToWalk() {
		if (!walking) {
			
		primer();
		Delay.msDelay(WalkerConsts.STEP_PAUSE_DURATION_MS);
		LCD.drawString("primed!", 0, 0);
		walking = true;
		primed = true;
		}
	}
	
	private synchronized final void walkCycle() {
		if (primed) {
			suppressor = false;
			while (walking && !suppressor && programRunning) {
				if (Button.ENTER.isDown()) {
					break;
				}
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
				Delay.msDelay(WalkerConsts.STEP_PAUSE_DURATION_MS);
				if (suppressor) {
					break;
				}
			}
		}
		neutralise();
	}
	
	public synchronized boolean getPrimedFlag() {
		return primed;
	}
	
	private synchronized final void primer() {
		// FrontLeft & BackRight neutral --> forward
		// FrontRight & BackLeft neutral --> neutral
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.BACK_RIGHT).stepForward(LegState.FORWARD);
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.endSync();
		legs.waitToStop();
	}
	
	private synchronized  final void moveStageOne() {
		// FrontLeft & BackRight forward --> neutral
		// FrontRight & BackLeft neutral --> Back
		// i.e: rock forward
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).returnToNeutral();
		legs.getLeg(LegID.BACK_RIGHT).returnToNeutral();
		
		legs.getLeg(LegID.FRONT_RIGHT).stepBack(LegState.BACK);
		legs.getLeg(LegID.BACK_LEFT).stepBack(LegState.BACK);
		legs.endSync();
		legs.waitToStop();
	}
	
	private synchronized final void moveStageTwo() {
		// FrontLeft & BackRight neutral --> neutral
		// FrontRight & BackLeft back (--> neutral) --> forward
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).returnToNeutral();
		legs.getLeg(LegID.BACK_RIGHT).returnToNeutral();
		
		legs.getLeg(LegID.FRONT_RIGHT).doubleStepForward();
		legs.getLeg(LegID.BACK_LEFT).doubleStepForward();
		legs.endSync();
		legs.waitToStop();
	}
	
	private synchronized final void moveStageThree() {
		// FrontLeft & BackRight neutral --> back
		// FrontRight & BackLeft forward --> neutral
		// i.e: rock forward
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).stepBack(LegState.BACK);
		legs.getLeg(LegID.BACK_RIGHT).stepBack(LegState.BACK);
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.endSync();
		legs.waitToStop();
	}
	
	private synchronized final void moveStageFour() {
		// FrontLeft & BackRight back (--> neutral) --> forward
		//	FrontRight & BackLeft neutral --> neutral
		// i.e: return to start
		legs.beginSync();
		legs.getLeg(LegID.FRONT_LEFT).doubleStepForward();
		legs.getLeg(LegID.BACK_RIGHT).doubleStepForward();
		
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
		legs.endSync();
		legs.waitToStop();
	}
	
	public synchronized final void stop() {
		walking = false;
		Thread.yield();
	}
	
	public synchronized final void hardStop() {
		legs.immediateStop();
		legCycleStage = 0;
	}
	
	public synchronized final void neutralise() {
		legs.returnToNeutral();
		primed = false;
		walking = false;
		Thread.yield();
		legCycleStage = 0;
	}

	@Override
	public boolean takeControl() {
		// we always want to walk, this is the lowest priority method
		return programRunning;
	}

	@Override
	public synchronized void action() {
		// get ready, go, then stop afterwards
		if (!primed) {
			primeToWalk();
			walkCycle();			
		}
	}

	@Override
	public void suppress() {
		walking = false;
		suppressor = true;		
	}
}
