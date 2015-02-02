package menus;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;

/* Generic menu class
 * 
 *
 * 
 */

public abstract class Menu {
	
	/* Menu identifiers */
	public static final int MENU_MAIN = 0;
	
	private boolean isOpen;
	private int menuType;


	protected boolean selectionActivated = false;
	protected int activeSelection = 0;
	protected ArrayList<MenuSelection> selections;
	
	
	public Menu(int menuType){
		this.menuType = menuType;
		
		
	}
	
//	protected abstract void defineMenuSelections();

	public boolean isOpen(){
		return this.isOpen;
	}
	public void toggle(){
		this.isOpen = !isOpen;
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
		this.selectionActivated = false;
		return selections.get(activeSelection).getCommand();
	}



	public boolean isSelectionActivated() {
		// TODO Auto-generated method stub
		return selectionActivated;
	}

	
}
