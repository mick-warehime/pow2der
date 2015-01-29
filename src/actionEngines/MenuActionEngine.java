package actionEngines;


import menus.Menu;
import menus.MenuHandlerData;
import commands.InputListenerAggregator;

//Performs actions for a menu, based on inputs
public class MenuActionEngine extends ActionEngine{

	private int menuToggleTimer = 0;
	private int menuToggleInterval = 20;
	private boolean canToggleMenu = true;
	private MenuHandlerData menuHandlerData;

	

	public MenuActionEngine(InputListenerAggregator listener, MenuHandlerData menuHandlerData) {
		super(listener);
		this.menuHandlerData = menuHandlerData;
		
	}
	
	public void toggleMenu(int menuType){
		if (canToggleMenu ){
			for (Menu menu : menuHandlerData.getMenus()){
				if (menu.getType() == menuType){
					menu.toggle();
					if (menu.isOpen()){
						menuHandlerData.setActiveMenu(menu);
					}else{
						menuHandlerData.deactivateActiveMenu();
					}
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
