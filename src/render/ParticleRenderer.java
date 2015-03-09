package render;


import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/* Draws a single image at the location of a shape */

public class ParticleRenderer extends Renderer{

	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	private Shape shape;
	
	
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public ParticleRenderer(String imageFileName, String emitterFileName, Shape shape) throws SlickException, IOException{	
		
		//load the test particle and 
//		Image image = new Image("data/test_particle.png", false);
		Image image = new Image(imageFileName, false);
		system = new ParticleSystem(image,1500);
		
//		File xmlFile = new File("data/fireball.xml");
		File xmlFile = new File(emitterFileName);
		emitter = ParticleIO.loadEmitter(xmlFile);
		
		system.addEmitter(emitter);
	}
	
	


	public void render(Graphics g, int offsetX, int offsetY) {

		render(g, offsetX,offsetY,1f);

	}

	
	public void render(Graphics g, int renderX, int renderY, float scale) {
		
		float x = shape.getX();
		float y = shape.getY();
		
		emitter.setPosition(x-renderX,y-renderY);
	
		
	}


	

}