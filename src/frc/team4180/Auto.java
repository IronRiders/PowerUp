package frc.team4180;

import edu.wpi.first.wpilibj.DriverStation;
import static frc.team4180.AutonomousMode.SwitchPosition.*;

public class Auto {

    public Auto(){

        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.charAt(0) == 'L'){
            AutonomousMode.setCurrentMode(new AutonomousMode(null,LEFT ));
            // need to get input from driver station

        }
        else if (gameData.charAt(0) == 'R'){
            AutonomousMode.setCurrentMode(new AutonomousMode(null,RIGHT));
            // need to get input from driver station
        }



    }



}
