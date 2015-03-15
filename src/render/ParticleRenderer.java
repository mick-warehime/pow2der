package render;


import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/* Draws a single image at the location of a shape */

public class ParticleRenderer extends Renderer{

	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	private Shape shape;
	private int updateSpeed;

	private int offsetXOld;
	private int offsetYOld;

	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public ParticleRenderer(String imageFileName, String emitterFileName, Shape shape, float[] direction, int updateSpeed) throws SlickException, IOException{	



		this.shape = shape;

		this.updateSpeed = updateSpeed;

		//load the test particle and 
		Image image = new Image(imageFileName, false);
		int maxParticles = 1500;
		system = new ParticleSystem(image,maxParticles);

		File xmlFile = new File(emitterFileName);
		emitter = ParticleIO.loadEmitter(xmlFile);

		double angle = Math.atan2(direction[1], direction[0])*180/Math.PI;		
		if (angle<0){ angle = angle + 360;}
		angle -= 90;

		emitter.angularOffset.setValue((float) angle);

		system.addEmitter(emitter);

		system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);	

		// this variable keeps track of how to shift the particles
		offsetXOld = 0;
		offsetYOld = 0;
	}




	public void render(Graphics g, int offsetX, int offsetY) {


		float x = shape.getX();
		float y = shape.getY();

 
		system.update(50);
		system.render(x-offsetX,y-offsetY);

	

	}




}