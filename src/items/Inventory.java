package items;

import java.util.ArrayList;

public class Inventory {
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Inventory(){
		
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void removeItem(Item item){
		items.remove(item);
	}

}
