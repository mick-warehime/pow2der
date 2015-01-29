package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class ToggleMenuCommand extends BasicCommand implements GenericCommand{

	int menuIndex;
	
	public ToggleMenuCommand(int menuIndex) {
		super("Toggle menu" + menuIndex);
		this.menuIndex = menuIndex;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine) actionEngine).toggleMenu(menuIndex);
		
		
	}

}
