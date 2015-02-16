
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
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
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
    boolean elevatorMax;
    boolean elevatorMin;
    boolean elevatorManual;
    boolean fullDown;
    boolean camRetract;
    boolean camExtend;
    boolean camCancel1;
	
	int stage;
	int camMode;
	int leftR, rightR, elevatorR;
	int range1;
	int range2;
	int elevatorRange;
    
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
    
    elevatorManual = true;
    elevatorRange = 15900;
    camMode = 1;
    
    ElevatorEncoder = "Encoder";
    camPot = "Cam Potentiometer";
    
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
    	
    	ArcadeDrive();
    	
    	ArmMotors();
    	
    	Elevator();
    	
    	camManualSetpoint();
    	
    	pneumatics();
    	
    	camFullManual();
    	
    	
    	if (joy2.getRawButton(6) == false) {stillPressed7 = false;}
    	
    	if (joy2.getRawButton(6) && (stillPressed7 == false))
    	{	
    		if (camMode == 1)
    			{
    			camMode = 2;  		
    			stillPressed=true;
    			}
    		else
    		{
    			camMode = 1;
    			stillPressed=true;
    		}
    	}
 //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    	
    	//SmartDashboard Crap
    	
    	potDegrees = pot1.getVoltage();
    	
    	elevatorR = encoderElevator.get();
    	
    	SmartDashboard.putString("Jimmy is damn cool, fuck yea!!!!", " ");
    	
    	SmartDashboard.putNumber(ElevatorEncoder, elevatorR);
    	
    	SmartDashboard.putNumber(camPot, potDegrees);
   }

     //This function is called periodically during test mode
     
    public void testPeriodic() 
    {
    
    }
    
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    public void elevatorSetpoints()
    {

    	//Going from 1 tote setpoint to lowest setpoint
    	
    	if (joy2.getRawButton(1) == false) {stillPressed6 = false;}
    	
    	
    	if (joy2.getRawButton(1) && (stillPressed6 == false))
    	{
    		stillPressed6 = true;
    		fullDown = true;
    		camRetract = true;
    		
    		if((potDegrees > 10) && (camRetract == true))
    		{
    			talKicker.set(1);
    			camMode = 3;
    		}
    		
    		if((elevatorR < range1) && (fullDown == true))
    		{
    			elevatorManual = false;
        		
        		canWinch.set(1);
        		canWinch2.set(1);
    		}
    	}
    	
    	if(elevatorR < 240)
    	{
    		fullDown = false;
    		elevatorManual = true;
    	}
    	
    	if(potDegrees < 10)
    	{
    		camRetract = false;
    	}
    }
    public void camFullManual()
    {
    	if(camMode == 2)
    	{
    		if((joy.getRawButton(7) == true) && (joy.getRawButton(8) == true))
    		{
    			talKicker.set(0);
    		}
    	
    		if((joy.getRawButton(7) == false) && (joy.getRawButton(8) == false))
    		{
    			talKicker.set(0);
    		}
    	
    		if((joy.getRawButton(7) == true) && (joy.getRawButton(8) == false))
    		{
    			talKicker.set(1);
    		}
    	
    		if((joy.getRawButton(7) == false) && (joy.getRawButton(8) == true))
    		{
    			talKicker.set(-1);
    		}
    	}
    }
    
    public void camElevator()
    {
    	
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
    
    public void camManualSetpoint()
    {
        if (joy2.getRawButton(2) == false) {stillPressed5 = false;}
       
       	if(camMode == 1)
       	{
       		if ((joy2.getRawButton(2) == true) && (stillPressed5 == false))
       		{
       			stillPressed5 = true;
       			camCancel1 = true;
       			
       			if (pot1.getVoltage() <= 2.4)
       			{
       				talKicker.set(-1);
       			}
       			if(pot1.getVoltage() > 2.4)
       			{
       				talKicker.set(1);
       			}
       		}
       	}
       	
       	if((pot1.getVoltage() < 4.624) && (pot1.getVoltage() > 0.177))
       	{
       		camCancel1 = false;
       	}

        if((pot1.getVoltage() > 4.624) && (camCancel1 == false))
    	{
   			talKicker.set(0);
   		}
       	if((pot1.getVoltage() < 0.177) && (camCancel1 == false))
   		{
   			talKicker.set(0);
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
    	//Arms Motors [Thumbsticks Controller 2]
		
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
    	
    	elevatorMax = limit1.get();
    	elevatorMin = limit2.get();
    
    	if(elevatorManual = true)
    	{
    		if((joy2.getRawButton(5) == true) && (joy2.getRawButton(6) == true))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawButton(5) == false) && (joy2.getRawButton(6) == false))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawButton(7) == true) && (joy2.getRawButton(8) == false) && (elevatorMin == true))
        	{
        		canWinch.set(0.3);
        		canWinch2.set(0.3);
        	}
        	
        	if((joy2.getRawButton(7) == false) && (joy2.getRawButton(8) == true) && (elevatorMax == true))
        	{
        		canWinch.set(-0.3);
        		canWinch2.set(-0.3);
        	}
        }
    }
}
