package controls;


import gameobjects.Interactive;

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

	//Wiimote button to integer mappings
	public static final int WIIMOTE_A = 16;
	public static final int WIIMOTE_B = 17;
	public static final int WIIMOTE_X = 18;
	public static final int WIIMOTE_Y = 19;
	public static final int WIIMOTE_DLEFT = 14;
	public static final int WIIMOTE_DRIGHT = 15;
	public static final int WIIMOTE_DUP = 12;
	public static final int WIIMOTE_DDOWN = 13;
	public static final int WIIMOTE_RBUMPER = 21;
	public static final int WIIMOTE_LBUMPER = 20;
	public static final int WIIMOTE_SELECT = 8;
	public static final int WIIMOTE_START = 7;
	public static final int WIIMOTE_HOME = 9;
	
	public Joystick joystick;
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
		Command toggle = new InteractCommand(Interactive.INTERACTION_TOGGLE);
		Command pickup = new InteractCommand(Interactive.INTERACTION_PICKUP);
		
		

		//Bind commands to keyboard keys
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_W), moveUp);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_E), toggle);
		controlsInputProvider.bindCommand(new KeyControl(Input.KEY_G), pickup);

		
		//Bind commands to controller keys
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		controlsInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);	
		controlsInputProvider.bindCommand(new ControllerButtonControl(0,this.WIIMOTE_A), toggle);
		
	}
}
