package menus;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.InputProviderListener;

import actionEngines.MenuActionEngine;
import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class MenuHandler {



	private KeyboardInputListener keyboardInputs;
	private InputListenerAggregator listenerAggregator;
	private MenuActionEngine actionEngine;
	private MenuHandlerData menuHandlerData;


	public MenuHandler(){
		
		this.menuHandlerData = new MenuHandlerData();
		
		this.keyboardInputs = new KeyboardInputListener();
		this.listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addListener(keyboardInputs);

		this.actionEngine = new MenuActionEngine(listenerAggregator, menuHandlerData);


	}





	public InputProviderListener getListener() {
		// TODO Auto-generated method stub
		return this.keyboardInputs;
	}

	public void update() {
		actionEngine.update();	
	}

	public void renderActiveMenus(Graphics graphics){

		for (Menu menu : menuHandlerData.getMenus()){
			if (menu.isOpen()){
				menu.render(graphics);
			}
		}
	}
	
	public boolean isMenuActive(){
		for (Menu menu : menuHandlerData.getMenus()){
			if (menu.isOpen()){
				return true;
			}
		}
		return false;
	}
	
	

}
