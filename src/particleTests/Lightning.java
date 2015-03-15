package particleTests;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import render.ParticleRenderer;

public class Lightning extends ParticleState{

	private float angle;

	private int x;
	private int y;
	private int count;

	public Lightning(int ID) {
		super(ID);

		angle = 0;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		x = 300;
		y = 280;
		systemObject = new ParticleSystemObject("data/particle.png","data/lightning.xml",x,y);
		

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
//		super.update(gc, sbg, delta);
		
		 
		
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
 
 
	}


}
