For a lejos ev3 robot with four legs.
Built for year one at Royal Holloway's Computer Science course.
Everything you'll need to make it go is kept under WalkerFourLegs.
Any port mismatches that need changing can be found under persConsts.


BEHAVIOUR SYSTEMS:
PetTheCat: Handles periodic meowing and petting procedure.

BatteryChecker: Monitors battery level, and sits if below the minimum.

Shutdown: Monitors the escape key, sits and shuts down when pushed.

StandAndSit: Takes the back legs from sitting position to neutral standing position and vice versa.

WalkForwards: Walk cycle in four stages. Is capable of being interrupted (for a behaviour such as petting 
or meowing) and resuming with no problem.

Collision: Detects when something is half a meter from the robot, sits and meows until the obstruction is removed.


LEG:
Leg: Handles specific angles for behaviours (stand, sit, walking stages). Also holds gear ratios.  

LegID: Enum for identifying which legs are what. front legs are denoted FRONT_LEFT and FRONT_RIGHT. Back 
legs are denoted BACK_LEFT and BACK_RIGHT. Allows for easy identification for manipulating legs for 
behaviours such as sitting, standing or walking. 

Legs: Handles methods for controling leg angle and motor speed. 

LegState: Enum for identifying possible states for the legs corresponding to behaviours. Possible states 
are FORWARD, NEUTRAL, BACK, SEATED.


WALKERFOURLEGS:
BaseFrame: Arbitrator. Initialises the legs and sensors. Arbitrator ensures the priorities are correct. 
Petting and meowing are interrupts for example.  

Driver: Instantiates BaseFrame. Initialises behaviours, runs the arbitrator and allows manual control of 
behaviours (via pressing the enter key).

endProgram: Interface used by behaviors when terminating the BaseFrame so all behaviors exit gracefully.

TestBed: Utility for testing leg motors and angles. Provides status updates through LCD display. 


PERSCONSTS:
WalkerConsts: Constants for sensors and motors. 


resources:
MeowSoundEffect.wav: a heavily crunched and loudened meow, used by petTheCat.