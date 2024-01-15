package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Telescope extends Subsystem {
    
    //get instance
    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
    	return mInstance;
    }

    public CANSparkMax telescopeMotor;
    public RelativeEncoder encoder;
    public DigitalInput ArmMax;
    public DigitalInput ArmMin;

    double armExtend = 0;
    double armRetract = 0;

    public Telescope() {
        telescopeMotor = new CANSparkMax(Setup.TelescopeMotorID, MotorType.kBrushless);
        encoder = telescopeMotor.getEncoder();
        ArmMax = new DigitalInput(Setup.ArmMaxID);
        ArmMin = new DigitalInput(Setup.ArmMinID);
    }

    public CANSparkMax getTelescopeMotor(){
        return telescopeMotor;
    }

    public RelativeEncoder getTelescopeEncoder(){
        return encoder;
    }

    public boolean getArmMax(){
        return ArmMax.get();
    }

    public boolean getArmMin(){
        return ArmMin.get();
    }

    @Override
    public void updateSubsystem(){
        armExtend = Setup.getInstance().getSecondaryDriverRightTrigger();
        armRetract = Setup.getInstance().getSecondaryDriverLeftTrigger();

        //if the motor is over the offset value and not at its maximum set the motor speed
        //digital input values are backwards of what is expected (when theres a gamepiece its false)
        if(armExtend > .1 && ArmMax.get() == true){
            telescopeMotor.set(armExtend/2);
        } else if(armRetract > .1 && ArmMin.get() == true){
            telescopeMotor.set(-armRetract/2);
        } else {
            telescopeMotor.set(0);
        }
    }

    @Override
    public void outputToSmartDashboard(){
        SmartDashboard.putBoolean("Arm at maximum",ArmMax.get());
        SmartDashboard.putBoolean("Arm at minimum",ArmMin.get());
        SmartDashboard.putNumber("armExtend",armExtend);
        SmartDashboard.putNumber("armRetract",armRetract);
    }

    @Override
    public void stop(){}
}