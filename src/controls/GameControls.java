package controls;


import interfaces.Interactive;
import menus.InventoryMenu;
import menus.MainMenu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

import commands.ActivateAbilityCommand;
import commands.MenuActivateSelectionCommand;
import commands.MenuChangeSelectionCommand;
import commands.InteractCommand;
import commands.MenuToggleCommand;
import commands.MenuOpenCommand;
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
	private int[] mouseScreenPosition = new int[2];
	private InputProvider avatarInputProvider; //Controls pertaining to player's avatar
	private InputProvider menuInputProvider;

	public GameControls(GameContainer gc) throws SlickException{
		initializeAvatarKeyBindings(gc);
		initializeMenuKeyBindings(gc);

	}

	public void setMousePosition(int mouseX, int mouseY) {
		mouseScreenPosition[0] = mouseX;
		mouseScreenPosition[1] = mouseY;
	}
	
	public int[] getMouseScreenPosition(){
		return mouseScreenPosition;
	}

	public void addAvatarInputProviderListener(InputProviderListener listener) {
	
		avatarInputProvider.addListener(listener);
	}
	
	public void addMenuInputProviderListener(InputProviderListener listener) {
		
		menuInputProvider.addListener(listener);
	}

	private void initializeMenuKeyBindings(GameContainer gc) throws SlickException{


		//This translates keyboard/mouse inputs into commands, for the appropriate listeners

		menuInputProvider = new InputProvider(gc.getInput());
		
		//Define commands
		Command toggleMainMenu = new MenuOpenCommand(new MainMenu(100,240));
		Command toggleInventoryMenu = new MenuToggleCommand(new InventoryMenu(300,100));
		Command menuDown = new MenuChangeSelectionCommand('y',1);
		Command menuUp = new MenuChangeSelectionCommand('y',-1);
		Command menuRight = new MenuChangeSelectionCommand('x',1);
		Command menuLeft = new MenuChangeSelectionCommand('x',-1);
		Command activate = new MenuActivateSelectionCommand();
		
		//Bind them to keys
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_ESCAPE), toggleMainMenu);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_I), toggleInventoryMenu);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_W), menuUp);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_S), menuDown);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_A), menuLeft);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_D), menuRight);
		menuInputProvider.bindCommand(new KeyControl(Input.KEY_ENTER), activate);
	}

	private void initializeAvatarKeyBindings(GameContainer gc){
		avatarInputProvider = new InputProvider(gc.getInput());

		//Define action commands for provider
		Command moveLeft = new MoveCommand(new float[] {-1f,0f});		
		Command moveRight = new MoveCommand(new float[] {1f,0f});
		Command moveUp = new MoveCommand(new float[] {0f,-1f});
		Command moveDown = new MoveCommand(new float[] {0f,1f});
		
		Command toggle = new InteractCommand(Interactive.INTERACTION_TOGGLE);
		Command pickup = new InteractCommand(Interactive.INTERACTION_PICKUP);
		Command ability0 = new ActivateAbilityCommand(0);
		Command ability1 = new ActivateAbilityCommand(1);
		Command ability2 = new ActivateAbilityCommand(2);

		//Bind commands to keyboard keys
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_W), moveUp);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_E), toggle);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_G), pickup);
		avatarInputProvider.bindCommand(new KeyControl(Input.KEY_LSHIFT), ability0);
		avatarInputProvider.bindCommand(new MouseButtonControl(Input.MOUSE_LEFT_BUTTON), ability1);
		avatarInputProvider.bindCommand(new MouseButtonControl(Input.MOUSE_RIGHT_BUTTON), ability2);
		
		//Bind commands to controller keys
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		avatarInputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);	
		avatarInputProvider.bindCommand(new ControllerButtonControl(0,GameControls.WIIMOTE_A), toggle);
	}

	
}
