package menus;

import java.util.ArrayList;

import commands.MenuCloseCurrentCommand;
import commands.NullCommand;

public class ItemMenu extends TextMenu {

	public ItemMenu( int menuRenderX, int menuRenderY) {
		super(Menu.MENU_ITEM, menuRenderX, menuRenderY);
	}

	@Override
	protected void defineMenuSelections() {
		selections = new ArrayList<MenuSelection>();

		
		addMenuSelection( new NullCommand(), "Drop Item");
		addMenuSelection( new NullCommand(), "Equip Item");
		addMenuSelection( new MenuCloseCurrentCommand(), "Cancel");


	}
	
	

}
