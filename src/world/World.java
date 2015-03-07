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
	public static SpriteSheet spriteSheet;

	private List<Level> levels = new ArrayList<Level>();
	private Player terri;
	private ItemBuilder itemBuilder;
	
	private CurrentLevelData currentLevelData = new CurrentLevelData();
	
	private LevelGraphics levelGraphics;
	
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	public World() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{
		
		 spriteSheet = new SpriteSheet("data/metroidtiles.png",16,16);
		
		// construct item builders
						
		ItemParser parser = new ItemParser();
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");
				
		terri = new Player();
		
		// width and height must be ODD
		newLevel(31,21);
		
		currentLevelData.setCurrentLevel(levels.get(0));
	
		

	}
	

	public void render(Graphics graphics) {
		
		 
		// draw current level
		levelGraphics.render((int) terri.getX(),(int)terri.getY());
		
		// draw level items/objects
		Level currentLevel = currentLevelData.getCurrentLevel();
		currentLevel.render(graphics,levelGraphics.getOffsetX(),levelGraphics.getOffsetY());
		

	}


	public void update() throws SlickException {
		
		
		currentLevelData.getCurrentLevel().update();
		
		if (terri.shouldRemove()){
			System.out.println("Terri is dead!");
		}
		
		
	}
	
	public void newLevel(int levelWidth, int levelHeight) throws SlickException{
		
		Level level = new Level(itemBuilder, levelWidth,levelHeight);
		
		level.assignToItems(currentLevelData);
		
		levelGraphics = new LevelGraphics(level);
		
		levels.add(level);		
		
		CollisionHandler collisionHandler = new CollisionHandler(level);
		for (Actor dude : level.getActors()){
			dude.setCollisionHandler(collisionHandler);
		}
		
		terri.setPosition(level.getStartX(),  level.getStartY());
		terri.setCollisionHandler(collisionHandler);
		level.addObject(terri);
	
	}
	
	
	public Player getPlayer(){
		return terri;
	}


	
	
	

}
