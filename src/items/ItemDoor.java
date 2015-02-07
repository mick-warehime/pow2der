package items;

import gameobjects.BasicObject;
import gameobjects.Interactive;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import world.CollisionHandler;
import actors.Status;

public class ItemDoor extends BasicObject implements Interactive{
	private boolean open = false;

	public ItemDoor(Image doorSprite, int xPos, int yPos) throws SlickException {

		super(doorSprite,xPos,yPos);	

	}

	public boolean canCollide(){
		return !open;
	}

	@Override
	public void interact(int interactionType, Status status) {

		if (interactionType != Interactive.INTERACTION_TOGGLE){return;}
		
		
		if (this.shape.intersects(status.getRect())){return;}
		
		open = !open;

	}

	public void render(Graphics g, int renderX, int renderY){
		if(!open){
			graphics.render(g,renderX, renderY);
		}
	}








}
