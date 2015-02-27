package menus;

import java.util.ArrayList;
import java.util.List;

import items.Inventory;
import items.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import commands.MenuOpenCommand;
import commands.NullCommand;

public class InventoryMenu extends Menu{


	private int inventoryWidthInItems = 5;
	private int inventoryHeightInItems = 7;
	private int imageSizeInPixels = 48;
	private SelectionGraph selectionGraph;
	
	private int equipmentMenuOffsetX = -200;
	private int equipmentMenuOffsetY = -100;


	public InventoryMenu( int menuRenderX, int menuRenderY) throws SlickException {
		super(Menu.MENU_INVENTORY, menuRenderX, menuRenderY);


	}





	@Override
	public void incrementActiveSelection(char xOrY, int direction) {

		selectionGraph.moveCurrentSelection(xOrY, direction);
		
		currentSelection = selectionGraph.currentSelectionNodeIndex();
	

	}

	public void setInventory(Inventory playerInventory) throws SlickException {

		defineSelectionsFromInventory(playerInventory);

	}

	private void defineSelectionsFromInventory(Inventory playerInventory) throws SlickException {
		selections = new ArrayList<MenuSelection>();
		currentSelection = 0;
		
		this.selectionGraph = new SelectionGraph();

		
		for (Item item : playerInventory.getInventoryItems()){
			
			MenuSelection newSelection =generateInventoryMenuSelection(item);
			selectionGraph.addInventorySelection(newSelection);
				
		}
		
		for (Item item : playerInventory.getEquippedItems()){
			int locationIndex = item.getProperties().equipLocation;
			MenuSelection newSelection =generateEquippedMenuSelection(item);
			selectionGraph.setEquippedSelection(newSelection,locationIndex);
		}
		
		


	}

	





	private MenuSelection generateInventoryMenuSelection(Item item) throws SlickException{
		Command menuCmd = new MenuOpenCommand(new ItemInventoryMenu(menuRenderX - 100,menuRenderY, item));
		InventorySelectionGraphics graphics = new InventorySelectionGraphics(item.getImage());
		
		return new MenuSelection(menuCmd,graphics);
		
	}
	
	private MenuSelection generateEquippedMenuSelection(Item item) throws SlickException{
		Command menuCmd = new MenuOpenCommand(new ItemEquippedMenu(menuRenderX - 150,menuRenderY, item));
		InventorySelectionGraphics graphics = new InventorySelectionGraphics(item.getImage());
		
		return new MenuSelection(menuCmd,graphics);
		
	}
	



	
	/*
	 * A graph with nodes representing all menu selections, 
	 * with functionality for changing the active node.
	 * 
	 */

	class SelectionGraph {

		private static final int EQUIP_SLOTS = 10; //Make sure this matches in inventory class
		
		private static final int INDEX_UNASSIGNED = -1;

		private static final int DIR_LEFT = 0;
		private static final int DIR_UP = 1;
		private static final int DIR_RIGHT = 2;
		private static final int DIR_DOWN = 3;




		SelectionNode[][] inventoryNodeArray;
		private List<SelectionNode> equippedNodes = new ArrayList<SelectionNode>();

		private SelectionNode currentSelectionNode;


		public SelectionGraph() throws SlickException{

			initializeInventoryNodes();
			initializeEquipmentNodes();


		}

		public void setEquippedSelection(MenuSelection newSelection,
				int locationIndex) {
			
			assignSelectionToNode(newSelection, equippedNodes.get(locationIndex));
			
		}

		

		public void moveCurrentSelection(char xOrY, int direction) {

			int neighborDirection = -1;
			if (xOrY == 'x'){
				if (direction == 1){
					neighborDirection = DIR_RIGHT;
				}else if(direction == -1){
					neighborDirection = DIR_LEFT;
				}
			}else if (xOrY == 'y'){
				if (direction == 1){
					neighborDirection = DIR_DOWN;
				}else if(direction == -1){
					neighborDirection = DIR_UP;
				}
			}

			if(currentSelectionNode.getNeighbor(neighborDirection)!= null){
				currentSelectionNode = currentSelectionNode.getNeighbor(neighborDirection);
				
			}


		}

		public int currentSelectionNodeIndex(){
			return currentSelectionNode.selectionsIndex;
		}


		


		


		
		
		private void initializeEquipmentNodes() throws SlickException {
			
			int xPos = menuRenderX + equipmentMenuOffsetX;
			
			//Refactor: this repeats code from initializeInventoryNodes
			for (int i = 0; i<EQUIP_SLOTS; i++){
				SelectionNode node = new SelectionNode();
				equippedNodes.add(node);

				
				int yPos = menuRenderY + equipmentMenuOffsetY+ imageSizeInPixels*i;
				
				int [] drawPosition = new int[] {xPos,yPos};
				
				
				InventorySelectionGraphics graphics = new InventorySelectionGraphics(null);
				graphics.setDrawPosition(drawPosition);
				
				MenuSelection selection = 
						new MenuSelection(new NullCommand(),
								graphics);
				
				
				selections.add(selection);
				
				node.initialize(true, selections.indexOf(selection), drawPosition);
				
				
				
				
			}
			
			assignEquipmentNodeNeighbors();
			
			
			
		}

		private void assignEquipmentNodeNeighbors() {
			assert inventoryNodeArray.length == inventoryHeightInItems : "Trying to assign equipment slot neighbors without inventory nodes assigned first!";
			assert equippedNodes.size() == EQUIP_SLOTS : "Trying to assign equipment slot neighbors without initializing nodes first!";
			
		
			
			
			for (int i = 0; i<inventoryHeightInItems;i++){
				
				SelectionNode node = equippedNodes.get(i);
				SelectionNode nodeRight = inventoryNodeArray[i][0];
				
				
				
				node.setNeighbor(DIR_RIGHT, nodeRight);
				nodeRight.setNeighbor(DIR_LEFT, node);
						
			}
			
			for (int i = 0; i<EQUIP_SLOTS;i++){

				SelectionNode node = equippedNodes.get(i);
				SelectionNode nodeBelow = equippedNodes.get(posMod(i+1,EQUIP_SLOTS));
				

				node.setNeighbor(DIR_DOWN, nodeBelow);				
				nodeBelow.setNeighbor(DIR_UP, node);

				

			}
			
			
			
			
			
			
			
		}

		public void addInventorySelection(MenuSelection selection){
			
			assert selection.getCommand() != null : "Error! Tried to fill an empty selection with another empty one!";
			
			for (int i = 0; i<inventoryHeightInItems;i++){
				for(int j = 0; j<inventoryWidthInItems; j++){
					if(inventoryNodeArray[i][j].isEmpty){
						
						
						assignSelectionToNode(selection,inventoryNodeArray[i][j]);
						
						inventoryNodeArray[i][j].isEmpty = false;
						return;
						
					}
					
				}
			}
			
		}
			
		private void assignSelectionToNode( MenuSelection selection, SelectionNode node){
			
			int index = node.selectionsIndex;
			int[] drawPosition = node.drawPosition;
			selections.set(index, selection);

			((InventorySelectionGraphics)selection.getGraphics()).setDrawPosition(drawPosition);
			
		}
			

		private void initializeInventoryNodes() throws SlickException{
			inventoryNodeArray = new SelectionNode[inventoryHeightInItems][inventoryWidthInItems];

			
			
			for (int i = 0; i<inventoryHeightInItems;i++){
				for (int j = 0; j<inventoryWidthInItems; j++){
					
					
					SelectionNode nodeIJ = new SelectionNode();
					inventoryNodeArray[i][j] = nodeIJ;
					
					int xPos = menuRenderX + imageSizeInPixels*j;
					int yPos = menuRenderY + imageSizeInPixels*i;
					
					int []drawPosition = new int[] {xPos,yPos};
					
					createAndAssignNullSelection(nodeIJ,drawPosition);
					
					
					
					
					
					


				}
			}

			currentSelectionNode = inventoryNodeArray[0][0];



			assignInventoryNodeNeighbors(inventoryNodeArray);
		}

		private void createAndAssignNullSelection(SelectionNode node,
				int[] drawPosition) throws SlickException {
			InventorySelectionGraphics graphics = new InventorySelectionGraphics(null);
			graphics.setDrawPosition(drawPosition);
			
			MenuSelection selection = 
					new MenuSelection(new NullCommand(),
							graphics);
			selections.add(selection);
			
			node.initialize(true, selections.indexOf(selection), drawPosition);
			
		}

		/* modulo with only positive values */
		private int posMod( int num, int modNum){
			return (((num % modNum ) + modNum) % modNum);
		}

		private void assignInventoryNodeNeighbors(SelectionNode[][] nodeArray) {

			int dim1 = nodeArray.length;
			int dim2 = nodeArray[0].length;



			for (int i = 0; i<(dim1);i++){


				for (int j = 0; j<(dim2);j++){

					SelectionNode central = nodeArray[i][j];



					// The posMod method allows wrapping around
					central.setNeighbor(DIR_DOWN, nodeArray[posMod(i+1,dim1) ][j]);
					central.setNeighbor(DIR_UP, nodeArray[posMod(i-1,dim1)][j]);
					central.setNeighbor(DIR_RIGHT, nodeArray[i][posMod(j+1,dim2)]);
					central.setNeighbor(DIR_LEFT, nodeArray[i][(posMod(j-1,dim2))]);
				}
			}

		}

		

	}

	class SelectionNode{

		public boolean isEmpty;
		public int selectionsIndex;
		public int[] drawPosition;

		private SelectionNode[] neighborNodes = {null,null,null,null};

		public void initialize(boolean isEmpty, int selectionsIndex, int[] drawPosition){
			this.isEmpty = isEmpty;
			this.selectionsIndex = selectionsIndex;
			this.drawPosition = drawPosition;
			
		}

		public void setNeighbor(int direction, SelectionNode neighbor){

			neighborNodes[direction] = neighbor;
		}


		public SelectionNode getNeighbor(int direction){
			return neighborNodes[direction];
		}



	}

}
