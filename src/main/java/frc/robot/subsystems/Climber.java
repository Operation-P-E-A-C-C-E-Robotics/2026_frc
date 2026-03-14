// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.lib.util.ServoMotor;
import static frc.robot.Constants.Climber.*;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.hardware.TalonFX;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  public Servo climberMotor = new Servo(climberServoID);
  public TalonFX winchMotor = new TalonFX(climberWinchMotorID);
  
  public Climber()
  {
    
  }
  
  public void SetClimberServo(double angle)
  {
    climberMotor.setAngle(angle);
  }

  public void SetClimberWinchSpeed(double speed)
  {
    winchMotor.set(speed);
  }

  public Command DeployClimber()
  {
    return this.run(() -> {SetClimberServo(deployAngle);});
  }
  public Command RestClimber()
  {
    return this.run(() -> {SetClimberServo(0);});
  }
  public Command Winch(DoubleSupplier speed)
  {
    return this.run(() -> {SetClimberWinchSpeed(speed.getAsDouble());});
  }
}
