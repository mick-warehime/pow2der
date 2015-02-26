package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Armor extends Item{


	private Integer AC;
	private String material;
	private String worn;

	public Armor(Map<String, String> itmInfo, Image sprite, int xPos, int yPos) throws SlickException {
		
		super(itmInfo,sprite,xPos,yPos);
		
		
		this.AC = (int) Float.parseFloat(itmInfo.get("ac"));
		this.worn = itmInfo.get("worn");
		this.material = itmInfo.get("material");
		this.type = itmInfo.get("type");
		
	}


	public Integer getAC(){
		return AC;
	}
	
	public String getMaterial(){
		return material;
	}
	
	public String getWorn(){
		return worn;
	}
	
	public void equip(){
		// add AC remove item from person
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

	

}
