package ac.uk.RHUL.Students.AlexJ.CS1821.WalkerFourLegs;

import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Leg;
import ac.uk.RHUL.Students.AlexJ.CS1821.Leg.Legs;
import ac.uk.RHUL.Students.AlexJ.CS1821.persConsts.WalkerConsts;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BaseFrame {

	private Legs legs;
	private StandAndSit riser;

	BaseFrame() {

		// initialise legs
		LCD.clear();
		LCD.drawString("Booting legs...", 0, 0);
		Leg FrontLeft = new Leg(WalkerConsts.FRONT_LEFT_LEG_PORT);
		Leg FrontRight = new Leg(WalkerConsts.FRONT_RIGHT_LEG_PORT);
		Leg BackLeft = new Leg(WalkerConsts.BACK_LEFT_LEG_PORT);
		Leg BackRight = new Leg(WalkerConsts.BACK_RIGHT_LEG_PORT);

		legs = new Legs(new Leg[] { FrontLeft, FrontRight, BackLeft, BackRight });

		LCD.clear();
		LCD.drawString("Booting sensors...", 0, 0);
		// sensors will be booted down here

		//

		// method classes down here

		LCD.clear();
		LCD.drawString("Booting conscious...", 0, 0);
		riser = new StandAndSit(legs);
	}

	public Legs getLegs() {
		return legs;
	}

	public void stand(Boolean reversed) {
		riser.stand(reversed);
		// will get more convoluted later
	}

	public void standFromCat() {
		riser.standFromCatMode();
	}
}
