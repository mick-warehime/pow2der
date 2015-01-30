package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class CloseAllMenusCommand extends BasicCommand implements GenericCommand {

	public CloseAllMenusCommand() {
		super("Close All Menus");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).closeAllMenus();
		
	}

}
