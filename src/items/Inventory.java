package items;

import java.util.ArrayList;

public class Inventory {
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Item> equippedItems = new ArrayList<Item>();
	
	public Inventory(){
		
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public ArrayList<Item>  getEquipped(){
		return equippedItems;
	}
	
	public void addItem(Item item){
		items.add(item);
		
	}
	
	public void removeItem(Item item){
		items.remove(item);
		if (equippedItems.contains(item)){
			equippedItems.remove(item);
		}
	}
	
	public void equipItem(Item item){
		assert items.contains(item) : "Tried to equip item," + item + " not in inventory" + this;
		if (!equippedItems.contains(item)){
			equippedItems.add(item);
		}
		
	}
	
	public void unequipItem(Item item){
		assert items.contains(item) : "Tried to unequip item," + item + " not in inventory" + this;
		if (equippedItems.contains(item)){
			equippedItems.remove(item);
		}
	}
	

}
