package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GlobalInputListener;
import commands.MoveCommand;
import gameobjects.GameObject;
import gameobjects.Interactive;


//Takes in command inputs and implements corresponding actions
public class PlayerActionEngine extends ActorActionEngine {

	
	

	private int interactTimer = 0;
	private int interactTimerIncrement = 20;	
	private float runDec = 2.5f;

	
	

	


	public PlayerActionEngine(GlobalInputListener listener, Status status){
		super(listener,status);
		
		this.runAcc = 2;
		this.maxSpeed = 2;
	}

	
	
	

	public void attemptInteract( int interactionType){
		//Get nearby objects to interact with
		ArrayList<GameObject> objects = status.nearbyInteractives();
		
		//Interact, if possible
		if (interactTimer==0 && !objects.isEmpty()){
			for (GameObject gObj: objects){
				((Interactive) gObj).interact(interactionType);
			}
			
			interactTimer+= interactTimerIncrement;
		}
	}
	
	

	
	
	
	
	
	




	protected void updateTimers(){
		
		if (interactTimer>0){
			interactTimer -=1;
		}
		
		
		
	}
	
	
	
	protected void doActions() {
		
		//Do actions from commands
		super.doActions();
		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.getCurrentActionCommands();

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
