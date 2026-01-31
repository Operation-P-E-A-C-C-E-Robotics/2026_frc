// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Hopper.*;

public class HoppyWoppy extends SubsystemBase {
  /** Creates a new HoppyWoppy. */
  private final TalonSRX hopperSRXOne = new TalonSRX(hopperSRXOneID);
  private final TalonSRX hopperSRXTwo = new TalonSRX(hopperSRXTwoID);
  public HoppyWoppy() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void SetSpeed(double srxOneSpeed, double srxTwoSpeed)
  {
    
  }
}
