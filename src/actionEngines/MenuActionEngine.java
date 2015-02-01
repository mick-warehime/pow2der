package actionEngines;


import menus.Menu;
import menus.MenuHandlerData;
import commands.InputListenerAggregator;

//Performs actions for a menu, based on inputs
public class MenuActionEngine extends ActionEngine{

	private int menuBusyTimer = 0;
	private int menuBusyTime = 10;
	private MenuHandlerData menuHandlerData;



	public MenuActionEngine(InputListenerAggregator listener, MenuHandlerData menuHandlerData) {
		super(listener);
		this.menuHandlerData = menuHandlerData;

	}

	public void activateActiveMenuSelection(){
		if (!isBusy()){
			Menu menu = menuHandlerData.getActiveMenu();
			menu.activateSelection();
		}
	}
	
	public void toggleMenu(int menuType){
		if (!isBusy() ){
			for (Menu menu : menuHandlerData.getMenus()){
				if (menu.getType() == menuType){
					menu.toggle();
					if (menu.isOpen()){
						menuHandlerData.setActiveMenu(menu);
					}else{
						menuHandlerData.deactivateActiveMenu();
					}
					makeBusy();
				}
			}
		}	
	}
	
	private void makeBusy(){
		menuBusyTimer += menuBusyTime;
	}
	
	public void closeAllMenus(){
		if (!isBusy()){
			for (Menu menu : menuHandlerData.getMenus()){
				if (menu.isOpen()){
					menu.toggle();
				}
			}
			makeBusy();
		}
	}



	public void update(){
		super.update();

		updateBusyTimer();

	}
	
	private void updateBusyTimer(){
		if (menuBusyTimer >0){
			menuBusyTimer-=1;
		}
	}
	private boolean isBusy(){
		return (menuBusyTimer>0);
	}

	public void changeActiveTextLine(char xOrY, int direction) {

		Menu menu = menuHandlerData.getActiveMenu();
		if (menu != null && !isBusy()){
			menu.incrementActiveSelection(xOrY,direction);
			makeBusy();
		}
	}

	public void setQuitting() {
		menuHandlerData.setQuitting(true);
		
	}


}