package actionEngines;


import java.util.Stack;

import items.Inventory;
import items.Item;

import org.newdawn.slick.SlickException;

import menus.Menu;
import menus.MenuHandlerData;
import menus.InventoryMenu;
import commands.CommandProviderAggregator;

//Performs actions for a menu, based on inputs
public class MenuActionEngine extends ActionEngine{

	private int menuBusyTimer = 0;
	private int menuBusyTime = 10;
	private MenuHandlerData menuHandlerData;



	public MenuActionEngine(CommandProviderAggregator listener, MenuHandlerData menuHandlerData) {
		super(listener);
		this.menuHandlerData = menuHandlerData;

	}

	public void activateActiveMenuSelection(){
		Stack <Menu> activeMenus = menuHandlerData.getAllActiveMenus();
		
		if (!isBusy() && !activeMenus.isEmpty()){
			Menu menu = menuHandlerData.getTopActiveMenu();
			menu.activateCurrentSelection();
			
		}
	}

	public void openMenu(Menu menu) throws SlickException{
		if (!isBusy()){
			menuHandlerData.setTopActiveMenu(menu);
			if (menu.getType() == Menu.MENU_INVENTORY){
				((InventoryMenu)menu).setInventory(menuHandlerData.getPlayerInventory());
			}
			makeBusy();
		}


	}

	private void makeBusy(){
		menuBusyTimer += menuBusyTime;
	}

	public void closeAllMenus(){
		if (!isBusy()){
			menuHandlerData.deactivateAllMenus();
			makeBusy();
		}

	}



	public void update() throws SlickException{
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

	

	public void closeTopActiveMenu() {
		if (!isBusy()){
			menuHandlerData.deactivateTopActiveMenu();
			makeBusy();
		}
	}

	public void toggleMenu(Menu menu) throws SlickException {
		if (!isBusy()){
			if (menuHandlerData.getTopActiveMenu() == menu){
				menuHandlerData.deactivateTopActiveMenu();
			}else{
				openMenu(menu);
			}
			
			
			makeBusy();
		}
		
	}

	public void dropItem(Item item) {
		if (!isBusy()){
			menuHandlerData.getPlayerInventory().removeItem(item);
			closeAllMenus();
			item.drop();
			makeBusy();
		}
		
	}

	public void equipItemInPlayerInventory(Item item) {
		Inventory playerInventory = menuHandlerData.getPlayerInventory();
		playerInventory.equipItem(item);
		closeAllMenus();
		
	}

	public void unequipItemInPlayerInventory(Item item) {
		Inventory playerInventory = menuHandlerData.getPlayerInventory();
		playerInventory.unequipItem(item);
		closeAllMenus();
		
	}
	
	
	
	


}
