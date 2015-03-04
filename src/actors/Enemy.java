package actors;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import render.ActorRenderer;
import world.CollisionHandler;
import actionEngines.AbilitySlots;
import actionEngines.EnemyActionEngine;
import commands.CollisionCommandProvider;
import commands.DieCommand;
import commands.CommandProviderAggregator;
import gameobjects.Broadcaster;

public class Enemy extends Actor implements Broadcaster{

	private EnemyBehavior behavior;
	

	public Enemy(int xPixels, int yPixels) throws SlickException {
		super();
		
		Rectangle rect = new Rectangle(xPixels,yPixels,32,32);
		 
		commandProviderAggregator = new CommandProviderAggregator();
				
		status = new Status(rect);
		
		abilitySlots = new AbilitySlots();
		
		
		graphics = new ActorRenderer("data/dwarf.png", status);

		engine = new EnemyActionEngine(commandProviderAggregator, status, abilitySlots,objsToCreate);

		behavior = new EnemyBehavior(status);

		commandProviderAggregator.addProvider(behavior);
		
	}

	public void update() throws SlickException{
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
		ArrayList<Command> outputCommands = new ArrayList<Command>();
//		if (collidingObjectClass.equals(Player.class)){
//			outputCommands.add( new DieCommand());
//		}
		return outputCommands;
	}

	

	
	





}
