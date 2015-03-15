package actors;

import java.io.IOException;

import knowledge.Knowledge;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import pathfinding.Mover;
import render.ActorRenderer;
import world.Level;
import abilities.FireballAbility;
import actionEngines.AbilitySlots;
import actionEngines.EnemyActionEngine;
import commands.CommandProviderAggregator;

public class Enemy extends Actor implements Mover{

	private EnemyBehavior behavior;

	public Enemy(int xPixels, int yPixels, Level level, Player player) throws SlickException {
		super();
		
		Rectangle rect = new Rectangle(xPixels,yPixels,32,32);
		
		commandProviderAggregator = new CommandProviderAggregator();
				
		status = new Status(rect);
		
		abilitySlots = new AbilitySlots();
		abilitySlots.setAbility(new FireballAbility(),0);
		
		graphics = new ActorRenderer("data/dwarf.png", status);

		engine = new EnemyActionEngine(commandProviderAggregator, status, abilitySlots,objsToCreate);

		behavior = new EnemyBehavior(status, new Knowledge(this,player,level));

		commandProviderAggregator.addProvider(behavior);
		
	}

	public void update() throws SlickException, IOException{
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handlers not incorporated!";
	}

	

	

	

	
	





}
