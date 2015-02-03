package menus;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

import actionEngines.MenuActionEngine;
import commands.CommandProvider;
import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class MenuHandler {



	private KeyboardInputListener keyboardInputs;
	private MenuInputListener menuInputs;
	private InputListenerAggregator listenerAggregator;
	private MenuActionEngine actionEngine;
	private MenuHandlerData menuHandlerData;


	public MenuHandler(){
		
		
		
		this.keyboardInputs = new KeyboardInputListener();
		this.menuInputs = new MenuInputListener();
		this.listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addListener(keyboardInputs);
		listenerAggregator.addListener(menuInputs);

		this.menuHandlerData = new MenuHandlerData(listenerAggregator);
		
		this.actionEngine = new MenuActionEngine(listenerAggregator, menuHandlerData);


	}



	public InputProviderListener getKeyboardListener() {
		// TODO Auto-generated method stub
		return this.keyboardInputs;
	}

	public void update() {
		actionEngine.update();	
		
//		System.out.println(menuHandlerData.getActiveMenu());
	}

	public void renderOpenMenus(Graphics graphics){

		for (Menu menu : menuHandlerData.getMenus()){
			if (menu.isOpen()){
//				System.out.print(menu + " ");
				menu.render(graphics);
			}
		}
//		System.out.println();
	}
	
	public boolean isMenuActive(){
		for (Menu menu : menuHandlerData.getMenus()){
			if (menu.isOpen()){
				return true;
			}
		}
		return false;
	}
	
	
	//Listens to inputs from menus
	class MenuInputListener implements CommandProvider
	{
		@Override
		public ArrayList<Command> getCommands() {
			Menu menu = menuHandlerData.getActiveMenu();
			ArrayList<Command> output = new ArrayList<Command>();
			
			if (menu != null){
				if (menu.isSelectionActivated()){
					Command cmd = menu.getSelectionCommand();
					output.add(cmd);
				}
			}
			
			return output;
		}

		
	}


	public boolean isQuitting() {
		// TODO Auto-generated method stub
		return menuHandlerData.isQuitting();
	}

	
	

}
