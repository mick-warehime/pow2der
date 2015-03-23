package render;


import org.newdawn.slick.Graphics;

import pathfinding.Path;
import world.World;

public class AStarRenderer implements LineObject{

 	protected float length;
 	protected Path path;
 	
	public AStarRenderer(Path path){		
		this.path = path;
	}
 
	public void render(Graphics g, int offsetX, int offsetY) {
		for(int p = (path.getLength()-1); p >1 ; p--){
			
			float delta = 8.0f;
			
			float x1 = (path.getX(p))*World.TILE_WIDTH+delta;
			float y1 = (path.getY(p))*World.TILE_HEIGHT+delta;
			float x2 = (path.getX(p-1))*World.TILE_WIDTH+delta;
			float y2 = (path.getY(p-1))*World.TILE_HEIGHT+delta;
	
			g.drawLine(x1-offsetX,y1-offsetY,x2-offsetX,y2-offsetY);
			
		}
	}

}
