package commands;

import menus.Menu;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuOpenMenuCommand extends BasicCommand implements GenericCommand{

	private int menuIndex;

	public MenuOpenMenuCommand(int menuIndex) {
		super("Open menu: " + menuIndex);
		this.menuIndex = menuIndex;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).setActiveMenu(menuIndex);
		
	}

}
