package menus;

import java.util.ArrayList;

import commands.MenuCloseCurrentCommand;
import commands.MenuQuitCommand;
import commands.MenuCloseAllCommand;
import commands.NullCommand;

public class OptionsMenu extends TextMenu{

	public OptionsMenu(int menuRenderX, int menuRenderY) {
		super(Menu.MENU_OPTIONS, menuRenderX, menuRenderY);
	}

	@Override
	protected void defineMenuSelections() {
		selections = new ArrayList<MenuSelection>();
		
		addMenuSelection(new NullCommand(), "Audio");
		addMenuSelection(new NullCommand(), "Video");
		addMenuSelection(new NullCommand(), "Disco");
		addMenuSelection(new MenuCloseCurrentCommand(), "Exit");
		
		
	}

}
