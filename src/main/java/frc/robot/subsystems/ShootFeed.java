// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

import static frc.robot.Constants.ShooterFeed.*;

public class ShootFeed extends SubsystemBase {
  public TalonFX shootFeedMotor = new TalonFX(shootFeedMotorID);
  /** Creates a new ShootFeed. */
  public ShootFeed()
  {
    
  }

  public void setSpeed(double speed)
  {
    shootFeedMotor.set(speed);
  }
  
  public Command Intake()
  {
    return this.run(() -> {
      setSpeed(shootFeedMotorSpeed);
    // System.out.println("BRBEAIDNSADSADANDUISAD");
  });
  }
  
  @Override
  public void periodic() {
    
  }
}
