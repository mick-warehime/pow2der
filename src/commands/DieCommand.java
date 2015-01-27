package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.ActorActionEngine;

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
