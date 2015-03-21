package world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class SectorizedMap {

	private ArrayList<SectorOld> sectorOlds;
	private ArrayList<SectorOld> activeSectors;
	private int sectorSize;

	public SectorizedMap(int[][] tileMap, int sectorSize){
		this.sectorSize = sectorSize;
		generateSectors(tileMap,sectorSize);
		activeSectors = new ArrayList<SectorOld>();
	}

	private void generateSectors(int[][] tileMap, int size){
		sectorOlds = new ArrayList<SectorOld>();
		
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
				sectorOlds.add(new SectorOld(shape,tiles));
			}
		}

	}
	
	public void update(Shape playerShape){

		activeSectors = new ArrayList<SectorOld>();
		
		// create a box around the player and collide it with the sectorOlds
		int xTile = (int) (playerShape.getX()/World.TILE_WIDTH);
		int yTile = (int) (playerShape.getY()/World.TILE_HEIGHT);
		int xMin = (int) (xTile-sectorSize/2);
		int yMin = (int) (yTile-sectorSize/2);
		Shape playerSectorShape = new Rectangle(xMin,yMin,sectorSize,sectorSize);
		
		// add any overlaping sectorOlds to the active sectorOlds list
		for(SectorOld sectorOld : sectorOlds){
			if(playerSectorShape.intersects(sectorOld.getShape())){		
				activeSectors.add(sectorOld);
			}
		}
	}
	
	
	public ArrayList<SectorOld> getActiveSectors(){
		return activeSectors;
	}

}
