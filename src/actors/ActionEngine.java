package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.GlobalInputListener;

public class ActionEngine {

	protected GlobalInputListener listener;

	public ActionEngine(GlobalInputListener listener) {
		this.listener = listener;
	}

	public void update() {
	
		doActions();	
	}

	protected void doActions() {
	
		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.getCurrentActionCommands();
	
		//Do the associated actions
		for (Command cmd : currentActionCommands){
			((GenericCommand)cmd).execute(this);
		}
	
	
	
	
	}


}