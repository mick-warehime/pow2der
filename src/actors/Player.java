package actors;


import items.Inventory;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import render.ActorRenderer;
import world.CollisionHandler;
import actionEngines.AbilitySlots;
import actionEngines.PlayerActionEngine;
import commands.CommandProviderAggregator;
import controls.DirectionCommandProvider;
import controls.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	

	public Player(int [] mousePosition) throws SlickException {

 			
		
		
		
		
		
		
		Rectangle rect = new Rectangle(0f, 0f, 28, 28);
		status = new Status(rect);
		
		keyboard = new KeyboardInputListener();
		DirectionCommandProvider directionProvider = new DirectionCommandProvider(rect,mousePosition);
		
		commandProviderAggregator = new CommandProviderAggregator();
		commandProviderAggregator.addProvider(keyboard);
		commandProviderAggregator.addProvider(directionProvider);
		
		

		abilitySlots = new AbilitySlots();

		engine = new PlayerActionEngine(commandProviderAggregator,status, abilitySlots, objsToCreate);
		
		this.graphics = new ActorRenderer("data/dwarf2.png", status);
		
		
	}

	

	
	

	

	

	public KeyboardInputListener getKeyboardListener() {
		return keyboard;
	}
	
	
	
	public void setPosition(int startX, int startY){		
		status.setX(startX);
		status.setY(startY);
	}




	public Inventory getInventory() {
		
		return status.getInventory();
	}

	



	@Override
	public void setCollisionHandler(CollisionHandler collisionHandler){
		super.setCollisionHandler(collisionHandler);
		collisionHandler.addPlayerRect(status.getRect());
		
		
	}










	public Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}










	

	



}
