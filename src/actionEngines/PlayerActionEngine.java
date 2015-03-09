package actionEngines;

import gameobjects.Interactive;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import abilities.FireballAbility;
import abilities.RunAbility;
import actors.Effect;
import actors.Status;
import commands.CommandProviderAggregator;
import commands.MoveCommand;


//Takes in command inputs and implements corresponding actions
public class PlayerActionEngine extends ActorActionEngine {


	public PlayerActionEngine(CommandProviderAggregator listener, Status status,AbilitySlots abilitySlots,ArrayList<Object>objectsToCreate) throws NullPointerException, IndexOutOfBoundsException, SlickException{
		super(listener,status, abilitySlots,objectsToCreate);
		
		this.acceleration = 2;
		this.walkSpeed = 2;
		this.runSpeed = 4;
		
		abilitySlots.setAbility(new RunAbility(), 0);
		abilitySlots.setAbility(new FireballAbility(), 1);
		
	}

	
	
	

	
	
	public void attemptInteract( int interactionType){
		
		if (!canInteract()){
			return;
		}
		
		//Get nearby objects to interact with
		ArrayList<Interactive> objs = status.nearbyInteractives();
		
		//Interact, if possible
		if (!objs.isEmpty()){
			for (Interactive obj : objs){
				obj.interact(interactionType, status);
			}
			status.gainEffect(Effect.EFFECT_INTERACTING, 20);
			
		}
	}
	
	
	
	
	
	




	private boolean canInteract() {
		
		return !status.hasEffects(Effect.EFFECTS_PREVENTING_ACTION);
	}







	
	
	
	
	protected void doActions() throws SlickException {
		
		//Do actions from commands
		super.doActions();
		//Get all player commands
		ArrayList<Command> currentActionCommands = listenerAggregator.popCurrentActionCommands();
//		System.out.println(currentActionCommands.size());
//		
		//See if the player should decelerate
		boolean triedXMove = false;
		boolean triedYMove = false;
//		

		//Check which actions are done (There is a better what to do this)
		for (Command cmd : currentActionCommands){
//			System.out.println(cmd);
			if (cmd instanceof MoveCommand){
				if (((MoveCommand) cmd).getDirection()=='x'){
					triedXMove = true;
				}else{
					triedYMove = true;
				}
				
			}
		}
		
		if (!triedXMove ){
			decelerate('x');
		}
		if (!triedYMove ){
			decelerate('y');
		}
		
		

	}

	//////////////////////////
	
	private void decelerate(char direction) {
		
		if (direction == 'x'){
			if (vx>0){ vx = Math.max(vx-acceleration,(float) 0);}
			if (vx<0){ vx = Math.min(vx+acceleration,(float) 0);}
		}
		
		if (direction == 'y'){
			if (vy>0){ vy = Math.max(vy-acceleration,(float) 0);}
			if (vy<0){ vy = Math.min(vy+acceleration,(float) 0);}
		}
		
	}

	
	



}
