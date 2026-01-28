// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.lib.util.ServoMotor;
import static frc.robot.Constants.Climber.*;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  public Servo climberMotor = new Servo(climberServoID);
  
  public Climber()
  {
    
  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
  
  public void SetClimber(double angle)
  {
    climberMotor.setAngle(angle);
  }

  public Command DeployClimber()
  {
    return this.run(() -> {SetClimber(deployAngle);});
  }
  public Command RestClimber()
  {
    return this.run(() -> {SetClimber(0);});
  }
}
