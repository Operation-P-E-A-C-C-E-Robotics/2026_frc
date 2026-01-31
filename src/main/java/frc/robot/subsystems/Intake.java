// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Intake.*;

public class Intake extends SubsystemBase {
  private final TalonFX intakeMotor = new TalonFX(intakeMotorID);
  /** Creates a new Intake. */
  public Intake() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed)
  {
    intakeMotor.set(speed);
  }

  public Command IntakeStuff()
  {
    return this.run(() -> {setSpeed(intakeSpinSpeed);});
  }
}
