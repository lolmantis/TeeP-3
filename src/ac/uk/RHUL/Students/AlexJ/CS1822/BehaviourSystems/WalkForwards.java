package ac.uk.RHUL.Students.AlexJ.CS1822.BehaviourSystems;

import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegID;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.LegState;
import ac.uk.RHUL.Students.AlexJ.CS1822.Leg.Legs;

public class WalkForwards {
	private final Legs legs;
	private boolean walking = false;
	private int legCycleStage = 0;
	
	public WalkForwards(Legs legs) {
		this.legs = legs;
	}

	
	public final void walk() {
		walking = true;
		
		primer();
		
		while (walking) {
			switch (legCycleStage++) {
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
		}
//		legs.returnToNeutral();
	}
	
	private final void primer() {
		// FrontLeft & BackRight neutral --> forward
		// FrontRight & BackLeft neutral --> neutral
		legs.getLeg(LegID.FRONT_LEFT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.BACK_RIGHT).stepForward(LegState.FORWARD);
		legs.getLeg(LegID.FRONT_RIGHT).returnToNeutral();
		legs.getLeg(LegID.BACK_LEFT).returnToNeutral();
	}
	
	private final void moveStageOne() {
		// FrontLeft & BackRight forward --> neutral
		// FrontRight & BackLeft neutral --> Back
		// i.e: rock forward
	}
	
	private final void moveStageTwo() {
		// FrontLeft & BackRight neutral --> neutral
		// FrontRight & BackLeft back (--> neutral) --> forward
	}
	
	private final void moveStageThree() {
		// FrontLeft & BackRight neutral --> back
		// FrontRight & BackLeft forward --> neutral
		// i.e: rock forward
	}
	
	private final void moveStageFour() {
		// FrontLeft & BackRight back (--> neutral) --> forward
		//	FrontRight & BackLeft neutral --> neutral
		// i.e: return to start
	}
	
	public final void stop() {
		walking = false;
	}
}
