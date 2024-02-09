package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Setup;
import com.revrobotics.AbsoluteEncoder;

public class Pivot extends Subsystem {
   public CANSparkMax pivot;
   public double speed= 0.25;
   public double rightJoystickPos;
   public AbsoluteEncoder pEncoder;
   //get instance
   static Pivot mInstance = new Pivot();

   public static Pivot getInstance() {
      return mInstance;
   }

   public Pivot(){
      pivot = new CANSparkMax(Setup.PivotMotorID, MotorType.kBrushed);
      pEncoder = pivot.getAbsoluteEncoder(com.revrobotics.SparkMaxAbsoluteEncoder.Type.kDutyCycle);

   }

   public boolean AbletoDown(){
      return rightJoystickPos >= 0.3; //&& pEncoder.getPosition()*360 
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
   public void outputToSmartDashboard(){
      SmartDashboard.putNumber("pivot angle",pEncoder.getPosition()*360);
   }

   @Override
   public void stop(){
      pivot.set(0);
   }
}