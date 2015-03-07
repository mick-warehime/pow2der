package render;


import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import world.Level;
import world.World;

/* 
 * Renders static objects close to the player's position
 * 
 */

public class LevelStaticRenderer extends Renderer{

	private ArrayList<Shape> walls;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;


	private int playerX;
	private int playerY;

	private int tileSize;
<<<<<<< HEAD:src/graphics/LevelGraphics.java
	private int screenWidth;
	private int screenHeight;
	
	public LevelGraphics(Level level) throws SlickException {

		this.screenWidth = main.Game.WIDTH;
		this.screenHeight = main.Game.HEIGHT;

		this.levelWidth = level.getWidth()*LevelBuilder.SCALING;
		this.levelHeight = level.getHeight()*LevelBuilder.SCALING;
=======
	public LevelStaticRenderer(Level level) throws SlickException {

		
>>>>>>> 4134c8ff81e56ab80be00b9c12568442ee96f6c7:src/render/LevelStaticRenderer.java

		this.tileSize = World.TILE_HEIGHT;

		this.walls = level.getWalls();
		this.floors = level.getFloors();
		this.halls = level.getHalls();
		
<<<<<<< HEAD:src/graphics/LevelGraphics.java
	}

	public void render(int playerX, int playerY) {

		setLevelCoordinates(playerX,playerY);

		renderDimmed(playerX, playerY);

//		renderVisible(playerX, playerY);
=======
		new ArrayList<Shape>();

	}


	public void recordPlayerPosition(int playerX, int playerY){
		
		this.playerX = playerX;
		this.playerY = playerY;
		
	}
	
	private void render(int offsetX, int offsetY) {

		renderDimmed(offsetX,offsetY);

		renderVisible(offsetX,offsetY);
>>>>>>> 4134c8ff81e56ab80be00b9c12568442ee96f6c7:src/render/LevelStaticRenderer.java

	}

	private void renderDimmed(int offsetX, int offsetY){

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



<<<<<<< HEAD:src/graphics/LevelGraphics.java
=======
	private void renderVisible( int offsetX, int offsetY){

		float alpha = 50000f;
		
		for (Shape wall : walls){
			if(isVisible(wall,playerX,playerY)){
				
				Image im = World.spriteSheet.getSubImage(26,5);					
				
				im.setAlpha((float) (alpha/distToPlayer(wall,playerX,playerY)));
				im.draw(wall.getX()-offsetX,wall.getY()-offsetY);				
			}

		}
		for (Shape floor : floors){
			if(isVisible(floor,playerX,playerY)){
			
				Image im = World.spriteSheet.getSubImage(25,40);

				im.setAlpha((float) (alpha/distToPlayer(floor,playerX,playerY)));
				im.draw(floor.getX()-offsetX,floor.getY()-offsetY);
			}
		}
		for (Shape hall : halls){
			if(isVisible(hall,playerX,playerY)){
				Image im = World.spriteSheet.getSubImage(60,25);

				im.setAlpha((float) (alpha/distToPlayer(hall,playerX,playerY)));
				im.draw(hall.getX()-offsetX,hall.getY()-offsetY);
			}
		}
	}

>>>>>>> 4134c8ff81e56ab80be00b9c12568442ee96f6c7:src/render/LevelStaticRenderer.java

	
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


		return onScreen;

	}


<<<<<<< HEAD:src/graphics/LevelGraphics.java
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
=======


	private boolean isVisible(Shape shape, int playerX, int playerY){

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


	@Override
	public void render(Graphics g, int offsetX, int offsetY) {
		render(offsetX,offsetY);
		
>>>>>>> 4134c8ff81e56ab80be00b9c12568442ee96f6c7:src/render/LevelStaticRenderer.java
	}


	
	




}
