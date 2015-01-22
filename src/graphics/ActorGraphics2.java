package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import actors.Status;

public class ActorGraphics2 {

	/* Right now the appropriate sprite locations are hard-coded
	 * but there should be a way to load them from data.
	 */
	private static int STAND = 0;
	private static int WALK1 = 1;
	private static int WALK2 = 2;
	private static int INTERACT = 3;
	private static int RIGHT = 0;
	private static int DOWNRIGHT = 1;
	private static int DOWN = 2;
	private static int DOWNLEFT = 3;
	private static int LEFT = 4;
	private static int UPLEFT = 5;
	private static int UP = 6;
	private static int UPRIGHT = 7;
	private static int SPRITEWIDTHPIXELS = 32;
	private static int SPRITEHEIGHTPIXELS = 32;
	private static int DIRECTIONS = 8;
	private static int ACTIONS = 4;
	
	protected SpriteSheet spriteSheet;
	
	
	public ActorGraphics2(String spriteSheetFileName, Status status) throws SlickException{
		
		spriteSheet = new SpriteSheet(spriteSheetFileName, SPRITEWIDTHPIXELS,SPRITEHEIGHTPIXELS);
		
		
	}
	
	private void loadSpriteSheet(String spriteSheetFileName){
		
		
		
		for (int i = 0; i<DIRECTIONS; i++){
			for (int j = 0; j < ACTIONS; j++){
				
			}
		}
		
		
		
	}

	public void render(Graphics g, int renderX, int renderY) {
		
		
		spriteSheet.getSubImage(WALK1,DOWNRIGHT ).draw(renderX, renderY);
		
	}
	
}
