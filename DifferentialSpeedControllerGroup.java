/*----------------------------------------------------------------------------*/
/* Copyright (c) 2016-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Modified by Firebird Robotics, 2020. All right may not be reserved.        */
/* Partially written by 'AC', modified by OTHERS. (Raplace OTHERS with names) */
/*----------------------------------------------------------------------------*/

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class DifferentialSpeedControllerGroup implements SpeedController, Sendable, AutoCloseable {

   protected final SpeedController[] m_speedControllers;
   private static int instances;
   private boolean [] speedCotrollerInversion;


   @SuppressWarnings("PMD.AvoidArrayLoops")
   public DifferentialSpeedControllerGroup(SpeedController speedController,
                              SpeedController... speedControllers) {
      speedCotrollerInversion = new boolean [speedControllers.length + 1];
      m_speedControllers = new SpeedController[speedControllers.length + 1];
      m_speedControllers[0] = speedController;
      SendableRegistry.addChild(this, speedController);

      for (int i = 0; i < speedControllers.length; i++) {
         m_speedControllers [i+1] = speedControllers [i];
         SendableRegistry.addChild(this, speedControllers[i]);
      }
      instances++;
      SendableRegistry.addLW(this, "tSpeedControllerGroup", instances);
   }

   @Override
   public void close() {
      SendableRegistry.remove(this);
   }

   @Override
   public void set(double speed) {
      for (int i = 0; i < m_speedControllers.length; i++) {
         m_speedControllers[i].set ((speedCotrollerInversion[i])? -speed : speed);
      }
   }

   // Will always return the velocity accounting for inversion.
   @Override
   public double get() {
      return (speedCotrollerInversion [0] ? -m_speedControllers[0].get() : m_speedControllers[0].get());
   }

   // Will set inversion for every single speedController, regardless of previous status.
   @Override
   public void setInverted(boolean isInverted) {
      for (int i = 0; i < speedCotrollerInversion.length; i++) {
         speedCotrollerInversion [i] = isInverted;
      }    
   }

   // Work differently than normal getInverted(), returns true if at least one of the speedControllers is set to Inversion.
   @Override
   public boolean getInverted() {
      for (boolean inversion : speedCotrollerInversion) {
         if (inversion == true) return true;
      }
      return false;
   }

   @Override
   public void disable() {
      for (SpeedController speedController : m_speedControllers) {
         speedController.disable();
      }
   }

   @Override
   public void stopMotor() {
      for (SpeedController speedController : m_speedControllers) {
         speedController.stopMotor();
      }
   }

   @Override
   public void pidWrite(double output) {
      set(output);
   }

   @Override
   public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("Speed Controller");
      builder.setActuator(true);
      builder.setSafeState(this::stopMotor);
      builder.addDoubleProperty("Value", this::get, this::set);
  }

   public void setIndividualInversion (int targetIndex, boolean isInverted) {
      if (targetIndex > speedCotrollerInversion.length || targetIndex < 0) throw new IndexOutOfBoundsException("Motor index does not exist in current speedContorllerGroup.");
      speedCotrollerInversion [targetIndex] = isInverted;
   }

   public boolean getIndividualInversion (int targetIndex) {
      if (targetIndex > speedCotrollerInversion.length || targetIndex < 0) throw new IndexOutOfBoundsException("Motor index does not exist in current speedContorllerGroup.");
      return speedCotrollerInversion [targetIndex];
   }
}