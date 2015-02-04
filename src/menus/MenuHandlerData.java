package menus;

import java.util.Stack;

import commands.InputListenerAggregator;

public class MenuHandlerData {

	private Stack<Menu> activeMenuStack;
	private boolean isQuitting = false;

	public MenuHandlerData(InputListenerAggregator listenerAggregator){
		
		this.activeMenuStack = new Stack<Menu>();
	}
	
	public Stack<Menu> getAllActiveMenus(){
		return activeMenuStack;
	}
	
	public void setTopActiveMenu(Menu menu){
		activeMenuStack.push(menu);
		return;
		
	}
	
	public void deactivateActiveMenu(){
		assert !activeMenuStack.isEmpty() : "Deactivated a null menu!";
		activeMenuStack.pop();
	}
	
	public Menu getTopActiveMenu(){
		if (activeMenuStack.isEmpty()){return null;}
		return activeMenuStack.peek();
	}

	public void setQuitting(boolean bool) {
		this.isQuitting  = bool;
		
	}
	public boolean isQuitting(){
		return this.isQuitting;
	}

	public void deactivateAllMenus() {
		this.activeMenuStack = new Stack<Menu>();	
	}
}
