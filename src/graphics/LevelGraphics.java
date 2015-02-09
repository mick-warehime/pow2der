package graphics;


import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import world.Level;
import world.World;

public class LevelGraphics{

	
	private SpriteSheet spriteSheet = new SpriteSheet("data/metroidtiles.png",16,16);
	private ArrayList<Shape> blocks;
	
	private int levelHeight;
	private int levelWidth;

	private int offsetX = 0;
	private int offsetY = 0;

	private int bufferDistX = 12; // number of tiles away from edge
	private int bufferDistY = 8; 
	private int bufferX; // number of tiles away from edge
	private int bufferY; // number of tiles away from edge

	private int tileSize;
	private int screenWidth;
	private int screenHeight;
	
	
	public LevelGraphics(Level level, int levelHeight, int levelWidth) throws SlickException {
		
		this.screenWidth = main.Game.WIDTH;
		this.screenHeight = main.Game.HEIGHT;

		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;

		this.tileSize = World.TILE_HEIGHT;
		
		blocks = level.getBlocks();
		
	}
	
	
	
	public void render(Graphics g, int playerX, int playerY) {
		
		setLevelCoordinates(playerX,playerY);
		
		
		for (Shape block : blocks){
//			System.out.println(block.getX()+" "+block.getY());
			spriteSheet.getSubImage(2,2).draw(block.getX()-offsetX,block.getY()-offsetY);
		}
		
	}
	
	
	private void setLevelCoordinates(int playerX, int playerY){

		// allows the player to get within bufferX/bufferY of the top/side
		// int offset, int coord, int buffer, int levelDim, int screenDim ){
		offsetX = boundCoordinate(offsetX,playerX,bufferX,levelWidth*tileSize,screenWidth);
		offsetY = boundCoordinate(offsetY,playerY,bufferY,levelHeight*tileSize,screenHeight);


		if(offsetX<0){
			offsetX = 0;
			bufferX = 0;			
		}else if(offsetX>(levelWidth*tileSize-screenWidth)){
			offsetX = levelWidth*tileSize-screenWidth;
			bufferX = 0;
		}else{
			bufferX = bufferDistX*tileSize;

		}


		if(offsetY<0){
			offsetY = 0;
			bufferY = 0;			
		}
		else if(offsetY>(levelHeight*tileSize-screenHeight)){
			offsetY = levelHeight*tileSize-screenHeight;
			bufferY = 0;
		}
		else{
			bufferY = bufferDistY*tileSize;
		}
	}
	
	public int boundCoordinate(int offset, int coord, int buffer, int levelDim, int screenDim ){


		// have the 
		if ((coord+buffer-screenDim)>offset){return (coord+buffer-screenDim);}
		if ((coord-buffer)<offset){return (coord-buffer);}


		return offset;

	}



	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}

}
