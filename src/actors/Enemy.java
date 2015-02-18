package actors;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import world.CollisionHandler;
import actionEngines.EnemyActionEngine;
import commands.DieCommand;
import commands.InputListenerAggregator;
import gameobjects.Broadcaster;
import graphics.ActorGraphics;

public class Enemy extends Actor implements Broadcaster{

	private LemmingBehavior behavior;
	

	public Enemy(int xPixels, int yPixels) throws SlickException {
		super();
		
		Rectangle rect = new Rectangle(xPixels,yPixels,32,32);
		 
		listenerAggregator = new InputListenerAggregator();
				
		status = new Status(rect);
		
		
		graphics = new ActorGraphics("data/dwarf.png", status);

		engine = new EnemyActionEngine(listenerAggregator, status);

		behavior = new LemmingBehavior(status);

		listenerAggregator.addListener(behavior);
		
	}

	public void update(){
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handler not incorporated!";
	}

	@Override
	public void onCollisionDo(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		if (collidingObjectClass.equals(Player.class)){
			status.gainEffect(Effect.EFFECT_COLLIDED_WITH_PLAYER, 1);
		}
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> list = new ArrayList<Command>();
		if (collidingObjectClass.equals(Player.class)){
			list.add( new DieCommand());
		}
		return list;
	}

	

	@Override
	public void setCollisionHandler(CollisionHandler collisionHandler){
		super.setCollisionHandler(collisionHandler);
		behavior.setCollisionHandler(collisionHandler);
	
	}
	





}
