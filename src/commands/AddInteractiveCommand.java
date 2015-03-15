package commands;

import interfaces.Interactive;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;


public class AddInteractiveCommand extends BasicCommand implements GenericCommand{

	


	private Interactive toAdd;


	public AddInteractiveCommand(Interactive interactive) {
		super("Add to list of nearby interactives: " + interactive );
		this.toAdd = interactive;
		
		if (interactive==null){
			throw new NullPointerException("Attempted to add a null interactive!");
		}
	}


	@Override
	public void execute(ActionEngine engine){
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine)engine).addAccessibleInteractive(toAdd);
		}



	}
}
