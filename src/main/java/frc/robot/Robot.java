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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.util.Utilities;
import frc.robot.util.drivers.NavX;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Wrist;

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
    outputToSmartDashboard = OutputToSmartDashboard.getInstance();

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
