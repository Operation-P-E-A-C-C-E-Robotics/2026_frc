// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import frc.lib.util.ServoMotor;
import static frc.robot.Constants.Climber.*;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.hardware.TalonFX;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  //public Servo climberMotor = new Servo(climberMotorOne);
  public TalonFX climberMotorOne = new TalonFX(climberMotorOneID);
  public TalonFX climberMotorTwo = new TalonFX(climberMotorTwoID); //Slave of cmOne
  public Climber()
  {
    
  }

  public double GetDifference()
  {
    return (climberMotorOne.getPosition().getValueAsDouble() - climberMotorTwo.getPosition().getValueAsDouble());
  }
  
  public void SetClimberWinchSpeed(double speed)
  {
    //climberMotorOne.set(speed); 
    //climberMotorTwo.set(speed - (climberMotorTwoPGain * GetDifference())); 
  }

  //  unjamTimer.start();
  //       if(unjamming && unjamTimer.get() > 0.15){
  //         unjamming = false;
  //         unjamTimer.reset();
  //       } else if (unjamTimer.get() > 1){
  //         unjamming = true;
  //         unjamTimer.reset();
  
  public double timerMax = 1;
  public Command AutoUnWinch()
  {
    return this.run(() -> {});
  }

  public Command Winch(DoubleSupplier speed)
  {
    return this.run(() -> {SetClimberWinchSpeed(speed.getAsDouble());});
  }
}
