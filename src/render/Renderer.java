package render;

import org.newdawn.slick.Graphics;

/*
 * Gives rendering functionality for objects in the game
 * 
 */

public abstract class Renderer {

	/*
	 * g -> slick2D graphics object
	 * cornerX, cornerY -> refers to the ingame position of 
	 * the top left corner of the screen
	 * 
	 */
	public abstract void render(Graphics g, int offsetX, int offsetY);
	
	
}
