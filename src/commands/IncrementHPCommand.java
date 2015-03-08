package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

public class IncrementHPCommand extends BasicCommand implements GenericCommand {

	private int increment;

	public IncrementHPCommand(int increment) {
		super("Increment HP by" + increment);
		this.increment = increment;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		if (actionEngine instanceof ActorActionEngine){
			((ActorActionEngine)actionEngine).incrementHP(increment);}
	}

}
