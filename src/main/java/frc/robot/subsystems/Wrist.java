package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends Subsystem {
   
    //get instance
    static Wrist mInstance = new Wrist();

    public static Wrist getInstance() {
    	return mInstance;
    }

    public DigitalInput wristRightMax;
    public DigitalInput wristLeftMax;
    public CANSparkMax wristMotor;
    public RelativeEncoder encoder;

    public Wrist() {
        wristMotor = new CANSparkMax(Setup.WristMotorID, MotorType.kBrushless);
        encoder = wristMotor.getEncoder();
        wristRightMax = new DigitalInput(Setup.wristRightMaxID);
        wristLeftMax = new DigitalInput(Setup.wristLeftMaxID);
    }

    public CANSparkMax getWristMotor(){
        return wristMotor;
    }

    public RelativeEncoder getWristEncoder(){
        return encoder;
    }

    public boolean getRightMax(){
        return wristRightMax.get();
    }

    public boolean getLeftMax(){
        return wristLeftMax.get();
    }

    public double getWristPosition(){
        return encoder.getPosition();
    }

    @Override
    public void updateSubsystem(){
        double wristMotorSpeed = Setup.getInstance().getSecondaryDriverLeftXAxis();

        //if the motor is over the offset value and not at its maximum set the motor speed
        if(wristMotorSpeed > .1 && wristLeftMax.get() == false){
            wristMotor.set(wristMotorSpeed/8);
        } else if(wristMotorSpeed < -.1 && wristRightMax.get() == false){
            wristMotor.set(wristMotorSpeed/8);
        } else {
            wristMotor.set(0);
        }

        //manualy reset wrist encoder
        if(wristLeftMax.get() == true){
            encoder.setPosition(0);
        }
    }

    
    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putBoolean("Wrist at Maximum Right",wristRightMax.get());
        SmartDashboard.putBoolean("Wrist at Maximum Left",wristLeftMax.get());
        SmartDashboard.putNumber("Wrist Encoder",Wrist.getInstance().getWristPosition());
        
    }

    @Override
    public void stop(){
        wristMotor.set(0);
    }
}