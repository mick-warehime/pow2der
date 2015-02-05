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
		
		MenuSelection audio = new MenuSelection(new NullCommand(),
				new TextSelectionGraphics("Audio", this.menuRenderX,this.menuRenderY));
		MenuSelection video = new MenuSelection(new NullCommand(),
				new TextSelectionGraphics("Video", this.menuRenderX,this.menuRenderY + 1*this.textLineHeight));
		MenuSelection disco = new MenuSelection(new NullCommand(),
				new TextSelectionGraphics("Disco", this.menuRenderX,this.menuRenderY + 2*this.textLineHeight));
		MenuSelection exit = new MenuSelection(new MenuCloseCurrentCommand(),
				new TextSelectionGraphics("Exit", this.menuRenderX,this.menuRenderY + 3*this.textLineHeight));
		
		selections.add(audio);
		selections.add(video);
		selections.add(disco);
		selections.add(exit);
	}

}
