package menus;

import items.Item;

import java.util.ArrayList;

import commands.MenuCloseCurrentCommand;
import commands.MenuDropItemCommand;
import commands.MenuUnequipItemCommand;

public class ItemEquippedMenu extends TextMenu {

	
	
	

	public ItemEquippedMenu( int menuRenderX, int menuRenderY, Item item) {
		super(Menu.MENU_ITEM, menuRenderX, menuRenderY);
		
		defineMenuSelections(item);
	}

	
	protected void defineMenuSelections(Item item) {
		selections = new ArrayList<MenuSelection>();

		
		addMenuSelection( new MenuUnequipItemCommand(item), "Unequip");
		addMenuSelection( new MenuDropItemCommand(item), "Drop");
		addMenuSelection( new MenuCloseCurrentCommand(), "Cancel");


	}


	
	
	

}
