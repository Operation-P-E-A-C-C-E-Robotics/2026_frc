// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.configs.MotorOutputConfigs;

import static frc.robot.Constants.Shooter.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  //public TalonFX shooterMotor = new TalonFX(shootMotorID);
  public TalonFX shooterFollowerOne = new TalonFX(shootFollowerOneID);
  public TalonFX shooterFollowerTwo = new TalonFX(shootFollowerTwoID);
  public TalonFX shooterFollowerThree = new TalonFX(shootFollowerThreeID);
  public Shooter() 
  {
    //shooterMotor.getConfigurator().apply(motorConfig);
    shooterFollowerOne.getConfigurator().apply(motorConfig);
    shooterFollowerTwo.getConfigurator().apply(motorConfig);
    shooterFollowerThree.getConfigurator().apply(motorConfig);
    //shooterFollowerOne.setControl(new Follower(shootMotorID, MotorAlignmentValue.Aligned)); 
    shooterFollowerTwo.setControl(new Follower(shootFollowerOneID, MotorAlignmentValue.Opposed)); 
    shooterFollowerThree.setControl(new Follower(shootFollowerOneID, MotorAlignmentValue.Opposed)); 
    // shooterMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));
    shooterFollowerOne.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));
    shooterFollowerTwo.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));
    shooterFollowerThree.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));
    //System.out.println(shooterMotor.getVelocity().toString());


    // shooterMotor.getConfigurator().apply(motorConfig);
    // shooterFollowerOne.getConfigurator().apply(motorConfig);
    // shooterFollowerTwo.getConfigurator().apply(motorConfig);
    // shooterFollowerThree.getConfigurator().apply(motorConfig);
    // shooterFollowerOne.setControl(new Follower(shootMotorID, MotorAlignmentValue.Aligned)); 
    // shooterFollowerTwo.setControl(new Follower(shootMotorID, MotorAlignmentValue.Opposed)); 
    // shooterFollowerThree.setControl(new Follower(shootMotorID, MotorAlignmentValue.Opposed)); 
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
    shooterFollowerOne.set(speed);
        //shooterFollowerOne.set(speed);
            //shooterFollowerTwo.set(speed);
                //shooterFollowerThree.set(speed);

  }

  public void setRPS(double RPS)
  {
    // shooterFollowerOne.setControl(new VelocityVoltage(RPS));
    // shooterFollowerOne.setControl(new VelocityVoltage(RPS));
    // shooterFollowerTwo.setControl(new VelocityVoltage(RPS));
    // shooterFollowerThree.setControl(new VelocityVoltage(RPS));
    shooterFollowerOne.setControl(new VelocityVoltage(RPS));
  }

  public Command Shoot()
  {
    // return this.run(() -> {
    //   setRPS(targetRPS);});
    return this.run(() -> {setRPS(12.7);}); //was 20
  }
    
  public Command IdleShooter()
  {
    //return this.run(() -> {setRPS(0);}); 
    return this.run(() -> {setSpeed(0);});  
  }
}
