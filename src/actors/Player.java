package actors;


import items.Inventory;
import graphics.ActorGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import world.CollisionHandler;
import world.Level;
import actionEngines.AbilitySlots;
import actionEngines.PlayerActionEngine;
import commands.CollisionCommandProvider;
import commands.CommandProviderAggregator;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	

	public Player() throws SlickException {

 			
		
		keyboard = new KeyboardInputListener();
		commandProviderAggregator = new CommandProviderAggregator();
		commandProviderAggregator.addProvider(keyboard);
		
		
		Rectangle rect = new Rectangle(0f, 0f, 28, 28);
		status = new Status(rect);

		abilitySlots = new AbilitySlots();

		engine = new PlayerActionEngine(commandProviderAggregator,status, abilitySlots);
		
		this.graphics = new ActorGraphics("data/dwarf2.png", status);
		
		
	}

	

	
	

	

	

	public KeyboardInputListener getListener() {
		return keyboard;
	}
	
	
	
	public void placePlayer(int startX, int startY){		
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

	



}
