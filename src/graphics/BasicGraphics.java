package graphics;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import actors.Effect;
import actors.Status;

public class BasicGraphics {

	protected Image sprite;
	private int xPos;
	private int yPos;
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicGraphics(Image sprite, int xPos, int yPos) throws SlickException{	
		this.sprite = sprite;
		this.xPos = xPos;
		this.yPos = yPos;		

	}


	public void render(Graphics g, int renderX, int renderY) {

		sprite.draw(xPos-renderX,yPos-renderY);

	}

	public void render(Graphics g, int renderX, int renderY, float scale) {

		sprite.draw(xPos-renderX,yPos-renderY, scale);
		
	}


	public Image getImage() {
		// TODO Auto-generated method stub
		return sprite;
	}

}
