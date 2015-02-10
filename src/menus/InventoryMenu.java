package menus;

import java.util.ArrayList;

import items.Inventory;
import items.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import commands.MenuOpenCommand;
import commands.NullCommand;

public class InventoryMenu extends Menu{


	private int menuWidthInItems = 5;
	private int menuHeightInItems = 7;
	private int imageSizeInPixels = 48;

	public InventoryMenu( int menuRenderX, int menuRenderY) {
		super(Menu.MENU_INVENTORY, menuRenderX, menuRenderY);

	}

	

	@Override
	public void incrementActiveSelection(char xOrY, int direction) {
		int menuX = indexToMenuX(currentSelection);
		int menuY = indexToMenuY(currentSelection);

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


		currentSelection = menuXYToIndex(menuX, menuY);

	}

	public void setInventory(Inventory playerInventory) throws SlickException {

		defineSelectionsFromInventory(playerInventory);

	}

	private void defineSelectionsFromInventory(Inventory playerInventory) throws SlickException {
		selections = new ArrayList<MenuSelection>();

		for (Item item : playerInventory.getItems()){

			addItemSelection(item);

		}

		for (int i = selections.size(); i<(menuWidthInItems*menuHeightInItems); i++){
			addItemSelection(null);
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


	private void addItemSelection(Item item) throws SlickException {

		int length = selections.size();

		int menuX = indexToMenuX(length);
		int menuY = indexToMenuY(length);
		assert (menuY<menuHeightInItems) : "Player's inventory has too many elements for the menu!";


		int xPos = menuRenderX + imageSizeInPixels*menuX;
		int yPos = menuRenderY + imageSizeInPixels*menuY;

		MenuSelection selection;
		if (item != null){
			
			
			Command menuCmd = new MenuOpenCommand(new ItemMenu(menuRenderX - 100,menuRenderY, item));
			
			selection = new MenuSelection(	menuCmd, 
					new InventorySelectionGraphics(item.getImage(),xPos,yPos));
			
		}else
		{
			selection = new MenuSelection(
					new NullCommand(), 
					new InventorySelectionGraphics(null,xPos,yPos));
		}
		
		selections.add(selection);



	}



}
