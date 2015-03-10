package commands;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import actionEngines.ActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine) throws SlickException, IOException;
}
