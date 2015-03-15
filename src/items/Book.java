package items;

import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Book extends Item{



	public Book(Map<String, String> itm, Image sprite, int xPos, int yPos) throws SlickException {
		
		super(itm,sprite,xPos,yPos);
		
		
	}

	public void equip(){
		// add AC remove item from person
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

	

}
