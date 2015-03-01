
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

import java.util.Timer;
import java.util.TimerTask;

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
    
    Encoder encoderL;
	Encoder encoderR;
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
    String autoMode;
    
    
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
	boolean gotoSpot, gotoSpot2, gotoSpot3;
	boolean gotoCam1 = true;
	boolean gotoCam2 = false;
	boolean camChange = false;
	boolean camActivate = false;
	
	int camMode;
	int leftR, rightR, elevatorR;
	int elevatorRange;
	int case1, case2, case3;
	
    public void robotInit() {
    new Timer().schedule(new TimerTask(){public void run(){camSetpoint();}}, 20);
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
    encoderR = new Encoder(4, 5, true, EncodingType.k4X);
	encoderL = new Encoder(2, 3, true, EncodingType.k4X);
	encoderL.reset();
	encoderR.reset();
    encoderElevator.reset();
    autoMode = "1";
    }

    
    
     //This function is called periodically [20 ms] during autonomous
    void getEncoders(){
    	leftR = encoderR.get();
    	rightR = encoderL.get();
    	elevatorR = encoderElevator.get();
    }
    public void autonomousPeriodic()
    {
    	for(int i = 0; i < 1; i++)
    	{ 	
    		new Timer().schedule(new TimerTask(){public void run(){getEncoders();}}, 20);
    		if(autoMode == "1")
    		{
    		moveToZone();	
    		}
    		
    		if(autoMode == "2")
    		{
    			
    		}
    		
    		if(autoMode == "3")
    		{
    			
    		}
    		
    		if(autoMode == "4")
    		{
    			
    		}
    		
    		if(autoMode == "5")
    		{
    			
    		}
    		
    		if(autoMode == "6")
    		{
    			
    		}
    		
    		if(autoMode == "7")
    		{
    			
    		}
    	}
    	
    }

    
     //This function is called periodically [20 ms] during operator control
    
	public void teleopPeriodic() 
    {
    	
    	elevatorR = encoderElevator.get();
    	potDegrees = pot1.getVoltage();
    	elevatorMin = limit2.get();
    	elevatorMax = limit1.get();
    	
    	ArcadeDrive();
    	
    	ArmMotors();
    	
    	Elevator();
    		
    	buttonToggles();
    	
    	camFullManual();
    	
    	camSetpoint();
    	
    	elevatorOneTote();
    	
    	elevatorLow();
    	
    	//elevatorHigh();
    	
    	
    	
    	if(gearShift.get() == DoubleSolenoid.Value.kForward)
    	{
    		gearPos2 = "High Gear";
    	}
    	if(gearShift.get() == DoubleSolenoid.Value.kReverse)
    	{
    		gearPos2 = "Low Gear";
    	}
    	
    	
    	
 //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    	
    	//SmartDashboard
    
    	SmartDashboard.putNumber(ElevatorEncoder, elevatorR);   
    	SmartDashboard.putNumber(camPot, potDegrees); 
    	SmartDashboard.putBoolean(limitHigh, elevatorMax);   
    	SmartDashboard.putBoolean(limitLow, elevatorMin);   
    	SmartDashboard.putString(gearPos, gearPos2);	
    	SmartDashboard.putBoolean("CamActivate", camActivate);
    	SmartDashboard.putBoolean("gotoCam1", gotoCam1);
    	SmartDashboard.putBoolean("camChange", camChange);
   }

     //This function is called periodically during test mode
     
    public void testPeriodic() 
    {
    	
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\\
    
    public void elevatorLow()
    {
    	if (joy2.getRawButton(3) == false) {stillPressed7 = false;}
    	
    	if (joy2.getRawButton(3) && (stillPressed7 == false))
    	{
    		gotoSpot2 = true;
    		gotoCam1 = false;
			gotoCam2 = true;
    		camActivate = true;
    		stillPressed7 = true;
    	}
    	
    	if (gotoSpot2)
    	{

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorMin) && (elevatorR >= 1500))
    		{
    			canWinch.set(0.68);
    			canWinch2.set(0.68);
    		}
    		
    		else if((elevatorMin) && (elevatorR < 1500))
    		{
    			canWinch.set(0.3);
    			canWinch2.set(0.3);
    		}
    		
    		else 
    		{
    			gotoSpot2=false;
   			}
    	}
    }
    
    public void elevatorHigh()
    {
    	if (joy2.getRawButton(2) == false) {stillPressed9 = false;}
    	
    	if (joy2.getRawButton(2) && (stillPressed9 == false))
    	{
    		gotoSpot3 = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;	
    		stillPressed9 = true;
    	}
    	
    	if (gotoSpot3)
    	{

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorMax) && (elevatorR <= 13000))
    		{
    			canWinch.set(-0.8);
    			canWinch2.set(0.8);
    		}
    		else if((elevatorMax) && (elevatorR > 13000))
    		{
    			canWinch.set(-0.33);
    			canWinch2.set(0.33);
    		}
    		else {
    			gotoSpot2=false;
    		}
    	}
    }
    
    public void elevatorOneTote()
    {
    	if (joy2.getRawButton(4) == false) {stillPressed6 = false;}
    	
    	if (joy2.getRawButton(4) && (stillPressed6 == false))
    	{
    		gotoSpot = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;
    		stillPressed6 = true;
    		
    	}
    	
    	if (gotoSpot)
    	{

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if (elevatorR < 10500)
    		{
    			canWinch.set(-0.9);
    			canWinch2.set(-0.9);
    		}
    		else {
    			gotoSpot=false;
    		}
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
    
    public void buttonToggles()
    
    {
    	//Cam Setpoint Toggle
    	
    	if (joy2.getRawButton(1) == false) {stillPressed5 = false;}
    	
    	if (joy2.getRawButton(1) && (stillPressed5 == false))
    	{
    		if (gotoCam1)
			{
				gotoCam1 = false;
			}
    		else if (!gotoCam1)
    		{
    			gotoCam1 = true;
    		}
    		
    		camActivate = true;
    	}
    
    	//Cam Mode Switching [RB]
    	
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
       
    public void camSetpoint()
    {
    	if ((camActivate) && (camMode == 1))
    	{
    		if(gotoCam1)
    		{

        		if (potDegrees < 3)
        		{
        			talKicker.set(-1);
        		}
        		
        		else 
        		{
        			talKicker.set(0);
        			camActivate=false;
        		}
    		}
    		
    		if(!gotoCam1)
    		{

    			if (potDegrees > 2.5)
        		{
        			talKicker.set(1);
        		}
        		
        		else 
        		{
        			talKicker.set(0);
        			camActivate=false;
        		}
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
    		canFL.set(-(leftThumb + (rightThumb * turnRad)));
    		canBL.set(-(leftThumb + (rightThumb * turnRad)));
    		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if((leftThumb < -deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb - (rightThumb * turnRad));
    		canFR.set(leftThumb - (rightThumb * turnRad));
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
    	
     		if((joy2.getRawAxis(3) > 0.09) && (joy2.getRawAxis(2) > 0.09))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
     		
     		if((joy2.getRawAxis(3) < 0.09) && (joy2.getRawAxis(2) < 0.09))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawAxis(3) > 0.09) && (joy2.getRawAxis(2) < 0.09) && (elevatorMax == true))
        	{

        		elevatorManual = true;
        		gotoSpot=false;
        		canWinch.set(-(joy2.getRawAxis(3)));
        		canWinch2.set(-(joy2.getRawAxis(3)));
        	}
        	
        	if((joy2.getRawAxis(3) < 0.1) && (joy2.getRawAxis(2) > 0.1) && (elevatorMin == true))
        	{

        		elevatorManual = true;
        		gotoSpot=false;
        		canWinch.set(joy2.getRawAxis(2));
        		canWinch2.set(joy2.getRawAxis(2));
        }
    	
    	if(joy.getRawButton(7) == true)
    	{
    		encoderElevator.reset();
    	}
    }

    public void moveForward(int distance, double power)
    {
    	while((leftR > -distance) && (rightR < distance))
    	
    		canFL.set(-power);
    		canBL.set(-power);
    		
    		canBR.set(power);
    		canFR.set(power);
    	
    	encoderR.reset();
    	encoderL.reset();
    }
    
    public void moveBackward(int distance, double power)
    {
    	while((leftR < -distance) && (rightR > distance))
    	{
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(-power);
    		canFR.set(-power);
    	}
    	encoderR.reset();
    	encoderL.reset();
    }
    
    
    //----------------------------------------------------------------------------------------------------------------------------------\\
    
    
    public void moveToZone()
    {
    	moveBackward(2000, 1);
    }
    
    }
