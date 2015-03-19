package collisions;


import interfaces.Broadcaster;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;

import world.Sector;
import world.SectorMap;

public class ContextualCollisions {

	// Objects that do something on collision
	private ArrayList<BroadcasterCommandProvider> listeners;
	private SectorMap sectorMap;
	
	
	

	public ContextualCollisions( SectorMap sectorMap){
		this.sectorMap = sectorMap;
		this.listeners = new ArrayList<BroadcasterCommandProvider>();
	}
	

	public void update(){
		
		for (BroadcasterCommandProvider listener : listeners){
			
			Shape shape = listener.getOwnerShape();
			Class<?> className = listener.getOwnerClass();
			
			
			for (Broadcaster bcaster : broadcastersInRangeOf(shape)){
				bcaster.onCollisionDo(className, shape);
				ArrayList<Command> commands = bcaster.onCollisionBroadcast(className, shape);
				listener.addCommands(commands);
			}
			
			
			
		}
		
		for (Iterator<BroadcasterCommandProvider> iterator = listeners.iterator(); iterator.hasNext();){
			BroadcasterCommandProvider bcp = iterator.next();
			if (bcp.shouldRemove()){
				iterator.remove();
			}
		}
		
	}
	


	

	
	

	


	


	private ArrayList<Broadcaster> broadcastersInRangeOf(Shape shape) {
		ArrayList<Broadcaster> output = new ArrayList<Broadcaster>();
		
		
		
		for (Sector sector: sectorMap.getSectorsNear(shape)){

			for (Broadcaster bcaster: sector.getBroadcasters()){
				if (shape.intersects(bcaster.getInteractionRange())){ output.add(bcaster);}
			}
		}
		
		return output;
	}


	public void addListener(BroadcasterCommandProvider bcp) {
		this.listeners.add(bcp);
		
	}






}
