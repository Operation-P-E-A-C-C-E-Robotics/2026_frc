// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import static frc.robot.Constants.Shooter.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  public TalonFX shooterMotor = new TalonFX(shootMotorID);
  public TalonFX shooterFollowerOne = new TalonFX(20);
  public TalonFX shooterFollowerTwo = new TalonFX(21);
  public TalonFX shooterFollowerThree = new TalonFX(22);
  public Shooter() 
  {
    shooterMotor.getConfigurator().apply(motorConfig);
    //shooterFollowerOne.getConfigurator().apply(motorConfig);
    //shooterFollowerTwo.getConfigurator().apply(motorConfig);
    //shooterFollowerThree.getConfigurator().apply(motorConfig);
    //System.out.println(shooterMotor.getVelocity().toString());
  }

  @Override
  public void periodic() 
  {
    //System.out.println(shooterMotor.getVelocity().toString());
  }

public void setSpeed(double speed)
  {
    //shooterMotor.setControl(new VelocityVoltage(speed));
    // + (m_request.withVelocity(8).withFeedForward(0.5)); 
    shooterMotor.set(speed);
        //shooterFollowerOne.set(speed);
            //shooterFollowerTwo.set(speed);
                //shooterFollowerThree.set(speed);

  }

  public void setRPS(double RPS)
  {
    shooterMotor.setControl(new VelocityVoltage(RPS));
        // shooterFollowerOne.setControl(new VelocityVoltage(RPS));
        //     shooterFollowerTwo.setControl(new VelocityVoltage(RPS));
        //         shooterFollowerThree.setControl(new VelocityVoltage(RPS));
  }

  public Command Shoot()
  {
    return this.run(() -> {
      setRPS(targetRPS);});
      
      
  }
  
  public Command IdleShooter()
  {
    return this.run(() -> {setSpeed(0);});
  }
}
