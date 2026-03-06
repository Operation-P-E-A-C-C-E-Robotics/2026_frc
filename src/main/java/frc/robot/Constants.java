// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.util.function.DoubleFunction;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathConstraints;


import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.lib.swerve.SwerveDescription.CANIDs;
import frc.lib.swerve.SwerveDescription.Dimensions;
import frc.lib.swerve.SwerveDescription.EncoderOffsets;
import frc.lib.swerve.SwerveDescription.Gearing;
import frc.lib.swerve.SwerveDescription.Inversion;
import frc.lib.swerve.SwerveDescription.Physics;
import frc.lib.swerve.SwerveDescription.PidGains;
import frc.lib.util.JoystickCurves;
import frc.lib.vision.ApriltagCamera.ApriltagPhotonvision;

public final class Constants {
  public static final double period = 0.01;

  public static final class Cameras {
    public static final String exampleLimelight = "limelight";

    public static final String examplePhotonvisionName = "photonvision";

    public static final double LIMELIGHT_FOCAL_LENGTH = (1*83)/0.32; //as calculated by me: (distance * pixels) / size

    public static final Transform3d robotToExamplePhotonvision = new Transform3d(
        Units.inchesToMeters(10.5), //forward offset
        Units.inchesToMeters(6),    
        Units.inchesToMeters(8.5), 
        new Rotation3d(0,Units.degreesToRadians(15),0)
    );

    public static final ApriltagPhotonvision examplePhotonvision = new ApriltagPhotonvision(
      examplePhotonvisionName,
      robotToExamplePhotonvision,
      FieldConstants.aprilTags,
      0.9
    );
  }
  public static final class Swerve {
    /* TELEOP */
    //speeds in m/s (probably)
    public static final double teleopLinearMultiplier = 5.0;
    public static final double teleopAngularMultiplier = 7.0;

    //acceleration limits
    public static final double teleopLinearSpeedLimit = 5.0;
	  public static final double teleopLowBatteryLinearSpeedLimit = 2; //more acceleration limit when battery is low
    public static final double teleopLinearAngleLimit = 2.0;
    public static final double teleopAngularRateLimit = 3.0;

    //deadband
    public static final double teleopLinearSpeedDeadband = 0.15;
    public static final double teleopAngularVelocityDeadband = 0.2;
    public static final double teleopDeadbandDebounceTime = 0.1;

    public static final DoubleFunction <Double> teleopLinearSpeedCurve = (double linearSpeed) -> JoystickCurves.herraFCurve(linearSpeed, 6, 4.5); //a nice gentle curve which is Peaccy's (me!!) favorite :)
    public static final DoubleFunction <Double> teleopAngularVelocityCurve = (double angularVelocity) -> JoystickCurves.powerCurve(angularVelocity, 2); //TODO decide if the driver (me) wants a curve on this or not.

    //number of loops to keep track of position correction for (so multiply by 20ms to get the duration the correction is considering)
    //todo too slow?
    public static final int teleopPositionCorrectionIters = 0; 




    /* CTRE SWERVE CONSTANTS  You need to measure and write these values down for swerve tuning. Is wheel distance */
    public static final Dimensions dimensions = new Dimensions(Units.inchesToMeters(18.75), Units.inchesToMeters(18.75));

    public static final CANIDs frontLeftIDs =   new CANIDs(4,   5,    6); //module 2   YOU NEED TO SET THESE
    public static final CANIDs rearLeftIDs =   new CANIDs(7,   8,    9); //module 1
    public static final CANIDs rearRightIDs =   new CANIDs(10,   11,   12); //module 4
    public static final CANIDs frontRightIDs =    new CANIDs(13,  14,   15); //module 3

    public static final Gearing gearing = new Gearing(DriveGearRatios.SDSMK4i_L2, ((150.0 / 7.0) / 1.0), (3.807/2), 0);
    // public static final EncoderOffsets offsets = new EncoderOffsets(-0.488770, -0.225342, -0.224609, -0.906738); //todo these offsets are very wrong.
    public static final EncoderOffsets offsets = new EncoderOffsets(  //YOU HAVE TO NEGATE THESE
      -0.328125+0.25, //Front Left, module 0   
      0.226074+0.25, //Front Right, module 3
      -0.494141+0.25,  // Rear Left, module 1
      -0.249023+0.25); //Rear Right, module 2

    public static final Inversion inversion = new Inversion(true, true, false, false);

    //inertia only used for simulation
    public static final Physics physics = new Physics(0.05,0.01, Robot.isReal() ? 80 : 800, 7);
    public static final double steerMotorCurrentLimit = Robot.isReal() ? 40 : 120; //amps
    
    public static final PidGains driveGains = new PidGains(0, 0, 0, 0.15, 0); 
    public static final PidGains angleGains = new PidGains(40, 0, 0.000, 0, 0);
    
    public static final int pigeonCANId = 3;  //TODO SWERVE DRIVE CHANGE PID ID TO WHAT ITS SET TO IN TUNER OR SMTHIN
    public static final boolean invertSteerMotors = Robot.isReal(); //cant invert in simulation which is dumb.
    
    /* HEADING CONTROLLER CONSTANTS */
    //How aggressivley the robot snaps to an angle, ex in an auto when aiming to shoot.
    public static final double autoHeadingKP = 400;
    public static final double lockHeadingKP = 1000;
    public static final double autoHeadingKI = 0.0; //DOES NOTHING LOL
    public static final double autoHeadingKD = 0.0; //ALSO DOES NOTHING LOL
    public static final double autoHeadingKV = 0.7;
    public static final double autoHeadingKA = 0.02;
    public static final double autoHeadingMaxVelocity = 3; //deg/s (i think)
    public static final double autoHeadingMaxAcceleration = 10; //deg/s^2
    public static final double lockHeadingMaxVelocity = 6;
    public static final double lockHeadingMaxAcceleration = 20;
    public static final boolean useSoftHoldHeading = false;
    public static final double softHeadingCurrentLimit = 30;

    public static final double aimTolerance = 1; //degrees

    
    /* PATH FOLLOWING CONSTANTS */
    public static final double pathfollowingMaxVelocity = 3,
                              pathfollowingMaxAcceleration = 3,
                              pathfollowingMaxAngularVelocity = 360,
                              pathfollowingMaxAngularAcceleration = 360;

    public static final double measuredMaxVelocity = 3,
                              measuredMaxAcceleration = 3,
                              measuredMaxAngularVelocity = 360,
                              measuredMaxAngularAcceleration = 360;

    public static final PathConstraints autoMaxSpeed = new PathConstraints(
      pathfollowingMaxVelocity,
      pathfollowingMaxAcceleration,
      pathfollowingMaxAngularVelocity,
      pathfollowingMaxAngularAcceleration
    );

    public static final RobotConfig defaultPathplannerConfig = new RobotConfig(
      Units.lbsToKilograms(120), //robot weight
      6.883, //robot MOI
      new ModuleConfig(
        Units.inchesToMeters(2), //wheel radius //TODO: fudge factor
        5, //Measured max robot speed
        1.2, //wheel Coefficient of Friction
        DCMotor.getFalcon500(1).withReduction(Swerve.gearing.driveRatio),
        Swerve.physics.wheelSlipCurrent, //Drive current limit
        1 //drive motors per module
      ),
      Swerve.dimensions.frontLeft, Swerve.dimensions.frontRight, Swerve.dimensions.rearLeft, Swerve.dimensions.rearRight);
  }

  public static final class FieldConstants {
    public static final AprilTagFieldLayout aprilTags;
    static {
      try {
        aprilTags = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static final class ControlSystem {
    public static final int PDPCanId = 0;
    public static final ModuleType PDPModuleType = ModuleType.kCTRE;
  }

    //TELEOP
    //Speeds in m/s
        public static final RobotConfig defaultPathplannerConfig = new RobotConfig(
      Units.lbsToKilograms(120), //robot weight
      6.883, //robot MOI
      new ModuleConfig(
        Units.inchesToMeters(2), //wheel radius //TODO: fudge factor
        5, //Measured max robot speed
        1.2, //wheel Coefficient of Friction
        DCMotor.getFalcon500(1).withReduction(Swerve.gearing.driveRatio),
        Swerve.physics.wheelSlipCurrent, //Drive current limit
        1 //drive motors per module
      ),
      Swerve.dimensions.frontLeft, Swerve.dimensions.frontRight, Swerve.dimensions.rearLeft, Swerve.dimensions.rearRight);
    
     public class DriveGearRatios{
    /** SDS MK3 - 8.16 : 1 */
    public static final double SDSMK3_Standard = (8.16 / 1.0);
    /** SDS MK3 - 6.86 : 1 */
    public static final double SDSMK3_Fast = (6.86 / 1.0);

    /** SDS MK4 - 8.14 : 1 */
    public static final double SDSMK4_L1 = (8.14 / 1.0);
    /** SDS MK4 - 6.75 : 1 */
    public static final double SDSMK4_L2 = (6.75 / 1.0);
    /** SDS MK4 - 6.12 : 1 */
    public static final double SDSMK4_L3 = (6.12 / 1.0);
    /** SDS MK4 - 5.14 : 1 */
    public static final double SDSMK4_L4 = (5.14 / 1.0);
    
    /** SDS MK4i - 8.14 : 1 */
    public static final double SDSMK4i_L1 = (8.14 / 1.0);
    /** SDS MK4i - 6.75 : 1 */
    public static final double SDSMK4i_L2 = (6.75 / 1.0);
    /** SDS MK4i - 6.12 : 1 */
    public static final double SDSMK4i_L3 = (6.12 / 1.0);
  }

  public static final class Shooter {
    public static final int shootMotorID = 27; //TODO
    public static final int shootFollowerOneID = 28;
    public static final int shootFollowerTwoID = 29;
    public static final int shootFollowerThreeID = 30;
    public static final double targetRPS = 1; //TODO
    public static final double shootMotorSpeed = 0.5;//TODO

    public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    static {
    motorConfig.Slot0.withGravityType(GravityTypeValue.Arm_Cosine)
                       .withKP(1)
                       .withKI(0)
                       .withKD(0)
                       .withKS(-0.8575)
                       .withKV(0)
                       .withKA(0)
                       .withKG(0); }
  }

  public static final class ShooterFeed {
    public static final int shootFeedMotorID = 5;
    public static final double shootFeedMotorSpeed = 0.2;

  }

  public static final class Climber {
    public static final int climberServoID = 10; //TODO
    public static final int climberWinchMotorID = 14;
    public static final double deployAngle = 90;
  }
  
  public static final class Hood {
    public static final int hoodMotorID = 20;
    public static final double motorRotateSpeed = 0.2;
  }
  public static final class IntakeDeploy{
    public static final int intakeDeployMotorID = 6; //TODO
    public static final double intakeDeployspeed = 0.2;    
    public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    static {
    motorConfig.Slot0.withGravityType(GravityTypeValue.Arm_Cosine)
                       .withKP(1)
                       .withKI(0)
                       .withKD(0)
                       .withKS(0)
                       .withKV(0)
                       .withKA(0)
                       .withKG(0); }
  }
  public static final class Intake{
    public static final int intakeMotorID = 7; 
    public static final double intakeSpinSpeed = 0.4;
  }
  public static final class Hopper{   
    public static final int hopperSRXOneID = 12;
    public static final int hopperSRXTwoID = 13;
    public static final int hopperFloorMotorID = 11;
    public static final double hopperSRXSpeed = 0.3;
    public static final double hopperFloorSpeed = 0.3;
  }
}
 
