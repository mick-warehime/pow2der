package controls;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class WiimoteJoysticks {

	private Joystick joystick = null;
	
	public WiimoteJoysticks(){
		
		Controller[] controllers; controllers=ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for (Controller c : controllers){
			if(c.getName().contains("Wiimote")){
				joystick = new Joystick(c.getType());
			}
		}
	}
	
	public float RightXValue(){
		if (joystick == null){return 0f;}
		return joystick.getX_RightJoystick_Value();
	}
	public float RightYValue(){
		if (joystick == null){return 0f;}
		return joystick.getY_RightJoystick_Value();
	}
	public float LeftXValue(){
		if (joystick == null){return 0f;}
		return joystick.getX_LeftJoystick_Value();
	}
	public float LeftYValue(){
		if (joystick == null){return 0f;}
		return joystick.getY_LeftJoystick_Value();
	}
	
	
}
