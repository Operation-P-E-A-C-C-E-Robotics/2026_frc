// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.util.ButtonMap;
import frc.lib.util.ButtonMap.Button;
import frc.lib.util.ButtonMap.JoystickTrigger;
import frc.lib.util.ButtonMap.OIEntry;
import frc.lib.util.ButtonMap.SimplePOV;
import frc.robot.commands.drivetrain.PeaccyDrive;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Hood.HoodSetpoints;
import frc.robot.commands.BreathFire;
import frc.robot.subsystems.ShootFeed;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.HoppyWoppy;
import frc.robot.subsystems.IntakeDeploy;
import frc.robot.subsystems.Intake;

public class RobotContainer {
  /* OI CONSTANTS */
  private final int translationAxis = 1; //forward/backward
  private final int strafeAxis = 0;
  private final int rotationAxis = 4;
  private final int zeroButtonNo = 7;

  /* SENSORS */

  /* SUBSYSTEMS */
  //ONE OF THESE MUST BE COMMENTED OUT. ONLY USE THE TUNEABLE ONE FOR TUNING.
  private final Swerve driveTrain = new Swerve();
  private final ShootFeed shootFeed = new ShootFeed();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();
  private final Hood hood = new Hood();
  private final IntakeDeploy intakeDeploy = new IntakeDeploy();
  private final Intake intake = new Intake();
  private final HoppyWoppy hoppyWoppy = new HoppyWoppy();
  //private final DriveTrainTuner driveTrainTuneable = new DriveTrainTuner();

  /* OI DEFINITIONS */
  private final Joystick driverController = new Joystick(0);
  private final Joystick operatorController = new Joystick(1);
  private final JoystickButton zeroButton = new JoystickButton(driverController, zeroButtonNo); //for debugging

  private final OIEntry[] driverMap = new OIEntry[] 
  {  
    // Button.onHold(shootFeed.Intake(), 1), //TODO
    // Button.onHold(shooter.Shoot(), 2), //TODO
    //Button.toggle(climber.DeployClimber(), 5), //TODO
    Button.onHold(intakeDeploy.DeployIntake(), 5),
    // Button.onHold(intakeDeploy.DeployIntake(), 4), //TODO
    // Button.onHold(intake.IntakeStuff(), 8), //TODO
    // Button.onHold(hoppyWoppy.SortHopper(), 9), //TODO
    

  };

  private final OIEntry[] operatorMap = new OIEntry[] 
  { // change shooter to velocity control
    
    // TODO whenver climber is accepted as a member of society JoystickTrigger.onMove(operatorController, climber.Winch(() -> -operatorController.getRawAxis(5)*0.3), 3, 0.10),
    JoystickTrigger.onMove(operatorController, hood.ControlHoodMotor(() -> -operatorController.getRawAxis(1)*-0.6), 1, 0.15),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.layup), 0),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleFour), 270),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleThree), 360),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleTwo), 90),
    Button.onHold(shootFeed.Intake(), 6), //TODO
    Button.onHold(hoppyWoppy.SortHopper(), 6), //TODO
    Button.onHold(intake.IntakeStuff(), 5), //TODO
    Button.onHold(intakeDeploy.DeployIntake(), 5),
    // Button.onHold(shooter.Shoot(), 8),
    JoystickTrigger.onMove(operatorController, shooter.Shoot(), 3, 0.10),
    JoystickTrigger.onZero(operatorController, shooter.IdleShooter(), 3, 0.1),
  };


  /* COMMANDS */
  private final PeaccyDrive peaccyDrive = new PeaccyDrive(driveTrain);
  //private final BreathFire breathFire = new BreathFire();
  

  // private final SendableChooser<Command> autoChooser = AutoBuilder.buildAutoChooser();

  public RobotContainer() {
    configureBindings();

    // SmartDashboard.putData("AUTO MODE", autoChooser);
  }

  private void configureBindings() {
    peaccyDrive.withTranslation(() -> -driverController.getRawAxis(translationAxis))
               .withStrafe     (() -> -driverController.getRawAxis(strafeAxis))
               .withRotation   (() -> -driverController.getRawAxis(rotationAxis))
               .withHeading    (() -> (double) -driverController.getPOV())
               .useHeading     (() -> driverController.getPOV() != -1)
               .isFieldRelative(() -> driverController.getRawAxis(2) < 0.2) //left trigger
               .isLockIn       (() -> driverController.getRawAxis(3) > 0.2) //right trigger
               .isZeroOdometry (() -> zeroButton.getAsBoolean())
               .isOpenLoop     (() -> false);
    driveTrain.setDefaultCommand(peaccyDrive);
    driveTrain.register(driverController);
    new ButtonMap(driverController).map(driverMap);
    new ButtonMap(operatorController).map(operatorMap);


    //shooter.setDefaultCommand(shooter.IdleShooter());
    climber.setDefaultCommand(climber.RestClimber());
    hoppyWoppy.setDefaultCommand(hoppyWoppy.StopHoppyWoppy());
    intakeDeploy.setDefaultCommand(intakeDeploy.UnDeploy()); //Set this to stop and make some other way to undeploy, if your having issues with this overriding controls.
    intake.setDefaultCommand(intake.Stop());
    shootFeed.setDefaultCommand(shootFeed.Stop());
    hood.setDefaultCommand(hood.StoppyWoppy());
  }



  public Command getAutonomousCommand() { 
    // return AutoBuilder.followPath(PathPlannerPath.fromPathFile("ExamplePathE"));
    // return new PathPlannerAuto("ExamplePathE");
    return null;
  }
}