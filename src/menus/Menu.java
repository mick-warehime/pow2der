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

	protected boolean isToggled;
	
	public Menu(){
		this.isToggled = false;
	}
	
	public boolean isToggled(){
		return this.isToggled;
	}
	
	public abstract void draw();
}
