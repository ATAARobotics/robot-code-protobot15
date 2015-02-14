
/**
 * 		BINDS: 
 * 		
 * 		Joystick 1:
 * 
 * 		A = Switch Gears
 * 		Y = Winch Up
 * 		B = Winch Down
 * 
 * 		Left Thumbstick - Forward/Backward
 * 		Right Thumbstick - Turn Left/Right
 * 
 * 		LB = Arms Toggle
 * 		RB = Flipper
 *		
 *		Triggers = Kicker
 *		Left Stick = Drive F/R
 *		Right Stck = Drive Turning
 *
 *		Joystick 2:
 *
 *		LB = Arms Toggle
 *		
 *		Triggers = Kicker
 *
 *		Left Stick = Arm Motors In/Out
 *		Right Stick = Arm Motors L/R
 *
 */


package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;


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
    CANTalon canKicker;
    CANTalon canArmLeft;
    CANTalon canArmRight;
    
    Encoder encoderL;
	Encoder encoderR;
	
	Potentiometer pot1;
	
	Compressor comp;
    DoubleSolenoid gearShift;
    DoubleSolenoid armChange;
    DoubleSolenoid flipper;
    
    double leftThumb,rightThumb;
    double leftThumb2,rightThumb2;
    double leftTrig,rightTrig;
    double leftTrig2,rightTrig2;
    double degrees;
	
    boolean stillPressed;
    boolean stillPressed2;
    boolean stillPressed3;
    boolean stillPressed4;
	
	int stage;
	int leftR;
	int rightR;
     
    public void robotInit() {
    canFL = new CANTalon(0);
	canBL = new CANTalon(1);
	canFR = new CANTalon(2);
    canBR = new CANTalon(3);
    canWinch = new CANTalon(4);
    canKicker = new CANTalon(5);
    
    joy = new Joystick(0);
    joy2 = new Joystick(1);
    
    comp  = new Compressor(0);
    comp.setClosedLoopControl(true);
    
    pot1 = new AnalogPotentiometer(0, 300, 0);
    
    armChange = new DoubleSolenoid(2, 3);
    armChange.set(DoubleSolenoid.Value.kForward);
    gearShift = new DoubleSolenoid(0, 1);
    gearShift.set(DoubleSolenoid.Value.kForward);
    flipper = new DoubleSolenoid(4,5);
    flipper.set(DoubleSolenoid.Value.kForward);
    
    encoderR = new Encoder(1, 2, true, EncodingType.k4X);
	encoderL = new Encoder(3, 4, true, EncodingType.k4X);
	encoderL.reset();
	encoderR.reset();
	
	
    
    stage = 0;
    }

    void right (){
    	if((leftR < 770) && (rightR < 770)){
    		
			canFL.set(-1);
			canBL.set(-1);
			canFR.set(-1);
			canBR.set(-1);
    	
		}
		else {
			
			canFL.set(0);
        	canBL.set(0);
        	canFR.set(0);
        	canBR.set(0);
        	encoderL.reset();
    		encoderR.reset();
        	
		}
    }
    
    void left (){
    	if((leftR < 770) && (rightR < 770)){
    		
			canFL.set(1);
			canBL.set(1);
			canFR.set(1);
			canBR.set(1);
    	
		}
		else {
			
			canFL.set(0);
        	canBL.set(0);
        	canFR.set(0);
        	canBR.set(0);
        	encoderL.reset();
    		encoderR.reset();
        	
		}
    }
    
    void inverse (Boolean left){
    	if (left){
    		if((leftR < 1540) && (rightR < 1540)){
    		
			canFL.set(1);
			canBL.set(1);
			canFR.set(1);
			canBR.set(1);
    	
		}
		else {
			
			canFL.set(0);
        	canBL.set(0);
        	canFR.set(0);
        	canBR.set(0);
        	encoderL.reset();
    		encoderR.reset();
        	
		}
    }
    	else{
    		if((leftR < 1540) && (rightR < 1540)){
        		
    			canFL.set(-1);
    			canBL.set(-1);
    			canFR.set(-1);
    			canBR.set(-1);
        	
    		}
    		else {
    			
    			canFL.set(0);
            	canBL.set(0);
            	canFR.set(0);
            	canBR.set(0);
            	encoderL.reset();
        		encoderR.reset();
            	
    		}
    	}
    }
    
    void custom (int turn, double speed, Boolean left){
    	if((leftR < turn) && (rightR < turn)){
    		if(left){
    			canFL.set(speed);
    			canBL.set(speed);
    			canFR.set(speed);
    			canBR.set(speed);
    		}
    		else{
    			canFL.set(-speed);
    			canBL.set(-speed);
    			canFR.set(-speed);
    			canBR.set(-speed);
    		}
    	}
    	else{
    	canFL.set(0);
    	canBL.set(0);
    	canFR.set(0);
    	canBR.set(0);
    	encoderL.reset();
		encoderR.reset();
    	}
    }
    
    void drive (int time, double speed, Boolean forward){
    	if((leftR < time) && (-rightR < time)){
    		if(forward){
    			canFL.set(1);
        		canBL.set(1);
        		canFR.set(-1);
        		canBR.set(-1);
    		}
    		else{
    			canFL.set(-1);
        		canBL.set(-1);
        		canFR.set(1);
        		canBR.set(1);
    		}
    		
    	}
    	else{
    		canFL.set(0);
        	canBL.set(0);
        	canFR.set(0);
        	canBR.set(0);
        	encoderL.reset();
    		encoderR.reset();
    	}
    }
    
     //This function is called periodically [20 ms] during autonomous
     
    public void autonomousPeriodic() {
    	
 
		leftR = encoderL.get();
    	rightR = encoderR.get();
    	degrees = pot1.get();
    	
    	if(stage == 0){
    		
    		if(degrees > 70){
    			
    			stage = 1;
    			
    			encoderR.reset();
    			encoderL.reset();
    		}
    		
    	}
    	
    	if(stage == 1){
    		leftR = encoderL.get();
        	rightR = encoderR.get();
        	drive(16000, 1, true);
        	stage = 2;
    	
    	}
    	
    	if(stage == 2){
    		    		
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		right();
    		stage = 3;
    		
    	}
 
    	if(stage == 3){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		drive(7000, 1, true);
    		stage = 4;
    	}
    
    	if(stage == 4) {
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		right();
    		stage = 5;
    	}
    	
    	if(stage == 5){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		drive(4000, 1, true);
    		stage = 6;
    	}
    	if(stage == 6){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		left();
    		stage = 7;
    	}
    	if(stage == 7){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		drive(7000, 1, true);
    		stage = 8;
    	}
    	if(stage == 8){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		left();
    		stage = 9;
    	}
    	if(stage == 9){
    		
    		leftR = encoderL.get();
        	rightR = encoderR.get();
    		
    		drive(7000, 1, true);
    		stage = 10;
    	}
    }
    

    
    
     //This function is called periodically [20 ms] during operator control
    
    public void teleopPeriodic() {
    	
    	leftThumb=(-(joy.getRawAxis(1)));
    	rightThumb=(joy.getRawAxis(4));
    
    	arcadeMode();
    	
    	//Winch Motor [Y = Up B = Down]
    	
    	if(joy.getRawButton(4) == true){
    		
    		canWinch.set(2);
    	}
    	
    	if(joy.getRawButton(2) == true){
    		
    		canWinch.set(-2);
    	}
    	
    	//Kicker [Triggers] Controller 1
    	
    	leftTrig = (joy.getRawAxis(2));
    	rightTrig = (joy.getRawAxis(3));
    	
    	if((leftTrig > 0) && (rightTrig == 0)){
    		
    		canKicker.set(leftTrig);
    	}
    	
    	if((rightTrig > 0) && (leftTrig == 0)){
    		
    		canKicker.set(-rightTrig);
    	}
    	
    	//Kicker [Triggers] Controller 2
    	
    	leftTrig2 = (joy2.getRawAxis(2));
    	rightTrig2 = (joy2.getRawAxis(3));
    	
    	if((leftTrig2 > 0) && (rightTrig2 == 0)){
    		
    		canKicker.set(leftTrig2);
    	}
    	
    	if((rightTrig2 > 0) && (leftTrig2 == 0)){
    		
    		canKicker.set(rightTrig2);
    	}
    	
    	//Gear Shifting [Right Thumbstick Button]
    	
    	if (joy.getRawButton(8) == false) {stillPressed = false;}
    	
    	if (joy.getRawButton(8) && (stillPressed == false)){
    		
    		if (gearShift.get() == DoubleSolenoid.Value.kForward)
    			{
    			gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed=true;
    			}
    		else{
    			gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed=true;
    		}
    	}
    	
    	//Arms Toggle [LB] Controller 1
    	
    	if (joy.getRawButton(5) == false) {stillPressed2 = false;}
    		
    	if (joy.getRawButton(5) && (stillPressed2 == false)){
    		
    		if (armChange.get() == DoubleSolenoid.Value.kForward)
   				{
   				armChange.set(DoubleSolenoid.Value.kReverse);  		
   				stillPressed2=true;
   				}
    		else {
    			armChange.set(DoubleSolenoid.Value.kForward);
    			stillPressed2=true;
    		}
    	}
    	
    	//Arm Toggle [LB] Controller 2
    	
    	if (joy2.getRawButton(5) == false) {stillPressed4 = false;}
		
    	if (joy2.getRawButton(5) && (stillPressed4 == false)){
    		
    		if (armChange.get() == DoubleSolenoid.Value.kForward)
   				{
   				armChange.set(DoubleSolenoid.Value.kReverse);  		
   				stillPressed4=true;
   				}
    		else {
    			armChange.set(DoubleSolenoid.Value.kForward);
    			stillPressed4=true;
    		}
    	}
    	
    	//Flipper [RB]
    	
    		if (joy.getRawButton(6) == false) {stillPressed3 = false;}
    		
    		if (joy.getRawButton(6) && (stillPressed3 == false)){
    		
    			if (flipper.get() == DoubleSolenoid.Value.kForward)
    				{
    				flipper.set(DoubleSolenoid.Value.kReverse);  		
    				stillPressed3=true;
    				}
    			else{
    				flipper.set(DoubleSolenoid.Value.kForward);
    				stillPressed3=true;
    		}
    	}	
    		
    	//Arms Motors [Joysticks Controller 2]
    		
    		leftThumb2=(-(joy2.getRawAxis(1)));
        	rightThumb2=(joy2.getRawAxis(4));
 
    	armMotors();
   }
    
    
    
     //This function is called periodically during test mode
     
    public void testPeriodic() {
    
    }
    
    public void arcadeMode() {
    	
    	//If left thumbstick is still

    	if((leftThumb>-0.1) && (leftThumb<0.1)) {

    	canFL.set(-(rightThumb));
    	canBL.set(-(rightThumb));
 
    	canFR.set(-(rightThumb));
    	canBR.set(-(rightThumb));

    	}

    	//If right thumbstick is still

    	if((rightThumb>-0.1) && (rightThumb<0.1)) {

    	canFL.set(leftThumb);
    	canBL.set(leftThumb);
    	    
    	canFR.set(-leftThumb);
    	canBR.set(-leftThumb);

    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if((leftThumb>0.1) && (rightThumb>0.1)) {

    	canFL.set(leftThumb - (rightThumb * 0.9));
    	canBL.set(leftThumb - (rightThumb * 0.9));
    	    
    	canFR.set(-(leftThumb));
    	canBR.set(-(leftThumb));

    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if((leftThumb>0.1) && (rightThumb<-0.1)) {

    	canFL.set(leftThumb);
    	canBL.set(leftThumb);
    	    
    	canFR.set(-(leftThumb + (rightThumb * 0.9)));
    	canBR.set(-(leftThumb + (rightThumb * 0.9)));

    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if((leftThumb<-0.1) && (rightThumb>0.1)) {

    	canFL.set(leftThumb + (rightThumb * 0.9));
    	canBL.set(leftThumb + (rightThumb * 0.9));
    	    
    	canFR.set(-(leftThumb));
    	canBR.set(-(leftThumb));

    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((leftThumb<-0.1) && (rightThumb<-0.1)) {

    	canFL.set(leftThumb);
    	canBL.set(leftThumb);
    	    
    	canFR.set(-(leftThumb - (rightThumb * 0.9)));
    	canBR.set(-(leftThumb - (rightThumb * 0.9)));
    	
    	}
    	
   }


	public void armMotors() {	
	
	//If left thumbstick is still

	if((leftThumb2>-0.1) && (leftThumb2<0.1)) {

	canFL.set(-(rightThumb2));
	canBL.set(-(rightThumb2));
	    
	canFR.set(-(rightThumb2));
	canBR.set(-(rightThumb2));

	}

	//If right thumbstick is still

	if((rightThumb2>-0.1) && (rightThumb2<0.1)) {

	canFL.set(leftThumb2);
	canBL.set(leftThumb2);
	    
	canFR.set(-leftThumb2);
	canBR.set(-leftThumb2);

	}

	//If left thumbstick is positive and right thumbstick is positive

	if((leftThumb2>0.1) && (rightThumb2>0.1)) {

	canFL.set(leftThumb2 - (rightThumb2 * 0.9));
	canBL.set(leftThumb2 - (rightThumb2 * 0.9));
	    
	canFR.set(-(leftThumb2));
	canBR.set(-(leftThumb2));

	}

	//If left thumbstick is positive and right thumbstick is negative

	if((leftThumb2>0.1) && (rightThumb2<-0.1)) {

	canFL.set(leftThumb2);
	canBL.set(leftThumb2);
	    
	canFR.set(-(leftThumb2 + (rightThumb2 * 0.9)));
	canBR.set(-(leftThumb2 + (rightThumb2 * 0.9)));

	}

	//If left thumbstick is negative and right thumbstick is positive

	if((leftThumb2<-0.1) && (rightThumb2>0.1)) {

	canFL.set(leftThumb2 + (rightThumb2 * 0.9));
	canBL.set(leftThumb2 + (rightThumb2 * 0.9));
	    
	canFR.set(-(leftThumb2));
	canBR.set(-(leftThumb2));

	}

	//If left thumbstick is negative and right thumbstick is negative

	if((leftThumb2<-0.1) && (rightThumb2<-0.1)) {

	canFL.set(leftThumb2);
	canBL.set(leftThumb2);
	    
	canFR.set(-(leftThumb2 - (rightThumb2 * 0.9)));
	canBR.set(-(leftThumb2 - (rightThumb2 * 0.9)));
	
	}
}}
