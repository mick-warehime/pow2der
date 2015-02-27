package world;

import graphics.LevelGraphics;
import items.Item;
import items.ItemBuilder;
import items.ItemParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.xml.sax.SAXException;

import actors.Actor;
import actors.Player;
import menus.MenuHandler;
import controls.GameControls;

public class World {
	
	private List<Level> levels = new ArrayList<Level>();
	private Player terri;
	private CollisionHandler collisionHandler;
	private SpriteSheet spriteSheet;	
	private ItemBuilder itemBuilder;
	private LevelBuilder levelBuilder;
	
	private CurrentLevelData currentLevelData = new CurrentLevelData();
	
	private LevelGraphics levelGraphics;
	
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	public World() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{
	
		
		// construct item builders
						
		ItemParser parser = new ItemParser("items/items.xml");
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");
		
		this.levelBuilder = new LevelBuilder();
		levelBuilder.newLevel2(31,21);
		
		terri = new Player();
		
		newLevel(50,50);
		
		currentLevelData.setCurrentLevel(levels.get(0));
	
//		itemBuilder.testItems();
		

	}
	

	public void render(Graphics graphics) {
		
		 
		// draw current level
		levelGraphics.render(graphics,(int) terri.getX(),(int)terri.getY());
		
		// draw level items/objects
		Level currentLevel = currentLevelData.getCurrentLevel();
		currentLevel.render(graphics,levelGraphics.getOffsetX(),levelGraphics.getOffsetY());
		
		// draw player
		terri.render(graphics,levelGraphics.getOffsetX(),levelGraphics.getOffsetY());

	}


	public void update() throws SlickException {
		terri.update();
		
		
		currentLevelData.getCurrentLevel().update();
		
		if (terri.isDying()){
			System.out.println("Terri is dead!");
		}
		
		
	}
	
	public void newLevel(int levelWidth, int levelHeight) throws SlickException{
		
		Level newLevel = new Level(itemBuilder,levelWidth,levelHeight);
		
		newLevel.assignToItems(currentLevelData);
		
		levelGraphics = new LevelGraphics(newLevel,levelWidth,levelHeight);
		
		levels.add(newLevel);		
		
		CollisionHandler collisionHandler = new CollisionHandler(newLevel);
		for (Actor dude : newLevel.getActors()){
			dude.setCollisionHandler(collisionHandler);
		}
		
		terri.placePlayer(newLevel.getStartX(),  newLevel.getStartY());
		terri.setCollisionHandler(collisionHandler);
		
//		
	}
	
	
	public Player getPlayer(){
		return terri;
	}


	
	
	

}
