package gameobjects;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import render.SpriteSheetRenderer;

public class Wall extends BasicObject {

	public Wall(Shape shape) throws SlickException {
		this.canCollide = true;
		this.shape = shape;
		
		this.renderer = new SpriteSheetRenderer(26,5, (int)shape.getX(),(int)shape.getY());
		
	}

}
