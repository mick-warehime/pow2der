package menus;

import org.newdawn.slick.Graphics;

/*
 * MenuSelection
	===
	Stores graphical representation and action of a given menu selection
 */

public class MenuSelection {

	private MenuSelectionAction action;
	private MenuSelectionGraphics menuSelectionGraphics;
	
	public MenuSelection(MenuSelectionAction action, 
			MenuSelectionGraphics menuSelectionGraphics ){
		
		this.action = action;
		this.menuSelectionGraphics = menuSelectionGraphics;
		
	}
	
	
	public void activate(){
		action.Activate();
	}
	
	public void render(Graphics graphics){
		menuSelectionGraphics.render(graphics);
	}
	
}
