// CoFpyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ControlType;

import static frc.robot.Constants.Hood.*;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */ 
  private final SparkFlex hoodMotor = new SparkFlex(hoodMotorID, MotorType.kBrushless);
  public Hood() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void SetSpeed(double speed)
  {
    hoodMotor.set(speed);
  }

  public Command SpinHoodMotors()
  {
    return this.run(() -> {SetSpeed(motorRotateSpeed);});
  }

}