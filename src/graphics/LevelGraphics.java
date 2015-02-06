package graphics;

import gameobjects.BasicObject;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import world.NewLevel;

public class LevelGraphics{

	
	private SpriteSheet spriteSheet = new SpriteSheet("data/metroidtiles.png",16,16);
	private ArrayList<Shape> blocks;
	
	public LevelGraphics(NewLevel level) throws SlickException {
		
		blocks = level.getBlocks();
				
	}
	
	
	
	public void render(Graphics g, int terriX, int terriY) {
		
		for (Shape block : blocks){
//			System.out.println(block.getX()+" "+block.getY());
			spriteSheet.getSubImage(2,2).draw(block.getX(),block.getY());
		}
		
	}
	
	
//	public void render(Graphics g,int x, int y){		
//
//
//		// min/max sets the submatrix of tiles to draw		
//		int tXmin = (int) mapX/tileSize;
//		int tYmin = (int) mapY/tileSize;
//
//		// dX/dY are the offsets from the submatrix to the actual screen position
//		int dX = mapX - tXmin*tileSize;
//		int dY = mapY - tYmin*tileSize;
//
//		// allows the player to get within tolX/tolY of the top/side
//		if (mapX > (x - tolX) ){mapX = x-tolX;}
//		if (mapX < (x+tolX-width)){mapX = x+tolX-width;}
//		if (mapY > (y - tolY) ){mapY = y-tolY;}
//		if (mapY < (y+tolY-height)){mapY = y+tolY-height;}
//
//		// see if we are close to the edge of a map inwhich case dont let mapx<0 or mapx>size of map in pixels
//		mapCheck();
//
//
//		// map.render(-mapX,-mapY);
//		map.render(-dX,-dY,tXmin,tYmin,mapWidthInTiles,mapHeightInTiles+1,tileLayerId,false);
//
//		for (Actor nme: actors){
//			nme.render(g, mapX,mapY);
//		}
//
//		for (BasicObject obj: basicObjects){
//			
//			obj.render(g,mapX,mapY);
//
//		}
//
//	}
//
//	private void mapCheck(){
//		if(mapX<0){
//			mapX = 0;
//			tolX = tileSize;			
//		}else if(mapX>map.getWidth()*tileSize-width){
//			mapX = map.getWidth()*tileSize-width;
//			tolX = tileSize;
//		}else{
//			tolX = tol*tileSize;
//		}
//
//
//		if(mapY<0){
//			mapY = 0;
//			tolY = tileSize;			
//		}
//		else if(mapY>map.getHeight()*tileSize-height){
//			mapY = map.getHeight()*tileSize-height;
//			tolY = tileSize;
//		}
//		else{
//			tolY = tol*tileSize;
//		}
//	}
//
//	
// 

}
