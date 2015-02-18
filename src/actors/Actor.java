package actors;

import graphics.ActorGraphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import world.CollisionHandler;
import actionEngines.ActionEngine;
import commands.InputListenerAggregator;

public abstract class Actor {

	protected ActorGraphics graphics;
	protected InputListenerAggregator listenerAggregator;
	protected ActionEngine engine;
	protected Status status;
	
	
	public Actor() throws SlickException {
				
	}
	

	public float getX() {return status.getX();}

	public float getY() {return status.getY();}

 
	
	public void render( Graphics g, int mapX, int mapY) {
		graphics.render(g,mapX, (int) mapY);
	}
	
	
	
	public void update() {
	
		//Note: The order of these calls is important!
		//Update status
		status.updateEffects();
		
		
		//Do actions (depends on listenerAggregator)
		engine.update();
		
		
	}

	
	public boolean canCollide(){
		return true;
	}

	public Rectangle getShape() {
		return status.getRect();
	}
	
 
	
	public boolean isDying(){
		return status.isDying();
	}

	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		throw new UnsupportedOperationException("Not implemented!");
		
	}



}