package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import world.CollisionHandler;
import commands.CommandProvider;

public class Behavior implements CommandProvider{

	protected Status status;
	protected CollisionHandler collisionHandler;
	protected ArrayList<Command> commandStack;

	public Behavior(Status status2) {
		this.status = status2;
		
		this.commandStack = new ArrayList<Command>();
	}
	
	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Command> getCommands() {
		return (ArrayList<Command>) commandStack.clone();
	}

	public void determine() {
		throw new UnsupportedOperationException("Not Implemented");
			
	}

}