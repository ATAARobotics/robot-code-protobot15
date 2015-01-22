
/**
 * 		BINDS: 
 * 		
 * 		A = Switch Gears
 * 
 * 		Left Thumbstick - Forward/Backward
 * 		Right THumbstick - Turn Left/Right
 * 
 * 		
 *		
 * 
 */


package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
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
	int stage;
	
	int leftR;
	int rightR;

	
	Encoder encoderL;
	Encoder encoderR;
    
    public void robotInit() {
    
    vicFL = new Victor(0);
	vicBL = new Victor(1);
	vicFR = new Victor(2);
    vicBR = new Victor(3);
    
    joy = new Joystick(0);
    
    comp  = new Compressor(0);
    gearShift = new DoubleSolenoid(0, 1);
    gearShift.set(DoubleSolenoid.Value.kForward);
    comp.setClosedLoopControl(true);
    
    encoderR = new Encoder(1, 2, true, EncodingType.k4X);
	encoderL = new Encoder(3, 4, true, EncodingType.k4X);
	
	encoderL.reset();
	encoderR.reset();
	
	
    
    stage = 1;
    }

    
    
     //This function is called periodically during autonomous
     
    public void autonomousPeriodic() {
    	
    	leftR = encoderL.get ();
    	rightR = encoderR.get();
    	
    	/*if (stage == 1) {
    		
    		if ((leftR < 1000) && (-rightR < 1000)) {
    			vicFL.set (.95);
    			vicBL.set (.95);
    			vicFR.set (1);
    			vicBR.set (1);
    		}
    		else {
    			vicFL.set (0);
    			vicBL.set (0);
    			vicFR.set (0);
    			vicBR.set (0);
    		}
    		}
    	}
    	*/
 
		leftR = encoderL.get();
    	rightR = encoderR.get();
    	
    	if(stage == 1){
    		leftR = encoderL.get();
    		rightR = encoderR.get();
    		
    		
        	if((leftR < 17000) && (-rightR < 17000)) {
    		
        		vicFL.set(0.9);
        		vicBL.set(0.9);
        		vicFR.set(-1);
        		vicBR.set(-1);
        		
    			}
        	
        	else {
        		
        		vicFL.set(0);
        		vicBL.set(0);
        		vicFR.set(0);
        		vicBR.set(0);
        		stage = 2;
        		encoderL.reset();
        		encoderR.reset();
        		
        	}
    	
    	}
    	
    	if(stage == 2){
    		    		
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
        	
    		if((leftR < 730) && (rightR < 730)){
    		
    			vicFL.set(-0.95);
    			vicBL.set(-0.95);
    			vicFR.set(-1);
    			vicBR.set(-1);
        	
    		}
    		else {
    			
    			vicFL.set(0);
            	vicBL.set(0);
            	vicFR.set(0);
            	vicBR.set(0);
            	stage = 3;
            	encoderL.reset();
        		encoderR.reset();
            	
    		}
    		
    	}
 
    	if(stage == 3){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
        	
    		if((leftR < 7000) && (-rightR < 7000)) {
        		
        		vicFL.set(0.95);
        		vicBL.set(0.95);
        		vicFR.set(-1);
        		vicBR.set(-1);
        		
    			}
        	
        	else {
        		
        		vicFL.set(0);
        		vicBL.set(0);
        		vicFR.set(0);
        		vicBR.set(0);
        		stage = 4;
        		encoderL.reset();
        		encoderR.reset();
        		
        	}
    	}
    
    	if(stage == 4) {
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
        	
    		if((leftR < 730) && (rightR < 730)){
    		
    			vicFL.set(-0.95);
    			vicBL.set(-0.95);
    			vicFR.set(-1);
    			vicBR.set(-1);
        	
    		}
    		else {
    			
    			vicFL.set(0);
            	vicBL.set(0);
            	vicFR.set(0);
            	vicBR.set(0);
            	stage = 5;
            	encoderL.reset();
            	encoderR.reset();
            	
    		}
    	}
    	
    	if(stage == 5){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
        	
    		if((leftR < 12500) && (-rightR < 12500)) {
        		
        		vicFL.set(0.95);
        		vicBL.set(0.95);
        		vicFR.set(-1);
        		vicBR.set(-1);
        		
    			}
        	
        	else {
        		
        		vicFL.set(0);
        		vicBL.set(0);
        		vicFR.set(0);
        		vicBR.set(0);
        		stage = 6;
        		encoderL.reset();
        		encoderR.reset();
        		
        	}
    	}
    	if (stage ==6) {
    		leftR = encoderL.get();
    		rightR = encoderR.get();
    		
    		if ((leftR < 3350) && (rightR < 3350)) {
    			
    			vicFL.set (-0.95);
    			vicBL.set (-0.95);
    			vicFR.set (-1);
    			vicBR.set (-1);
    			
    		}
    		
    		else {
    			
    			vicFL.set (0);
    			vicBL.set (0);
    			vicFR.set (0);
    			vicBR.set (0);
    			stage = 7;
    			encoderL.reset();
    			encoderR.reset();
    		}
    	}
    	
    	if (stage ==7) {
    		leftR = encoderL.get();
    		rightR = encoderR.get();

    		if ((leftR < 6000) && (-rightR < 6000)) {
    				
    				vicFL.set (0.95);
    				vicBL.set (0.95);
    				vicFR.set (-1);
    				vicBR.set (-1);
    				
    			}
    			
    			else {
    				
    				vicFL.set (0);
    				vicBL.set (0);
    				vicFR.set (0);
    				vicBR.set (0);
    				stage = 8;
    			    encoderL.reset();
    				encoderR.reset();
    				
    			}
    	}
    	 if (stage ==8) {
    		 
    		 leftR = encoderL.get () ;
    		 rightR = encoderR.get ();
    		 
    		 if ((leftR < 10) && (rightR < 10)) {
    			 
    			 vicFL.set (0.95);
    			 vicBL.set (0.95);
    			 vicFR.set (-1);
    			 vicBR.set (-1);
    		 }
    		 else {
    			 vicFL.set (0);
    			 vicBL.set (0);
    			 vicFR.set (0);
    			 vicBR.set (0);
    			 stage = 9;
    			 //encoderL.reset();
    			 //encoderR.reset();
    		
    			
    		 }
    	 }
    }
    

    
    
     //This function is called periodically during operator control
    
    public void teleopPeriodic() {
    	
    	leftThumb=(-(joy.getRawAxis(1)));
    	rightThumb=(joy.getRawAxis(4));
    	
    	//If left thumbstick is still

    	if((leftThumb>-0.1) && (leftThumb<0.1)) {

    	vicFL.set(-(rightThumb));
    	vicBL.set(-(rightThumb));
    	    
    	vicFR.set(-(rightThumb));
    	vicBR.set(-(rightThumb));

    	}

    	//If right thumbstick is still

    	if((rightThumb>-0.1) && (rightThumb<0.1)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-leftThumb);
    	vicBR.set(-leftThumb);

    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if((leftThumb>0.1) && (rightThumb>0.1)) {

    	vicFL.set(leftThumb - (rightThumb * 0.9));
    	vicBL.set(leftThumb - (rightThumb * 0.9));
    	    
    	vicFR.set(-(leftThumb));
    	vicBR.set(-(leftThumb));

    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if((leftThumb>0.1) && (rightThumb<-0.1)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-(leftThumb + (rightThumb * 0.9)));
    	vicBR.set(-(leftThumb + (rightThumb * 0.9)));

    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if((leftThumb<-0.1) && (rightThumb>0.1)) {

    	vicFL.set(leftThumb + (rightThumb * 0.9));
    	vicBL.set(leftThumb + (rightThumb * 0.9));
    	    
    	vicFR.set(-(leftThumb));
    	vicBR.set(-(leftThumb));

    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((leftThumb<-0.1) && (rightThumb<-0.1)) {

    	vicFL.set(leftThumb);
    	vicBL.set(leftThumb);
    	    
    	vicFR.set(-(leftThumb - (rightThumb * 0.9)));
    	vicBR.set(-(leftThumb - (rightThumb * 0.9)));
    	
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
