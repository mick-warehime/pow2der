package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuCloseCurrentCommand extends BasicCommand implements GenericCommand {

	public MenuCloseCurrentCommand() {
		super("Close All Menus");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).closeTopActiveMenu();
		
	}

}
