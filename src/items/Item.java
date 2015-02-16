package items;


import gameobjects.BasicObject;
import gameobjects.Interactive;

import java.util.ArrayList;
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

	protected String name;
	protected int value;
	protected String type;
	protected boolean stackable;
	protected int weight;
	protected ArrayList<String> properties;
	private ItemLocation location = new ItemLocation(this);

	public Item(Map<String, String> itmInfo, Image image, int xPos, int yPos) throws SlickException{		

		super(image,xPos,yPos);

		this.type = itmInfo.get("itemType");		
		
		this.canCollide = false;
		
		
		
		
	}


	public boolean isOnGround(){
		return location.onGround;
	}
	

	@Override
	public void render(Graphics g, int renderX, int renderY){
		if(location.onGround){
			graphics.render(g, renderX, renderY, (float) 1);
		}
	}
	
	

	public String getItemType() {
		return type;
	}

	public Integer getValue() {
		return value;
	}

	

	public void addProperty(String prop){
		properties.add(prop);
	}

	public ArrayList<String> getProperties(){
		return properties;
	}

	public boolean stackable() {
		return stackable;
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
	
	class ItemLocation{
		
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
	
	

	

}
