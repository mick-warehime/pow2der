package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenusCloseAllCommand extends BasicCommand implements GenericCommand {

	public MenusCloseAllCommand() {
		super("Close All Menus");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).closeAllMenus();
		
	}

}
