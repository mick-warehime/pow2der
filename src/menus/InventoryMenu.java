package menus;

import java.util.ArrayList;

import items.Inventory;
import items.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import commands.NullCommand;

public class InventoryMenu extends Menu{


	private int menuWidthInItems = 2;
	private int menuHeightInItems = 8;

	public InventoryMenu( int menuRenderX, int menuRenderY) {
		super(Menu.MENU_INVENTORY, menuRenderX, menuRenderY);

	}

	@Override
	public void render(Graphics graphics) {
		for (MenuSelection selection : selections){
			if (selection == selections.get(activeSelection)){
				graphics.setColor(Color.red);
			}else{ graphics.setColor(Color.white);}

			selection.render(graphics);
		}

	}

	@Override
	public void incrementActiveSelection(char xOrY, int direction) {
		int menuX = indexToMenuX(activeSelection);
		int menuY = indexToMenuY(activeSelection);
		
		if (xOrY == 'x'){
			if (direction>0){
				menuX +=1;
			}
			else {
				menuX -=1;
			}
			menuX = menuX % (menuWidthInItems);
			if (menuX <0){ 
				menuX = menuWidthInItems+menuX;
			}
		}
		if (xOrY == 'y'){
			if (direction>0){
				menuY +=1;
			}
			else {
				menuY -=1;
			}
			menuY = menuY % (menuHeightInItems);
			if (menuY <0){ 
				menuY = menuHeightInItems+menuY;
			}
		}
		
		
		activeSelection = menuXYToIndex(menuX, menuY);

	}

	public void setInventory(Inventory playerInventory) {

		defineSelectionsFromInventory(playerInventory);

	}

	private void defineSelectionsFromInventory(Inventory playerInventory) {
		selections = new ArrayList<MenuSelection>();

		for (Item item : playerInventory.getItems()){

			addItemSelection(item);

		}



	}

	private int indexToMenuX(int index){
		return index % (this.menuWidthInItems);
	}
	private int indexToMenuY(int index){
		int output = index/menuWidthInItems;
		return output;
	}
	private int menuXYToIndex(int menuX,int menuY){
		
		return menuWidthInItems*menuY + menuX;
	}
	
	
	private void addItemSelection(Item item) {

		int length = selections.size();

		int menuX = indexToMenuX(length);
		int menuY = indexToMenuY(length);
		assert (menuY<menuHeightInItems) : "Player's inventory has too many elements for the menu!";


		int xPos = menuRenderX + 16*menuX;
		int yPos = menuRenderY + 16*menuY;


		MenuSelection selection = new MenuSelection(new NullCommand(), new TextSelectionGraphics(""+length,xPos,yPos));

		selections.add(selection);
		System.out.println("Added item to menu: " + item + "at " + xPos + "," + yPos);

	}

	

}
