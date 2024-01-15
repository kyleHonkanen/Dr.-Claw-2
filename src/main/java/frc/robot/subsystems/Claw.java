package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Setup;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class Claw extends Subsystem {

    //get instance
    static Claw mInstance = new Claw();

    public static Claw getInstance() {
    	return mInstance;
    }

    public boolean succ;
    public boolean unSucc;
    public CANSparkMax clawMotor;
    public DigitalInput gamePiece;
    public Solenoid Solenoid;
    public boolean clawSolenoid;

    public Claw(){
        clawMotor = new CANSparkMax(Setup.ClawMotorID,MotorType.kBrushless);
        gamePiece = new DigitalInput(Setup.gamePieceSensorID);
        Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Setup.IntakeSolenoidId);
    } 

    public CANSparkMax getClawMotor(){
        return clawMotor;
    }

    public Solenoid getSolenoid(){
        return Solenoid;
    }

    public boolean getGamepiece(){
        return gamePiece.get();
    }

    @Override
    public void updateSubsystem(){
        succ = Setup.getInstance().getSecondaryDriverRightBumper();
        unSucc = Setup.getInstance().getSecondaryDriverLeftBumper();

       if(gamePiece.get()==false && unSucc==false){
            clawMotor.set(0);
            clawSolenoid = false;
       }else if(succ){
            clawMotor.set(1);
            clawSolenoid = true;
        } else if(unSucc){
            clawSolenoid = true;
        } else {
            clawMotor.set(0);
            clawSolenoid = false;
        }
        
        Solenoid.set(clawSolenoid);

        if(succ==true){
            Setup.secondaryJoystick.setRumble(RumbleType.kBothRumble, .7);
        } else {
            Setup.secondaryJoystick.setRumble(RumbleType.kBothRumble,0);
        }

        SmartDashboard.putBoolean("game piece",gamePiece.get());
        SmartDashboard.putBoolean("succ",succ);
        SmartDashboard.putBoolean("unSucc",unSucc);
    }

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}