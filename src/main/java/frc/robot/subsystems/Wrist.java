package frc.robot.subsystems;

public class Wrist extends Subsystem{

    static Wrist mInstance = new Wrist();

    public static Wrist getInstance() {
       return mInstance;
    }

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}

}
