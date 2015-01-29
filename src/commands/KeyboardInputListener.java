package commands;


import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

//Takes in input commands and implements them for the player
public class KeyboardInputListener implements InputProviderListener,CommandProvider{

	
	private ArrayList<Command> currentActions = new ArrayList<Command>();
	
	
	public KeyboardInputListener() {
	}
	
	
	private void addCommand(Command cmd){
		currentActions.add(cmd);
	}
	private void removeCommand(Command cmd){
		currentActions.remove(cmd);
	}
	

	public ArrayList<Command> getCommands(){
		return currentActions;
	}

	@Override
	public void controlPressed(Command cmd) {
		
		addCommand(cmd);
		
		
	}

	@Override
	public void controlReleased(Command cmd) {
		
		removeCommand(cmd);
		
	}

}
