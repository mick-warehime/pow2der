package items;

import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon extends Item{


	public Weapon(Map<String, String> itmInfo, Image sprite, int xPos, int yPos) throws SlickException {
		
		super(itmInfo,sprite,xPos,yPos);
		
		definePropertiesFromMap(itmInfo);
		
	}

	
	
	protected void definePropertiesFromMap(Map<String, String> itmInfo){
		
	}
	

	

}
