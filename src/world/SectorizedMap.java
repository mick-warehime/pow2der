package world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class SectorizedMap {

	private ArrayList<Sector> sectors;
	private ArrayList<Sector> activeSectors;
	private int sectorSize;

	public SectorizedMap(int[][] tileMap, int sectorSize){
		this.sectorSize = sectorSize;
		generateSectors(tileMap,sectorSize);
		activeSectors = new ArrayList<Sector>();
	}

	private void generateSectors(int[][] tileMap, int size){
		sectors = new ArrayList<Sector>();
		
		int numRows = tileMap.length;
		int numCols = tileMap[0].length;

		for(int col= 0; col < numCols; col+=size){

			int xMin = col;
			int width = size;
			if((col+size)>numCols){
				width = numCols-col;
			}

			for(int row = 0; row < numRows; row+=size){


				int yMin = row;
				int height = size;
				if((row+size)>numRows){
					height = numRows-row;
				}	

				ArrayList<int[]> tiles = new ArrayList<int[]>();
				
				for(int x = col; x < (col+width); x++){
					for(int y = row; y < (row+height); y++){
						tiles.add(new int[] {x,y});
					}
				}
				
				Shape shape = new Rectangle(xMin,yMin,width,height);
				sectors.add(new Sector(shape,tiles));
			}
		}

	}
	
	public void update(Shape playerShape){

		activeSectors = new ArrayList<Sector>();
		
		// create a box around the player and collide it with the sectors
		int xTile = (int) (playerShape.getX()/World.TILE_WIDTH);
		int yTile = (int) (playerShape.getY()/World.TILE_HEIGHT);
		int xMin = (int) (xTile-sectorSize/2);
		int yMin = (int) (yTile-sectorSize/2);
		Shape playerSectorShape = new Rectangle(xMin,yMin,sectorSize,sectorSize);
		
		// add any overlaping sectors to the active sectors list
		for(Sector sector : sectors){
			if(playerSectorShape.intersects(sector.getShape())){		
				activeSectors.add(sector);
			}
		}
	}
	
	
	public ArrayList<Sector> getActiveSectors(){
		return activeSectors;
	}

}
