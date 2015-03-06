package gameobjects;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import commands.DieCommand;

public class DeadlyObject extends StaticObject implements Broadcaster{
		
		 
	
	

	public DeadlyObject(Image image, int xPos, int yPos) throws SlickException {
		super(image, xPos, yPos);
		
	}
	public boolean canCollide(){
		return false;
	}
	@Override
	public void onCollisionDo(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class<?> collidingObjectClass, Shape collidingObjectShape) {
		
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DieCommand());
		return list;
		// TODO Auto-generated method stub
		
	}

	
}
