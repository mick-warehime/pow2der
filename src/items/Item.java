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
	protected Image sprite;
	protected Integer value;
	protected String type;
	protected boolean stackable;
	protected Integer weight;
	protected ArrayList<String> properties;
	private ItemLocation location;

	public Item(Map<String, String> itm, Image sprite, ItemLocation location) throws SlickException{		

		super(sprite,location.getX(),location.getY());

		this.type = itm.get("itemType");		

		this.location = location;
		this.canCollide = false;
		
	}


	public Shape getShape(){
		return shape;
	}

	public void render(Graphics g, int renderX, int renderY){
		if(location.onGround()){
			graphics.render(g, renderX, renderY, (float) 0.6);
		}
	}
	

	public void setLocation(ItemLocation location){
		this.location = location;
	}

	public ItemLocation getLocation(){
		return location;
	}

	public String getItemType() {
		return type;
	}

	public Integer getValue() {
		return value;
	}

	public Image getSprite(){
		return sprite;
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
		Inventory inventory = status.getInventory();
		inventory.addItem(this);
		location.pickedUp(inventory);
		
		

	}

}
