package menus;

import java.util.ArrayList;
import java.util.Stack;

import commands.InputListenerAggregator;

public class MenuHandlerData {

	private ArrayList<Menu> menus;
	private Stack<Menu> activeMenuStack;
	private boolean isQuitting = false;

	public MenuHandlerData(InputListenerAggregator listenerAggregator){
		this.menus= new ArrayList<Menu>();
		this.menus.add(new MainMenu(100,240));
		this.menus.add(new OptionsMenu(200,240));
		
		this.activeMenuStack = new Stack<Menu>();
	}
	
	public ArrayList<Menu> getMenus(){
		return menus;
	}
	
	public void setActiveMenu(int menuIndex){
		for (Menu menu : menus){
			if (menu.getType() == menuIndex)
			{
				activeMenuStack.push(menu);
				return;
			}
		}
		
	}
	
	public void deactivateActiveMenu(){
		assert !activeMenuStack.isEmpty() : "Deactivated a null menu!";
		activeMenuStack.pop();
	}
	
	public Menu getActiveMenu(){
		if (activeMenuStack.isEmpty()){return null;}
		return activeMenuStack.peek();
	}

	public void setQuitting(boolean bool) {
		this.isQuitting  = bool;
		
	}
	public boolean isQuitting(){
		return this.isQuitting;
	}
}
