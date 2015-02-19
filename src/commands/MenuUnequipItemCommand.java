package commands;

import items.Item;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuUnequipItemCommand extends BasicCommand implements GenericCommand {

	private Item item;

	public MenuUnequipItemCommand(Item item) {
		super("Unequip item" + item);
		this.item = item;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).unequipItemInPlayerInventory(item);

	}
}