package collisions;


import gameobjects.BasicObject;
import gameobjects.Broadcaster;
import gameobjects.Interactive;

import java.util.ArrayList;
import java.util.Observable;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import commands.AddInteractiveCommand;
import commands.BroadcasterCommandProvider;
import world.Level;

public class ContextualCollisions {

	// Objects that do something on collision
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;

	private ArrayList<BroadcasterCommandProvider> listeners;

	

	public ContextualCollisions(Level level){
		this.basicObjects = level.getBasicObjects();
		this.broadcasters = level.getBroadcasters();
		this.listeners = new ArrayList<BroadcasterCommandProvider>();
	}
	

	public void update(){
		
		for (BroadcasterCommandProvider listener : listeners){
			
			Shape shape = listener.getOwnerShape();
			Class<?> className = listener.getOwnerClass();
			
			for (Broadcaster bcaster : broadcasters){
				if (shape.intersects(bcaster.getShape())){
					bcaster.onCollisionDo(className, shape);
					ArrayList<Command> commands = bcaster.onCollisionBroadcast(className, shape);
					listener.addCommands(commands);
				}
			}
			
			for (BasicObject obj : basicObjects){
				if(obj instanceof Interactive){
					if(obj.isNear(shape)){
						AddInteractiveCommand cmd = new AddInteractiveCommand((Interactive) obj);
						listener.addCommand(cmd);
					}
				}
			}
			
		}
		
	}
	


	

	
	

	


	


	public void addListener(BroadcasterCommandProvider bcp) {
		this.listeners.add(bcp);
		
	}






}
