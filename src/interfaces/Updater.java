package interfaces;

import java.io.IOException;

import org.newdawn.slick.SlickException;

/*
 * An object that is updated in the level
 */

public interface Updater {
	
	public void update() throws SlickException, IOException;

}
