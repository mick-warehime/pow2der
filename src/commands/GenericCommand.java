package commands;

import org.newdawn.slick.SlickException;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine) throws SlickException;
}
