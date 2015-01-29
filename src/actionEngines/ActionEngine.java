package actionEngines;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.InputListenerAggregator;

public class ActionEngine {

	protected InputListenerAggregator listener;

	public ActionEngine(InputListenerAggregator listener) {
		this.listener = listener;
	}

	public void update() {
	
		doActions();	
	}

	protected void doActions() {
	
		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.popCurrentActionCommands();
	
		//Do the associated actions
		for (Command cmd : currentActionCommands){
			((GenericCommand)cmd).execute(this);
		}
	
	
	
	
	}


}