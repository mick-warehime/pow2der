package controls;


import interfaces.CommandProvider;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

//Takes in input commands and implements them for the player
public class KeyboardInputListener implements InputProviderListener,CommandProvider{

	
	private ArrayList<Command> currentActions = new ArrayList<Command>();
	
	
	public KeyboardInputListener() {
	}
	
	
	
	

	public ArrayList<Command> getCommands(){
		return currentActions;
	}

	@Override
	public void controlPressed(Command cmd) {
		
		currentActions.add(cmd);
		
		
	}

	@Override
	public void controlReleased(Command cmd) {
		
		currentActions.remove(cmd);
		
	}

}
