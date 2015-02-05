package controls;


import items.Inventory;
import gameobjects.Interactive;
import menus.InventoryMenu;
import menus.MainMenu;
import menus.Menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import commands.MenuActivateSelectionCommand;
import commands.MenuChangeSelectionCommand;
import commands.InteractCommand;
import commands.MoveCommand;
import commands.MenuOpenCommand;


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
	private InputProvider avatarInputProvider; //Controls pertaining to player's avatar
	private InputProvider menuInputProvider;

	public GameControls(GameContainer gc){
		initializeAvatarKeyBindings(gc);
		initializeMenuKeyBindings(gc);

	}

	public void setMousePosition(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		mousePos[0] = mouseX;
		mousePos[1] = mouseY;
	}
	
	public int[] getMousePos(){
		return mousePos;
	}

	public void addAvatarInputProviderListener(InputProviderListener listener) {
	
		avatarInputProvider.addListener(listener);
	}
	
	public void addMenuInputProviderListener(InputProviderListener listener) {
		
		menuInputProvider.addListener(listener);
	}

	private void initializeMenuKeyBindings(GameContainer gc){


		//This translates keyboard/mouse inputs into commands, for the appropriate listeners

		menuInputProvider = new InputProvider(gc.getInput());
		
		//Define commands
		Command toggleMainMenu = new MenuOpenCommand(new MainMenu(100,240));
		Command toggleInventoryMenu = new MenuOpenCommand(new InventoryMenu(300,240));
		Command menuDown = new MenuChangeSelectionCommand('y',1);
		Command menuUp = new MenuChangeSelectionCommand('y',-1);
		Command activate = new MenuActivateSelectionCommand();
		
		//Bind them to keys
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_ESCAPE), toggleMainMenu);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_I), toggleInventoryMenu);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_W), menuUp);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_S), menuDown);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_ENTER), activate);
	}

	private void initializeAvatarKeyBindings(GameContainer gc){
		avatarInputProvider = new InputProvider(gc.getInput());

		//Define action commands for provider
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand('x', -1);
		Command moveRight = new MoveCommand('x', 1);
		Command moveUp = new MoveCommand('y', -1);
		Command moveDown = new MoveCommand('y', 1);
		Command toggle = new InteractCommand(Interactive.INTERACTION_TOGGLE);
		Command pickup = new InteractCommand(Interactive.INTERACTION_PICKUP);
		

		//Bind commands to keyboard keys
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_W), moveUp);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_E), toggle);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_G), pickup);
		
		
		//Bind commands to controller keys
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);	
		avatarInputProvider.bindCommand(new ControllerButtonControl(0,GameControls.WIIMOTE_A), toggle);
	}

	
}
