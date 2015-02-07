package actors;


import java.util.logging.Level;

import items.Inventory;
import graphics.ActorGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import world.CollisionHandler;
import actionEngines.PlayerActionEngine;
import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	private Rectangle rect;
	

	public Player(world.Level level, CollisionHandler collisionHandler) throws SlickException {

 		
//		image = new Image("data/head.png");		
		
		keyboard = new KeyboardInputListener();
		listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addListener(collisionHandler);
		listenerAggregator.addListener(keyboard);
		
		
		setStatus( level.getStartX(),level.getStartY(), collisionHandler);

		collisionHandler.addPlayerRect(status.getRect());

		engine = new PlayerActionEngine(listenerAggregator,status);
		
		this.graphics = new ActorGraphics("data/dwarf.png", status);
	}

	

	
	public void setStatus(int x, int y, CollisionHandler collisionHandler){
				
		
		
		// GETS starting position from map
		// shifts initial y position by 1 pixel upwards otherwise dude gets stuck
				
		rect = new Rectangle((float) x, (float) y-1, 32, 32);
		status = new Status(rect);
		status.setCollisionHandler(collisionHandler);
		
	}

	

	public void update(){
		super.update();
		
	}

	public KeyboardInputListener getListener() {
		return keyboard;
	}
	
	public void resetPlayer(int startX, int startY){
		status.setAlive();
		status.setX(startX);
		status.setY(startY);
	}
	
	public void placePlayer(int startX, int startY){		
		status.setX(startX);
		status.setY(startY);
	}




	public Inventory getInventory() {
		
		return status.getInventory();
	}

	

	



}
