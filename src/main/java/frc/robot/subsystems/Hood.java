// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import static frc.robot.Constants.Hood.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  private final SparkFlex hoodMotor = new SparkFlex(hoodMotorID, MotorType.kBrushless);
  public Hood() {
  }

  public void setSpeed(double speed)
  {
    hoodMotor.set(speed);
  }

  public Command SpinHoodMotor()
  {
    return this.run(() -> {setSpeed(motorRotateSpeed);});
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
