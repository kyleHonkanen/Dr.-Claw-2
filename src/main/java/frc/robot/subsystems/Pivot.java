package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Setup;

public class Pivot extends Subsystem {
   public CANSparkMax pivot;
   public double speed= 0.25;
   public double rightJoystickPos;
   //get instance
   static Pivot mInstance = new Pivot();

   public static Pivot getInstance() {
      return mInstance;
   }

   public Pivot(){
      pivot = new CANSparkMax(Setup.PivotMotorID, MotorType.kBrushed);

   }

   public boolean AbletoDown(){
      return rightJoystickPos >= 0.3; //&& ADD LIMITS SOMEHOW
   }
   public boolean AbletoUp(){
      return rightJoystickPos<=-0.3; //&& ADD LIMITS SOMEHOW
   }

   @Override
   public void updateSubsystem(){
      rightJoystickPos = Setup.getInstance().getSecondaryDriverRightYAxis();
      if (AbletoDown()){
         pivot.set(-speed);
      }else if (AbletoUp()){
         pivot.set(speed);
      }else{
         pivot.set(0);
      }
   }

   @Override
   public void outputToSmartDashboard(){}

   @Override
   public void stop(){
      pivot.set(0);
   }
}