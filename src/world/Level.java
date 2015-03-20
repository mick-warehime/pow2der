package world;

import gameobjects.Door;
import gameobjects.Stairs;
import gameobjects.Wall;
import interfaces.Collider;
import items.Item;
import items.ItemBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import render.LevelStaticRenderer;
import actors.Enemy;
import actors.Player;
import collisions.ContextualCollisions;
import collisions.PhysicalCollisions;



/* Top level management of all game objects. */

public class Level {


	private int startX;
	private int startY;

	private int heightInTiles;
	private int widthInTiles;

	private SectorMap sectorMap;
	private int numXSectors = 2;
	private int numYSectors = 2;

	private int[][] map;

	private ContextualCollisions contextualCollisions;
	private PhysicalCollisions physicalCollisions;
	private LevelStaticRenderer renderer;


	private ArrayList<Stairs> stairsAll;








	public Level(ItemBuilder itemBuilder, int width, int height, Player player) throws SlickException {


		this.widthInTiles = width;
		this.heightInTiles = height;

		this.sectorMap = 
				new SectorMap(World.TILE_WIDTH*widthInTiles*LevelBuilder.SCALING,
						World.TILE_HEIGHT*heightInTiles*LevelBuilder.SCALING,
						numXSectors,
						numYSectors);


		contextualCollisions = new ContextualCollisions(sectorMap);
		physicalCollisions = new PhysicalCollisions(sectorMap);

		buildNewLevel(itemBuilder, player);






	};

	private void buildNewLevel(ItemBuilder itemBuilder, Player player) throws SlickException{

		this.stairsAll = new ArrayList<Stairs>();
		// build a new Level
		LevelBuilder levelBuilder = new LevelBuilder(widthInTiles,heightInTiles);

		// store the shapes for doors/walls/floors
		ArrayList<Shape> wallShapes = levelBuilder.getWalls();
		ArrayList<Shape> doors = levelBuilder.getDoors();
		ArrayList<Shape> floors = levelBuilder.getFloors();
		ArrayList<Shape> halls = levelBuilder.getHalls();



		renderer = new LevelStaticRenderer(floors,halls);

		map = levelBuilder.getMap();



		// poop out the starting position
		int[] startPosition = levelBuilder.getStartingPosition();
		startX = startPosition[0];
		startY = startPosition[1];

		for (Shape wallShape : wallShapes){

			addObject(new Wall(wallShape),(int) wallShape.getX(), (int)wallShape.getY());

		}

		// build items using the levelbuilder to get the random locations
		for(int[] itemLoc : levelBuilder.randomLocationsAllRooms(0.75,3)){
			Item item = itemBuilder.newItem(itemLoc[0],itemLoc[1]);
			addObject(item,(int)item.getShape().getX(),(int)item.getShape().getY());
		}

		for(int[] enemyLoc : levelBuilder.randomLocationsAllRooms(1,2)){
			Enemy enemy = new Enemy(enemyLoc[0],enemyLoc[1],this,player);

			addObject(enemy,(int) enemy.getX(), (int) enemy.getY());
		}

		// build two sets of stairsAll to the next world
		for(int[] stairLoc : levelBuilder.randomLocationsStartRoom()){
			Stairs stairs = new Stairs(stairLoc[0],stairLoc[1],false);
			int x = (int) stairs.getShape().getX();
			int y = (int) stairs.getShape().getY();
			stairsAll.add(stairs);
			addObject(stairs, x,y);
		}
		for(int[] stairLoc : levelBuilder.randomLocationsStartRoom()){
			Stairs stairs = new Stairs(stairLoc[0],stairLoc[1],true);
			int x = (int) stairs.getShape().getX();
			int y = (int) stairs.getShape().getY();
			stairsAll.add(stairs);
			addObject(stairs,x,y);
		}


		for(Shape doorShape : doors){
			Door door = new Door(doorShape);

			addObject(door,(int) doorShape.getX(), (int) doorShape.getY());


		}


	}





	protected void update() throws SlickException, IOException{


		contextualCollisions.update();

		for( Sector sector:  sectorMap.getActiveSectors()){
			sector.update();


			for (Collider collider : sector.popNewColliders()){
				collider.assignPhysicalCollisions(physicalCollisions);
				collider.assignContextualCollisions(contextualCollisions);
			}

		}



		checkStairs();
	}

	public void resetStairs(){
		for(Stairs stairs : stairsAll){
			stairs.setClimbed(false);
		}
		
	}

	public int checkStairs(){
		for (Stairs stair : stairsAll){

			int stairClimb = stair.climbed();
			if(stairClimb!=0){
				return stairClimb;
			}

		}
		return 0;
	}

	public ArrayList<Stairs> getStairs(char UorD){
		boolean wantUp;
		if (UorD == 'U' || UorD == 'u'){
			wantUp = true;
		}else if (UorD == 'D' || UorD == 'd'){
			wantUp = false;
		}else{
			throw new UnsupportedOperationException("Improper input character to removeStairs");
		}
		
		ArrayList<Stairs> output = new ArrayList<Stairs>();
		
		for (Stairs stair : stairsAll){
			if ( wantUp^stair.isStairsDown()){
				output.add(stair);
			}
		}
		
		
		return output;
	}
	

	public void removeStairs(char UorD){
		
		boolean wantRemoveUp;
		if (UorD == 'U' || UorD == 'u'){
			wantRemoveUp = true;
		}else if (UorD == 'D' || UorD == 'd'){
			wantRemoveUp = false;
		}else{
			throw new UnsupportedOperationException("Improper input character to removeStairs");
		}

		HashSet<Stairs> toRemove = new HashSet<Stairs>();

		for(Stairs stair: stairsAll){

			if( wantRemoveUp^stair.isStairsDown()){
				toRemove.add(stair);
			}

		}
		
		for (Stairs stair : toRemove){
			stairsAll.remove(stair);
			for (Sector sector : sectorMap.getSectorsNear(stair.getShape())){
				sector.removeFromAllLists(stair);
			}
			
			
		}
		
		



	}
	
	
	




	public void render(Graphics g, int offsetX, int offsetY){		

		for (Sector sector : sectorMap.getActiveSectors()){
			sector.render(g, offsetX, offsetY);
		}



	}


	public ArrayList<Shape> getWalls(){

		return new ArrayList<Shape>();
	}

	public ArrayList<Shape> getClosedDoors(){
		ArrayList<Shape> closedDoors = new ArrayList<Shape>();
		
		return closedDoors;
	}



	
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getHeightInTiles(){
		return heightInTiles;	
	}
	public int getWidthInTiles(){
		return widthInTiles;
	}

	public int[][] getMap(){
		return map;
	}


	public void addObject(Object obj, int xPos, int yPos) throws SlickException {
		sectorMap.placeObjectInSector(obj, xPos, yPos);



		if (obj instanceof Collider){
			System.out.println("Added to " + obj + "the physical Collisions"+ physicalCollisions);
			((Collider) obj).assignPhysicalCollisions(physicalCollisions);
			((Collider) obj).assignContextualCollisions(contextualCollisions);
		}



	}

	



	public void assignToItems(CurrentLevelData currentLevelData) {

		sectorMap.assignToItems(currentLevelData);


	}

	public LevelStaticRenderer getRenderer() {

		return this.renderer;
	}








}


