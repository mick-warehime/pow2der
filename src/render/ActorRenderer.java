package render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import actors.Effect;
import actors.Status;

/* Uses a spriteSheet and an actor's status to draw it. */

public class ActorRenderer extends Renderer{

	private static int STAND = 0;
	private static int WALK1 = 1;
	private static int WALK2 = 2;
	private static int WALK3 = 0;
	private static int INTERACT = 3;
	private static int RIGHT = 0;
	private static int UPRIGHT = 1;
	private static int UP = 2;
	private static int UPLEFT = 3;
	private static int LEFT = 4;
	private static int DOWNLEFT = 5;
	private static int DOWN = 6;
	private static int DOWNRIGHT = 7;
	
	private static int SPRITEWIDTHPIXELS = 32;
	private static int SPRITEHEIGHTPIXELS = 32;
	private static int SPRITESPACINGINPIXELS = 4;
	
	
	
	
	
	protected SpriteSheet spriteSheet;
	private Status status;
	private int currentActorDirection = 0;
	private int currentActorAction = 0;
	
	private int walkSpriteDuration = 5;
	private int walkSpriteCounter = 0;
	
	
	public ActorRenderer(String spriteSheetFileName, Status status) throws SlickException{
		
		Image img = new Image(spriteSheetFileName);
		int w = SPRITEWIDTHPIXELS-SPRITESPACINGINPIXELS;
		int h = SPRITEHEIGHTPIXELS - SPRITESPACINGINPIXELS;
		
		spriteSheet = new SpriteSheet(img, w, h,SPRITESPACINGINPIXELS);
		this.status = status;
		
	}
	
	
	
	
	public void render(Graphics g, int offsetX, int offsetY) {
		
//		renderShape( g,  renderX,  renderY);
		
		
		determineCurrentActorDirection();
		determineCurrentActorAction();
		
		Shape shape = status.getRect();
		float x = shape.getX();
		float y = shape.getY();
		
		spriteSheet.getSubImage(currentActorAction,currentActorDirection ).draw(x-offsetX, y-offsetY);
		
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
			currentActorAction = ActorRenderer.INTERACT;
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
		currentActorAction = ActorRenderer.STAND;
		
		
		
		
	}
	private void determineCurrentActorDirection(){
		
		
		
		float [] faceDirection = status.getFacingDirection();
		
		
		
		double facingAngle  = Math.atan2(faceDirection[1], faceDirection[0]) ;
		//Output of atan2 is from -pi to pi. Need to translate to 0 to 2 pi
		if (facingAngle<0){ facingAngle = facingAngle + 2*Math.PI;}
		
		//Assign actor direction according to octants, but we need to translate by a 16th of a rotation
		int dir = (int) Math.floor(16*facingAngle/(2*Math.PI));
		dir = (dir+1) %16;
		
		
		currentActorDirection = dir/2;
		
		
	
		
		
		
		
		return;
		
	}
	
}
