package world;

import gameobjects.BasicObject;
import items.Item;

import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/*
 * Stores sectors and keeps track of active ones
 * 
 */

public class SectorMap{

	private Sector[][] sectorGrid;
	private ArrayList<Sector> activeSectors;
	private int[][] solidMap;
	
	
	private int numRows;
	private int numCols;
	private int sectorWidthInPixels;
	private int sectorHeightInPixels;
	
	

	public SectorMap(int levelWidthInPixels, int levelHeightInPixels, int numXSectors, int numYSectors) throws SlickException{

		numRows = numYSectors;
		numCols = numXSectors;

		sectorWidthInPixels = levelWidthInPixels/numXSectors;
		sectorHeightInPixels = levelHeightInPixels/numYSectors;

		assert sectorHeightInPixels*numYSectors == levelHeightInPixels : "Number of sectors must divide level dimensions!";
		assert sectorWidthInPixels*numXSectors == levelWidthInPixels : "Number of sectors must divide level dimensions!";

		
		this.activeSectors = new ArrayList<Sector>();

		sectorGrid = new Sector[numRows][numCols];

		for (int i = 0; i<numRows; i++){
			for (int j = 0; j<numCols; j++){
				int xMin = j*sectorWidthInPixels;
				int yMin = i*sectorHeightInPixels;
				sectorGrid[i][j] = new Sector(xMin,yMin,sectorWidthInPixels,sectorHeightInPixels);
				activeSectors.add(sectorGrid[i][j]); //All sectors added for now
			}
		}
		
		int numXTiles = levelWidthInPixels/World.TILE_WIDTH;
		int numYTiles = levelHeightInPixels/World.TILE_HEIGHT;
		
		solidMap = new int[numYTiles][numXTiles];

		

	}

	public void placeObjectInSector(Object obj, int xPos, int yPos) throws SlickException{

		Sector sector = getSectorWithPosition(xPos,yPos);

		sector.addObject(obj);

	}

	private Sector getSectorWithPosition(int xPos, int yPos) {

		
		
		int i = yPos/(sectorHeightInPixels*numRows);
		int j = xPos/(sectorWidthInPixels*numCols);
		
		if (i<0) { i = 0;}
		if (j<0) {j = 0;}
		if (i>=numRows) {i = numRows-1;}
		if (j>=numCols) {j = numCols-1;}
		
		return this.sectorGrid[i][j];

	}
	
	public void refreshActiveSectors(){
		
	}
	
	public ArrayList<Sector> getActiveSectors(){
		return this.activeSectors;
	}

	
	
	public HashSet<Sector> getSectorsNear(Shape shape){
		
		
		
		HashSet<Sector> output = new HashSet<Sector>();
		
		int height = (int) shape.getHeight();
		int width = (int) shape.getWidth();
		
		int x = (int) shape.getX() + width/2;
		int y = (int) shape.getY() + height/2;
		
		
		
		output.add(getSectorWithPosition(x-width, y-height));
		output.add(getSectorWithPosition(x-width, y));
		output.add(getSectorWithPosition(x-width, y+height));
		output.add(getSectorWithPosition(x, y-height));
		output.add(getSectorWithPosition(x, y));
		output.add(getSectorWithPosition(x, y+height));
		output.add(getSectorWithPosition(x+width, y-height));
		output.add(getSectorWithPosition(x+width, y));
		output.add(getSectorWithPosition(x+width,y+height));
		
		
		
		
		return output;
		
	}

	public void assignToItems(CurrentLevelData currentLevelData) {
		
		for (int i = 0; i<numRows; i++){
			for (int j = 0; j<numCols; j++){
				for (BasicObject obj : sectorGrid[i][j].getBasicObjects()){
					if (obj instanceof Item){
						((Item)obj).setCurrentLevelData(currentLevelData);
					}
				}
			}
		}
		
		
	}

	


	
	
}

