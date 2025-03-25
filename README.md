For a lejos ev3 robot with four legs.
Built for year one at Royal Holloway's Computer Science course.
Everything you'll need to make it go is kept under WalkerFourLegs.
Any port mismatches that need changing can be found under persConsts.


BEHAVIOUR SYSTEMS:
PetTheCat: Handles periodic meowing and purring procedure. 
StandAndSit: Takes the back legs from sitting position to neutral standing position and vice versa. 
WalkForwards: Walk cycle in four stages. Is capable of being interrupted (for a behaviour such as petting 
or meowing) and resuming with no problem. 

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
Purring and meowing are interrupts for example.  

Driver: Instantiates BaseFrame. Initialises behaviours, runs the arbitrator and allows manual control of 
behaviours (via pressing the enter key)

TestBed: Utility for testing leg motors and angles. Provides status updates through LCD display. 

PERSCONSTS:
WalkerConsts: Constants for sensors and motors. 

