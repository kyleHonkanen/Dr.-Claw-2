/* 

      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@           @@@@@@@@@@@@@          @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
     @@@@@@@@@@@@@@@            @@@@@@@@@@@@          @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@         @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@         @@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@        @@@@@@@@@@@@@@            @@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@        @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       @@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
    @@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@    
         @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@          
             @@@@@@@@@@@@@@@    @@@@@@@@@@@@@@      @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@              
            @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@      @@@@@@@@@@@@@@     @@@@@@@@@@@@@@@             
           @@@@@@@@@@@@@@@@     @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@      @@@@@@@@@@@@@@@            
           @@@@@@@@@@@@@@@      @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@       @@@@@@@@@@@@@@@           
          @@@@@@@@@@@@@@@       @@@@@@@@@@@@@@@     @@@@@@@@@@@@@@       @@@@@@@@@@@@@@@@          
         @@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@          
        @@@@@@@@@@@@@@@@         @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@         @@@@@@@@@@@@@@@         
       @@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@        
      @@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@       
     @@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@    @@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@      
     @@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@            @@@@@@@@@@@@@@@@     
    @@@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@             @@@@@@@@@@@@@@@@    
   @@@@@@@@@@@@@@@@              @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@              @@@@@@@@@@@@@@@@    
  @@@@@@@@@@@@@@@@               @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@   
 @@@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@@  
                                                                                                   
*/

package frc.robot;
//import frc.robot.util.math.*;
//import edu.wpi.first.math.*;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {
  private static Setup oi;
  boolean FieldOrientedONS = true;
  double forward;
  double strafe;
  double rotation;  
  Setup setup;
  Claw claw;
  Pivot pivot;
  Telescope telescope;
  Wrist wrist;
  NavX navX;
  OutputToSmartDashboard outputToSmartDashboard;
  Limelight limelight;

  private static final String kEscapeAuto = "Escape";
  private static final String kPivotMovus = "move arm";
  private static final String kscoreChargeRightAuto = "Score right Then ChargeStation Robot pos is on right";
  private static final String kscoreChargeLeftAuto = "Score left Then ChargeStation Robot pos is on left";
  private static final String kScoreEscapeAuto = "Score then Escape";
  private static final String kBalance = "Balance";
  private static final String kNothing = "Do Nothing";
  private static final String kScoreMiddleChargeAuto = "Score Middle then Chargestation Robot pos is center";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static Setup getOi() {
    return oi;
  }

  public void updateAllSubsystems() {
    claw.updateSubsystem();
    pivot.updateSubsystem();
    telescope.updateSubsystem();
    wrist.updateSubsystem();
  }

  public void stopAllSubsystems() {
    claw.stop();
    pivot.stop();
    telescope.stop();
    wrist.stop();
  }

  public void outputAllSmartDashboard() {
    claw.outputToSmartDashboard();
    pivot.outputToSmartDashboard();
    telescope.outputToSmartDashboard();
    wrist.outputToSmartDashboard();
    limelight.outputToSmartDashboard();
  }

  //---------------------------------------------------------------------------------------------------------

  @Override
  public void robotInit() {
    oi = Setup.getInstance();
    setup=Setup.getInstance();
    claw = Claw.getInstance();
    pivot = Pivot.getInstance();
    telescope = Telescope.getInstance();
    wrist = Wrist.getInstance();
    navX = NavX.getInstance();
    limelight = Limelight.getInstance();
    outputToSmartDashboard = OutputToSmartDashboard.getInstance();

    m_chooser.setDefaultOption("Escape", kEscapeAuto);
    m_chooser.addOption("Score Center", kPivotMovus);
    m_chooser.addOption("Score Right then ChargeStation (Robot Position is Right)", kscoreChargeRightAuto);
    m_chooser.addOption("Score Left then ChargeStation (Robot Position is Left)", kscoreChargeLeftAuto);
    m_chooser.addOption("Score then Escape", kScoreEscapeAuto);
    m_chooser.addOption("Balance", kBalance);
    m_chooser.addOption("Do Nothing", kNothing);
    m_chooser.addOption("Score Middle then ChargeStation (Robot Position is Center)", kScoreMiddleChargeAuto);
    SmartDashboard.putData("AutoSelect", m_chooser);

    setup.setupCompressor();
    Setup.getInstance();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("navX", navX.getX());
    SmartDashboard.putNumber("navY", navX.getY());
  }


  @Override
  public void teleopInit() {
    //setup.setupCompressor();
    Setup.getInstance();
  }

  @Override
  public void teleopPeriodic() {
    
    updateAllSubsystems();
    outputAllSmartDashboard();

   //--------------------------------------------------- Drivetrain -----------------------------------------------------
    if (setup.getFieldOrientedOn()) {
      FieldOrientedONS=true;
    } else if(setup.getFieldOrientedOff()) {
      FieldOrientedONS=false;
    }

    double speed = DrivetrainSubsystem.getInstance().getSpeed(DrivetrainSubsystem.getInstance().getSpeedSetting());
    boolean fieldoriented = FieldOrientedONS;

    forward = Robot.getOi().getPrimaryJoystick().getRawAxis(1);
    forward = Math.copySign(Math.pow(forward, 2.0), forward);
    forward = Utilities.deadband(forward);

    strafe = Robot.getOi().getPrimaryJoystick().getRawAxis(0);
    strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

    rotation = Robot.getOi().getPrimaryJoystick().getRawAxis(5);
    rotation = Utilities.deadband(rotation);
    rotation = DrivetrainSubsystem.getInstance().getRotation(DrivetrainSubsystem.getInstance().getSpeedSetting(), rotation);

    DrivetrainSubsystem.getInstance().drive(new Translation2d(-forward, -strafe), -rotation, fieldoriented, speed);
    DrivetrainSubsystem.getInstance().periodic();

    SmartDashboard.putBoolean("FieldOriented", fieldoriented);
    SmartDashboard.putNumber("Pitch", (navX.getAxis(frc.robot.util.drivers.NavX.Axis.PITCH)));
  }
}
