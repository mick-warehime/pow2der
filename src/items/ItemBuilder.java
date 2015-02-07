package items;

import gameobjects.BasicObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import world.CollisionHandler;

public class ItemBuilder {

	private List<Map<String,String>> itemMaps;
	private HashMap<String,ArrayList<Integer>> itemTypeMap = new HashMap<String, ArrayList<Integer>>();
	private ArrayList<String> itemTypes = new ArrayList<String>();


	private SpriteSheet spriteSheet;
	private static int SPRITEWIDTHPIXELS = 48;
	private static int SPRITEHEIGHTPIXELS = 48;



	public ItemBuilder(List<Map<String,String>> itemMaps, String spriteSheetFileName) throws SlickException{

		this.spriteSheet = new SpriteSheet(spriteSheetFileName, SPRITEWIDTHPIXELS,SPRITEHEIGHTPIXELS);

		this.itemMaps = itemMaps;

		initializeBuilder();

		// no idea why this is -1 here
		
		// item types [armor, weapons, books, misc]


	}


	public Item newItem(Shape shape) throws SlickException {
		
		return newItem((int) shape.getX(),(int) shape.getY());
	}


	public Item newItem(int xPos, int yPos) throws SlickException{
		// build a random item
		int itemIndex =  (int)(Math.random()*itemMaps.size());

		return buildItem(itemIndex, xPos, yPos);
	}


	public Item newItem(String typeOrName, int xPos, int yPos) throws SlickException{

		int itemIndex = -1;

		// get a random item of the input type
		if(itemTypes.contains(typeOrName)){

			ArrayList<Integer> index = itemTypeMap.get(typeOrName);

			int randomNum = (int)(Math.random()*index.size());
			itemIndex = index.get(randomNum);

		}
		// or get an item by name
		else{
			for (int i = 0; i < itemMaps.size(); i++){

				// get the indices of all items with type t
				if(typeOrName.equalsIgnoreCase(itemMaps.get(i).get("name"))){
					itemIndex = i;
				}

			}

		}		

		return buildItem(itemIndex,xPos,yPos);

	}
	
	

	private Item buildItem(int i, int xPos, int yPos) throws SlickException{

		// builds the ith item in the vector list
		Map<String,String> itm = itemMaps.get(i);

		// load image

		Integer row = (int) Float.parseFloat(itm.get("spriteRow"));
		Integer col = (int) Float.parseFloat(itm.get("spriteCol"));

		Image sprite = spriteSheet.getSubImage(col-1,row-1);

		if(itm.get("itemType").equalsIgnoreCase("armor")){
			Item item = new Armor(itm, sprite, xPos, yPos);
			return item;
		}else if(itm.get("itemType").equalsIgnoreCase("weapons")){
			Item item = new Weapon(itm, sprite, xPos, yPos);			

			return item;
		}
		else if(itm.get("itemType").equalsIgnoreCase("misc")){
			Item item = new Misc(itm, sprite, xPos, yPos);
			return item;
		}
		else if (itm.get("itemType").equalsIgnoreCase("books")){
			Item item = new Book(itm, sprite, xPos, yPos);
			return item;
		}
		System.out.println("no item built!" +"  item index: "+i+" x: "+xPos+" y: "+yPos);

		return null;

	}


	// this finds the indices of all the items of all the various types
	// too allow for faster searching when a new item is needed
	private void initializeBuilder(){
		// loop over all items and pull out the item types

		// create a list of the itemTypes
		for (Map<String,String> m : itemMaps ){

			String type = m.get("itemType");

			if(!itemTypes.contains(type)){
				itemTypes.add(type);
			}
		}

		// create a look-up table for the item type
		// create a list of the itemTypes
		for (String type : itemTypes){
			ArrayList<Integer> index = new ArrayList<Integer>();

			for (int i = 0; i < itemMaps.size(); i++){

				// get the indices of all items with type t
				if(type.equalsIgnoreCase(itemMaps.get(i).get("itemType"))){
					index.add(i);
				}

			}
			itemTypeMap.put(type,index);
		}


 


	}

	public void testItems() throws SlickException{
		
		// test all item builds
		for (int i = 0; i<itemMaps.size(); i++){
			Map<String,String> itm = itemMaps.get(i);
			System.out.print(itm.get("type")+": "+itm.get("name"));
			buildItem(i,400,800);
			System.out.print(" --> Built Sucessfully");
			System.out.println();
			System.out.println("no item built!" +"  item index: "+i);

			
		}
		
		System.out.println("All Items Built Successfully!");
		
	}



}
