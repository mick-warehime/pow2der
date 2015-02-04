package menus;

import java.util.ArrayList;

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
		
		MenuSelection cnt = new MenuSelection(new MenuCloseAllCommand(),
				new textSelectionGraphics("Quit", this.menuRenderX,this.menuRenderY));
		
		selections.add(cnt);
	}

}
