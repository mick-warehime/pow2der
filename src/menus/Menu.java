package menus;

/*
 * 
* Toggle functionality
* Take keyboard/mouse inputs to alter displayed selection
* graphics for displaying the menu, and showing what the selected item is
* Binds selected option to a command
 * 
 */

public abstract class Menu {
	
	/* Menu identifiers */
	public static final int MENU_MAIN = 0;
	
	private boolean isOpen;
	private int menuType;

	
	public Menu(int menuType){
		this.menuType = menuType;
		
	}
	
	public boolean isOpen(){
		return this.isOpen;
	}
	public void toggle(){
		this.isOpen = !isOpen;
	}
	public int getType(){
		return menuType;
	}
	
	public abstract void render();
}
