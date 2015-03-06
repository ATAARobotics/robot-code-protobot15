
package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	//int session;
	//Image frame;
	//CameraServer server;
	USBCamera microCam1;
    public void robotInit() {
    	//frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
       microCam1 = new USBCamera();
    	//session = NIVision.IMAQdxOpenCamera("cam0",
    			//NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    	//NIVision.IMAQdxConfigureGrab(session);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //server = CameraServer.getInstance();
        //server.setQuality(50);
        //server.startAutomaticCapture ("cam0");
        //NIVision.IMAQdxStartAcquisition(session);
         microCam1.openCamera();
       microCam1.startCapture();
        //NIVision.IMAQdxStopAcquisition(session);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
