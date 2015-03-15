package particleTests;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import render.ParticleRenderer;

public class DoubleHelix extends ParticleState{

	private float omega;
	private int x;
	private int y;

	public DoubleHelix(int ID) {
		super(ID);
		omega = 0;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		x = 300;
		y = 280;
		systemObject = new ParticleSystemObject("data/particle.png","data/ray.xml",x,y);
		systemObject.addSystem("data/particle.png","data/ray.xml",x,y);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);

		float shift = (float) (10*Math.cos(omega/10));
		omega++;


		((ConfigurableEmitter) systemObject.getParticleSystem(0).getEmitter(0)).setPosition(x+shift,y, false);
		((ConfigurableEmitter) systemObject.getParticleSystem(1).getEmitter(0)).setPosition(x-shift,y, false);


	}

}
