# Modified-SpeedControllerGroups-capable-of-preserving-inversion-BETA-
To be used instead of follow method and normal SpeedControllerGroups. Currently missing packaging and comments.

IMPORTANT: Read comments in methods, since a few work quite differentely than their SpeedControllerGroup counterparts.
MAIN FEATURE: SpeedController array declared as protected, so that subClasses of different motor types (say, Sparks or Talon) are capable of using all their featues by wrting new methods in extended classes.

Written / modified by someone with a bit of free time, credit to FireBird Robotics.
