package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;

public class Weapon extends Item{


	private Integer mHitBonus;
	private String material;


	public Weapon(Map<String, String> itm) {
		
		super(itm);
		
		
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
