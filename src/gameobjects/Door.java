package gameobjects;


import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import actors.Status;

public class Door extends GameObject implements Interactive{
	
	protected boolean open = false;

	public Door(int x, int y, int w, int h, String name, TiledMap map,
			Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		this.proximity = 10;
		// TODO Auto-generated constructor stub
	}

	public void render(int mapX, int mapY){			
		
		if(!open){
			graphics.renderTile((int) shape.getX(), (int) shape.getY(), mapX,mapY, (float) 1);
		}
	}
	
	
	
	public boolean canCollide(){
		return !open;
	}

	@Override
	public void interact(int interactionType, Status status) {
		if (interactionType != Interactive.INTERACTION_TOGGLE){
			return;
		}
		
		if (this.collisionHandler.isCollidedWithActor(this.shape)){return;}
		
		open = !open;
		
	}
}
