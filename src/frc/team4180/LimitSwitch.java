package frc.team4180;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;


public class LimitSwitch {
DigitalInput limitSwitch;
public void limitSwitchInit(){
    limitSwitch = new DigitalInput(1);
}
public void sensor{
    while(limitSwitch.get()){
        Timer.delay(1);
    }
    }
}