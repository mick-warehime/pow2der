package actionEngines;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.CommandProviderAggregator;

public class ActionEngine {

	protected CommandProviderAggregator listenerAggregator;

	public ActionEngine(CommandProviderAggregator listener) {
		this.listenerAggregator = listener;
	}

	public void update() {
	
		doActions();	
	}

	protected void doActions() {
	
		//Get all player commands
		ArrayList<Command> currentActionCommands = listenerAggregator.popCurrentActionCommands();
	
		//Do the associated actions
		for (Command cmd : currentActionCommands){
			((GenericCommand)cmd).execute(this);
		}
	
	
	
	
	}


}