package world;

import java.util.ArrayList;


/*
 * An object that emits other objects into the game world (i.e. into level)
 * 
 */

public interface ObjectCreator {

	public boolean hasObjects();
	public ArrayList<Object> popObjects();
}
