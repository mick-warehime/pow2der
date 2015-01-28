package menus;

import java.util.ArrayList;

import org.newdawn.slick.InputListener;
import org.newdawn.slick.command.InputProviderListener;

import actors.ActionEngine;
import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class MenuHandler {

	private ArrayList<Menu> menus;
	private KeyboardInputListener keyboardInputs;
	private InputListenerAggregator listenerAggregator;
	private ActionEngine actionEngine;
	

	public MenuHandler(){
		this.menus= new ArrayList<Menu>();

		this.menus.add(new MainMenu());
		
		this.keyboardInputs = new KeyboardInputListener();
		this.listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addProvider(keyboardInputs);
		
		this.actionEngine = new ActionEngine(listenerAggregator);
		
		
	}



	public InputProviderListener getListener() {
		// TODO Auto-generated method stub
		return this.keyboardInputs;
	}

	public void update() {
		actionEngine.update();		
	}
	
}
