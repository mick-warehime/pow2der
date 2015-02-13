package menus;

import items.Inventory;
import items.Item;

import java.util.ArrayList;
import java.util.Stack;

import commands.InputListenerAggregator;

/* Stores basic information used by the menu Handler class:
 * stack of currently active Menus,
 * whether the player is quitting,
 * the player's inventory (needed to create the inventory menu).
 */

public class MenuHandlerData {

	private Stack<Menu> activeMenuStack;
	private boolean isQuitting = false;
	private Inventory playerInventory;
	private ArrayList<Item> itemsToDrop = new ArrayList<Item>();

	public MenuHandlerData(InputListenerAggregator listenerAggregator){
		
		this.activeMenuStack = new Stack<Menu>();
	}
	
	public Stack<Menu> getAllActiveMenus(){
		return activeMenuStack;
	}
	
	public void setTopActiveMenu(Menu menu){
		if (activeMenuStack.contains(menu)){
			while (menu!= activeMenuStack.pop()){
			}
		}
		activeMenuStack.push(menu);
		return;
		
	}
	
	public void deactivateTopActiveMenu(){
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

	public void setPlayerInventory(Inventory inventory) {
		this.playerInventory = inventory;
		
	}

	public  Inventory getPlayerInventory() {
		return playerInventory;
	}

	public void addItemToDrop(Item item) {
		this.itemsToDrop.add(item);
	}
	
	public ArrayList<Item> popItemsToDrop(){
		ArrayList<Item> output = this.itemsToDrop;
		itemsToDrop = new ArrayList<Item>();
		return output;
	}
}
