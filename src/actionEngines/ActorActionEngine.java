package actionEngines;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import abilities.Ability;
import abilities.AbilityObject;
import actors.Effect;
import actors.Status;
import commands.CommandProviderAggregator;


public class ActorActionEngine extends ActionEngine {



	protected Status status;
	protected float vx ;
	protected float vy ;
	protected float runAcc; 
	protected float walkSpeed;
	protected float runSpeed;
	protected AbilitySlots abilitySlots;
	private ArrayList<Object> objectsToCreate;



	public ActorActionEngine(CommandProviderAggregator listener, Status status, AbilitySlots slots, ArrayList<Object> objectsToCreate) {
		super(listener);
		this.status = status;
		this.abilitySlots = slots;
		this.objectsToCreate = objectsToCreate;
		
		
	}

	public void attemptMoveTo(char xOrY, int direction) {
		float maxSpeed;
		if (status.hasEffect(Effect.EFFECT_RUNNING)){
			maxSpeed = runSpeed;
		}else{
			maxSpeed = walkSpeed;
		}
		
		if (xOrY == 'x'){
			if (direction>0 ){
				vx = Math.min(vx + runAcc, maxSpeed);
				status.setDirection('x', 1);
			}else if(direction<0){
				vx = Math.max(vx - runAcc, -maxSpeed);
			} 
		}
		if (xOrY == 'y'){
			if (direction>0 ){
				vy = Math.min(vy + runAcc, maxSpeed);
			}else if(direction<0){
				vy = Math.max(vy - runAcc, -maxSpeed);
			} 
		}
		
		status.setDirection(xOrY, direction);

		return;

	}

	public void die(){
		status.setDying(true);
		return;
	}

	public void update() throws SlickException {
		
		doActions();
		movePhysics();
	}
	
	//////////////////////////

	protected void movePhysics() {        


		//X movement and collision checking
		boolean displacedX = attemptDisplacement(vx,'x');
		if (!displacedX){
			status.gainEffect(Effect.EFFECT_X_COLLISION, 1);
			vx = 0;
		}

		//Y movement and collision checking
		boolean displacedY = attemptDisplacement(vy,'y');
		if (!displacedY){
			status.gainEffect(Effect.EFFECT_Y_COLLISION, 1);
			vy = 0;
		}



		assert !status.isCollided() : "Actor at" + status.getX() + "," + status.getY() + " is inside an object!";

	}

	

	public boolean attemptDisplacement(float disp, char XorY){

		float dMax = disp;
		float lastValid = 0;

		if (disp == 0){
			return true;
		}

		int sign = 1;
		if (disp<0){ sign = -1;} //Accounts for a negative disp below

		float diff = (float) Math.abs(dMax*0.5);
		float minDiff = (float) 0.1;


		boolean collided;

		//Check displacements dMax until the maximum displacement
		// is reached. Store largest valid displacement.

		while (diff>minDiff){ //diff starts at dMax/2

			//Check if dMax displacement collides
			status.displace(dMax,XorY);
			collided = status.isCollided();
			status.displace(-dMax, XorY);

			if (!collided && dMax == disp){ //Maximum initial displacement doesn't collide, so accept it
				status.displace(dMax, XorY);
				return true;
			}

			if (collided){ //dMax collides, so decrease it.
				dMax -= sign*diff;

			}
			//Non-maximal displacement doesn't collide, so increase it
			// and store this valid displacement.
			if (!collided){ 
				lastValid = dMax;
				dMax += sign*diff;
			}

			diff = (float) (diff*.5);

		}

		//Do the last valid displacement 
		status.displace(lastValid,XorY);
		return (Math.abs(lastValid)>0); //Success if any displacement occurred


	}

	public void Teleport(int destX, int destY) {
		status.setX(destX);
		status.setY(destY);
	}
	
	public Status getStatus(){
		return status;
	}
	

	public void applyEffect(int effectName, int duration) {
		status.gainEffect(effectName, duration);

	}

	public void attemptActivateAbility(int abilitySlot) throws SlickException {
		
		if (!canActivate(abilitySlot)){
			return;
		}
		Ability ability = this.abilitySlots.getAbility(abilitySlot);
		
		int[][] onCastEffects = ability.getOnCastEffects();
		
		for (int i = 0; i<onCastEffects.length;i++){
			status.gainEffect(onCastEffects[i][0], onCastEffects[i][1]);
		}
		
		if (ability.hasAbilityObject()){
			AbilityObject obj = ability.instantiateAbilityObject((int)status.getX(), (int)status.getY());
			objectsToCreate.add(obj);
		}
		
		
		
		
	}

	private boolean canActivate(int abilitySlot) {
		
		return !status.hasEffects(Effect.EFFECTS_PREVENTING_ACTION);
	}
	
	

}