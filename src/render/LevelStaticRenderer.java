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
	public LevelStaticRenderer(Level level) throws SlickException {

		

		this.tileSize = World.TILE_HEIGHT;

		this.walls = level.getWalls();
		this.floors = level.getFloors();
		this.halls = level.getHalls();
		
		new ArrayList<Shape>();

	}


	public void recordPlayerPosition(int playerX, int playerY){
		
		this.playerX = playerX;
		this.playerY = playerY;
		
	}
	
	private void render(int offsetX, int offsetY) {

		renderDimmed(offsetX,offsetY);

		renderVisible(offsetX,offsetY);

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
		
	}


	
	




}
