package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.util.drivers.SwerveModule;
import frc.robot.util.math.Vector2;
import frc.robot.util.drivers.Mk2SwerveModuleBuilder;
import frc.robot.Setup;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;

public class DrivetrainSubsystem {
           
        //get instance
        private static DrivetrainSubsystem instance;

        public static DrivetrainSubsystem getInstance() {
                if (instance == null) {
                        instance = new DrivetrainSubsystem();
                }
                return instance;
        }

        //Establish motor variables
        private double frontLeftMotor = 0;
        private double fronttRightMotor = 0;
        private double backRightMotor = 0;
        private double backLeftMotor= 0;

        //Establishes Swerve Module variables
        public final SwerveModule frontLeftModule;
        public final SwerveModule frontRightModule;
        public final SwerveModule backLeftModule;
        public final SwerveModule backRightModule;

        //Establishes speed throttle
        public double speedChanger = 0;

        double speed = .15;
        String speedSetting = "medium";

        public DrivetrainSubsystem() {
                /*sets up the swerve modules for CANbus, sets a frame rate limit to prevent errors, establishes a center point for vector math, 
                *gets encoder position, makes motor objects for rotation and translation, the compiles into one object.*/
                CANCoder frontLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_LEFT_ANGLE_ENCODER);
                frontLeftCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder frontLeftModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(Setup.instance.TRACKWIDTH / 2.0, Setup.instance.WHEELBASE / 2.0));
                frontLeftModuleBuilder.angleEncoder(Math.toRadians(frontLeftCANCoder.getPosition()), Setup.instance.FRONT_LEFT_ANGLE_OFFSET);
                frontLeftModuleBuilder.angleMotor(new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_LEFT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                frontLeftModuleBuilder.driveMotor(new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_LEFT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                frontLeftModule = frontLeftModuleBuilder.build();     

                CANCoder frontRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_ENCODER);
                frontRightCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder frontRightModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(Setup.instance.TRACKWIDTH / 2.0, -Setup.instance.WHEELBASE / 2.0));
                frontRightModuleBuilder.angleEncoder(Math.toRadians((new CANCoder(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_ENCODER).getPosition())), Setup.instance.FRONT_RIGHT_ANGLE_OFFSET);
                frontRightModuleBuilder.angleMotor(new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_RIGHT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                frontRightModuleBuilder.driveMotor(new CANSparkMax(Setup.DrivetrainSubsystem_FRONT_RIGHT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                frontRightModule = frontRightModuleBuilder.build();

                CANCoder backLeftCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_ENCODER);
                backLeftCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder backLeftModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(-Setup.instance.TRACKWIDTH / 2.0, Setup.instance.WHEELBASE / 2.0));
                backLeftModuleBuilder.angleEncoder(Math.toRadians((new CANCoder(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_ENCODER).getPosition())), Setup.instance.BACK_LEFT_ANGLE_OFFSET);
                backLeftModuleBuilder.angleMotor(new CANSparkMax(Setup.DrivetrainSubsystem_BACK_LEFT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                backLeftModuleBuilder.driveMotor(new CANSparkMax(Setup.DrivetrainSubsystem_BACK_LEFT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                backLeftModule = backLeftModuleBuilder.build();

                CANCoder backRightCANCoder = new CANCoder(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_ENCODER);
                backRightCANCoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData,100,100);
                Mk2SwerveModuleBuilder backRightModuleBuilder = new Mk2SwerveModuleBuilder(new Vector2(-Setup.instance.TRACKWIDTH / 2.0, -Setup.instance.WHEELBASE / 2.0));
                backRightModuleBuilder.angleEncoder(Math.toRadians((new CANCoder(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_ENCODER).getPosition())), Setup.instance.BACK_RIGHT_ANGLE_OFFSET);
                backRightModuleBuilder.angleMotor(new CANSparkMax(Setup.DrivetrainSubsystem_BACK_RIGHT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                backRightModuleBuilder.driveMotor(new CANSparkMax(Setup.DrivetrainSubsystem_BACK_RIGHT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),Mk2SwerveModuleBuilder.MotorType.NEO);
                backRightModule = backRightModuleBuilder.build();

                Setup.instance.gyroscope.setInverted(true); 
        } 

        //updates sensors and corresponding states periodically
        public void periodic(){

                frontLeftModule.updateSensors();
                frontRightModule.updateSensors();
                backLeftModule.updateSensors();
                backRightModule.updateSensors();

                frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
                frontRightModule.updateState(TimedRobot.kDefaultPeriod);
                backLeftModule.updateState(TimedRobot.kDefaultPeriod);
                backRightModule.updateState(TimedRobot.kDefaultPeriod);
        }

        //Assigning x,y,z coordinates in m/s to the swerve modules and initializing them as 0
        ChassisSpeeds speeds;
        public SwerveModuleState[] states=Setup.instance.kinematics.toSwerveModuleStates(new ChassisSpeeds(0, 0, 0));

        //calculates where it's moving
        public void drive(Translation2d translation, double rotation, boolean fieldOriented, double throttle) {
               
                //calculates how the wheel spins, converts rotational speeds to inches
                rotation *= -12.0 /
                 Math.hypot(Setup.instance.WHEELBASE, Setup.instance.TRACKWIDTH);
                

                if(fieldOriented) {

                        //field oriented
                        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(Math.signum(translation.getX()), (translation.getY()), (rotation), Rotation2d.fromDegrees(Setup.instance.gyroscope.getAngle().toDegrees()));
                     
                } else {
        
                        //robot oriented
                        speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
        
                }
                
                //calculates swerve module states, results in an array of angles and speeds
                states = Setup.instance.kinematics.toSwerveModuleStates(speeds,new Translation2d(0,0 ));
               
                //makes it 0 to 1 instead of -1 to 1
                speedChanger = ((throttle-1)/-2);

                //makes speed grow exponentially 
                speedChanger = (speedChanger*speedChanger);

                //double x = Setup.getInstance().getPrimaryX();
                //double y = Setup.getInstance().getPrimaryY();
                //double z = Setup.getInstance().getPrimaryZ();

                //sets the modules and motors target velocity and angle in accordance with the joystick
                // if(x==0 && y==0 && z!=0){
                //         frontLeftModule.setTargetVelocity(z*speedChanger, -.25 * Math.PI);
                //         frontRightModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                //         backLeftModule.setTargetVelocity(z*speedChanger, .25 * Math.PI);
                //         backRightModule.setTargetVelocity(-z*speedChanger, -.25 * Math.PI);
                
                //  } else 
                 if (speeds.omegaRadiansPerSecond!=0 || speeds.vxMetersPerSecond!=0 || speeds.vyMetersPerSecond!=0){
                        frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond*speedChanger, states[0].angle.getRadians());
                        frontRightModule.setTargetVelocity(-states[1].speedMetersPerSecond*speedChanger, states[1].angle.getRadians());
                        backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond*speedChanger, states[2].angle.getRadians());
                        backRightModule.setTargetVelocity(states[3].speedMetersPerSecond*speedChanger, states[3].angle.getRadians());

                        frontLeftMotor = states[0].angle.getRadians();
                        fronttRightMotor = states[1].angle.getRadians();
                        backLeftMotor = states[2].angle.getRadians();
                        backRightMotor = states[3].angle.getRadians();

                //if the joystick is not moving set velocity to 0        
                 }else {
                        frontLeftModule.setTargetVelocity(0, frontLeftMotor);
                        frontRightModule.setTargetVelocity(0, fronttRightMotor);
                        backLeftModule.setTargetVelocity(0, backRightMotor);
                        backRightModule.setTargetVelocity(0, backLeftMotor);
                }
                
        }

        //determine which speed setting the driver sets
        public String getSpeedSetting(){
                if(Setup.getInstance().getPrimaryDriverXButton()){
                        speedSetting = "fast";
                } else if(Setup.getInstance().getPrimaryDriverAButton()){
                        speedSetting = "medium";
                } else if(Setup.getInstance().getPrimaryDriverBButton()){
                        speedSetting = "slow";
                } else if(Setup.getInstance().getPrimaryDriverYButton()){
                        speedSetting = "reallySlow";
                }
                return speedSetting;
    
        }

        //set the speed based on the current speed setting
        public double getSpeed(String speedSetting) {
                String whichSpeed = speedSetting;
                if(whichSpeed == "fast"){
                        speed=-.8;
                } else if(whichSpeed == "medium"){
                        speed=-.3;
                } else if(whichSpeed == "slow"){
                        speed=.1;
                } else if(whichSpeed == "reallySlow"){
                        speed = .5;
                }
                return speed;
        }

        //change rotation based on the current speed setting
        public double getRotation(String speedSetting, double rotation) {
                double rotate = rotation;
                String whichSpeed = speedSetting;
                if(whichSpeed == "fast"){
                        rotate /=3;
                } else if(whichSpeed == "medium"){
                        rotate /=4.5;
                } else if(whichSpeed == "slow"){
                        rotate /=10;
                } else if(whichSpeed == "reallySlow"){
                        rotate /=3.8;
                }
                return rotate;
        }

        //stops the robot lol
        public void stop() {
                frontLeftModule.setTargetVelocity(0, 0);
                frontRightModule.setTargetVelocity(0, 0);
                backLeftModule.setTargetVelocity(0, 0);
                backRightModule.setTargetVelocity(0, 0);
        }

        //resets the gyro
        public void resetGyroscope() {
                Setup.instance.gyroscope.setAdjustmentAngle(Setup.instance.gyroscope.getUnadjustedAngle());
        }
}