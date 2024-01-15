package frc.robot.subsystems;

public class Wrist extends Subsystem {
   
    //get instance
    static Wrist mInstance = new Wrist();

    public static Wrist getInstance() {
    	return mInstance;
    }
 
    public Wrist() {}

    @Override
    public void updateSubsystem(){}
    
    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}