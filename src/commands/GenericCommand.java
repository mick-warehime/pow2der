package commands;

import org.newdawn.slick.SlickException;

import actionEngines.ActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine) throws SlickException;
}
