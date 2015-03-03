package render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import actors.Effect;
import actors.Status;

public class ActorGraphics extends Renderer{

	private static int STAND = 0;
	private static int WALK1 = 1;
	private static int WALK2 = 2;
	private static int WALK3 = 0;
	private static int INTERACT = 3;
	private static int RIGHT = 0;
	private static int DOWNRIGHT = 1;
	private static int DOWN = 2;
	private static int DOWNLEFT = 3;
	private static int LEFT = 4;
	private static int UPLEFT = 5;
	private static int UP = 6;
	private static int UPRIGHT = 7;
	
	private static int SPRITEWIDTHPIXELS = 32;
	private static int SPRITEHEIGHTPIXELS = 32;
	private static int SPRITESPACINGINPIXELS = 4;
	
	
	
	
	
	protected SpriteSheet spriteSheet;
	private Status status;
	private int currentActorDirection = 0;
	private int currentActorAction = 0;
	
	private int walkSpriteDuration = 5;
	private int walkSpriteCounter = 0;
	
	
	public ActorGraphics(String spriteSheetFileName, Status status) throws SlickException{
		
		Image img = new Image(spriteSheetFileName);
		int w = SPRITEWIDTHPIXELS-SPRITESPACINGINPIXELS;
		int h = SPRITEHEIGHTPIXELS - SPRITESPACINGINPIXELS;
		
		spriteSheet = new SpriteSheet(img, w, h,SPRITESPACINGINPIXELS);
		this.status = status;
		
	}
	
	
	
	
	public void render(Graphics g, int cornerX, int cornerY) {
		
//		renderShape( g,  renderX,  renderY);
		
		
		determineCurrentActorDirection();
		determineCurrentActorAction();
		
		Shape shape = status.getRect();
		float x = shape.getX();
		float y = shape.getY();
		
		spriteSheet.getSubImage(currentActorAction,currentActorDirection ).draw(x-cornerX, y-cornerY);
		
	}
	
	
	@SuppressWarnings("unused")
	private void renderShape(Graphics g, int renderX, int renderY) {
		Shape shape = status.getRect();
		float x = shape.getX();
		float y = shape.getY();
		shape.setX(x - renderX);
		shape.setY(y -renderY);
		g.draw(shape);
		shape.setX(x);
		shape.setY(y);
		
	}




	private void determineCurrentActorAction(){
		
		boolean isWalkingX = status.hasEffect(Effect.EFFECT_WALKING_X);
		boolean isWalkingY = status.hasEffect(Effect.EFFECT_WALKING_Y);
		boolean isInteracting = status.hasEffect(Effect.EFFECT_INTERACTING);
		
		
		if (isInteracting){
			currentActorAction = ActorGraphics.INTERACT;
			return;
		}
		if (isWalkingX || isWalkingY){
			
			walkSpriteCounter =(1 + walkSpriteCounter)%walkSpriteDuration;
			if (walkSpriteCounter == 0){
				if (currentActorAction == WALK1){
					currentActorAction = WALK2;
				}else if (currentActorAction == WALK2){
					currentActorAction = WALK3;
				}else if (currentActorAction == WALK3){
					currentActorAction = WALK1;
				}else{ currentActorAction = WALK1;}
					
			}
			
			
			return;
		}
		currentActorAction = ActorGraphics.STAND;
		
		
		
		
	}
	private void determineCurrentActorDirection(){
		
		int xDirection = status.getDirection('x');
		int yDirection = status.getDirection('y');
		
		
		boolean isWalkingX = status.hasEffect(Effect.EFFECT_WALKING_X);
		boolean isWalkingY = status.hasEffect(Effect.EFFECT_WALKING_Y);
		
		
		
		
		//Diagonal movement
		if (isWalkingX && isWalkingY){

			if (xDirection == 1){
				if(yDirection==1){ currentActorDirection = DOWNRIGHT;}
				if(yDirection==-1){ currentActorDirection = UPRIGHT;}
			}
			if (xDirection == -1){
				if(yDirection==1){ currentActorDirection = DOWNLEFT;}
				if(yDirection==-1){ currentActorDirection = UPLEFT;}
			}

		}
		
		//Vertical movement
		if (!isWalkingX && isWalkingY){
			if (yDirection == 1){currentActorDirection = DOWN;}
			if (yDirection == -1){currentActorDirection = UP;}
		}
		
		//Horizontal movement
		if (isWalkingX && !isWalkingY){
			if (xDirection == 1){currentActorDirection = RIGHT;}
			if (xDirection == -1){currentActorDirection = LEFT;}
		}
		
		return;
		
	}
	
}
