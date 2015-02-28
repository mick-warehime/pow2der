package world;

import org.newdawn.slick.geom.Shape;


/* Stores data about the currently loaded level */
public class CurrentLevelData {

	private NewLevel currentLevel;
	
	
	protected void setCurrentLevel(NewLevel newLevel){
		this.currentLevel = newLevel;
	}
	
	public NewLevel getCurrentLevel(){
		return this.currentLevel;
	}
	
	
	
	
	
}
