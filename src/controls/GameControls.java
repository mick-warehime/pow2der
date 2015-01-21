package controls;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;

import commands.InteractCommand;
import commands.KeyboardInputListener;
import commands.MoveCommand;


//Handles keybindings and player inputs
public class GameControls {

	private int[] mousePos = new int[2];
	private InputProvider keyboardInputProvider;
	
	public GameControls(GameContainer gc){
		initializeKeyBindings(gc);
	}

	public void setMousePosition(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		mousePos[0] = mouseX;
		mousePos[1] = mouseY;
	}
	public int[] getMousePos(){
		return mousePos;
	}

	public void addPlayerListener(KeyboardInputListener listener) {
		// TODO Auto-generated method stub
		keyboardInputProvider.addListener(listener);
	}
	
	private void initializeKeyBindings(GameContainer gc){
		Controller[] controllers=ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for (Controller c : controllers){			
			if(c.getName().equals("Wiimote (18-2a-7b-44-b0-bc)")){
				joystick jstick = new joystick(c.getType());
				System.out.println(jstick.getX_LeftJoystick_Value());
			}
		}
		
		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		keyboardInputProvider = new InputProvider(gc.getInput());
		//The listener is linked to the provider		

		//Define action commands for provider
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand('x', -1);
		Command moveRight = new MoveCommand('x', 1);
		Command moveUp = new MoveCommand('y', -1);
		Command moveDown = new MoveCommand('y', 1);
		Command interact = new InteractCommand();
		
		

		//Bind commands to keyboard keys
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_W), moveUp);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_F), interact);

		
		//Bind commands to controller keys
		keyboardInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		keyboardInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		keyboardInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		keyboardInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);
//		keyboardInputProvider.bindCommand(new ControllerButtonControl(0,1),);
		keyboardInputProvider.bindCommand(new ControllerButtonControl(0,2), interact);
		//The left bumper is 5, the right is 6
	}
}