package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import actors.Effect;
import actors.Status;

public class ActorGraphics2 {

	/* Right now the appropriate sprite locations are hard-coded
	 * but there should be a way to load them from data.
	 */
	//Actions
	private static int ACTIONS = 4;
	private static int STAND = 0;
	private static int WALK1 = 1;
	private static int WALK2 = 2;
	private static int WALK3 = 0;
	private static int INTERACT = 3;
	//Directions
	private static int DIRECTIONS = 8;
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
	
	
	
	
	
	protected SpriteSheet spriteSheet;
	private Status status;
	private int currentActorDirection = 0;
	private int currentActorAction = 0;
	
	private int walkSpriteDuration = 5;
	private int walkSpriteCounter = 0;
	
	
	public ActorGraphics2(String spriteSheetFileName, Status status) throws SlickException{
		
		spriteSheet = new SpriteSheet(spriteSheetFileName, SPRITEWIDTHPIXELS,SPRITEHEIGHTPIXELS);
		this.status = status;
		
	}
	
	

	public void render(Graphics g, int renderX, int renderY) {
		
		
		
		determineCurrentActorDirection();
		determineCurrentActorAction();
		
		spriteSheet.getSubImage(currentActorAction,currentActorDirection ).draw(renderX, renderY);
		
	}
	
	
	private void determineCurrentActorAction(){
		
		boolean isWalkingX = status.hasEffect(Effect.EFFECT_WALKING_X);
		boolean isWalkingY = status.hasEffect(Effect.EFFECT_WALKING_Y);
		boolean isInteracting = status.hasEffect(Effect.EFFECT_INTERACTING);
		
		
		if (isInteracting){
			currentActorAction = ActorGraphics2.INTERACT;
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
		currentActorAction = ActorGraphics2.STAND;
		
		
		
		
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
