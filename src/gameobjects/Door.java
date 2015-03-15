package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import commands.AddInteractiveCommand;

import render.Renderer;
import world.LevelBuilder;
import world.World;
import actors.Actor;
import actors.Enemy;
import actors.Player;
import actors.Status;

public class Door extends BasicObject implements Interactive, Broadcaster{
	private boolean isOpen;
	private boolean northSouth;
	private ArrayList<Actor> actors;
	private Rectangle interactionRange;
	
	private static final int INTERACTION_RANGE = 10;
	
	public Door(Shape doorShape, ArrayList<Actor> actors) throws SlickException {
		this.actors  = actors;
		this.shape = doorShape;
		
		isOpen = false;
		
		this.northSouth = doorShape.getHeight()>doorShape.getWidth();	
		
		
		
		this.renderer = new DoorRenderer();
		
		this.interactionRange= new Rectangle(shape.getX()-INTERACTION_RANGE,
				shape.getY()-INTERACTION_RANGE,
				shape.getWidth()+2*INTERACTION_RANGE,
				shape.getHeight()+2*INTERACTION_RANGE);
		
		
		
	}

	

	public boolean canCollide(){
		return !isOpen;
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
		
		isOpen = !isOpen;

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
			if(!isOpen){
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
		return isOpen;
	}



	@Override
	public boolean isAccessible(Status status) {
		
		return this.interactionRange.intersects(status.getRect());
	}
	
	



	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {
		
		
	}



	@Override
	public ArrayList<Command> onCollisionBroadcast(
			Class<?> collidingObjectClass, Shape collidingObjectShape) {
		
		
		boolean doBroadcast = collidingObjectClass.equals(Actor.class);
		doBroadcast = doBroadcast || collidingObjectClass.equals(Enemy.class);
		doBroadcast = doBroadcast || collidingObjectClass.equals(Player.class);
		
		ArrayList<Command> output = new ArrayList<Command>();
		
		if (doBroadcast){
			output.add(new AddInteractiveCommand(this));
			
		}
		
		
		return output;
	}



	@Override
	public Shape getInteractionRange() {
		return this.interactionRange;
	}
	
	



}
