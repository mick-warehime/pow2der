package render;


import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/* Draws a single image at the location of a shape */

public class ParticleRenderer2 extends Renderer{

	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	private Shape shape;
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public ParticleRenderer2(String imageFileName, String emitterFileName, Shape shape, float[] direction, int updateSpeed) throws SlickException, IOException{	



		this.shape = shape;

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
	}




	public void render(Graphics g, int offsetX, int offsetY) {


		float x = shape.getX();
		float y = shape.getY();

 
		system.update(50);
		system.render(x-offsetX,y-offsetY);

	

	}




}