package graphics;


import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import world.Level;
import world.NewLevel;
import world.NewLevelBuilder;
import world.World;

public class LevelGraphics{


	private SpriteSheet spriteSheet = new SpriteSheet("data/metroidtiles.png",16,16);

	private ArrayList<Shape> walls;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> doors;
	private ArrayList<Shape> halls;


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


	public LevelGraphics(NewLevel newLevel) throws SlickException {

		this.screenWidth = main.Game.WIDTH;
		this.screenHeight = main.Game.HEIGHT;

		this.levelWidth = newLevel.getWidth()*NewLevelBuilder.SCALING;
		this.levelHeight = newLevel.getHeight()*NewLevelBuilder.SCALING;

		this.tileSize = World.TILE_HEIGHT;

		walls = newLevel.getWalls();
		floors = newLevel.getFloors();
		doors = newLevel.getDoors();
		halls = newLevel.getHalls();

	}



	public void render(Graphics g, int playerX, int playerY) {

		setLevelCoordinates(playerX,playerY);

		for (Shape wall : walls){
			if(onScreen(wall,playerX,playerY)){
				spriteSheet.getSubImage(26,5).draw(wall.getX()-offsetX,wall.getY()-offsetY);
			}

		}
		for (Shape floor : floors){
			if(onScreen(floor,playerX,playerY)){
				spriteSheet.getSubImage(25,40).draw(floor.getX()-offsetX,floor.getY()-offsetY);
			}
		}
		for (Shape hall : halls){
			if(onScreen(hall,playerX,playerY)){
				spriteSheet.getSubImage(60,25).draw(hall.getX()-offsetX,hall.getY()-offsetY);
			}
		}

	}

	private boolean onScreen(Shape shape, int playerX, int playerY){
		//		given a shape and the players position determine if the shape should be drawn

		boolean onScreen = true;

		
		int xCutoff = 30*tileSize;
		int yCutoff = 25*tileSize;

		if( Math.abs(shape.getX()-playerX) > xCutoff){
			onScreen = false;
			return onScreen;
		}
		if( Math.abs(shape.getY()-playerY) > yCutoff){
			onScreen = false;
			return onScreen;
		}

//	double cutoff = 20*tileSize;
//		
//		double distance2 = Math.pow(playerX-shape.getX(),2) + Math.pow(playerY-shape.getY(),2);  
//
//		if( distance2 > Math.pow(cutoff,2)){
//			onScreen = false;
//			return onScreen;
//		}
		
		return onScreen;

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
