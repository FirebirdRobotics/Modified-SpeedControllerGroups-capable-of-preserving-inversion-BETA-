package frc.robot;
import edu.wpi.first.wpilibj.SpeedController;
import frc.DifferentialSpeedControllerGroup;

/*----------------------------------------------------------------------------*/
/* Modified by Firebird Robotics, 2020. All right may not be reserved.        */
/* Partially written by 'AC', modified by OTHERS. (Raplace OTHERS with names) */
/*----------------------------------------------------------------------------*/

public class DualInvertedSpeedControllerGroup extends DifferentialSpeedControllerGroup {
   public DualInvertedSpeedControllerGroup (SpeedController speedController_1, SpeedController speedController_2) {
      super (speedController_1, speedController_2);
      setIndividualInversion(1, true);
   }

   public void flipInversion () {
      setIndividualInversion(0, ((getIndividualInversion(0))? false : true));
      setIndividualInversion(1, ((getIndividualInversion(1))? false : true));
   }

   // This type of controller is always inverted. Therefore, this method is obsolete.
   @Override
   public void setInverted (boolean isInverted) {};

}