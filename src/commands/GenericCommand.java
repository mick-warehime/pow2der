package commands;

import actors.ActionEngine;
import actors.ActorActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine);
}
