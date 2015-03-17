package render;


import java.util.ArrayList;

import main.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import world.Level;
import world.LevelBuilder;
import world.World;

/* 
 * Renders static objects close to the player's position
 * 
 */

public class LevelStaticRenderer extends Renderer{

	private ArrayList<Shape> walls;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;
	private Image wallImage;
	private Image floorImage;
	private Image hallImage;

	private int playerX;
	private int playerY;

	private int xDrawCutoffPixels;
	private int yDrawCutoffPixels;

	private int tileSize;

	public LevelStaticRenderer(Level level) throws SlickException {
		this.wallImage = World.spriteSheet.getSubImage(26,5);					
		this.floorImage = World.spriteSheet.getSubImage(25,40);
		this.hallImage = World.spriteSheet.getSubImage(60,25);


		this.tileSize = World.TILE_HEIGHT;

		this.xDrawCutoffPixels = Game.WIDTH;
		this.yDrawCutoffPixels = Game.HEIGHT;

		this.walls = level.getWalls();
		this.floors = level.getFloors();
		this.halls = level.getHalls();

		// make a list of light sources??

	}




	public void recordPlayerPosition(int playerX, int playerY){

		this.playerX = playerX;
		this.playerY = playerY;

	}

	private void render(int offsetX, int offsetY) {

		renderDimmed(offsetX,offsetY);


	}

	private void renderDimmed(int offsetX, int offsetY){

		float alpha = 0.85f;

		for (Shape wall : walls){
			if(onScreen(wall,playerX,playerY)){
				wallImage.setAlpha(alpha);
				wallImage.draw(wall.getX()-offsetX,wall.getY()-offsetY);			
			}

		}
		for (Shape floor : floors){
			if(onScreen(floor,playerX,playerY)){			
				floorImage.setAlpha(alpha);
				floorImage.draw(floor.getX()-offsetX,floor.getY()-offsetY);
			}
		}
		for (Shape hall : halls){
			if(onScreen(hall,playerX,playerY)){
				hallImage.setAlpha(alpha);
				hallImage.draw(hall.getX()-offsetX,hall.getY()-offsetY);
			}
		}
	}




	private boolean onScreen(Shape shape, int playerX, int playerY){
		boolean onScreen = true;


		if( Math.abs(shape.getX()-playerX) > xDrawCutoffPixels){
			onScreen = false;
			return onScreen;
		}
		if( Math.abs(shape.getY()-playerY) > yDrawCutoffPixels){
			onScreen = false;
			return onScreen;
		}

		//		given a shape and the players position determine if the shape should be drawn


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
