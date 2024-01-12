package frc.robot.subsystems;

public class Telescope extends Subsystem{

    static Telescope mInstance = new Telescope();

    public static Telescope getInstance() {
       return mInstance;
    }

    @Override
    public void updateSubsystem(){}

    @Override
    public void outputToSmartDashboard(){}

    @Override
    public void stop(){}

}
