package controls;


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
	private InputProvider controlsInputProvider;
	
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
		controlsInputProvider.addListener(listener);
	}
	
	private void initializeKeyBindings(GameContainer gc){
		
		
		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		controlsInputProvider = new InputProvider(gc.getInput());
		//The listener is linked to the provider		

		//Define action commands for provider
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand('x', -1);
		Command moveRight = new MoveCommand('x', 1);
		Command moveUp = new MoveCommand('y', -1);
		Command moveDown = new MoveCommand('y', 1);
		Command interact = new InteractCommand();
		
		

		//Bind commands to keyboard keys
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_W), moveUp);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_F), interact);

		
		//Bind commands to controller keys
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);
//		
		controlsInputProvider.bindCommand(new ControllerButtonControl(0,1), interact);
		//The left bumper is 5, the right is 6
	}
}
