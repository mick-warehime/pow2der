package controls;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;

import commands.ClimbCommand;
import commands.InteractCommand;
import commands.JumpCommand;
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
		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		keyboardInputProvider = new InputProvider(gc.getInput());
		//The listener is linked to the provider		

		//Define action commands for provider
		Command jump = new JumpCommand();
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand( -1);
		Command moveRight = new MoveCommand( 1);
		Command interact = new InteractCommand();
		Command ascend = new ClimbCommand(-1);
		Command descend = new ClimbCommand(+1);
		
		

		//Bind commands to keys
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_W), ascend);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_S), descend);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_F), interact);

		
		
	}
}
