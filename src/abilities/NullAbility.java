package abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class NullAbility extends AbilityObject {

	
	private int countDown;

	public NullAbility(int xPos, int yPos) throws SlickException {		
		super(new Image("data/thrusterFlame.png"), xPos, yPos);
		this.countDown = 20;
	}

	@Override
	public boolean shouldRemove() {
		return this.countDown<0;
	}

	@Override
	public void update() {
		this.countDown -=1;

	}

}
