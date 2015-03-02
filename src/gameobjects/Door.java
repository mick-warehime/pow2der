package gameobjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import world.CollisionHandler;
import world.LevelBuilder;
import world.World;
import actors.Status;

public class Door extends BasicObject implements Interactive{
	private boolean open;
	private boolean northSouth;
	private Image image;
	private int proximity = 10;
	public Door(Shape shape) throws SlickException {
		super(shape);
		
		open = false;
		
		northSouth = false;
		if(shape.getHeight()>shape.getWidth()){
			northSouth = true;
		}
		
		image = World.spriteSheet.getSubImage(52,36);
		
	}

	public boolean canCollide(){
		return !open;
	}

	@Override
	public void interact(int interactionType, Status status) {

		if (interactionType != Interactive.INTERACTION_TOGGLE){return;}
		
		
		if (this.shape.intersects(status.getRect())){return;}
		
		open = !open;

	}

	public void render(Graphics g, int renderX, int renderY){
		if(!open){
			float x = shape.getX();
			float y = shape.getY();
			
			if(northSouth){
				for(int j = 0; j<LevelBuilder.DOORSIZE;j++){
					image.draw(x-renderX,y-renderY+j*World.TILE_HEIGHT);
				}
			}else{
				for(int j = 0; j<LevelBuilder.DOORSIZE;j++){
					image.draw(x-renderX+j*World.TILE_WIDTH,y-renderY);
				}
			}


		}
	}

	//Checks if an input shape is near the object's shape
	public boolean isNear(Rectangle rectTest) {
		
		Rectangle slightlyBiggerRect = 
				new Rectangle(shape.getX()-proximity,
							shape.getY()-proximity,
							shape.getWidth()+2*proximity,
							shape.getHeight()+2*proximity);
		return slightlyBiggerRect.intersects(rectTest);
	}







}
