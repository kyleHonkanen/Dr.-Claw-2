package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OutputToSmartDashboard;

import com.ctre.phoenix.sensors.CANCoder;

public class OutputToSmartDashboard {

    public static OutputToSmartDashboard instance = new OutputToSmartDashboard();

    public static OutputToSmartDashboard getInstance() { 
        if (instance == null) {
            instance = new OutputToSmartDashboard();
        }   
        return instance;
    }   

    public void outputSmartDashboard(){

        CANCoder frontLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_LEFT_ANGLE_ENCODER);
        SmartDashboard.putNumber("Front Left Module Angle", frontLeftCANCoder.getPosition());

        CANCoder frontRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_ENCODER);
        SmartDashboard.putNumber("Front right module angle", frontRightCANCoder.getPosition());

        CANCoder backLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_ENCODER);
        SmartDashboard.putNumber("Back Left module angle", backLeftCANCoder.getPosition());

        CANCoder backRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_ENCODER);
        SmartDashboard.putNumber("Back Right module angle", backRightCANCoder.getPosition());
        
    }
}