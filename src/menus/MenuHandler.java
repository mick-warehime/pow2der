package menus;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.command.InputProviderListener;

import actionEngines.ActionEngine;
import actionEngines.MenuActionEngine;
import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class MenuHandler {




	private ArrayList<Menu> menus;
	private KeyboardInputListener keyboardInputs;
	private InputListenerAggregator listenerAggregator;
	private MenuActionEngine actionEngine;


	public MenuHandler(){
		this.menus= new ArrayList<Menu>();

		this.menus.add(new MainMenu());

		this.keyboardInputs = new KeyboardInputListener();
		this.listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addListener(keyboardInputs);

		this.actionEngine = new MenuActionEngine(listenerAggregator, menus);


	}





	public InputProviderListener getListener() {
		// TODO Auto-generated method stub
		return this.keyboardInputs;
	}

	public void update() {
		actionEngine.update();		
	}

	public void renderActiveMenus(Graphics graphics){
		for (Menu menu : menus){
			if (menu.isOpen()){
				menu.render(graphics);
			}
		}
	}

}
