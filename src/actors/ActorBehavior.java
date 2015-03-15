package actors;

import interfaces.CommandProvider;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

public class ActorBehavior implements CommandProvider{

	protected Status status;
//	protected CollisionHandler collisionHandler;
	protected ArrayList<Command> commandStack;

	public ActorBehavior(Status status) {
		this.status = status;
		this.commandStack = new ArrayList<Command>();
	}
	
//	public void setCollisionHandler(CollisionHandler collisionHandler){
//		this.collisionHandler = collisionHandler;
//	}

	
	public ArrayList<Command> getCommands() {
		@SuppressWarnings("unchecked")
		ArrayList<Command> outputCommands = (ArrayList<Command>) commandStack.clone();
		return outputCommands;
	}

	public void determine() {
		throw new UnsupportedOperationException("Not Implemented");
			
	}

}