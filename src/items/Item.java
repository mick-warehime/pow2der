package items;


import gameobjects.Interactive;
import gameobjects.Removeable;
import gameobjects.StaticObject;

import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import render.BasicRenderer;
import world.CurrentLevelData;
import actors.Status;


/* A basic object that can be picked up and put into inventory */

public class Item extends StaticObject implements Interactive,Removeable{





	private ItemProperties properties = new ItemProperties();
	private ItemLocation location = new ItemLocation(this);
	private static float SPRITE_DRAW_SCALE = 0.6f;
	private static int SPRITE_MARGIN = 5;
	private static float RECT_SHRINK_MARGIN = 15f;
	
	

	public Item(Map<String, String> itmInfo, Image image, int xPos, int yPos) throws SlickException{		

		super(image,xPos,yPos);

		this.definePropertiesFromMap(itmInfo);

		((BasicRenderer)renderer).setSpriteMargin(SPRITE_MARGIN);

		//Shrink shape because it's based on image size
		shrinkShapes();



		this.canCollide = false;


	}

	private void shrinkShapes(){

		((Rectangle)this.shape).grow(-RECT_SHRINK_MARGIN, -RECT_SHRINK_MARGIN);

	}

	public boolean isOnGround(){
		return location.onGround;
	}


	@Override
	public void render(Graphics g, int renderX, int renderY){

		if(location.onGround){
			((BasicRenderer)renderer).render(g, renderX , renderY, SPRITE_DRAW_SCALE);
		}
	}






	public ItemProperties getProperties(){
		return properties;
	}




	@Override
	public void interact(int interactionType, Status status) {
		if (interactionType != Interactive.INTERACTION_PICKUP){
			return;
		}
		assert location.onGround : "Error! Item receiving an interact command when it's not on the ground!";

		
		Inventory inventory = status.getInventory();
		Shape actorShape = status.getRect();
		location.moveToInventory(inventory, actorShape);

		


	}

	public void drop(){
		location.applyDrop();
	}


	public void setCurrentLevelData(CurrentLevelData currentLevelData) {
		location.currentLevelData = currentLevelData;

	}

	/* Handles item's location */

	public class ItemLocation{



		private Item owningItem;
		private boolean onGround = true;
		private Inventory storingInventory;
		private CurrentLevelData currentLevelData;
		private Shape ownerShape;

		public ItemLocation(Item owner){
			this.owningItem = owner;
		}



		public void applyDrop(){
			
			assert (currentLevelData != null): " Error! Using an item whose current level data hasn't been assigned!";
			this.onGround = true;

			float x = ownerShape.getX();
			float y= ownerShape.getY();
			ownerShape = null; // Prevents memory leak

			shape.setLocation(x, y);
			currentLevelData.getCurrentLevel().addObject(owningItem);
			storingInventory.removeItem(owningItem);
			storingInventory = null; //Prevents memory leak
			
			
			
		}
		
		

		public void moveToInventory(Inventory inventory, Shape actorShape){
			assert (currentLevelData != null): " Error! Using an item whose current level data hasn't been assigned!";
			
			
			this.storingInventory = inventory;
			this.ownerShape = actorShape;
			storingInventory.addItemToInventory(owningItem);
			this.onGround = false;
			this.currentLevelData.getCurrentLevel().removeFromAllLists(owningItem);
			
			


		}



	}


	protected void definePropertiesFromMap(Map<String, String> itmInfo){



		String typeString = itmInfo.get("type");


		properties.parseTypeString(typeString);



		if (itmInfo.containsKey("weight")){
			properties.weight = Float.parseFloat(itmInfo.get("weight")); 
		}else{
			properties.weight = 0f;
		}

		String equipLoc = itmInfo.get("equip");
		
		properties.parseEquipLocationString(equipLoc);
		
	







	}

	/*
	 * Permanent properties of an item
	 * 
	 */
	public class ItemProperties{


		public static final int EQUIP_LOCATION_UNDEFINED = -1;
		public static final int EQUIP_LOCATION_MAINHAND = 0;
		public static final int EQUIP_LOCATION_NECK = 1;
		public static final int EQUIP_LOCATION_CHEST = 2;
		public static final int EQUIP_LOCATION_OFFHAND = 3;
		public static final int EQUIP_LOCATION_HEAD = 4;
		public static final int EQUIP_LOCATION_HANDS = 5;
		public static final int EQUIP_LOCATION_FEET = 6;
		public static final int EQUIP_LOCATION_BACK = 7;
		public static final int EQUIP_LOCATION_FINGER = 8;
		public static final int EQUIP_LOCATION_UNEQUIPPABLE = 9;


		public static final int TYPE_UNDEFINED = -1;
		public static final int TYPE_AMULET = 0;
		public static final int TYPE_CHESTARMOR = 1;
		public static final int TYPE_SHIELD = 2;
		public static final int TYPE_HELM = 3;
		public static final int TYPE_GLOVES = 4;
		public static final int TYPE_CLOAK = 5;
		public static final int TYPE_BOOTS = 6;
		public static final int TYPE_RING = 7;
		public static final int TYPE_WEAPON = 8;
		public static final int TYPE_WEAPONRANGED = 9;
		public static final int TYPE_BOOK = 10;
		public static final int TYPE_POTION = 11;
		public static final int TYPE_SCROLL = 12;
		public static final int TYPE_WAND = 13;
		public static final int TYPE_STAFF = 14;
		public static final int TYPE_BOTTLE = 15;


		public int equipLocation;
		public int type;
		public float weight;
		public boolean stackable;

		public ItemProperties(){

		}

		public void parseTypeString(String typeString) {

			switch (typeString){
			case ("amulet"):
				type = TYPE_AMULET;
			break;
			case ("book"):
				type = ItemProperties.TYPE_BOOK;
			break;
			case ("boots"):
				type = ItemProperties.TYPE_BOOTS;
			break;
			case ("bottle"):
				type = ItemProperties.TYPE_BOTTLE;
			break;
			case ("chestArmor"):
				type = ItemProperties.TYPE_CHESTARMOR;
			break;
			case ("cloak"):
				type = ItemProperties.TYPE_CLOAK;
			break;
			case ("gloves"):
				type = ItemProperties.TYPE_GLOVES;
			break;
			case ("helm"):
				type = ItemProperties.TYPE_HELM;
			break;
			case ("potion"):
				type = ItemProperties.TYPE_POTION;
			break;
			case ("ring"):
				type = ItemProperties.TYPE_RING;
			break;
			case ("scroll"):
				type = ItemProperties.TYPE_SCROLL;
			break;
			case ("shield"):
				type = ItemProperties.TYPE_SHIELD;
			break;
			case ("staff"):
				type = ItemProperties.TYPE_STAFF;
			break;
			case ("wand"):
				type = ItemProperties.TYPE_WAND;
			break;
			case ("weapon"):
				type = ItemProperties.TYPE_WEAPON;
			break;
			case ("rangedWeapon"):
				type = ItemProperties.TYPE_WEAPONRANGED;
			break;
			default: 
				type = ItemProperties.TYPE_UNDEFINED;
				break;
			}

		}

		public void parseEquipLocationString(String location) {

			switch (location){
			case ("neck"):
				equipLocation = ItemProperties.EQUIP_LOCATION_NECK;
			break;
			case ("chest"):
				equipLocation = ItemProperties.EQUIP_LOCATION_CHEST;
			break;
			case ("offHand"):
				equipLocation = ItemProperties.EQUIP_LOCATION_OFFHAND;
			break;
			case ("head"):
				equipLocation = ItemProperties.EQUIP_LOCATION_HEAD;
			break;
			case ("hands"):
				equipLocation = ItemProperties.EQUIP_LOCATION_HANDS;
			break;
			case ("back"):
				equipLocation = ItemProperties.EQUIP_LOCATION_BACK;
			break;
			case ("feet"):
				equipLocation = ItemProperties.EQUIP_LOCATION_FEET;
			break;
			case ("finger"):
				equipLocation = ItemProperties.EQUIP_LOCATION_FINGER;
			break;
			case ("mainHand"):
				equipLocation = ItemProperties.EQUIP_LOCATION_MAINHAND;
			break;
			default: 
				equipLocation = ItemProperties.EQUIP_LOCATION_UNDEFINED;
				break;

			}



		}



	}


	@Override
	public boolean shouldRemove() {
		return !this.isOnGround();
	}



}
