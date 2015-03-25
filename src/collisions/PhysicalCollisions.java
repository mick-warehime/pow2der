package collisions;


import gameobjects.BasicObject;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import world.LevelBuilder;
import world.Sector;
import world.SectorMap;

/*
 * Provides information about physically collideable 
 * Objects in a level;
 * 
 */

public class PhysicalCollisions  {

	private SectorMap sectorMap;

	public PhysicalCollisions( SectorMap sectorMap){
		this.sectorMap = sectorMap;
	}



	//Checks for collisions with blocks and game Objects
	public boolean isCollidedWithSolids(Shape shape){	
		
		return isCollidedWithBasicObjects(shape);
	}

	

	private boolean isCollidedWithBasicObjects(Shape shape){

		for (Sector sector : sectorMap.getSectorsNear(shape)){


			for(BasicObject obj: sector.getBasicObjects()){
				// don't check with its own shape and dont check with objects that are currently being held
				assert (obj.getShape() != null) : "Error! Object" + obj + " has no shape!";

				if(obj.getShape() != shape){
					if(obj.canCollide()){
						if(shape.intersects(obj.getShape())){
							return true;
						}
					}
				}
			}
		}
		
		return false;
		
	}
	
	
	
	private int[][] generateLocalMap(int xTopLeft, int yTopLeft, int widthInTiles, int heightInTiles, int tileWidth, int tileHeight){
		
		int m = heightInTiles;
		int n = widthInTiles;
		
		int[][] map = new int[m][n];
		
		Rectangle tileShape = new Rectangle(xTopLeft,yTopLeft,tileWidth,tileHeight);
		
		for (int i = 0; i<m; i++){
			for (int j = 0; j<n; j++){
				tileShape.setX(xTopLeft + j*tileWidth);
				tileShape.setY(yTopLeft + i*tileHeight);
				
				if (isCollidedWithSolids(tileShape))
				{map[i][j] = LevelBuilder.OBJECT_WALL_TILE;}
				else
				{
				map[i][j] = LevelBuilder.OBJECT_ROOM_TILE;
				}
				
			}
			
		}
		
		
		return map;
	}



	public int[][] generateLocalMap(int[] localMapDimensions, int tileWidth,
			int tileHeight) {
		
		int xTopLeft = localMapDimensions[0];
		int yTopLeft = localMapDimensions[1];
		int widthInTiles = localMapDimensions[2];
		int heightInTiles = localMapDimensions[3];
		
		
		return generateLocalMap( xTopLeft,  yTopLeft,  widthInTiles,  heightInTiles,  tileWidth,  tileHeight);
			
	}






}
