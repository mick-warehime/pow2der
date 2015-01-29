package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Book extends Item{



	public Book(Map<String, String> itm, Image sprite, ItemLocation location ) throws SlickException {
		
		super(itm,sprite,location);
		
		
	}

	public void equip(){
		// add AC remove item from person
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

	

}
