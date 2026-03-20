// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;  
import static frc.robot.Constants.IntakeDeploy.*;

import java.util.function.DoubleSupplier;


public class IntakeDeploy extends SubsystemBase {
  public TalonFX intakeDeploy = new TalonFX(intakeDeployMotorID);

  public IntakeDeploy() 
  {
    intakeDeploy.getConfigurator().apply(motorConfig);
  }
  // public Command DeployIntake()
  // {
  //   return this.run(() -> {setSpeed(1);});
  // }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed)
  {
    intakeDeploy.set(speed);
  }
  public Command UnDeploy() 
  {   //TODO YOU NEED TO TUNE WHAT IS CURRENTLY 1, TO REFLECT WHERE A SAFE POSITION IS.
    return this.run(() -> {intakeDeploy.setPosition(1);});
  }
  public Command Stop()
  {
    return this.run(() -> {setSpeed(0);});
  } 
  public Command ControlMotor(DoubleSupplier speed)//TEMP
  {
    return this.run(() -> {setSpeed(speed.getAsDouble());});
  }
}
