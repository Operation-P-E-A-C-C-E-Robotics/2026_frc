// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.util.ButtonMap;
import frc.lib.util.ButtonMap.Button;
import frc.lib.util.ButtonMap.JoystickTrigger;
import frc.lib.util.ButtonMap.MultiButton;
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
import frc.robot.subsystems.SafeMode;

public class RobotContainer {
  /* OI CONSTANTS */
  private final int translationAxis = 1; //forward/backward
  private final int strafeAxis = 0;
  private final int rotationAxis = 2;
  private final int zeroButtonNo = 10;

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
    //Button.onHold(driveTrain.SlowSwerve(), 2),
    //MultiButton.onPress(driverController, ),
    JoystickTrigger.onMove(driverController, climber.Winch(() -> operatorController.getRawAxis(2) * 0.4), 2, 0.08),
  };

  private final OIEntry[] operatorMap = new OIEntry[] 
  { // change shooter to velocity control
    
    // TODO whenver climber is accepted as a member of society JoystickTrigger.onMove(operatorController, climber.Winch(() -> -operatorController.getRawAxis(5)*0.3), 3, 0.10),
    JoystickTrigger.onMove(operatorController, hood.ControlHoodMotor(() -> -operatorController.getRawAxis(1)*-0.6), 1, 0.15),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.layup), 0),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleFour), 270),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleThree), 360),
    SimplePOV.onHold(hood.GoToHoodSetpoint(HoodSetpoints.angleTwo), 90),
    Button.onHold(shootFeed.Intake().alongWith(
      intakeDeploy.Retract(),
      intake.IntakeStuff(() -> {return 0.5;})
    ), 6),
    Button.onHold(hoppyWoppy.SortHopper(false, () -> 1), 1),
    Button.onHold(hoppyWoppy.SortHopper(false, () -> 1), 2),
    Button.onHold(hoppyWoppy.SortHopper(false, () -> 1), 3),
    Button.onHold(hoppyWoppy.SortHopper(false, () -> 1), 4), 
    Button.onHold(hoppyWoppy.SortHopper(false, () -> 1), 6),
    JoystickTrigger.onMove(operatorController, intake.AntiIntake(() -> operatorController.getRawAxis(2) * -1), 2, 0.08),
    JoystickTrigger.onMove(operatorController, hoppyWoppy.SortHopper(true, () -> operatorController.getRawAxis(2)), 2, 0.08),
    JoystickTrigger.onMove(operatorController, intakeDeploy.ControlMotor(() -> operatorController.getRawAxis(5)* 0.4), 5, 0.1),
    JoystickTrigger.onMove(
      operatorController, 
      intake.IntakeStuff(() -> operatorController.getRawAxis(3) * .3).alongWith(
        hoppyWoppy.SortHopper(false, () -> 1),
        intakeDeploy.Deploy().withTimeout(3)
      ),
      3,
      0.05),
    JoystickTrigger.onZero(operatorController, intake.Stop(), 3, 0.08),
    Button.onHold(shooter.Shoot(), 5),
  };

// hi -avery
  /* COMMANDS */
  private final PeaccyDrive peaccyDrive = new PeaccyDrive(driveTrain);
  //private final BreathFire breathFire = new BreathFire();
  //remember to press ctrl + a and then press backspace

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
    //climber.setDefaultCommand(climber.AutoUnWinch());
    hoppyWoppy.setDefaultCommand(hoppyWoppy.StopHoppyWoppy());//w h y
    //intakeDeploy.setDefaultCommand(intakeDeploy.UnDeploy()); //Set this to stop and make some other way to undeploy, if your having issues with this overriding controls.
    intake.setDefaultCommand(intake.Stop());
    shootFeed.setDefaultCommand(shootFeed.Stop());
    hood.setDefaultCommand(hood.StoppyWoppy());
    shooter.setDefaultCommand(shooter.IdleShooter());
    //driveTrain.setDefaultCommand(driveTrain.UnSlowSwerve());
  }//comment



  public Command getAutonomousCommand() {
    // try {
    //   return AutoBuilder.followPath(PathPlannerPath.fromPathFile("ThePath")).andThen(
    //     new ParallelCommandGroup(
    //       shooter.Shoot(),
    //       new SequentialCommandGroup(new WaitCommand(2), shootFeed.Intake())
    //     )
    //   );
    // } catch (FileVersionException e) {
    //   e.printStackTrace();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // } catch (ParseException e) {
    //   e.printStackTrace();
    // }
    // return null;
    return null;
  }
}