package abilities;

import interfaces.Removeable;
import interfaces.Updater;
import gameobjects.BasicObject;

/*
 * Represents an observable ability in the game, e.g. a fireball or a sword swipe., 
 * that can interact with the outside world.
 * 
* Updates in Level
* Graphical representation.
* Functionality for removal from the game
* Ability to collide with objects, (apply effects if necessary)
 */

public abstract class AbilityObject extends BasicObject implements Removeable,Updater{

	
	
	
	
	public abstract void update();
	
	
	
	

}
