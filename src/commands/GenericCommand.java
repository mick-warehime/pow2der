package commands;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine);
}
