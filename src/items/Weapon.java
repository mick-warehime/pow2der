package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon extends Item{


	private Integer mHitBonus;
	private String material;


	public Weapon(Map<String, String> itmInfo, Image sprite, int xPos, int yPos) throws SlickException {
		
		super(itmInfo,sprite,xPos,yPos);
		
		definePropertiesFromMap(itmInfo);
		
		this.mHitBonus = (int) Float.parseFloat(itmInfo.get("mHitBonus"));
		this.material = itmInfo.get("material");
		
	}

	
	
	protected void definePropertiesFromMap(Map<String, String> itmInfo){
		
	}
	

	

}
