package collisions;


import interfaces.Broadcaster;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;

import world.Level;

public class ContextualCollisions {

	// Objects that do something on collision
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BroadcasterCommandProvider> listeners;

	

	public ContextualCollisions(Level level){
		level.getBasicObjects();
		this.broadcasters = level.getBroadcasters();
		this.listeners = new ArrayList<BroadcasterCommandProvider>();
	}
	

	public void update(){
		
		for (BroadcasterCommandProvider listener : listeners){
			
			Shape shape = listener.getOwnerShape();
			Class<?> className = listener.getOwnerClass();
			
			for (Broadcaster bcaster : broadcasters){
				if (shape.intersects(bcaster.getInteractionRange())){
					bcaster.onCollisionDo(className, shape);
					ArrayList<Command> commands = bcaster.onCollisionBroadcast(className, shape);
					listener.addCommands(commands);
				}
			}
			
			
			
		}
		
		for (Iterator<BroadcasterCommandProvider> iterator = listeners.iterator(); iterator.hasNext();){
			BroadcasterCommandProvider bcp = iterator.next();
			if (bcp.shouldRemove()){
				iterator.remove();
			}
		}
		
	}
	


	

	
	

	


	


	public void addListener(BroadcasterCommandProvider bcp) {
		this.listeners.add(bcp);
		
	}






}
