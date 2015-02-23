
/**
 * 		BINDS: 
 * 		
 * 		Joystick 1:
 * 
 * 		B = Switch Gears
 * 	
 * 		Left Thumbstick - Forward/Backward
 * 		Right Thumbstick - Turn Left/Right
 * 
 * 		LB = Arms Toggle
 * 		A = Stinger
 *		
 *		Triggers = Kicker	
 *
 *		Joystick 2:
 *
 *		LB = Arms Toggle
 *
 *		Y = Elevator Up
 * 		B = Elevator Down
 * 		A = Elevator Minimum
 * 		X = Elevator 1 Tote
 *		
 *		Triggers = Kicker
 *
 *		Left Stick = Arm Motors In/Out
 *		Right Stick = Arm Motors L/R
 *
 */

//THIS IS THE PRACTICE BOT CODE!!
package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



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
	Joystick joy2;
	
	CANTalon canFL;
    CANTalon canBL;
    CANTalon canFR;
    CANTalon canBR;
    CANTalon canWinch;
    CANTalon canWinch2;
    Talon talKicker;
    Talon talArmLeft;
    Talon talArmRight;
    
    //Encoder encoderL;
	//Encoder encoderR;
	Encoder encoderElevator;
	
	AnalogInput pot1;
	Compressor comp;
    DoubleSolenoid gearShift;
    DoubleSolenoid leftArm;
    DoubleSolenoid rightArm;
    DoubleSolenoid flipper;
    DigitalInput limit1;
    DigitalInput limit2;
    
    String ElevatorEncoder;
    String camPot;
    String limitLow;
    String limitHigh;
    String gearPos, gearPos2;
    
    Thread oneTote = new Thread(new setPointOne());
    Thread drivetrain = new Thread(new drivetrain());
    
    double leftThumb2,rightThumb2;
    double leftTrig,rightTrig;
    double leftTrig2,rightTrig2;
    double degrees, potDegrees;
	double leftThumb,rightThumb;
	double q;
	double turnRad;
	double deadZ, deadZ2;
	
    boolean stillPressed;
    boolean stillPressed2;
    boolean stillPressed3;
    boolean stillPressed4;
    boolean stillPressed5;
    boolean stillPressed6;
    boolean stillPressed7;
    boolean stillPressed8;
    boolean stillPressed9;
    boolean elevatorMax;
    boolean elevatorMin;
    boolean elevatorManual;
    boolean fullDown;
    boolean camSetPoint = false;
    boolean camCancel1;
	
	int stage;
	int camMode;
	int leftR, rightR, elevatorR;
	int range1;
	int range2;
	int elevatorRange;
	int case1, case2, case3;
	int i;
	
    public void robotInit() {
    
    canFL = new CANTalon(1);
	canBL = new CANTalon(2);
	canFR = new CANTalon(5);
    canBR = new CANTalon(6);
    canWinch = new CANTalon(3);
    canWinch2 = new CANTalon(4);
    talKicker = new Talon(0);
    talArmLeft = new Talon(1);
    talArmRight = new Talon(2);
    
    elevatorManual = false;
    elevatorRange = 15900;
    camMode = 1;
    i = 0;
    
    ElevatorEncoder = "Elevator Encoder:";
    camPot = "Cam Potentiometer:";
    limitLow = "Bottom Limit Switch:";
    limitHigh = "Top Limit Switch:";
    gearPos = "Gear Position:";
    
    joy = new Joystick(0);
    joy2 = new Joystick(1);
    
    comp  = new Compressor(0);
    comp.setClosedLoopControl(true);
    
    pot1 = new AnalogInput(0);
    
    limit1 = new DigitalInput(6);
    limit2 = new DigitalInput(7);
    
    rightArm = new DoubleSolenoid(2, 3);
    rightArm.set(DoubleSolenoid.Value.kForward);
    leftArm = new DoubleSolenoid(4, 5);
    leftArm.set(DoubleSolenoid.Value.kForward);
    gearShift = new DoubleSolenoid(6, 7);
    gearShift.set(DoubleSolenoid.Value.kForward);
    flipper = new DoubleSolenoid(0, 1);
    flipper.set(DoubleSolenoid.Value.kForward);
    
    encoderElevator = new Encoder(0, 1, true, EncodingType.k4X);
    //encoderR = new Encoder(1, 2, true, EncodingType.k4X);
	//encoderL = new Encoder(3, 4, true, EncodingType.k4X);
	//encoderL.reset();
	//encoderR.reset();
    encoderElevator.reset();
  
    stage = 0;
    }

    
    
     //This function is called periodically [20 ms] during autonomous
     
    public void autonomousPeriodic() 
    {
    	
    }

    
     //This function is called periodically [20 ms] during operator control
    
	public void teleopPeriodic() 
    {
    	
    	elevatorR = encoderElevator.get();
    	potDegrees = pot1.getVoltage();
    	
    	range1 = 240;
    	range2 = 19000;
    	
    	drivetrain.start();
    	
    	ArmMotors();
    	
    	Elevator();
    		
    	pneumatics();
    	
    	camFullManual();
    	
    	elevatorOneTote();
    	
    	elevatorLow();
    	
    	elevatorHigh();
    	
    	if (joy2.getRawButton(6) == false) {stillPressed8 = false;}
    	
    	if (joy2.getRawButton(6) && (stillPressed8 == false))
    	{
    		if (camMode == 1)
    			{
    				camMode = 2;
    			}
    		else if(camMode == 2)
    		{
    			camMode = 1;
    		}
    	}
    	
    	if(gearShift.get() == DoubleSolenoid.Value.kForward)
    	{
    		gearPos2 = "High Gear";
    	}
    	if(gearShift.get() == DoubleSolenoid.Value.kReverse)
    	{
    		gearPos2 = "Low Gear";
    	}
    	
    	if (joy2.getRawButton(1) == false) {stillPressed5 = false;}
    	
    	if (joy2.getRawButton(1) && (stillPressed5 == false))
    	{	
    		if (camSetPoint == false)
    		{
    			camExtend();
   			}
    		else if(camSetPoint == true)
    		{
    			camRetract();
    		}
    	}
    
    	
    	if (joy2.getRawButton(3) == false) {stillPressed9 = false;}
    	
    	if (joy2.getRawButton(3) && (stillPressed9 == false) && (elevatorManual == false))
    	
 //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    	
    	//SmartDashboard
    
    	SmartDashboard.putNumber(ElevatorEncoder, elevatorR);
    
    	SmartDashboard.putNumber(camPot, potDegrees);
    
    	SmartDashboard.putBoolean(limitHigh, elevatorMax);
    
    	SmartDashboard.putBoolean(limitLow, elevatorMin);
    
    	SmartDashboard.putString(gearPos, gearPos2);
   }

     //This function is called periodically during test mode
     
    public void testPeriodic() 
    {
    
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\\
    
    public void elevatorLow()
    {
    	
    	{
    		//camRetract();
    		
    		elevatorMin = limit2.get();
    		elevatorR = encoderElevator.get();
    		
    		if((elevatorMin == true) && (elevatorManual == false))
        	{
        		case2 = 1;
        		
        	}
       
        	switch(case2)
        	{
        		case 1:
        		{
        			while((elevatorMin == true) && (elevatorManual == false))
            		{
        				elevatorMin = limit2.get();
        			
            			canWinch.set(0.67);
            			canWinch.set(0.67);
            		}
        		}
        		
        		case 2:
        		{
        			canWinch.set(0);
        			canWinch2.set(0);
        			break;
        		}
        	}
    	}
    }
    
    public void elevatorHigh()
    {
    	if (joy2.getRawButton(2) == false) {stillPressed7 = false;}
    	
    	if (joy2.getRawButton(2) && (stillPressed7 == false) && (elevatorManual == false))
    	{
    		//camExtend();
    		
    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		elevatorMax = limit1.get();
    		elevatorR = encoderElevator.get();
    		
    		if((elevatorR < 13000) && (elevatorManual == false))
        	{
        		case3 = 1;
       
        	}
        	if((elevatorR >= 13000) && (elevatorManual == false))
        	{
        		case3 = 2;
        	}
        	switch(case3)
        	{
        		case 1:
        		{
        			while((elevatorR < 13000) && (elevatorManual == false))
            		{
        				elevatorMax = limit1.get();
        				elevatorR = encoderElevator.get();
        				
            			canWinch.set(-0.8);
            			canWinch.set(-0.8);
            		}
        		}
        		
        		case 2:
        		{
        			
        			
        			while((elevatorMax == true) && (elevatorManual == false))
        			{
        				elevatorMax = limit1.get();
        				
        				canWinch.set(-0.3);
        				canWinch2.set(-0.3);
        			}
        		}
        		
        		case 3:
        		{
        			canWinch.set(0);
        			canWinch2.set(0);
        			break;
        		}
        	}
    	}
    }
    
    public void elevatorOneTote()
    {

    	if (joy2.getRawButton(4) == false) {stillPressed6 = false;}
    	
    	if (joy2.getRawButton(4) && (stillPressed6 == false))
    	{
    		oneTote.start();
    		
    		//camExtend();
    		
    	}
    }
    
    public void camFullManual()
    {
    	if(camMode == 2)
    	{
    		if(joy.getRawButton(8) == false)
    		{
    			talKicker.set(0);
    		}
    	
    		if(joy.getRawButton(8) == true)
    		{
    			talKicker.set(-1);
    		}

    	}
    }
    
    public void pneumatics()
    
    {
    	//Gear Shifting [Right Thumbstick Button]
    	
    	if (joy.getRawButton(2) == false) {stillPressed = false;}
    	
    	if (joy.getRawButton(2) && (stillPressed == false))
    	{	
    		if (gearShift.get() == DoubleSolenoid.Value.kForward)
    			{
    			gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed=true;
    			}
    		else
    		{
    			gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed=true;
    		}
    	}
    	
    	//Arms Toggle [LB] Controller 1
    	
    	if (joy.getRawButton(5) == false) {stillPressed2 = false;}
    	
    	if (joy.getRawButton(5) && (stillPressed2 == false))
    	{
    		if ((leftArm.get() == DoubleSolenoid.Value.kForward) && (rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				leftArm.set(DoubleSolenoid.Value.kReverse);  
   				rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed2=true;
   			}
    		else 
    		{
    			leftArm.set(DoubleSolenoid.Value.kForward);
    			rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed2=true;
    		}
    	}
    	
    	//Arm Toggle [LB] Controller 2
    	if (joy2.getRawButton(5) == false) {stillPressed4 = false;}
		
    	if (joy2.getRawButton(5) && (stillPressed4 == false))
    	{
    		if ((leftArm.get() == DoubleSolenoid.Value.kForward) && (rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				leftArm.set(DoubleSolenoid.Value.kReverse);  
   				rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed4=true;
   			}
    		else 
    		{
    			leftArm.set(DoubleSolenoid.Value.kForward);
    			rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed4=true;
    		}
    	}
    	
    	//Stinger [RB]
    	
    		if (joy.getRawButton(1) == false) {stillPressed3 = false;}
    		
    		if (joy.getRawButton(1) && (stillPressed3 == false))
    		{
    			if (flipper.get() == DoubleSolenoid.Value.kForward)
    			{
    				flipper.set(DoubleSolenoid.Value.kReverse);  		
    				stillPressed3=true;
    			}
    			else
    			{
    				flipper.set(DoubleSolenoid.Value.kForward);
    				stillPressed3=true;
    			}
    		}	
    }
       
    public void camExtend()
    {
    	potDegrees = pot1.getVoltage();
		
    	if(potDegrees < 4.622)
        {
        	case2 = 1;
        		
        }
        	
        switch(case2)
        {
        	case 1:
        	{
        		while(potDegrees < 4.622)
           		{
        			potDegrees = pot1.getVoltage();
        			
           			talKicker.set(1);
           		}
        	}
        		
        	case 2:
        	{
        		talKicker.set(0);
        		break;
        	}
    	}
    }
    
    public void camRetract()
    {
    	potDegrees = pot1.getVoltage();
    		
    	if(potDegrees < 4.622)
        {
        	case2 = 1;
        		
        }
        	
        switch(case2)
        {
        	case 1:
        	{
        		while(potDegrees < 4.622)
           		{
        			potDegrees = pot1.getVoltage();
        			
           			talKicker.set(1);
           		}
        	}
        		
        	case 2:
        	{
        		talKicker.set(0);
        		break;
        	}
    	}
    }
        
    public void ArcadeDrive()
    {
    	q = (joy.getRawAxis(4));
    	
    	leftThumb = -(joy.getRawAxis(1));
    	
    	rightThumb = q;
     	
    	deadZ = 0.13;
    	
    	turnRad = 0.9;
    	
    	//If left thumbstick is still
    	
    	if((leftThumb < deadZ) && (leftThumb > -deadZ))
    	{
    		canFL.set(-rightThumb);
    		canBL.set(-rightThumb);
    		
    		canBR.set(-rightThumb);
    		canFR.set(-rightThumb);
    	}
    	
    	//If right thumbstick is still
    	
    	if((rightThumb < deadZ) && (rightThumb > -deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If both thumbsticks are positive
    	
    	if((leftThumb > deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb - (rightThumb * turnRad));
    		canFR.set(leftThumb - (rightThumb * turnRad));
    	}
    	
    	//If left thumbstick is positive and right thumbstick is negative
    	
    	if((leftThumb > deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(-(leftThumb + (rightThumb * turnRad)));
    		canBL.set(-(leftThumb + (rightThumb * turnRad)));
		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is positive
    	
    	if((leftThumb < -deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb + (rightThumb * turnRad));
    		canFR.set(leftThumb + (rightThumb * turnRad));
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if((leftThumb < -deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(-(leftThumb - (rightThumb * turnRad)));
    		canBL.set(-(leftThumb - (rightThumb * turnRad)));
    		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}

    }
       
    public void ArmMotors()
    {
    	//Arm motors
    	
		leftThumb2=(joy2.getRawAxis(1));
    	rightThumb2=(joy2.getRawAxis(4));
    	
    	deadZ2 = 0.17;
    	
    	//If left thumbstick is still

    	if((leftThumb2>-deadZ2) && (leftThumb2<deadZ2)) 
    	{
    		talArmLeft.set(-(rightThumb2));

    		talArmRight.set(-(rightThumb2));
    	}

    	//If right thumbstick is still

    	if((rightThumb2>-deadZ2) && (rightThumb2<deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-leftThumb2);
    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if((leftThumb2>deadZ2) && (rightThumb2>deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2 - (rightThumb2 * 0.9));

    		talArmRight.set(-(leftThumb2));
    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if((leftThumb2>deadZ2) && (rightThumb2<-deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-(leftThumb2 + (rightThumb2 * 0.9)));
    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if((leftThumb2<-deadZ2) && (rightThumb2>deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2 + (rightThumb2 * 0.9));

    		talArmRight.set(-(leftThumb2));
    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((leftThumb2<-deadZ2) && (rightThumb2<-deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-(leftThumb2 - (rightThumb2 * 0.9))); 	
    	}
    }
        
    public void Elevator()
    {
    	
    	//Elevator Motors [Y = Up B = Down]
    	
    	elevatorMin = limit2.get();
    	elevatorMax = limit1.get();
    
     		if((joy2.getRawButton(7) == true) && (joy2.getRawButton(8) == true))
        	{
     			elevatorManual = false;
     			
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawButton(7) == false) && (joy2.getRawButton(8) == false))
        	{
        		elevatorManual = false;
        		
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawButton(7) == true) && (joy2.getRawButton(8) == false) && (elevatorMin == true))
        	{

        		elevatorManual = true;
        		
        		canWinch.set(0.5);
        		canWinch2.set(0.5);
        	}
        	
        	if((joy2.getRawButton(7) == false) && (joy2.getRawButton(8) == true) && (elevatorMax == true))
        	{

        		elevatorManual = true;
        		
        		if(elevatorR <= 12000)
        		{
        			canWinch.set(-0.5);
            		canWinch2.set(-0.5);
            		
        		}	
        		
        		if(elevatorR > 12000)
        		{
        			canWinch.set(-0.3);
        			canWinch2.set(-0.3);
        		}
        }
    	
    	if(joy.getRawButton(7) == true)
    	{
    		encoderElevator.reset();
    	}
    }
 
    class setPointOne implements Runnable
    {
    	public void run()
    	{
    		
    		leftArm.set(DoubleSolenoid.Value.kForward);
    		rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		elevatorR = encoderElevator.get();
    		
    		if((elevatorR < 10447) && (elevatorManual == false))
        	{
        		case1 = 1;
        		
        	}
        	
        	if((elevatorR > 10447) && (elevatorManual == false))
        	{
        		case1 = 3;
        	}
        
        	switch(case1)
        	{
        		case 1:
        		{
        			while((elevatorR < 10447) && (elevatorManual == false))
            		{
        				elevatorR = encoderElevator.get();
        				
            			canWinch.set(-0.8);
            			canWinch.set(-0.8);
            		}
        		}
        		
        		case 2:
        		{
        			canWinch.set(0);
        			canWinch2.set(0);
        			break;
        		}
        		
        		case 3:
        		{
        			while((elevatorR > 10447) && (elevatorManual == false))
            		{
        				elevatorR = encoderElevator.get();
        				
            			canWinch.set(0.8);
            			canWinch.set(0.8);
            		}
        		}
        		
        		case 4:
        		{
        			canWinch.set(0);
        			canWinch2.set(0);
        			break;
        		}
        	}
    	}
    }
    
    class drivetrain implements Runnable
    {
    	public void run()
    	{
    		while(true)
    		{
    			ArcadeDrive();
    			try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
}
