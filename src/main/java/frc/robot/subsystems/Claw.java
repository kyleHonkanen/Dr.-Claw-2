package frc.robot.subsystems;

public class Claw extends Subsystem {
    
    static Claw mInstance = new Claw();

    public static Claw getInstance() {
       return mInstance;
    }

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}

}