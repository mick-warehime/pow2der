package actors;

import interfaces.Collider;
import interfaces.ObjectCreator;
import interfaces.Removeable;
import interfaces.Updater;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import render.ActorRenderer;
import actionEngines.AbilitySlots;
import actionEngines.ActionEngine;
import collisions.BroadcasterCommandProvider;
import collisions.ContextualCollisions;
import collisions.PhysicalCollisions;
import commands.CommandProviderAggregator;

public abstract class Actor implements Removeable, Updater, ObjectCreator, Collider{

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
	
	public float getCenterX() {return status.getRect().getX()+status.getRect().getWidth()/2;}

	public float getCenterY() {return status.getRect().getY()+status.getRect().getHeight()/2;}

	
	public void render( Graphics g, int offsetX, int offsetY) {
		graphics.render(g,offsetX, (int) offsetY);
	}
	
	
	
	public void update() throws SlickException, IOException {
	
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

	

	
	

	
	public void assignPhysicalCollisions(PhysicalCollisions detector){
		status.setCollisionDetector(detector);
		
	}
	
	public void assignContextualCollisions(ContextualCollisions contextuals){
		BroadcasterCommandProvider bcp = new BroadcasterCommandProvider(this.getClass(), this.getShape());

		commandProviderAggregator.addUniqueProvider(bcp);

		contextuals.addListener(bcp);
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
	
	public void onRemoveDo(){
		
		BroadcasterCommandProvider BCP = (BroadcasterCommandProvider) commandProviderAggregator.getProviderOfClass(BroadcasterCommandProvider.class);
		
		BCP.setForRemoval();
		
	}

}