package frc.robot.subsystems;

import frc.robot.Setup;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {

   //get instance
   static Pivot mInstance = new Pivot();

   public static Pivot getInstance() {
      return mInstance;
   }

   public CANSparkMax pivotMotor;
   public AbsoluteEncoder encoder;
   private double pivotMotorSpeed = 0;
   
   public Pivot() {
      pivotMotor = new CANSparkMax(Setup.PivotMotorID, MotorType.kBrushless);
      encoder = pivotMotor.getAbsoluteEncoder(com.revrobotics.SparkMaxAbsoluteEncoder.Type.kDutyCycle);
   }

   public CANSparkMax getPivotMotor(){
      return pivotMotor;
   }

   public AbsoluteEncoder getPivotEncoder(){
      return encoder;
   }

   public double getPivotPosition(){
      return encoder.getPosition()*360;
   }

   @Override
   public void updateSubsystem(){
      //geting motor speed and arm location
      pivotMotorSpeed = Setup.getInstance().getSecondaryDriverRightYAxis();
      double armPosition = encoder.getPosition()*360;

      //softStops
      if(armPosition<260 && armPosition>180 && pivotMotorSpeed>0){
         pivotMotorSpeed = 0;
      } else if(armPosition>105 && armPosition<180 && pivotMotorSpeed<0){
         pivotMotorSpeed = 0;
      
      //positions before the stops that slow down the speed
      } else if (armPosition<280 && armPosition>180){
         pivotMotorSpeed = pivotMotorSpeed/2;
      } else if(armPosition>85 && armPosition<180){
         pivotMotorSpeed = pivotMotorSpeed/2;
      }

      //dead zone
      if(pivotMotorSpeed>-.1 && pivotMotorSpeed<0){
         pivotMotorSpeed=0;
      } else if(pivotMotorSpeed<.1 && pivotMotorSpeed>0){
         pivotMotorSpeed=0;
      }

      //fast when pressing left stick
      if(Setup.getInstance().getSecondaryDriverRightStickPressed()){
         pivotMotorSpeed = pivotMotorSpeed*2;
      }

      pivotMotor.set(pivotMotorSpeed/2.2);
   }

   @Override
   public void outputToSmartDashboard(){
      SmartDashboard.putNumber("pivot angle",encoder.getPosition()*360);
   }

   @Override
   public void stop(){
      pivotMotorSpeed = 0;
   }
}