package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon extends Item{


	private Integer mHitBonus;
	private String material;


	public Weapon(Map<String, String> itm, Image sprite, ItemLocation location ) throws SlickException {
		
		super(itm,sprite,location);
		
		
		this.mHitBonus = (int) Float.parseFloat(itm.get("mHitBonus"));
		this.material = itm.get("material");
		
	}


	
	public void equip(){
		// add AC remove item from person
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

	

}
