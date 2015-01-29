package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

public class DieCommand extends BasicCommand implements GenericCommand {

	public DieCommand() {
		super("Die");
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		if (actionEngine instanceof ActorActionEngine){
			((ActorActionEngine)actionEngine).die();}
	}

}
