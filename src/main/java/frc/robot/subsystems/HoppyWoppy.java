// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SignalsConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Hopper.*;

import java.util.function.DoubleSupplier;
//system.out.print.ln(nonononononononononononononnononononononononononononononononononnoonnoonnnnoooonoooonnnnnnnooooooonononono)
public class HoppyWoppy extends SubsystemBase {
  /** Creates a new HoppyWoppy. */
  private final TalonSRX hopperSRXOne = new TalonSRX(hopperSRXOneID);
  private final TalonSRX hopperSRXTwo = new TalonSRX(hopperSRXTwoID);
  private final SparkFlex hopperFloorMotor = new SparkFlex(hopperFloorMotorID, MotorType.kBrushless);
  //Timer unjamTimer = new Timer();
  //boolean unjamming = false;
  String hi = "hi";
  
  public HoppyWoppy() {
    System.out.println(hi);
    hopperFloorMotor.configure(new SparkFlexConfig().smartCurrentLimit(60), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    hopperSRXOne.configAllSettings(srxConfigOne);
    hopperSRXTwo.configAllSettings(srxConfigTwo);
  }

//The second double is useless but its not rly hurting anything so o well.
  public void SetSpeed(double hopperFloorMotorSpeed, double srxOneSpeed, double srxTwoSpeed)
  { 
    hopperSRXOne.set(TalonSRXControlMode.PercentOutput, -srxOneSpeed);
    hopperSRXTwo.set(TalonSRXControlMode.PercentOutput, srxTwoSpeed);
    this.hopperFloorMotor.set(hopperFloorMotorSpeed);
    //unjamTimer.start();
  }

  public Command SortHopper(boolean reverse, DoubleSupplier multiplier) //Now that multipliers here we dont need the boolean but its not hurting anything ig
  {
    if(!reverse)
    {
      return this.run(() -> {SetSpeed(-hopperFloorSpeed * multiplier.getAsDouble(), -hopperSRXSpeed * multiplier.getAsDouble(), -hopperSRXSpeed * multiplier.getAsDouble());});
    }
    else
    {
      return this.run(() -> {
        // unjamTimer.start();
        // if(unjamming && unjamTimer.get() > 0.159){
        //   unjamming = false;
        //   unjamTimer.reset();
        // } else if (unjamTimer.get() > 1){
        //   unjamming = true;
        //   unjamTimer.reset();
        // }
        SetSpeed(hopperFloorSpeed * multiplier.getAsDouble() * multiplier.getAsDouble(), hopperSRXSpeed * multiplier.getAsDouble(), hopperSRXSpeed * multiplier.getAsDouble());
      });
    }
  }
  
  public Command StopHoppyWoppy()
  {
    return this.run(() -> {SetSpeed(0, 0, 0);});
  }
}
