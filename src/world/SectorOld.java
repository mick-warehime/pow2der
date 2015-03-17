package world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

public class SectorOld {

	private ArrayList<int[]> tiles;
	private Shape shape;
	
	public SectorOld(Shape shape, ArrayList<int[]> tiles){
		this.tiles = tiles;
		this.shape = shape;
	}
	
 
	public ArrayList<int[]> getTiles(){
		return tiles;
	}
	
	public Shape getShape(){
		return shape;
	}
	
}
