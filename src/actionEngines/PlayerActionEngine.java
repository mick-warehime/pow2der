package actionEngines;

import gameobjects.BasicObject;
import gameobjects.Interactive;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import abilities.RunAbility;
import actors.Effect;
import actors.Status;
import commands.CommandProviderAggregator;
import commands.MoveCommand;


//Takes in command inputs and implements corresponding actions
public class PlayerActionEngine extends ActorActionEngine {

	
	

		
	private float runDec = 2.5f;

	
	

	


	public PlayerActionEngine(CommandProviderAggregator listener, Status status,AbilitySlots abilitySlots,ArrayList<Object>objectsToCreate) throws NullPointerException, IndexOutOfBoundsException, SlickException{
		super(listener,status, abilitySlots,objectsToCreate);
		
		this.runAcc = 2;
		this.walkSpeed = 2;
		this.runSpeed = 4;
		
		abilitySlots.setAbility(new RunAbility(), 0);
	}

	
	
	

	
	
	public void attemptInteract( int interactionType){
		
		if (!canInteract()){
			return;
		}
		
		//Get nearby objects to interact with
		ArrayList<BasicObject> objs = status.nearbyInteractives();
		
		//Interact, if possible
		if (!objs.isEmpty()){
			for (BasicObject obj : objs){
				((Interactive) obj).interact(interactionType, status);
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

//		
		//See if the player should decelerate
		boolean triedXMove = false;
		boolean triedYMove = false;
//		

		//Check which actions are done (There is a better what to do this)
		for (Command cmd : currentActionCommands){
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
			if (vx>0){ vx = Math.max(vx-runDec,(float) 0);}
			if (vx<0){ vx = Math.min(vx+runDec,(float) 0);}
		}
		
		if (direction == 'y'){
			if (vy>0){ vy = Math.max(vy-runDec,(float) 0);}
			if (vy<0){ vy = Math.min(vy+runDec,(float) 0);}
		}
		
	}

	
	



}
