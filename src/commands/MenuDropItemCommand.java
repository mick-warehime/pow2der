package commands;

import items.Item;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuDropItemCommand extends BasicCommand implements GenericCommand {

	
	
	private Item item;

	public MenuDropItemCommand(Item item) {
		super("Drop item" + item);
		this.item = item;
		
	}

	@Override
	public void execute(ActionEngine actionEngine) throws SlickException {
		((MenuActionEngine)actionEngine).dropItem(item);
		
	}

}
