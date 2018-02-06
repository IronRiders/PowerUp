package frc.team4180;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import static edu.wpi.first.wpilibj.networktables.NetworkTable.getTable;

public class ImageRecognizer {
    NetworkTable networkTable;


    public ImageRecognizer(){
        networkTable = NetworkTableInstance.getDefault().getTable("GRIP/myContoursReport");

    }




}