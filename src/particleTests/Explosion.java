package particleTests;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import particleTests.ParticleState.ParticleSystemObject;
import render.ParticleRenderer;

public class Explosion extends ParticleState{

	private float count;
	
	public Explosion(int ID) {
		super(ID);
		count = 0;
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		systemObject = new ParticleSystemObject("data/particle.png","data/explosion.xml",300,300);
		systemObject.addSystem("data/particle.png","data/explosionSmoke.xml",300,300);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);

		count++;
		int maxTime = 60;
		if(count>maxTime){
			restart();
			count = 0;
		}
		
	}

}
