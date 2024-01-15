package frc.robot.subsystems;

public class Claw extends Subsystem {

    //get instance
    static Claw mInstance = new Claw();

    public static Claw getInstance() {
    	return mInstance;
    }

    public Claw(){} 

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}