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

import actors.Player;
import menus.MenuHandler;
import controls.GameControls;

public class World {
	
	private List<Level> levels = new ArrayList<Level>();
	private Player terri;
	private CollisionHandler collisionHandler;
	private SpriteSheet spriteSheet;	
	private ItemBuilder itemBuilder;
	
	private CurrentLevelData currentLevelData = new CurrentLevelData();
	
	private LevelGraphics levelGraphics;
	
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	public World() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{
	
		
		// construct item builders
						
		ItemParser parser = new ItemParser("items/items.xml");
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");
		

		
		newLevel(50,50);
		
		currentLevelData.setCurrentLevel(levels.get(0));
		
		

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
			System.out.println(terri.isDying());
		}
		
		
	}
	
	public void newLevel(int levelWidth, int levelHeight) throws SlickException{
		
		Level newLevel = new Level(itemBuilder,levelWidth,levelHeight);
		
		levelGraphics = new LevelGraphics(newLevel,levelWidth,levelHeight);
		
		levels.add(newLevel);		
		
		CollisionHandler collisionHandler = new CollisionHandler(newLevel);
		
		terri = new Player(newLevel,collisionHandler);
//		
	}
	
	
	public Player getPlayer(){
		return terri;
	}


	public void dropItemsFromPlayer(ArrayList<Item> itemsToDrop) {
		for (Item item : itemsToDrop){
			item.drop( terri.getX(),terri.getY());
			currentLevelData.getCurrentLevel().addObject(item);	
		}
		
	}
	
	

}
