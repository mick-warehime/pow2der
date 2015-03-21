package particleTests;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import render.ParticleRenderer;

public class Spiral extends ParticleState{

	private float omega;
	private float[] r;
	private float dr;
	private float domega;
	private float rmin;
	private float rmax;
	private int x;
	private int y;

	public Spiral(int ID) {
		super(ID);
		omega = 0;  // acount for angular frequency
		domega = 1.5f;
		rmin =  5;
		rmax = 50;
		dr = 0.5f; // account for radial srhinkage 
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		x = 300;
		y = 280;
		int numSystems = 7;
		
		systemObject = new ParticleSystemObject("data/particle.png","data/spiral.xml",x,y);
		
		
		
		for(int n = 0; n < (numSystems-1); n++){
			systemObject.addSystem("data/particle.png","data/spiral.xml",x,y);			
		}
		
		r = new float[numSystems];
		for(int n = 0; n < numSystems; n++){
			if(n%2==0){
				r[n] = rmax;
			}else{
				r[n] = rmax - (rmax-rmin)/2;
			}
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);

		
		omega+= domega;
		for(int s = 0; s < r.length; s++){
			
			r[s] -= dr;
			if(r[s]<rmin){
				r[s] = rmax;
			}
			
			float cosOmega = (float) (1.5*r[s]*Math.cos(omega/10+s*2*Math.PI/(r.length+1)));
			float sinOmega = (float) (r[s]*Math.sin(omega/10+s*2*Math.PI/(r.length+1)));

			((ConfigurableEmitter) systemObject.getParticleSystem(s).getEmitter(0)).setPosition(x+cosOmega,y+sinOmega, false);
		}

	}

}
