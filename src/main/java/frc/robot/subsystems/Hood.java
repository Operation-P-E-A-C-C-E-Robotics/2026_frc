// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import static frc.robot.Constants.Hood.*;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  // private final SparkFlex hoodMotor = new SparkFlex(hoodMotorID, MotorType.kBrushless);
  // private final SparkClosedLoopController hoodMotorController;
  private final TalonFX hoodMotor = new TalonFX(hoodMotorID);

  public Hood() 
  {
    // hoodMotorController = hoodMotor.getClosedLoopController();
  }

  @Override
  public void periodic()
  {

  }

  public void setSpeed(double speed)
  {
    if(speed <= -0.07 || speed >= 0.07)
    {
      hoodMotor.set(speed);      
    }

  }

   // *manually
  public Command ControlHoodMotor(DoubleSupplier speed)
  {
    return this.run(() -> {setSpeed(speed.getAsDouble());});
  }

  public Command GoToHoodSetpoint(HoodSetpoints rotation)
  {
    return this.run(() -> setPosition(rotation.getRotation() / hoodRotationsPerMotorRotations));
  }

  public void setPosition(double rotation)
  {
    // hoodMotorController.setSetpoint(rotation, ControlType.kPosition);
    hoodMotor.setPosition(rotation);
  }

  public enum HoodSetpoints { //all values DEFINETLEY neeed to be adjusted btw
    angleOne((0.0)),
    angleTwo((0.1)),
    angleThree((0.15)),
    angleFour((0.2));

    private double angle;
    
    HoodSetpoints(double angle) {
      this.angle = angle;
    }

    public double getRotation() {
      return angle;
    }
  }

}
