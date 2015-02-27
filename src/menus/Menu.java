package menus;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;

/* Generic menu class
 * 
 * Handles processing of its constituent `selection' elements.
 * 
 */

public abstract class Menu {
	
	/* Menu identifiers */
	public static final int MENU_MAIN = 0;
	public static final int MENU_OPTIONS = 1;
	public static final int MENU_INVENTORY = 2;
	public static final int MENU_ITEM = 3;
	
	
	protected int menuRenderX ;
	protected int menuRenderY ;
	
	private int menuType;


	protected boolean selectionActivated = false;
	protected int currentSelection = 0;
	protected List<MenuSelection> selections;
	
	
	public Menu(int menuType,int menuRenderX, int menuRenderY){
		this.menuType = menuType;
		this.menuRenderX = menuRenderX;
		this.menuRenderY = menuRenderY;
		
	}
	

	
	public int getType(){
		return menuType;
	}
	

	public void render(Graphics graphics){
		for (MenuSelection selection : selections){
			boolean isActive = (selection == selections.get(currentSelection));
			selection.render(graphics,isActive);
		}
	}
	
	

	public void activateCurrentSelection() {
		this.selectionActivated = true;
	}


	public Command getSelectionCommand() {
		assert this.selectionActivated == true : "Getting a command from no active selection!";
		this.selectionActivated = false;
		return selections.get(currentSelection).getCommand();
	}



	public boolean isSelectionActivated() {
		// TODO Auto-generated method stub
		return selectionActivated;
	}
	
	public abstract void incrementActiveSelection(char xOrY, int direction);


	
}
