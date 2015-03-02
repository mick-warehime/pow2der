package graphics;


import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import world.Level;
import world.LevelBuilder;
import world.World;

public class LevelGraphics{

	private ArrayList<Shape> walls;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;


	private int levelHeight;
	private int levelWidth;

	private int offsetX = 0;
	private int offsetY = 0;

	private int bufferDistX = 15; // number of tiles away from edge
	private int bufferDistY = 10; 
	private int bufferX; // number of tiles away from edge
	private int bufferY; // number of tiles away from edge

	private int tileSize;
	private int screenWidth;
	private int screenHeight;
	

	private ArrayList<Shape> visitedTiles;

	public LevelGraphics(Level level) throws SlickException {

		this.screenWidth = main.Game.WIDTH;
		this.screenHeight = main.Game.HEIGHT;

		this.levelWidth = level.getWidth()*LevelBuilder.SCALING;
		this.levelHeight = level.getHeight()*LevelBuilder.SCALING;

		this.tileSize = World.TILE_HEIGHT;

		this.walls = level.getWalls();
		this.floors = level.getFloors();
		this.halls = level.getHalls();
		
		visitedTiles = new ArrayList<Shape>();

	}



	public void render(int playerX, int playerY) {

		setLevelCoordinates(playerX,playerY);

		renderDimmed(playerX, playerY);

		renderVisible(playerX, playerY);

	}

	private void renderDimmed(int playerX, int playerY){

		float alpha = 0.85f;
		
		for (Shape wall : walls){
			if(onScreen(wall,playerX,playerY)){
				
				Image im = World.spriteSheet.getSubImage(26,5);					
				im.setAlpha(alpha);
				im.draw(wall.getX()-offsetX,wall.getY()-offsetY);				
			}

		}
		for (Shape floor : floors){
			if(onScreen(floor,playerX,playerY)){
			
				Image im = World.spriteSheet.getSubImage(25,40);

				im.setAlpha(alpha);
				im.draw(floor.getX()-offsetX,floor.getY()-offsetY);
			}
		}
		for (Shape hall : halls){
			if(onScreen(hall,playerX,playerY)){
				Image im = World.spriteSheet.getSubImage(60,25);

				im.setAlpha(alpha);
				im.draw(hall.getX()-offsetX,hall.getY()-offsetY);
			}
		}
	}



	private void renderVisible(int playerX, int playerY){

		float alpha = 50000f;
		
		for (Shape wall : walls){
			if(visible(wall,playerX,playerY)){
				
				Image im = World.spriteSheet.getSubImage(26,5);					
				
				im.setAlpha((float) (alpha/distToPlayer(wall,playerX,playerY)));
				im.draw(wall.getX()-offsetX,wall.getY()-offsetY);				
			}

		}
		for (Shape floor : floors){
			if(visible(floor,playerX,playerY)){
			
				Image im = World.spriteSheet.getSubImage(25,40);

				im.setAlpha((float) (alpha/distToPlayer(floor,playerX,playerY)));
				im.draw(floor.getX()-offsetX,floor.getY()-offsetY);
			}
		}
		for (Shape hall : halls){
			if(visible(hall,playerX,playerY)){
				Image im = World.spriteSheet.getSubImage(60,25);

				im.setAlpha((float) (alpha/distToPlayer(hall,playerX,playerY)));
				im.draw(hall.getX()-offsetX,hall.getY()-offsetY);
			}
		}
	}


	
	private boolean onScreen(Shape shape, int playerX, int playerY){
		boolean onScreen = true;
		int xCutoff = 35*tileSize;
		int yCutoff = 30*tileSize;

		if( Math.abs(shape.getX()-playerX) > xCutoff){
			onScreen = false;
			return onScreen;
		}
		if( Math.abs(shape.getY()-playerY) > yCutoff){
			onScreen = false;
			return onScreen;
		}

		//		given a shape and the players position determine if the shape should be drawn


//		System.out.println(plaer);
		return onScreen;

	}




	private boolean visible(Shape shape, int playerX, int playerY){

		//		given a shape and the players position determine if the shape should be drawn

		boolean onScreen = true;

		double cutoff = 10*tileSize;

		double distance2 = distToPlayer(shape,playerX,playerY);

		if( distance2 > Math.pow(cutoff,2)){
			onScreen = false;
			return onScreen;
		}

		return onScreen;

	}

	private double distToPlayer(Shape shape, int playerX, int playerY){
		double distance2 = Math.pow(playerX-shape.getX(),2) + Math.pow(playerY-shape.getY(),2);  
		return distance2;
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
