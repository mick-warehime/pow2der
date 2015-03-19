package world;

import interfaces.Broadcaster;

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

		

	}

	public void placeObjectInSector(Object obj, int xPos, int yPos) throws SlickException{

		Sector sector = getSectorWithPosition(xPos,yPos);

		sector.addObject(obj);

	}

	public Sector getSectorWithPosition(int xPos, int yPos) {

		
		
		int i = yPos/(sectorHeightInPixels*numRows);
		int j = xPos/(sectorWidthInPixels*numCols);
		
		return this.sectorGrid[i][j];

	}
	
	public void refreshActiveSectors(){
		
	}
	
	public ArrayList<Sector> getActiveSectors(){
		return this.activeSectors;
	}

	
	
	public HashSet<Sector> getSectorsNear(Shape shape){
		
		
		
		HashSet<Sector> output = new HashSet<Sector>();
		
		int x = (int) shape.getX();
		int y = (int) shape.getY();
		int height = (int) shape.getHeight();
		int width = (int) shape.getWidth();
		
		
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


	
	
}

