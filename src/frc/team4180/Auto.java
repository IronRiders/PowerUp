package frc.team4180;

public class Auto {
    AutonomousMode autonomousMode;
    public Auto{
        autonomousMode = AutonomousMode.getAutonomousMode();
    }

    public void move(DriveTrain driveTrain){
        switch(autonomousMode.state){
            case FORWORD:
                driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0.3,0));
                break;
            case TURN_LEFT:
                //driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0,0));
                break;
            case TURN_RIGHT:
                //driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0,0));
                break;
            case STOP:
                driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0,0));
                break;
        }

    }

    public void periodic(DriveTrain driveTrain , PositioningSystem positioningSystem}){
        switch(autonomousMode.startingPosition){
            case LEFT:
                switch(autonomousMode.switchPosition){
                    case RIGHT:
                        leftRight(driveTrain, positioningSystem);
                        break;
                    case LEFT:
                        leftLeft(driveTrain, positioningSystem);
                        break;
                }
                break;
            case CENTER:
                switch(autonomousMode.switchPosition){
                    case RIGHT:
                        centerRight(driveTrain, positioningSystem);
                        break;
                    case LEFT:
                        centerLeft(driveTrain, positioningSystem);
                        break;
                }
                break;
            case RIGHT:
                switch(autonomousMode.switchPosition){
                case RIGHT:
                    rightRight(driveTrain, positioningSystem);
                    break;
                case LEFT:
                    rightLeft(driveTrain, positioningSystem);
                    break;
            }
            break;
        }
    }
    public void leftLeft(DriveTrain driveTrain, PositioningSystem positioningSystem){

    }

    public void leftRight(DriveTrain driveTrain, PositioningSystem positioningSystem){
        if(positioningSystem.getPosY < 3.2){
            autonomousMode.state = FORWORD
            move(driveTrain)
        }
        else if(){

        }


    }

    public void rightRight(DriveTrain driveTrain, PositioningSystem positioningSystem){

    }

    public void rightLeft(DriveTrain driveTrain, PositioningSystem positioningSystem){

    }

    public void centerRight(DriveTrain driveTrain, PositioningSystem positioningSystem){

    }

    public void centerLeft(DriveTrain driveTrain){

    }
}
