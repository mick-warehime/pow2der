package abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import render.BasicRenderer;

public class NullAbilityObject extends AbilityObject {

	private int countDown;
	
	public NullAbilityObject( int xPos, int yPos)
			throws SlickException {
		this.shape = new Rectangle(xPos,yPos, 10,10);
		this.renderer = new BasicRenderer(new Image("data/thrusterFlame.png"), shape);
		
		this.countDown= 20;
		this.canCollide = false;
		
	}
	
	

	
	
	@Override
	public boolean shouldRemove() {
		return countDown<0;
	}

	@Override
	public void update() {
		countDown -=1;

	}





	@Override
	public void onRemoveDo() {
		// TODO Auto-generated method stub
		
	}

}
