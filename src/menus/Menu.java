package menus;

import java.util.ArrayList;

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
	
	
	protected int menuRenderX ;
	protected int menuRenderY ;
	
	private int menuType;


	protected boolean selectionActivated = false;
	protected int activeSelection = 0;
	protected ArrayList<MenuSelection> selections;
	
	
	public Menu(int menuType,int menuRenderX, int menuRenderY){
		this.menuType = menuType;
		this.menuRenderX = menuRenderX;
		this.menuRenderY = menuRenderY;
		
	}
	

	
	public int getType(){
		return menuType;
	}
	

	public abstract void render(Graphics graphics);
	public abstract void incrementActiveSelection(char xOrY, int direction);
	

	public void activateSelection() {
		this.selectionActivated = true;
	}


	public Command getSelectionCommand() {
		assert this.selectionActivated == true : "Getting a command from no active selection!";
		this.selectionActivated = false;
		return selections.get(activeSelection).getCommand();
	}



	public boolean isSelectionActivated() {
		// TODO Auto-generated method stub
		return selectionActivated;
	}


	
}
