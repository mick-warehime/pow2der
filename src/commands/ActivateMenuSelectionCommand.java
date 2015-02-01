package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class ActivateMenuSelectionCommand extends BasicCommand implements GenericCommand {

	public ActivateMenuSelectionCommand() {
		super("ActivateMenuSelection");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).activateActiveMenuSelection();

	}
}