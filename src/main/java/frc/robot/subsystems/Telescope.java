package frc.robot.subsystems;

public class Telescope extends Subsystem {
    
    //get instance
    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
    	return mInstance;
    }


    public Telescope() {}

  

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}
}