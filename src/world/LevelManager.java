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

		ItemParser parser = new ItemParser();

		itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");

		// build an array of levels with length = numLevels 
		// width and height must be ODD
		int levelHeight = 21;
		int levelWidth = 31;
		for(int l = 0; l < numLevels; l++){
			newLevel(levelWidth,levelHeight);
		}


		setLevel(0,levels.get(0).getStartX(),levels.get(0).getStartY());

	}



	private void setLevel(int levelNumber,int xPos, int yPos) throws SlickException{
		currentLevel = levelNumber;
		
		Level level = levels.get(currentLevel);

		currentLevelData.setCurrentLevel(level);

		contextuals = new ContextualCollisions(level);
		for (Actor dude : level.getActors()){
			dude.setCollisionHandlers(contextuals);
		}

		// set his new position to the position of the stiars
		terri.setPosition(xPos,yPos);
		terri.setCollisionHandlers(contextuals);

		level.addObject(terri);

		levelStaticRenderer = new LevelStaticRenderer(level);

		level.assignToItems(currentLevelData);
	}



	private void newLevel(int levelWidth, int levelHeight) throws SlickException{

		Level level = new Level(itemBuilder, levelWidth, levelHeight, terri);

		level.assignToItems(currentLevelData);

		levelStaticRenderer = new LevelStaticRenderer(level);

		levels.add(level);		

		contextuals = new ContextualCollisions(level);
		for (Actor dude : level.getActors()){
			dude.setCollisionHandlers(contextuals);
		}

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
		if(stairClimb == 1){
			// otherwise it constantly thinks you are climbing 
			levels.get(currentLevel).resetStairs();

			ArrayList<Stairs> stairsDown = levels.get(currentLevel+stairClimb).getStairsDown();
			int xPos = (int) stairsDown.get(0).getShape().getX();
			int yPos = (int) stairsDown.get(0).getShape().getY();

			setLevel(currentLevel+stairClimb,xPos,yPos);
		} else if(stairClimb == -1){
			// otherwise it constantly thinks you are climbing 
			levels.get(currentLevel).resetStairs();
			ArrayList<Stairs> stairsUp = levels.get(currentLevel-1).getStairsUp();
			int xPos = (int) stairsUp.get(0).getShape().getX();
			int yPos = (int) stairsUp.get(0).getShape().getY();
			setLevel(currentLevel-1,xPos,yPos);
		}
	}

	public void update() throws SlickException, IOException {
		checkStairs();

		contextuals.update();
		levels.get(currentLevel).update();		
	}
}
