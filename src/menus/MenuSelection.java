package menus;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;

/*
 * MenuSelection
	===
	Stores graphical representation and action of a given menu selection
 */

public class MenuSelection {
	
	private Command command;
	private MenuSelectionGraphics menuSelectionGraphics;
	
	
	public MenuSelection(Command command,
			MenuSelectionGraphics menuSelectionGraphics ){
		
		this.command = command;
		this.menuSelectionGraphics = menuSelectionGraphics;
		
	}
	
	public Command getCommand(){
		return this.command;
	}
	
	public void render(Graphics graphics, boolean isActiveSelection){
		menuSelectionGraphics.render(graphics, isActiveSelection);
	}
	
	public MenuSelectionGraphics getGraphics(){
		return this.menuSelectionGraphics;
	}
	
	
	
}
