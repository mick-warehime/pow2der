package menus;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.MenuToggleCommand;
import commands.MenuCloseAllCommand;
import commands.InputListenerAggregator;
import commands.MenuQuitCommand;

public class MainMenu extends TextMenu {


	public MainMenu(int menuRenderX, int menuRenderY) {
		super(Menu.MENU_MAIN, menuRenderX, menuRenderY);
	}

	protected void defineMenuSelections(){
		selections = new ArrayList<MenuSelection>();
		
		MenuSelection cnt = new MenuSelection(new MenuCloseAllCommand(),
				new textSelectionGraphics("Continue", this.menuRenderX,this.menuRenderY));
		
		OptionsMenu optionsMenu = new OptionsMenu(200,240);
		
		MenuSelection options = new MenuSelection( new MenuToggleCommand(optionsMenu),
				new textSelectionGraphics("Options", this.menuRenderX,this.menuRenderY + this.textLineHeight));
		
		MenuSelection quit = new MenuSelection( new MenuQuitCommand(), 
				new textSelectionGraphics("Quit", this.menuRenderX,this.menuRenderY + 2*this.textLineHeight));
		
		selections.add(cnt);
		selections.add(options);
		selections.add(quit);
		
	}

	



}
