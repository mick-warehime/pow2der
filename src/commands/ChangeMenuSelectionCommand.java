package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class ChangeMenuSelectionCommand extends BasicCommand implements GenericCommand {

	private char xOrY;
	private int direction;
	
	public ChangeMenuSelectionCommand(char xOrY, int direction) {
		super("Move Active Menu's Selection in direction" + direction +" *"+ xOrY);
		this.xOrY = xOrY;
		this.direction = direction;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).changeActiveTextLine(xOrY, direction);
		
	}
	
	

}
