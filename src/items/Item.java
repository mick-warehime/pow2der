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

import actors.Status;

public class Item extends BasicObject implements Interactive{

	protected String name;
	protected Image image;
	protected int value;
	protected String type;
	protected boolean stackable;
	protected int weight;
	protected ArrayList<String> properties;
	private ItemLocation location;

	public Item(Map<String, String> itm, Image image, int xPos, int yPos) throws SlickException{		

		super(image,xPos,yPos);

		this.type = itm.get("itemType");		
		
		this.canCollide = false;
		
//		onGround = true;
		
		this.location = new ItemLocation(this);
		
	}


	public Shape getShape(){
		return shape;
	}

	public void render(Graphics g, int renderX, int renderY){
		if(location.isOnGround()){
			graphics.render(g, renderX, renderY, (float) 0.6);
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
		if (! location.isOnGround()){ return;}
		status.getInventory().addItem(this);
		location.applyPickup( status.getInventory());
		
		
//		onGround = false;

	}
	
	public void drop(float xPos, float yPos){
		location.applyDrop((int) xPos, (int) yPos);
		shape.setLocation(xPos, yPos);
	}
	
	/* Is aware of item's position (if it's on ground) or position of 
	 * object holding it in inventory
	 * 
	 */
	class ItemLocation{
		
		private Item owningItem;
		private boolean onGround = true;
		private int xPos;
		private int yPos;
		private Inventory storingInventory;
		
		public ItemLocation( Item owningItem){
			this.owningItem = owningItem;
			
			
		}
		
		
		public void applyDrop(int xPos, int yPos){
			/* No longer track position of previously holding object */
			this.xPos = xPos;
			this.yPos = yPos;
			this.onGround = true;
			
			if (storingInventory !=null){
				storingInventory.removeItem(owningItem);
				storingInventory = null;
			}
			
		}
		public void applyPickup( Inventory storingInventory ){
			this.onGround = false;
			
			
			this.storingInventory = storingInventory;
		}
		
		public boolean isOnGround(){
			return onGround;
		}
		
		
		
		
	}

}
