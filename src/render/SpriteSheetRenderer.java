package render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import world.World;

/*
 * Draws an image based on a single location from the world sprite sheet
 * 
 */

public class SpriteSheetRenderer extends Renderer {

	private static final SpriteSheet spriteSheet = World.spriteSheet;
	
	private int subImageI;
	private int subImageJ;
	
	private int xPos;
	private int yPos;
	
	public SpriteSheetRenderer(int subImageI, int subImageJ, int xPos, int yPos){
		this.subImageI = subImageI;
		this.subImageJ = subImageJ;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
	}
	
	@Override
	public void render(Graphics g, int offsetX, int offsetY) {

		int x = xPos - offsetX;
		int y = yPos - offsetY;
		spriteSheet.getSubImage(subImageI,subImageJ).draw(x, y);
		
		

	}

}
