package world;

import gameobjects.Stairs;


import items.ItemBuilder;
import items.ItemParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import render.LevelStaticRenderer;
import actors.Actor;
import actors.Player;
import collisions.ContextualCollisions;

/* Generic menu class
 * 
 * Handles processing of its constituent `selection' elements.
 * 
 */


public class LevelManager {

	private int currentLevel;
	private ArrayList<Level> levels;
	private ItemBuilder itemBuilder;
	private Player terri;
	private LevelStaticRenderer levelStaticRenderer;
	private CurrentLevelData currentLevelData = new CurrentLevelData();
	private ContextualCollisions contextuals;
 	
	public LevelManager(Player terri, int numLevels) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{

		this.terri = terri;

		levels = new ArrayList<Level>();

		
		itemBuilder = new ItemBuilder(new ItemParser().getItemMaps(),"data/items.png");

		// build an array of levels with length = numLevels 
		// width and height must be ODD
		int levelHeight = 21;
		int levelWidth = 31;
		for(int l = 0; l < numLevels; l++){
			newLevel(levelWidth,levelHeight);
		}
		
		// remove down stairs from level one and up stairs from last level
		levels.get(0).removeStairsDown();
		levels.get(numLevels-1).removeStairsUp();
		
		// set the current level to be level 0
		setLevel(0,levels.get(0).getStartX(),levels.get(0).getStartY());
		
		

	}



	private void setLevel(int levelNumber, int xPos, int yPos) throws SlickException{
		currentLevel = levelNumber;
		
		Level level = levels.get(currentLevel);

		currentLevelData.setCurrentLevel(level);

		// set his new position to the position of the stiars
		terri.setPosition(xPos,yPos);
		level.addObjectOld(terri);
		
		contextuals = new ContextualCollisions(level);
		for (Actor dude : level.getActors()){
			dude.setContextuals(contextuals);
		}

		

		// get the minimap and walls from the current level
//		miniMap = new MiniMap(level.getMap(),level.getWalls(),level.getClosedDoors());
 
		levelStaticRenderer = new LevelStaticRenderer(level);

		level.assignToItems(currentLevelData);
	}



	private void newLevel(int levelWidth, int levelHeight) throws SlickException{

		Level level = new Level(itemBuilder, levelWidth, levelHeight, terri);

		level.assignToItems(currentLevelData);
	
		levels.add(level);		

	}

	public Level getCurrentLevel(){
		return levels.get(currentLevel);
	}



	public LevelStaticRenderer getRenderer() {
		// TODO Auto-generated method stub
		return levelStaticRenderer;
	}

	private void checkStairs() throws SlickException{
		// look through the current level staircases
		// if one has been activated use the setLevel method to 
		// set the current level and

		Level level = levels.get(currentLevel); 
		int stairClimb = level.checkStairs();
		int nextLevel = currentLevel+stairClimb;
		
		if(stairClimb == 1){
			// otherwise it constantly thinks you are climbing 
			levels.get(currentLevel).resetStairs();

			ArrayList<Stairs> stairsDown = levels.get(nextLevel).getStairsDown();
			int xPos = (int) stairsDown.get(0).getShape().getX();
			int yPos = (int) stairsDown.get(0).getShape().getY();

			setLevel(currentLevel+stairClimb,xPos,yPos);
		} else if(stairClimb == -1){
			// otherwise it constantly thinks you are climbing 
			levels.get(currentLevel).resetStairs();
			
			ArrayList<Stairs> stairsUp = levels.get(nextLevel).getStairsUp();
			ArrayList<Stairs> stairsDown = levels.get(nextLevel).getStairsDown();
						
			int xPos = (int) stairsUp.get(0).getShape().getX();
			int yPos = (int) stairsUp.get(0).getShape().getY();
			setLevel(nextLevel,xPos,yPos);
		}
	}

	public void update() throws SlickException, IOException {
		checkStairs();
		
		// update the miniMap using the players position in tile coordinates NOT PIXELS
// 		miniMap.update(terri.getShape());
		contextuals.update();
		levels.get(currentLevel).update();		
	}
}
