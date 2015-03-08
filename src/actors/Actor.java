package actors;

import java.util.ArrayList;
import java.util.Stack;

import gameobjects.Removeable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import render.ActorRenderer;
import world.CollisionHandler;
import world.ObjectCreator;
import world.Updater;
import abilities.AbilityObject;
import actionEngines.AbilitySlots;
import actionEngines.ActionEngine;
import commands.CollisionCommandProvider;
import commands.CommandProviderAggregator;

public abstract class Actor implements Removeable, Updater,ObjectCreator{

	protected ActorRenderer graphics;
	protected CommandProviderAggregator commandProviderAggregator;
	protected ActionEngine engine;
	protected Status status;
	protected AbilitySlots abilitySlots;
	protected ArrayList<Object> objsToCreate;
	
	
	public Actor() throws SlickException {
		this.objsToCreate = new ArrayList<Object>();
				
	}
	

	public float getX() {return status.getX();}

	public float getY() {return status.getY();}

 
	
	
	public void render( Graphics g, int mapX, int mapY) {
		graphics.render(g,mapX, (int) mapY);
	}
	
	
	
	public void update() throws SlickException {
	
		//Note: The order of these calls is important!
		//Update status
		status.updateEffects();
		
		
		//Do actions (depends on commandProviderAggregator)
		engine.update();
		
	}

	

	public Rectangle getShape() {
		return status.getRect();
	}
	
 
	
	public boolean shouldRemove(){
		return status.hasEffect(Effect.EFFECT_DYING);
	}

	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		throw new UnsupportedOperationException("Not implemented!");
		
	}

	public void setCollisionHandler(CollisionHandler collisionHandler) {

		
		
		status.setCollisionHandler(collisionHandler);
		
		CollisionCommandProvider ccp = new CollisionCommandProvider(collisionHandler,this.getClass(), this.getShape());
		commandProviderAggregator.removeListenersOfClass(ccp.getClass());
		commandProviderAggregator.addProvider(ccp);
	}
	

	



	public boolean hasObjects(){
		return !objsToCreate.isEmpty();
	}
	
	public ArrayList<Object> popObjects() {
		@SuppressWarnings("unchecked")
		ArrayList<Object> output = (ArrayList<Object>) objsToCreate.clone();
		objsToCreate.clear();
		return output;
	}

}