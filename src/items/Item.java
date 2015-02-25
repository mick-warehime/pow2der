package items;


import gameobjects.BasicObject;
import gameobjects.Interactive;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import world.CurrentLevelData;
import actors.Status;


/* A basic object that can be picked up and put into inventory */

public class Item extends BasicObject implements Interactive{

	public static final int PROPERTY_EQUIP_LOCATION = 2;
	public static final int PROPERTY_TYPE = 1;
	public static final int PROPERTY_WEIGHT = 0;
	
	public static final int TYPE_ARMOR = 0;
	public static final int TYPE_WEAPON = 1;
	public static final int TYPE_BOOK = 2;
	public static final int TYPE_POTION = 3;
	
	
	protected String name;
	protected int value;
	
	protected boolean stackable;
	protected int weight;
	protected Hashtable<Integer, Object> properties;
	private ItemLocation location = new ItemLocation(this);
	private static float SPRITE_DRAW_SCALE = 0.6f;
	private static int SPRITE_MARGIN = 5;
	private static float RECT_SHRINK_MARGIN = 15f;

	public Item(Map<String, String> itmInfo, Image image, int xPos, int yPos) throws SlickException{		

		super(image,xPos,yPos);
		
		this.definePropertiesFromMap(itmInfo);
		
		this.graphics.setSpriteMargin(SPRITE_MARGIN);
		
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
			graphics.render(g, renderX , renderY, SPRITE_DRAW_SCALE);
		}
	}
	
	

	

	
	public Object getProperty(Integer propertyIndex){
		
		return properties.get(propertyIndex);
		
	}
	

	

	@Override
	public void interact(int interactionType, Status status) {
		if (interactionType != Interactive.INTERACTION_PICKUP){
			return;
		}
		assert location.onGround : "Error! Item receiving an interact command when it's not on the ground!";
		
		Inventory inventory = status.getInventory();
		Shape actorShape = status.getRect();
		location.applyPickup(inventory, actorShape);
		
		this.location.onGround = false;
		

	}
	
	public void drop(){
		location.applyDrop();
	}

	
	public void setCurrentLevelData(CurrentLevelData currentLevelData) {
		location.currentLevelData = currentLevelData;
		
	}
	
	/* Handles item's location */
	
	public class ItemLocation{
		
		public static final int EQUIP_LOCATION_MAINHAND = 0;
		public static final int EQUIP_LOCATION_NECK = 1;
		public static final int EQUIP_LOCATION_CHEST = 2;
		public static final int EQUIP_LOCATION_OFFHAND = 3;
		public static final int EQUIP_LOCATION_HEAD = 4;
		public static final int EQUIP_LOCATION_HANDS = 5;
		public static final int EQUIP_LOCATION_FEET = 6;
		public static final int EQUIP_LOCATION_BACK = 7;
		public static final int EQUIP_LOCATION_FINGER = 8;
		
		private Item owningItem;
		private boolean onGround = true;
		private Inventory storingInventory;
		private CurrentLevelData currentLevelData;
		private Shape ownerShape;
		
		public ItemLocation(Item owner){
			this.owningItem = owner;
		}
		
		
		
		public void applyDrop(){
			this.onGround = true;
			
			float x = ownerShape.getX();
			float y= ownerShape.getY();
			ownerShape = null; // Prevents memory leak
			
			shape.setLocation(x, y);
			currentLevelData.getCurrentLevel().addObject(owningItem);
			storingInventory.removeItem(owningItem);
			storingInventory = null; //Prevents memory leak
		}
		
		public void applyPickup(Inventory inventory, Shape actorShape){
			this.storingInventory = inventory;
			this.ownerShape = actorShape;
			storingInventory.addItem(owningItem);
			
			
		}
		
		
		
	}
	
	
	protected void definePropertiesFromMap(Map<String, String> itmInfo){
		properties = new Hashtable<Integer, Object>();
		
		String type = itmInfo.get("itemType");
		
		properties.put(PROPERTY_TYPE, type);
		
		if (itmInfo.containsKey("weight")){
			float weight = Float.parseFloat(itmInfo.get("weight"));
			properties.put(PROPERTY_WEIGHT, weight);
		}
		else{
			properties.put(PROPERTY_WEIGHT,0f);
		}
		
		if (itmInfo.containsKey("equip")){
			properties.put(PROPERTY_EQUIP_LOCATION,itmInfo.get("equip") );
		}
		
		
		
		
		
		
		
		
	}

	

}
