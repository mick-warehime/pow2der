package render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import actors.Effect;
import actors.Status;

/* Uses a spriteSheet and an actor's status to draw it. */

public class EnemyRenderer extends Renderer{

	private static int STAND = 0;
	private static int WALK1 = 1;
	private static int WALK2 = 2;
	private static int WALK3 = 0;
	private static int INTERACT = 3;
	private static int SPRITEWIDTHPIXELS = 32;
	private static int SPRITEHEIGHTPIXELS = 32;
	private static int SPRITESPACINGINPIXELS = 0;
	
	
	
	
	
	protected SpriteSheet spriteSheet;
	private Status status;
	private int currentActorDirection = 0;
	private int currentActorAction = 0;
	
	private int walkSpriteDuration = 5;
	private int walkSpriteCounter = 0;
	
	private final static int DOWN = 0;
	private final static int LEFT = 1;
	private final static int RIGHT = 2;
	private final static int UP = 3;
	private int numSprites = 2;
	private int spriteX;
	private int spriteY;
	
	
	public EnemyRenderer(String spriteSheetFileName, Status status, int enemyID) throws SlickException{
		
		// for black dude its (0,0) for genie dude its (4,0)
		setSpriteSheetOffset(enemyID);
		
		Image img = new Image(spriteSheetFileName);
		
		spriteSheet = new SpriteSheet(img,SPRITEWIDTHPIXELS,SPRITEHEIGHTPIXELS);
		this.status = status;
		
	}
	
	private void setSpriteSheetOffset(int enemyID){
		if(enemyID <4){
			spriteY = 0;
		} else {
			spriteY = 4;
		}
		
		spriteX = enemyID%4;
		
		
		// account for number of directions and number of sprites in each set
		spriteX = spriteX*(numSprites+1);
 		
	}
	
	
	
	public void render(Graphics g, int offsetX, int offsetY) {
		
 
		determineCurrentActorDirection();
		determineCurrentActorAction();
		
		Shape shape = status.getRect();
		float x = shape.getX();
		float y = shape.getY();
		
		spriteSheet.getSubImage(currentActorAction+spriteX,currentActorDirection+spriteY).draw(x-offsetX, y-offsetY,32,32);
		
	}
	
	 
	private void determineCurrentActorAction(){
		
		boolean isWalking = status.hasEffects(Effect.EFFECTS_AMBULATING);
		boolean isInteracting = status.hasEffect(Effect.EFFECT_INTERACTING);
		
		
		if (isInteracting){
			currentActorAction = EnemyRenderer.INTERACT;
			return;
		}
		if (isWalking){
			
			walkSpriteCounter =(1 + walkSpriteCounter)%walkSpriteDuration;
			if (walkSpriteCounter == 0){
				currentActorAction ++;
				if(currentActorAction > numSprites){
					currentActorAction = 0;
				}					
			}
			
			
			return;
		}
		
		
		currentActorAction = EnemyRenderer.STAND;
		
		
	}
	private void determineCurrentActorDirection(){
		
		
		
		float [] faceDirection = status.getFacingDirection();
		
		
		
		double facingAngle  = Math.atan2(faceDirection[1], faceDirection[0]) ;
		//Output of atan2 is from -pi to pi. Need to translate to 0 to 2 pi
		if (facingAngle<0){ facingAngle = facingAngle + 2*Math.PI;}
		
		//Assign actor direction according to octants, but we need to translate by a 16th of a rotation
		int dir = (int) Math.floor(16*facingAngle/(2*Math.PI));
		dir = (dir+1) %16;
		
		
		if(facingAngle >= -Math.PI/4 & facingAngle < Math.PI/4){
			currentActorDirection = RIGHT;
		} else if(facingAngle >= Math.PI/4 & facingAngle < 3*Math.PI/4){
			currentActorDirection = DOWN;
		}  else if(facingAngle >= 3*Math.PI/4 & facingAngle < 5*Math.PI/4){
			currentActorDirection = LEFT;
		} else if(facingAngle >= 5*Math.PI/4 & facingAngle < 7*Math.PI/4){
			currentActorDirection = UP;
		}
			
		return;
		
	}
	
}
