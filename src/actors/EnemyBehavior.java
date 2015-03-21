package actors;

import org.newdawn.slick.Graphics;

import interfaces.CommandProvider;
import pathfinding.Path;
import render.AStarRenderer;
import knowledge.Knowledge;
import commands.ActivateAbilityCommand;
import commands.MoveCommand;

//Gives commands to an actor based on world conditions
public class EnemyBehavior extends ActorBehavior implements CommandProvider{

	private Knowledge knowledge;

	private BehaviorProfile behaviorProfile;

	private Path path;
	private AStarRenderer aStarRenderer;

	public EnemyBehavior(Status status, Knowledge knowledge, int enemyID) {
		super(status);
		this.knowledge = knowledge;

		behaviorProfile  = new BehaviorProfile(enemyID);

	}

	public BehaviorProfile getBehaviorProfile(){
		return behaviorProfile;
	}

	public void determine(){

		commandStack.clear();

		// check to see if you can now see the duder
		getsAgro();

		decideAttack();

		decideMovement();


	}

	private void decideAttack(){
		if(canAttack()){
			commandStack.add(new ActivateAbilityCommand(0));			
		}
	}


	private void decideMovement(){


		// if the dude can see the player chase him, otherwise keep going in same direction
		float[] currentDirection = status.getFacingDirection();

		if(movesTowardPlayer() ){

			// only update 
			currentDirection = knowledge.directionToPlayer();

			status.gainEffect(Effect.EFFECT_AGRO, behaviorProfile.getAgroTime());
			status.gainEffect(Effect.EFFECT_CHASING, behaviorProfile.getChaseTime());


		 
			// astar path should poop out a list of directions 
			// like left left left up up up up right right right
			// astar movement should just run through those
			// this can just check if the astar list is empty

		}else if (needsAstar() ){
			path = knowledge.aStarPath();
			aStarRenderer = new AStarRenderer(path);
		}

		// make sure the dude bounces off walls
		if (status.hasEffect(Effect.EFFECT_X_COLLISION)){
			currentDirection[0] = (float) (2*Math.random()-1);			
		}
		if (status.hasEffect(Effect.EFFECT_Y_COLLISION)){
			currentDirection[1] = (float) (2*Math.random()-1);
		}

		status.setFacingDirection(currentDirection);

		commandStack.add(new MoveCommand(currentDirection));

		return;
	}

	public void render(Graphics g, int offsetX, int offsetY){
		if(!(path==null) & status.hasEffect(Effect.EFFECT_CHASING)){
			aStarRenderer.render(g,offsetX,offsetY);
		}
	}





	private boolean canAttack(){

		if(!knowledge.playerIsVisible()){
			return false;
		}
		if(knowledge.distToPlayer() > behaviorProfile.getAttackDistance()){
			return false;
		}
		if(status.hasEffects(Effect.EFFECTS_PREVENTING_MOVEMENT)){
			return false;
		}
		if(!status.hasEffect(Effect.EFFECT_AGROED)){
			return false;
		}

		return true;

	}

	private void getsAgro(){

		if(behaviorProfile.getsAgro(knowledge, status)){
			status.gainEffect(Effect.EFFECT_AGROED,behaviorProfile.getAgroTime());
		}

	}

	public boolean needsAstar(){
//		return status.hasEffect(Effect.EFFECT_CHASING) & !knowledge.playerIsVisible();
		return status.hasEffect(Effect.EFFECT_CHASING);
	}


	private boolean movesTowardPlayer(){
		return status.hasEffect(Effect.EFFECT_AGROED) & knowledge.playerIsVisible();
	}


}
