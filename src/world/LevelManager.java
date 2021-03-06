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
import actors.Player;

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
	private CurrentLevelData currentLevelData = new CurrentLevelData();
 	
	public LevelManager(Player terri, int numLevels) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{

		this.terri = terri;

		levels = new ArrayList<Level>();

		
		itemBuilder = new ItemBuilder(new ItemParser().getItemMaps(),"data/items.png");

		// build an array of levels with length = numLevels 
		// width and height must be ODD
		int levelHeight = 17;
		int levelWidth = 25;
		for(int l = 0; l < numLevels; l++){
			newLevel(levelWidth,levelHeight);
		}
		
		// remove down stairs from level one and up stairs from last level
		levels.get(0).removeStairs('D');
		levels.get(numLevels-1).removeStairs('U');
		
		// set the current level to be level 0
		setLevel(0,levels.get(0).getStartX(),levels.get(0).getStartY());
		
		

	}



	private void setLevel(int levelNumber, int xPos, int yPos) throws SlickException{
		currentLevel = levelNumber;
		
		Level level = levels.get(currentLevel);

		currentLevelData.setCurrentLevel(level);

		// set his new position to the position of the stiars
		terri.setPosition(xPos,yPos);
		level.addObject(terri,xPos,yPos);
		
		

		

		// get the minimap and walls from the current level
//		miniMap = new MiniMap(level.getMap(),level.getWalls(),level.getClosedDoors());
 
		

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

			ArrayList<Stairs> stairsDown = levels.get(nextLevel).getStairs('D');
			int xPos = (int) stairsDown.get(0).getShape().getX();
			int yPos = (int) stairsDown.get(0).getShape().getY();

			setLevel(currentLevel+stairClimb,xPos,yPos);
		} else if(stairClimb == -1){
			// otherwise it constantly thinks you are climbing 
			levels.get(currentLevel).resetStairs();
			
			ArrayList<Stairs> stairsUp = levels.get(nextLevel).getStairs('U');
			
						
			int xPos = (int) stairsUp.get(0).getShape().getX();
			int yPos = (int) stairsUp.get(0).getShape().getY();
			setLevel(nextLevel,xPos,yPos);
		}
	}

	public void update() throws SlickException, IOException {
		checkStairs();
		
		// update the miniMap using the players position in tile coordinates NOT PIXELS

		levels.get(currentLevel).update();		
	}
}
