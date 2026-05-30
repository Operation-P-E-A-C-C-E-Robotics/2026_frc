// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class SafeMode extends SubsystemBase {
  public double defaultShootMult;
  public double defaultIntakeStuffMult;

  public double safeShootMult = .4;
  public double safeIntakeStuffMult = .4;
  public boolean safeModeOn;
  /** Creates a new SafeMode. */
  public SafeMode() 
  {

  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }

  public void SetSafeMode(boolean ChangeToSafe)
  {
    if(ChangeToSafe)
    {
      //Constants.Shooter.shootMotorSpeed = Constants.Shooter.shootMotorSpeed * safeShootMult;
    }
    
  }
// Hi, don't mess up. Or there goes the robot. -Avery
  public Command ToggleSafeMode()
  {
    if(safeModeOn)
    {
      return this.run(() -> {SetSafeMode(false);});      
    }
    else
    {
      return this.run(() -> {SetSafeMode(true);});
    }
  }
}
