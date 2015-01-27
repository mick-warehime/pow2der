package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.ActorActionEngine;

public class NullCommand extends BasicCommand implements GenericCommand{

	

	public NullCommand() {
		super("Do nothing");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		return;
		
	}

}
