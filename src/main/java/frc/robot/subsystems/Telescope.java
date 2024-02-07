package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.Setup;

public class Telescope extends Subsystem {
    CANSparkMax extendy;
    public DigitalInput min;
    public DigitalInput max;
    public double speed = 0.1;
    public double leftJoystickPos;
    
    //get instance
    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
    	return mInstance;
    }


    public Telescope() {
        extendy = new CANSparkMax(Setup.TelescopeMotorID, MotorType.kBrushed);
        min = new DigitalInput(Setup.ArmMinID);
        max = new DigitalInput(Setup.ArmMaxID);
    }
    public boolean AbletoExtend(){
        return leftJoystickPos >= 0.3 && min.get();
    }
    public boolean AbletoRetract(){
        return leftJoystickPos <= -0.3 && max.get();
    }
  

    @Override
    public void updateSubsystem(){
        leftJoystickPos = Setup.getInstance().getSecondaryDriverLeftYAxis();


        if(AbletoExtend()){
            extendy.set(speed);
        }else if (AbletoRetract()){
            extendy.set(-speed);
        }else{
            extendy.set(0);
        }
    }

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){
        extendy.set(0);
    }
}