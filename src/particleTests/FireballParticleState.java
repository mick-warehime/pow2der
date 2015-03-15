package particleTests;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import render.ParticleRenderer;

public class FireballParticleState extends ParticleState{
	
	
	public FireballParticleState(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		
		systemObject = new ParticleSystemObject("data/particle.png","data/fire.xml",300,300);
		systemObject.addSystem("data/particle.png","data/smoke.xml",300,250);
		
	}
	
}
