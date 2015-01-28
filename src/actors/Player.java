package actors;


import graphics.ActorGraphics;
import main.Level;
import main.CollisionHandler;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.InputListenerAggregator;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	private Rectangle rect;
	private ActorGraphics testGraphics;
	

	public Player(int x, int y, CollisionHandler collisionHandler, int[] mousePos) throws SlickException {

 		
//		sprite = new Image("data/head.png");		
		
		keyboard = new KeyboardInputListener();
		listenerAggregator = new InputListenerAggregator();
		listenerAggregator.addProvider(collisionHandler);
		listenerAggregator.addProvider(keyboard);
		
		
		setStatus( x,y, collisionHandler);

		collisionHandler.addPlayerRect(status.getRect());

		engine = new PlayerActionEngine(listenerAggregator,status);
		
		this.graphics = new ActorGraphics("data/dwarf3.png", status);
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

	

	



}
