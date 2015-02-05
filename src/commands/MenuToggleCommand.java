package commands;

import menus.Menu;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuToggleCommand extends BasicCommand implements GenericCommand{

	private Menu menu;
	
	public MenuToggleCommand(Menu menu) {
		super("Open menu" + menu);
		this.menu = menu;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine) actionEngine).toggleMenu(menu);
		
		
	}

}
