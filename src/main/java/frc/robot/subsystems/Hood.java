// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import static frc.robot.Constants.Hood.*;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  // private final SparkFlex hoodMotor = new SparkFlex(hoodMotorID, MotorType.kBrushless);
  // private final SparkClosedLoopController hoodMotorController;
  private final TalonFX hoodMotor = new TalonFX(hoodMotorID);

  public Hood() 
  {
    hoodMotor.getConfigurator().apply(motorConfig);
    hoodMotor.setPosition(0);
    // hoodMotorController = hoodMotor.getClosedLoopController();
  }

  @Override
  public void periodic()
  {

  }

  public void setSpeed(double speed)
  {
    hoodMotor.set(speed);  
  }

   // *manually
  public Command ControlHoodMotor(DoubleSupplier speed)
  {
    return this.run(() -> {setSpeed(speed.getAsDouble());});
  }

  public Command GoToHoodSetpoint(HoodSetpoints rotation)
  {
    return this.run(() -> setPosition(rotation.getRotation()));
  }

  public void setPosition(double rotation)
  {
    // hoodMotorController.setSetpoint(rotation, ControlType.kPosition);
    hoodMotor.setControl(new MotionMagicExpoVoltage(rotation));
  }

  public enum HoodSetpoints { //all values DEFINETLEY neeed to be adjusted btw
    layup(1),
    angleTwo(2),
    angleThree(3),
    angleFour(4);

    private double angle;
    
    HoodSetpoints(double angle) {
      this.angle = angle;
    }

    public double getRotation() {
      return angle;
    }
  }

  public Command StoppyWoppy()
  {
    return this.run(() -> {setSpeed(0);});
  }

}
