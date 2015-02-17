package edu.aci.ata.ACIlibj;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Drivetrain 
{

	private double turnRad;
	private double deadZ;
	public CANTalon canFL;
	public CANTalon canBL;
	public CANTalon canFR;
	public CANTalon canBR;
	private double leftThumb,rightThumb,rightThumb2;
	private double p,q,z;
	private Joystick drivesticks;
	
	public Drivetrain(CANTalon FL, CANTalon BL, CANTalon FR, CANTalon BR, double turnRad, double deadZ) 
	{
		this.deadZ = deadZ;
		this.turnRad = turnRad;
		this.canFL = FL;
		this.canBL = BL;
		this.canFR = FR;
		this.canBR = BR;
	
	}

	public void GetArcade()
	{
		p = (-(drivesticks.getRawAxis(1)));
    	q = (drivesticks.getRawAxis(4));
    	z = (drivesticks.getRawAxis(3));
    	
    	leftThumb = (Math.sqrt(p));
    	rightThumb = (Math.sqrt(q));
    	rightThumb2 = (Math.sqrt(z));
    	
		//If left thumbstick is still

    	if((leftThumb>-deadZ) && (leftThumb<deadZ)) {

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

    	canFL.set(leftThumb - (rightThumb * turnRad));
    	canBL.set(leftThumb - (rightThumb * turnRad));
    	    
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

    	canFL.set(leftThumb + (rightThumb * turnRad));
    	canBL.set(leftThumb + (rightThumb * turnRad));
    	    
    	canFR.set(-(leftThumb));
    	canBR.set(-(leftThumb));

    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((leftThumb<-0.1) && (rightThumb<-0.1)) {

    	canFL.set(leftThumb);
    	canBL.set(leftThumb);
    	    
    	canFR.set(-(leftThumb - (rightThumb * turnRad)));
    	canBR.set(-(leftThumb - (rightThumb * turnRad)));
    	
    	}

	}
	
	public void GetTank()
	{
	    canFL.set(leftThumb);
	   	canBL.set(leftThumb);
	    	    
	   	canFR.set(-rightThumb2);
	   	canBR.set(-rightThumb2);
		
	}
}
