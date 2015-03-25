package actors;

import java.io.IOException;
import java.util.Random;

import knowledge.Knowledge;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import pathfinding.Mover;
import render.EnemyRenderer;
import abilities.FireballAbility;
import actionEngines.AbilitySlots;
import actionEngines.EnemyActionEngine;
import commands.CommandProviderAggregator;

public class Enemy extends Actor implements Mover{

	private EnemyBehavior behavior;
	private EnemyRenderer enemyGraphics;
	private int enemyID;
	
	public Enemy(int xPixels, int yPixels, Player player) throws SlickException {
		super();
		
		int numEnemies = 2;
		Random rand = new Random();
		enemyID = rand.nextInt(numEnemies);
//		enemyID = 0;
		
		Rectangle rect = new Rectangle(xPixels,yPixels,32,32);
		
		commandProviderAggregator = new CommandProviderAggregator();
				
		status = new Status(rect);
		status.incrementHP(8);
		
		abilitySlots = new AbilitySlots();
//		if(enemyID==1){
//		
		abilitySlots.setAbility(new FireballAbility(),0);
//		}else{
//			abilitySlots.setAbility(new LightningAbility(),0);
//		}
			
		
		enemyGraphics = new EnemyRenderer("data/monster_bright.png", status,enemyID);

		behavior = new EnemyBehavior(status, new Knowledge(player,status), enemyID);
		
		engine = new EnemyActionEngine(commandProviderAggregator, status, abilitySlots,objsToCreate,behavior.getBehaviorProfile());
		
		commandProviderAggregator.addUniqueProvider(behavior);
		
	}

	public void update() throws SlickException, IOException{
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handlers not incorporated!";
	}

	
	public void render( Graphics g, int offsetX, int offsetY) {
		enemyGraphics.render(g,offsetX, (int) offsetY);
		behavior.render(g,offsetX, (int) offsetY);
	}
	

	

	
	





}
