// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;  
import static frc.robot.Constants.IntakeDeploy.*;


public class IntakeDeploy extends SubsystemBase {
  public TalonFX intakeDeploy = new TalonFX(intakeDeployMotorID);

  public IntakeDeploy() {

  }
  public Command DeployIntake(){
    return this.run(() -> {setSpeed(intakeDeployspeed);});
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
   public void setSpeed(double speed)
  {
    intakeDeploy.set(speed);
  }

}
