package menus;

import java.util.ArrayList;

import org.newdawn.slick.InputListener;
import org.newdawn.slick.command.InputProviderListener;

import actors.ActionEngine;
import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class MenuHandler {

	private ArrayList<Menu> menus;
	private KeyboardInputListener keyboardInputs;
	private GlobalInputListener globalInputListener;
	private ActionEngine actionEngine;
	

	public MenuHandler(){
		this.menus= new ArrayList<Menu>();

		this.menus.add(new MainMenu());
		
		this.keyboardInputs = new KeyboardInputListener();
		this.globalInputListener = new GlobalInputListener();
		globalInputListener.addProvider(keyboardInputs);
		
		this.actionEngine = new ActionEngine(globalInputListener);
		
		
	}



	public InputProviderListener getListener() {
		// TODO Auto-generated method stub
		return this.keyboardInputs;
	}

	public void update() {
		globalInputListener.receiveExternalInputs();
		actionEngine.update();		
	}
	
}
