package world;

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
import main.CollisionHandler;
import menus.MenuHandler;
import controls.GameControls;

public class World {
	
	private List<Level> levels = new ArrayList<Level>();
	private int currentLevel;
	private Player terri;
	private CollisionHandler collisionHandler;
	private SpriteSheet spriteSheet;	
	private ItemBuilder itemBuilder;
	private LevelBuilder levelBuilder;
	private NewLevel nLevel;
	
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	public World() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{
	
		
		// construct item builders
						
		ItemParser parser = new ItemParser("items/items.xml");
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");
		this.levelBuilder = new LevelBuilder(TILE_WIDTH,TILE_HEIGHT);
		
		currentLevel = 0;		

		nLevel = newLevel(40,30);
		CollisionHandler newCollisionHandler = new CollisionHandler(nLevel);
		
		// from here down needs to be in new level
		levels.add(new Level(10, itemBuilder));		
		
		// i dont like this initialization
		CollisionHandler collisionHandler = levels.get(currentLevel).getCollisionHandler();

		terri = new Player(100,200,newCollisionHandler);


	}
	

	public void render(Graphics graphics) {
		
		// draw current level
		levels.get(currentLevel).render(graphics,(int) terri.getX(),(int)terri.getY());
		
		// draw player
		terri.render(graphics,levels.get(currentLevel).getMapX(),levels.get(currentLevel).getMapY());

		nLevel.render(graphics, (int) terri.getX(),(int)terri.getY());
	}


	public void update() throws SlickException {
		terri.update();
		
		levels.get(currentLevel).update();
		
		if (terri.isDying()){
			System.out.println(terri.isDying());
		}
		
		
	}
	
	public NewLevel newLevel(int m, int n) throws SlickException{
		NewLevel nLevel = new NewLevel(new LevelBuilder(m,n),itemBuilder);
		
		CollisionHandler newCollisionHandler = new CollisionHandler(nLevel); 
		return nLevel;
	}
	
	public int getMapX(){
		return levels.get(currentLevel).getMapX();
	}
	
	public int getMapY(){
		return levels.get(currentLevel).getMapX();
	}
	
	public Player getPlayer(){
		return terri;
	}

}
