package menus;

import java.util.ArrayList;

import commands.MenuOpenCommand;
import commands.MenuCloseAllCommand;
import commands.MenuQuitCommand;

public class MainMenu extends TextMenu {


	public MainMenu(int menuRenderX, int menuRenderY) {
		super(Menu.MENU_MAIN, menuRenderX, menuRenderY);
		defineMenuSelections();
	}

	protected void defineMenuSelections(){
		selections = new ArrayList<MenuSelection>();
		
		
		addMenuSelection(new MenuCloseAllCommand(), "Continue");
		OptionsMenu optionsMenu = new OptionsMenu(200,240);
		addMenuSelection(new MenuOpenCommand(optionsMenu), "Options");
		addMenuSelection(new MenuQuitCommand(), "Quit");

	}

	



}
