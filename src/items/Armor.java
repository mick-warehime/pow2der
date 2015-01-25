package items;

import java.util.ArrayList;
import org.newdawn.slick.Image;

public class Armor extends Item{


	private Integer AC;
	private String material;
	private String worn;

	public Armor(String name, Integer type, Image sprite, Integer weight, Integer value, 
			boolean stackable, ArrayList<String> properties, Integer AC, String worn, String material) {
		super(name, type, sprite, weight, value, stackable, properties);

		this.AC = AC;
		this.worn = worn;
		this.material = material;
		

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
