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
	protected Boolean onGround;

	public Item(Map<String, String> itm, Image sprite, int xPos, int yPos) throws SlickException{		

		super(sprite,xPos,yPos);

		this.type = itm.get("itemType");		
		
		this.canCollide = false;
		
		onGround = true;
		
	}


	public Shape getShape(){
		return shape;
	}

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
		status.getInventory().addItem(this);
		
		onGround = false;

	}
	
	public void drop(Status status){
		onGround = true;
		shape.setLocation(status.getX(), status.getY());
	}

}
