package pathfinding;


import java.util.ArrayList;

import world.LevelBuilder;

public class LevelMap implements TileBasedMap {


	private int height;
	private int width;
	private int[][] map;
	private ArrayList<int[]> visited;

	public LevelMap(int[][] map){

		this.visited = new ArrayList<int[]>();
		
		this.map = map;
		this.height = map.length;
		this.width = map[0].length;
		
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return height;
	}

	/**
	 * Notification that the path finder visited a given tile. This is 
	 * used for debugging new heuristics.
	 * 
	 * @param x The x coordinate of the tile that was visited
	 * @param y The y coordinate of the tile that was visited
	 */
	@Override
	public void pathFinderVisited(int x, int y){
		visited.add(new int[] {x,y});
	}

	/**
	 * Check if the given location is blocked, i.e. blocks movement of 
	 * the supplied mover.
	 * 
	 * @param mover The mover that is potentially moving through the specified
	 * tile.
	 * @param x The x coordinate of the tile to check
	 * @param y The y coordinate of the tile to check
	 * @return True if the location is blocked
	 */
	@Override
	public boolean blocked(Mover mover, int x, int y){
		
		boolean blocked = false;
		
//		if(map[y][x]==LevelBuilder.OBJECT_DOOR){
//			blocked = true;
//		}
		if(map[y][x]==LevelBuilder.OBJECT_WALL_TILE){
			blocked = true;
		}
		
		return blocked;
		
	}

	/**
	 * Get the cost of moving through the given tile. This can be used to 
	 * make certain areas more desirable. A simple and valid implementation
	 * of this method would be to return 1 in all cases.
	 * 
	 * @param mover The mover that is trying to move across the tile
	 * @param sx The x coordinate of the tile we're moving from
	 * @param sy The y coordinate of the tile we're moving from
	 * @param tx The x coordinate of the tile we're moving to
	 * @param ty The y coordinate of the tile we're moving to
	 * @return The relative cost of moving across the given tile
	 */
	public float getCost(Mover mover, int sx, int sy, int tx, int ty){
		return 1f;
	}



}
