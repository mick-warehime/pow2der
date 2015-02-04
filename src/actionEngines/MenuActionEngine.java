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
			Menu menu = menuHandlerData.getTopActiveMenu();
			menu.activateSelection();
		}
	}

	public void toggleMenu(Menu menu){
		if (!isBusy()){
			if (menu == menuHandlerData.getTopActiveMenu()){
				menuHandlerData.deactivateActiveMenu();
			}
			else{
				menuHandlerData.setTopActiveMenu(menu);
			}
			makeBusy();
		}


	}

	private void makeBusy(){
		menuBusyTimer += menuBusyTime;
	}

	public void closeAllMenus(){
		menuHandlerData.deactivateAllMenus();
		//		if (!isBusy()){
		//			for (Menu menu : menuHandlerData.getAllOpenMenus()){
		//				if (menu.isOpen()){
		//					menu.toggle();
		//				}
		//			}
		//			makeBusy();
		//		}
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

		Menu menu = menuHandlerData.getTopActiveMenu();
		if (menu != null && !isBusy()){
			menu.incrementActiveSelection(xOrY,direction);
			makeBusy();
		}
	}

	public void setQuitting() {
		menuHandlerData.setQuitting(true);

	}

	public void setActiveMenu(Menu menu) {
		menuHandlerData.setTopActiveMenu(menu);
	}


}
