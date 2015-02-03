package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuChangeSelectionCommand extends BasicCommand implements GenericCommand {

	private char xOrY;
	private int direction;
	
	public MenuChangeSelectionCommand(char xOrY, int direction) {
		super("Move Active Menu's Selection in direction" + direction +" *"+ xOrY);
		this.xOrY = xOrY;
		this.direction = direction;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).changeActiveTextLine(xOrY, direction);
		
	}
	
	

}
