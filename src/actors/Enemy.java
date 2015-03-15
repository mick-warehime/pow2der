package actors;

import java.io.IOException;
import java.util.Random;

import knowledge.Knowledge;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import pathfinding.Mover;
import render.ActorRenderer;
import render.EnemyRenderer;
import world.Level;
import abilities.FireballAbility;
import abilities.LightningAbility;
import actionEngines.AbilitySlots;
import actionEngines.EnemyActionEngine;
import commands.CommandProviderAggregator;

public class Enemy extends Actor implements Mover{

	private EnemyBehavior behavior;
	private EnemyRenderer enemyGraphics;
	private int enemyID;
	
	public Enemy(int xPixels, int yPixels, Level level, Player player) throws SlickException {
		super();
		
		int numEnemies = 2;
		Random rand = new Random();
		enemyID = rand.nextInt(numEnemies);
		
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

		engine = new EnemyActionEngine(commandProviderAggregator, status, abilitySlots,objsToCreate);

		behavior = new EnemyBehavior(status, new Knowledge(this,player,level),enemyID);

		commandProviderAggregator.addProvider(behavior);
		
	}

	public void update() throws SlickException, IOException{
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handlers not incorporated!";
	}

	
	public void render( Graphics g, int offsetX, int offsetY) {
		enemyGraphics.render(g,offsetX, (int) offsetY);
	}
	

	

	
	





}
