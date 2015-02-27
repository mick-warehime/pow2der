package items;

import java.util.ArrayList;

public class Inventory {
	
	private ArrayList<Item> inventoryItems = new ArrayList<Item>();
	private EquippedItems equippedItems;
	
	
	
	
	public Inventory(){
		this.equippedItems = new EquippedItems();
	}
	
	public ArrayList<Item> getInventoryItems(){
		return inventoryItems;
	}
	
	public ArrayList<Item>  getEquippedItems(){
		return equippedItems.nonNullItems();
	}
	
	public void addItemToInventory(Item item){
		inventoryItems.add(item);
		
	}
	
	public void removeItem(Item item){
		inventoryItems.remove(item);
		unequipItem(item);
	}
	
	public void equipItem(Item item){
		assert inventoryItems.contains(item) : "Tried to equip item," + item + " not in inventory" + this;
		equippedItems.equipItem(item);
		inventoryItems.remove(item);
		
	}
	
	public void unequipItem(Item item){
		equippedItems.unequipItem(item);
	}
	
	class EquippedItems{
		
		private static final int EQUIP_SLOTS = 10;
		private ArrayList<Item> equipSlots ;
		
		
		public EquippedItems(){
			equipSlots = new ArrayList<Item>();
			for (int i = 0; i< EQUIP_SLOTS; i++){
				equipSlots.add(null);
			}
		}
		
		public ArrayList<Item> nonNullItems(){
			ArrayList<Item> output  = new ArrayList<Item>() ;
			
			for (Item item : equipSlots){
				if (item != null){
					output.add(item);
				}
			}
			
			
			return output;
		}
		
		public void equipItem(Item item){
			
			
			int index = item.getProperties().equipLocation;
			
			Item oldItem = equipSlots.get(index);
			if (oldItem!=null){
				inventoryItems.add(oldItem);
			}
			
			equipSlots.set(index, item);
			
			
			
		}
		
		protected void unequipItem(Item item){
			
			assert item!= null : "Tried to unequip a null item!";
			
			if (equipSlots.contains(item)){
				equipSlots.remove(item);
				inventoryItems.add(item);
			}
			
			
			
		}
		
		
		
		
	}
	

}
