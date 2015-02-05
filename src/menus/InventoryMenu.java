package menus;

import items.Inventory;

import org.newdawn.slick.Graphics;

public class InventoryMenu extends Menu{

	

//	private Inventory playerInventory;

	public InventoryMenu( int menuRenderX, int menuRenderY) {
		super(Menu.MENU_INVENTORY, menuRenderX, menuRenderY);
		
	}

	@Override
	public void render(Graphics graphics) {
		
		
	}

	@Override
	public void incrementActiveSelection(char xOrY, int direction) {
		
		
	}

	public void setInventory(Inventory playerInventory) {
		
		defineSelectionsFromInventory(playerInventory);
		
	}

	private void defineSelectionsFromInventory(Inventory playerInventory) {
		System.out.println("Inventory menu selections set!");
		
	}

}
