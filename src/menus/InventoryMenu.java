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
	private int equipableSelections = 10;
	private int imageSizeInPixels = 48;
	private SelectionGraph selectionGraph;



	public InventoryMenu( int menuRenderX, int menuRenderY) throws SlickException {
		super(Menu.MENU_INVENTORY, menuRenderX, menuRenderY);

		

	}





	@Override
	public void incrementActiveSelection(char xOrY, int direction) {

		selectionGraph.moveCurrentSelection(xOrY, direction);

		//		int menuX = indexToMenuX(currentSelection);
		//		int menuY = indexToMenuY(currentSelection);
		//
		//		if (xOrY == 'x'){
		//			if (direction>0){
		//				menuX +=1;
		//			}
		//			else {
		//				menuX -=1;
		//			}
		//			menuX = menuX % (inventoryWidthInItems);
		//			if (menuX <0){ 
		//				menuX = inventoryWidthInItems+menuX;
		//			}
		//		}
		//		if (xOrY == 'y'){
		//			if (direction>0){
		//				menuY +=1;
		//			}
		//			else {
		//				menuY -=1;
		//			}
		//			menuY = menuY % (inventoryHeightInItems);
		//			if (menuY <0){ 
		//				menuY = inventoryHeightInItems+menuY;
		//			}
		//		}
		//
		//
		//		currentSelection = menuXYToIndex(menuX, menuY);

	}

	public void setInventory(Inventory playerInventory) throws SlickException {

		defineSelectionsFromInventory(playerInventory);

	}

	private void defineSelectionsFromInventory(Inventory playerInventory) throws SlickException {
		selections = new ArrayList<MenuSelection>();
		
		this.selectionGraph = new SelectionGraph();

		ArrayList<Item> equippedItems = playerInventory.getEquipped();
		
		for (Item item : playerInventory.getItems()){
			
			if (equippedItems.contains(item)){
//				addEquippedItemSelection(item);
			}else{
				
				MenuSelection newSelection =generateMenuSelection(item);
				
				selectionGraph.replaceFirstEmptyInventorySelection(newSelection);
				

			}
		}
		
		
		






	}

	private MenuSelection generateMenuSelection(Item item) throws SlickException{
		Command menuCmd = new MenuOpenCommand(new ItemMenu(menuRenderX - 100,menuRenderY, item));
		InventorySelectionGraphics graphics = new InventorySelectionGraphics(item.getImage());
		
		return new MenuSelection(menuCmd,graphics);
		
	}
	



	
	

	class SelectionGraph {

		private static final int INDEX_UNASSIGNED = -1;

		private static final int DIR_LEFT = 0;
		private static final int DIR_UP = 1;
		private static final int DIR_RIGHT = 2;
		private static final int DIR_DOWN = 3;




		SelectionNode[][] inventoryNodeArray;
		private List<SelectionNode> equippedNodes = new ArrayList<SelectionNode>();

		private SelectionNode currentSelectionNode;


		public SelectionGraph() throws SlickException{

			initializeGraphs();

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

			currentSelectionNode = currentSelectionNode.getNeighbor(neighborDirection);
			currentSelection = currentSelectionNode.selectionsIndex;


		}




		


		

		private void initializeGraphs() throws SlickException {

			initializeInventoryNodes();
			initializeEquipmentNodes();


		}

		
		
		private void initializeEquipmentNodes() {
			
			
			
		}

		public void replaceFirstEmptyInventorySelection(MenuSelection selection){
			
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

			int dim1 = inventoryNodeArray.length;
			int dim2 = inventoryNodeArray[0].length;
			
			for (int i = 0; i<dim1;i++){
				for (int j = 0; j<dim2; j++){
					
					SelectionNode nodeIJ = new SelectionNode(INDEX_UNASSIGNED);
					inventoryNodeArray[i][j] = nodeIJ;

					int xPos = menuRenderX + imageSizeInPixels*j;
					int yPos = menuRenderY + imageSizeInPixels*i;
					
					nodeIJ.drawPosition = new int[] {xPos,yPos};
					
					
					InventorySelectionGraphics graphics = new InventorySelectionGraphics(null);
					graphics.setDrawPosition(nodeIJ.drawPosition);
					
					MenuSelection selection = 
							new MenuSelection(new NullCommand(),
									graphics);
					
					
					
					selections.add(selection);
					
					inventoryNodeArray[i][j].selectionsIndex = selections.indexOf(selection);
					inventoryNodeArray[i][j].isEmpty = true;
					
					


				}
			}

			currentSelectionNode = inventoryNodeArray[0][0];



			assignNeighbors(inventoryNodeArray);
		}


		private int posMod( int num, int modNum){
			return (((num % modNum ) + modNum) % modNum);
		}

		private void assignNeighbors(SelectionNode[][] nodeArray) {

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

		public SelectionNode( int selectionsIndex){

			this.selectionsIndex = selectionsIndex;
		}

		public void setNeighbor(int direction, SelectionNode neighbor){

			neighborNodes[direction] = neighbor;
		}


		public SelectionNode getNeighbor(int direction){
			return neighborNodes[direction];
		}



	}

}
