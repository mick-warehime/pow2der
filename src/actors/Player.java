package actors;


import graphics.ActorGraphics2;
import graphics.PlayerGraphics;
import main.Level;
import main.CollisionHandler;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	private Rectangle rect;
	private ActorGraphics2 testGraphics;
	

	public Player(int x, int y, CollisionHandler collisionHandler, int[] mousePos) throws SlickException {

 		
//		sprite = new Image("data/head.png");		
		
		keyboard = new KeyboardInputListener();
		listener = new GlobalInputListener();
		listener.addProvider(collisionHandler);
		listener.addProvider(keyboard);
		
		
		setStatus( x,y, collisionHandler);

		collisionHandler.addPlayerRect(status.getRect());

		engine = new PlayerActionEngine(listener,status);
		
		this.graphics = new PlayerGraphics(status,"data/head.png");
		
		testGraphics = new ActorGraphics2("data/dwarf3.png", status);
	}

	public void render( Graphics g, int mapX, int mapY) {
//		((PlayerGraphics)graphics).render(g,(int) status.getX()-mapX, (int) status.getY() - mapY);
		testGraphics.render(g,(int) status.getX()-mapX, (int) status.getY() - mapY );
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
