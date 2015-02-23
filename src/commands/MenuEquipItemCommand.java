package commands;

import items.Item;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;

public class MenuEquipItemCommand extends BasicCommand implements GenericCommand {

	private Item item;

	public MenuEquipItemCommand(Item item) {
		super("Equip item" + item);
		this.item = item;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((MenuActionEngine)actionEngine).equipItemInPlayerInventory(item);

	}
}