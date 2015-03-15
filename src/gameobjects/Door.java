package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import render.Renderer;
import world.LevelBuilder;
import world.World;
import actors.Actor;
import actors.Status;

public class Door extends BasicObject implements Interactive{
	private boolean open;
	private boolean northSouth;
	private ArrayList<Actor> actors;
	
	private static final int INTERACTION_RANGE = 10;
	
	public Door(Shape doorShape, ArrayList<Actor> actors) throws SlickException {
		this.actors  = actors;
		this.shape = doorShape;
		
		open = false;
		
		this.northSouth = doorShape.getHeight()>doorShape.getWidth();	
		
		
		
		this.renderer = new DoorRenderer();
		
		
	}

	

	public boolean canCollide(){
		return !open;
	}

	@Override
	public void interact(int interactionType, Status status) {

		
		if (interactionType != Interactive.INTERACTION_TOGGLE){return;}
		
		
		if (shape.intersects(status.getRect())){return;}
		
		for(Actor actor : actors){
			if(shape.intersects(actor.getShape())){
				return;
			}
		}
		
		open = !open;

	}


	


	class DoorRenderer extends Renderer{

		
		
		
		private Image image;

		public DoorRenderer() {
			this.image = World.spriteSheet.getSubImage(52,36).copy();;
			
			if(northSouth){
				image.setRotation(90);
			}
			
		}

		public void render(Graphics g, int offsetX, int offsetY){
			if(!open){
				float x = shape.getX();
				float y = shape.getY();
				
				if(northSouth){
					for(int j = 0; j<LevelBuilder.DOORSIZE;j++){
						image.draw(x-offsetX,y-offsetY+j*World.TILE_HEIGHT);
					}
				}else{
					for(int j = 0; j<LevelBuilder.DOORSIZE;j++){
						image.draw(x-offsetX+j*World.TILE_WIDTH,y-offsetY);
					}
				}

			}
			
		}
		
	}


	public boolean isOpen() {
		// TODO Auto-generated method stub
		return open;
	}



	@Override
	public boolean isAccessible(Status status) {
		
		return isNear(status.getRect());
	}
	
	@Override
	public boolean isNear(Shape shape2){
		
		
		Rectangle slightlyBiggerRect = 
				new Rectangle(shape.getX()-INTERACTION_RANGE,
						shape.getY()-INTERACTION_RANGE,
						shape.getWidth()+2*INTERACTION_RANGE,
						shape.getHeight()+2*INTERACTION_RANGE);
		
		boolean output = slightlyBiggerRect.intersects(shape2);
		
		
		return output;
		
	}




}
