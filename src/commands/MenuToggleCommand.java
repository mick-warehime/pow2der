package commands;

import menus.Menu;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuToggleCommand extends BasicCommand implements GenericCommand{

	private Menu menu;

	public MenuToggleCommand(Menu menu) {
		super("Toggle menu: " + menu);
		this.menu = menu;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).setActiveMenu(menu);
		
	}

}
