package commands;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;


/* Gives commands based on collisions with broadcasters */
public class BroadcasterCommandProvider implements CommandProvider {

	private Class<?> ownerClass;
	private Shape ownerShape;
	private ArrayList<Command> outputCommands;
	
	

	public BroadcasterCommandProvider(Class<?> ownerClass, Shape ownerShape){
		
		this.ownerClass = ownerClass;
		this.ownerShape = ownerShape;
		
		this.outputCommands = new ArrayList<Command>();

		
	}
	


	
	
	public Shape getOwnerShape(){
		return this.ownerShape;
	}
	
	

	public Class<?> getOwnerClass() {
		return this.ownerClass;
	}

	public void addCommands(ArrayList<Command> commands) {
		this.outputCommands.addAll(commands);		
	}
	
	public ArrayList<Command> getCommands(){
		 @SuppressWarnings("unchecked")
		ArrayList<Command> output = (ArrayList<Command>) outputCommands.clone();
		outputCommands.clear();
		return output;
		
	}





	public void addCommand(AddInteractiveCommand cmd) {
		this.outputCommands.add(cmd);
		if (cmd == null){
			throw new NullPointerException("Attempted to add a null command!");
		}
		
	}

}
