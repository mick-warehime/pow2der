package pathfinding;


import java.util.ArrayList;

public class LevelMap implements TileBasedMap {


	private int height;
	private int width;
	private int[][] map;
	private ArrayList<int[]> visited;

	public LevelMap(int height, int width){

		this.visited = new ArrayList<int[]>();
		
		this.height = height;
		this.width = width;

		// build a map with a wall in the middle
		map = new int[height][width];

		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col ++){
				if( row == 5 & col>1 & col<8){				
					map[row][col] = 1;
				}else{
					map[row][col] = 0;
				}
			}
		}

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
		return map[y][x]==1;
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

	public void printMap(){

		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col ++){
				System.out.format(" %2d ",map[row][col]);
			}
			System.out.println();
		}
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public void setMap(int[][] map){
		this.map = map;
	}

}
