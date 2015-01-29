package actionEngines;

import java.util.ArrayList;
import java.util.Hashtable;

import menus.Menu;
import commands.InputListenerAggregator;

//Performs actions for a menu, based on inputs
public class MenuActionEngine extends ActionEngine{

	private int menuToggleTimer = 0;
	private int menuToggleInterval = 20;
	private boolean canToggleMenu = true;

	private ArrayList<Menu> menus;
	

	public MenuActionEngine(InputListenerAggregator listener, ArrayList<Menu> menus) {
		super(listener);
		this.menus = menus;
		
	}
	
	public void toggleMenu(int menuType){
		if (canToggleMenu ){
			for (Menu menu : menus){
				if (menu.getType() == menuType){
					menu.toggle();
					canToggleMenu =false;
					
				}
			}
		}
		
	}
	
	public void update(){
		super.update();
		
		if (!canToggleMenu){
			menuToggleTimer+=1;
		}
		if (menuToggleTimer >= menuToggleInterval){
			menuToggleTimer = 0;
			canToggleMenu = true;
		}
		
		
		
		
		
	}

	
}
