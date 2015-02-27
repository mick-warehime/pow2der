package abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameobjects.BasicObject;
import gameobjects.Removeable;

/*
 * Represents an observable ability in the game, e.g. a fireball or a sword swipe., 
 * that can interact with the outside world.
 * 
* Updates in Level
* Graphical representation.
* Functionality for removal from the game
* Ability to collide with objects, (apply effects if necessary)
 */

public abstract class AbilityObject extends BasicObject implements Removeable{

	public AbilityObject(Image image, int xPos, int yPos) throws SlickException {
		super(image, xPos, yPos);
		
	}
	
	public abstract void update();
	
	
	

}
