package gameobjects;

import interfaces.Broadcaster;
import interfaces.Interactive;

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

public class Stairs extends BasicObject implements Interactive, Broadcaster{
	private Rectangle interactionRange;
	private boolean stairsDown;
	private boolean climbed;
	
	private static final int INTERACTION_RANGE = 20;

	public Stairs(int topLeftX, int topLeftY, Boolean stairsDown) throws SlickException {
		
		shape = new Rectangle(topLeftX,topLeftY,World.TILE_WIDTH,World.TILE_HEIGHT);
		
		this.stairsDown = stairsDown;

		this.renderer = new StairsRenderer(stairsDown);

		this.interactionRange= new Rectangle(shape.getX()-INTERACTION_RANGE,
				shape.getY()-INTERACTION_RANGE,
				shape.getWidth()+2*INTERACTION_RANGE,
				shape.getHeight()+2*INTERACTION_RANGE);

	}



	public boolean canCollide(){
		return false;
	}

	@Override
	public void interact(int interactionType, Status status) {


		if (interactionType != Interactive.INTERACTION_TOGGLE){return;}

		
//		if (shape.intersects(status.getRect())){return;}
		
		climbed = true;

	}
	
	public void setClimbed(boolean climbed){
		this.climbed = climbed;
	}

	public int climbed(){
		if(climbed){
			if(stairsDown){
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}




	class StairsRenderer extends Renderer{




		private Image imageUp;
		private Image imageDown;
		private boolean stairsDown;
		
		public StairsRenderer(boolean stairsDown) {
			this.imageUp = World.spriteSheet.getSubImage(50,37).copy();
			this.imageDown = World.spriteSheet.getSubImage(7,12).copy();;
			
			this.stairsDown = stairsDown;
		}

		@Override
		public void render(Graphics g, int offsetX, int offsetY) {

			float x = shape.getX();
			float y = shape.getY();

			if(stairsDown){
				imageDown.draw(x-offsetX,y-offsetY);
			}else{
				imageUp.draw(x-offsetX,y-offsetY);
			}

		}

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

	public boolean isStairsDown(){
		return stairsDown;
	}



}
