package menus;

import java.util.ArrayList;

import commands.MenuOpenCommand;
import commands.MenuCloseAllCommand;
import commands.MenuQuitCommand;

public class MainMenu extends TextMenu {


	public MainMenu(int menuRenderX, int menuRenderY) {
		super(Menu.MENU_MAIN, menuRenderX, menuRenderY);
	}

	protected void defineMenuSelections(){
		selections = new ArrayList<MenuSelection>();
		
		MenuSelection cnt = new MenuSelection(new MenuCloseAllCommand(),
				new TextSelectionGraphics("Continue", this.menuRenderX,this.menuRenderY));
		
		OptionsMenu optionsMenu = new OptionsMenu(200,240);
		
		MenuSelection options = new MenuSelection( new MenuOpenCommand(optionsMenu),
				new TextSelectionGraphics("Options", this.menuRenderX,this.menuRenderY + this.textLineHeight));
		
		MenuSelection quit = new MenuSelection( new MenuQuitCommand(), 
				new TextSelectionGraphics("Quit", this.menuRenderX,this.menuRenderY + 2*this.textLineHeight));
		
		selections.add(cnt);
		selections.add(options);
		selections.add(quit);
		
	}

	



}
