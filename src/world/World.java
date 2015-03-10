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

import render.LevelStaticRenderer;
import actors.Actor;
import actors.Player;

public class World {
	public static SpriteSheet spriteSheet;

	private List<Level> levels = new ArrayList<Level>();
	private Player terri;
	private ItemBuilder itemBuilder;

	private CurrentLevelData currentLevelData = new CurrentLevelData();

	private LevelStaticRenderer levelStaticRenderer;

	private ScreenPositionTracker screenTracker;

	private MousePositionTracker mouseTracker;

	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	private boolean gameOver;

	public World(int [] mouseScreenPosition) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{

		spriteSheet = new SpriteSheet("data/metroidtiles.png",16,16);
		
		this.screenTracker = new ScreenPositionTracker();
		this.mouseTracker = new MousePositionTracker(mouseScreenPosition);

		// construct item builders
		
		ItemParser parser = new ItemParser();
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");

		gameOver = false;
		
		terri = new Player(mouseTracker.getMouseLevelPosition());

		// width and height must be ODD
		newLevel(31,21);

		currentLevelData.setCurrentLevel(levels.get(0));

		

	}


	public void render(Graphics graphics) {


		

		Level currentLevel = currentLevelData.getCurrentLevel();
		
		int maxX = currentLevel.getWidth()*LevelBuilder.SCALING*World.TILE_HEIGHT;
		int maxY = currentLevel.getHeight()*LevelBuilder.SCALING*World.TILE_HEIGHT;
		screenTracker.setLevelCoordinates((int) terri.getX(),(int)terri.getY(), maxX, maxY);
		
		// draw current level
		levelStaticRenderer.recordPlayerPosition((int) terri.getX(),(int)terri.getY());
		levelStaticRenderer.render(graphics, screenTracker.getOffsetX(),screenTracker.getOffsetY());
		
		
		// draw level items/objects	
		currentLevel.render(graphics,screenTracker.getOffsetX(),screenTracker.getOffsetY());

		
		
		
	}


	public void update() throws SlickException, IOException {

		mouseTracker.updateMousePosition();
		currentLevelData.getCurrentLevel().update();

		
		if (terri.shouldRemove()){
			System.out.println("Terri is dead!");
			gameOver = true;
		}


	}

	public void newLevel(int levelWidth, int levelHeight) throws SlickException{

		Level level = new Level(itemBuilder, levelWidth, levelHeight, terri);

		level.assignToItems(currentLevelData);

		levelStaticRenderer = new LevelStaticRenderer(level);

		levels.add(level);		

		CollisionHandler collisionHandler = new CollisionHandler(level);
		for (Actor dude : level.getActors()){
			dude.setCollisionHandler(collisionHandler);
		}

		terri.setPosition(level.getStartX(),  level.getStartY());
		terri.setCollisionHandler(collisionHandler);
		level.addObject(terri);

	}


	public boolean gameOver(){
		return gameOver;
	}
	
	public Player getPlayer(){
		return terri;
	}


	/*
	 * Determines ingame position of the the top left corner of the screen
	 * 
	 */
	class ScreenPositionTracker{

		private int offsetX;
		private int offsetY;
		
		private int tileSize;
		private int screenWidth;
		private int screenHeight;
		
		private int bufferDistX = 30; // number of tiles away from edge
		private int bufferDistY = 20; 
		private int bufferX; // number of tiles away from edge
		private int bufferY; // number of tiles away from edge

		public ScreenPositionTracker(){
			screenWidth = main.Game.WIDTH;
			screenHeight = main.Game.HEIGHT;
			tileSize = World.TILE_HEIGHT;
			
			
			
		}
		
		private int boundCoordinate(int offset, int coord, int buffer, int screenDim ){

			if ((coord+buffer-screenDim)>offset){return (coord+buffer-screenDim);}
			if ((coord-buffer)<offset){return (coord-buffer);}

			return offset;
		}

		public void setLevelCoordinates(int playerX, int playerY, int maxX, int maxY){

			
			// allows the player to get within bufferX/bufferY of the top/side
			// int offset, int coord, int buffer, int levelDim, int screenDim ){
			offsetX = boundCoordinate(offsetX,playerX,bufferX,screenWidth);
			offsetY = boundCoordinate(offsetY,playerY,bufferY,screenHeight);

			

			if(offsetX<0){
				offsetX = 0;
				bufferX = 0;			
			}else if(offsetX>(maxX-screenWidth)){
				offsetX = maxX-screenWidth;
				bufferX = 0;
			}else{
				bufferX = bufferDistX*tileSize;

			}


			if(offsetY<0){
				offsetY = 0;
				bufferY = 0;			
			}
			else if(offsetY>(maxY-screenHeight)){
				offsetY = maxY-screenHeight;
				bufferY = 0;
			}
			else{
				bufferY = bufferDistY*tileSize;
			}
		}
		
		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}


	}
	
	/*
	 * Determines ingame position of the mouse (using the mouse's position on the screen as reference)
	 * 
	 */
	
	class MousePositionTracker{
	
		
	

		private int[] mouseScreenPosition;
		private int[] mouseLevelPosition;

		public MousePositionTracker(int [] mouseScreenPositon){
			this.mouseScreenPosition = mouseScreenPositon;
			mouseLevelPosition = new int[] {0,0};
			updateMousePosition();
		}
		
		public void updateMousePosition(){
			
			int dx = screenTracker.getOffsetX();
			int dy = screenTracker.getOffsetY();
			
			mouseLevelPosition[0] = mouseScreenPosition[0] + dx;
			mouseLevelPosition[1] = mouseScreenPosition[1] + dy;
		}
		
		public int[] getMouseLevelPosition(){
			return mouseLevelPosition;
		}
		
	}


}
