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
	
	private ItemBuilder itemBuilder;
	
	public World(GameControls gameControls, MenuHandler menuHandler) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{
	
		
		// construct item builders
		ItemParser parser = new ItemParser("items/items.xml");
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");
		
		currentLevel = 0;
		
		
		Level level = new Level(10, itemBuilder);

		level.setMousePosition(gameControls.getMousePos());
		
		levels.add(level);
		
		
		// i dont like this initialization
		CollisionHandler collisionHandler = level.getCollisionHandler();

		terri = new Player(100,800,collisionHandler, gameControls.getMousePos());


		gameControls.addAvatarInputProviderListener(terri.getListener());
		menuHandler.setPlayerInventory(terri.getInventory());

		
		
	}
	

	public void render(Graphics graphics) {
		
		// draw current level
		levels.get(currentLevel).render(graphics,(int) terri.getX(),(int)terri.getY());
		
		// draw player
		terri.render(graphics,levels.get(currentLevel).getMapX(),levels.get(currentLevel).getMapY());
		
	}


	public void update() throws SlickException {
		terri.update();
		
		levels.get(currentLevel).update();
		
		
	}
	
	public int getMapX(){
		return levels.get(currentLevel).getMapX();
	}
	
	public int getMapY(){
		return levels.get(currentLevel).getMapX();
	}

}
