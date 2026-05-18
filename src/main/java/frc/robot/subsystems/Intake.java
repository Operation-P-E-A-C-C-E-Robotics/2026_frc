// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//
package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Intake.*;

import java.util.function.DoubleSupplier;

public class Intake extends SubsystemBase {
  private final SparkFlex intakeMotor = new SparkFlex(intakeMotorID, MotorType.kBrushless);
  /** Creates a new Intake. */
  public Intake() {
  }


  public void setSpeed(double speed)
  {
    intakeMotor.set(speed); //TODO see if it should be set negative or not (just by checking its spinning the right way)
  }

  public Command IntakeStuff(DoubleSupplier speed)
  {
    return this.run(() -> {setSpeed(-speed.getAsDouble());});
  }
  public Command Stop()
  {
    return this.run(() -> {setSpeed(0);});
  }
  public Command AntiIntake(DoubleSupplier speed)
  {
    return this.run(() -> {setSpeed(speed.getAsDouble());});
  }
}
