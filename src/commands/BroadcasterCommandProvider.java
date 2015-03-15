package commands;

import gameobjects.Interactive;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;


/* Gives commands based on collisions with broadcasters */
public class BroadcasterCommandProvider implements CommandProvider {

	private Class<?> ownerClass;
	private Shape ownerShape;
	private ArrayList<Command> outputCommands;
	private ArrayList<Interactive> nearbyInteractives;
	
	

	public BroadcasterCommandProvider(Class<?> ownerClass, Shape ownerShape){
		
		this.ownerClass = ownerClass;
		this.ownerShape = ownerShape;
		
		this.outputCommands = new ArrayList<Command>();

		this.nearbyInteractives = new ArrayList<Interactive>();
		
	}
	
	public void addInteractive(Interactive obj){
		nearbyInteractives.add(obj);
	}
	
	public ArrayList<Interactive> popNearbyInteractives(){
		@SuppressWarnings("unchecked")
		ArrayList<Interactive> output = (ArrayList<Interactive>) this.nearbyInteractives.clone();
		this.nearbyInteractives.clear();
		return output;
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

}
