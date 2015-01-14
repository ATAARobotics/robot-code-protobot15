
/**
 * 		BINDS: 
 * 		
 * 		Switch Gears: A
 * 
 * 		Forward/Backward LEFT: Left Joy Y axis
 * 		Forward/Backward RIGHT: Right Joy Y axis
 *		
 * 
 */


package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends IterativeRobot {
    
      //This function is run when the robot is first started up and should be
      //used for any initialization code.
     
    
	Joystick joy;
	Victor vicFL;
    Victor vicBL;
    Victor vicFR;
    Victor vicBR;
    Compressor comp;
    DoubleSolenoid gearShift;
    double leftThumb,rightThumb;
	boolean stillPressed;
	int number;
	
    
    public void robotInit() {
		vicFL = new Victor(1);
		vicBL = new Victor(3);
		vicFR = new Victor(0);
		vicBR = new Victor(2);
      joy = new Joystick(0);
      comp  = new Compressor(0);
      gearShift = new DoubleSolenoid(0, 1);
      gearShift.set(DoubleSolenoid.Value.kForward);
      comp.setClosedLoopControl(true);
    number  = 6;
    }

    
    
     //This function is called periodically during autonomous
     
    public void autonomousPeriodic() {
    	 vicBL.set(1);
    	 vicFL.set(1);
    	 vicBR.set(-1);
    	 vicFR.set(-1);
    	 gearShift.set(DoubleSolenoid.Value.kForward);
    }

    
    
     //This function is called periodically during operator control
    
    public void teleopPeriodic() {
    	
    	leftThumb=(joy.getRawAxis(1));
    	rightThumb=-(joy.getRawAxis(5));
    	

    	//If left thummbstick is still

    	if((joy.getRawAxis(1)>-5) && (joy.getRawAxis(1)<5)) {

    	vicFL.set(rightThumb);
    	vicBL.set(rightThumb);
    	    
    	vicFR.set(rightThumb);
    	vicBR.set(rightThumb);

    	}

    	//If right thumbstick is still

    	if((joy.getRawAxis(6)>-5) && (joy.getRawAxis(6)<5)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-leftThumb);
    	vicBR.set(-leftThumb);

    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if((joy.getRawAxis(1)>5) && (joy.getRawAxis(6)>5)) {

    	vicFL.set(leftThumb + rightThumb);
    	vicBL.set(leftThumb + rightThumb);
    	    
    	vicFR.set(-leftThumb);
    	vicBR.set(-leftThumb);

    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if((joy.getRawAxis(1)>5) && (joy.getRawAxis(6)<-5)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-(leftThumb - rightThumb));
    	vicBR.set(-(leftThumb - rightThumb));

    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if((joy.getRawAxis(1)<-5) && (joy.getRawAxis(6)>5)) {

    	vicFL.set(leftThumb - rightThumb);
    	vicBL.set(leftThumb - rightThumb);
    	    
    	vicFR.set(-leftThumb);
    	vicBR.set(-leftThumb);

    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((joy.getRawAxis(1)<-5) && (joy.getRawAxis(6)<-5)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-(leftThumb + rightThumb));
    	vicBR.set(-(leftThumb + rightThumb));

    	}
    	
    	if (joy.getRawButton(1)==false) {stillPressed=false;}
    	if (joy.getRawButton(1)&&(stillPressed==false)){
    		if (gearShift.get()==DoubleSolenoid.Value.kForward)
    			{
    			gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed=true;
    			}
    		else {
    			gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed=true;
    		}
    	}
    	System.out.print(comp.getPressureSwitchValue());
    	System.out.print(comp.getCompressorCurrent());
    	System.out.print(comp.enabled());
    	System.out.print("/");
    }
    
    
    
     //This function is called periodically during test mode
     
    public void testPeriodic() {
    
    }
    
}
