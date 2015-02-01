package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuQuitCommand extends BasicCommand implements GenericCommand {

	public MenuQuitCommand() {
		super("Quit command");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).setQuitting();
		
	}


}
