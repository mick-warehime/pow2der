package world;



/* Stores data about the currently loaded level */
public class CurrentLevelData {

	private Level currentLevel;
	
	
	protected void setCurrentLevel(Level level){
		this.currentLevel = level;
	}
	
	public Level getCurrentLevel(){
		return this.currentLevel;
	}
	
	 
}
