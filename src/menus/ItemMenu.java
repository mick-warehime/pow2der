package menus;

import items.Item;

import java.util.ArrayList;

import commands.MenuCloseCurrentCommand;
import commands.MenuDropItemCommand;
import commands.MenuEquipItemCommand;
import commands.NullCommand;

public class ItemMenu extends TextMenu {

	
	
	

	public ItemMenu( int menuRenderX, int menuRenderY, Item item) {
		super(Menu.MENU_ITEM, menuRenderX, menuRenderY);
		
		defineMenuSelections(item);
	}

	
	protected void defineMenuSelections(Item item) {
		selections = new ArrayList<MenuSelection>();

		
		addMenuSelection( new MenuDropItemCommand(item), "Drop Item");
		addMenuSelection( new MenuEquipItemCommand(item), "Equip Item");
		addMenuSelection( new MenuCloseCurrentCommand(), "Cancel");


	}


	
	
	

}
