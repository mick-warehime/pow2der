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


/* A basic object that can be picked up and put into inventory */

public class Item extends BasicObject implements Interactive{

	protected String name;
	protected int value;
	protected String type;
	protected boolean stackable;
	protected int weight;
	protected ArrayList<String> properties;
	private boolean onGround = true;

	public Item(Map<String, String> itm, Image image, int xPos, int yPos) throws SlickException{		

		super(image,xPos,yPos);

		this.type = itm.get("itemType");		
		
		this.canCollide = false;
		
		
		
	}


	public boolean isOnGround(){
		return onGround;
	}
	

	@Override
	public void render(Graphics g, int renderX, int renderY){
		if(onGround){
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
		assert onGround : "Error! Item receiving an interact command when it's not on the ground!";
		
		status.getInventory().addItem(this);
		this.onGround = false;
		
		

	}
	
	public void drop(float xPos, float yPos){
		this.onGround = true;
		shape.setLocation(xPos, yPos);
	}
	
	
	
	

	

}
