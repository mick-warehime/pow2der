package pathfinding;


public final class TestAStar{
	public static void main(String[] args) {
		
		LevelMap map = new LevelMap(10,10);
		map.printMap();
		
		FakeActor actor = new FakeActor();
		
		AStarPathFinder astar = new AStarPathFinder(map,15,true);
		
		Path path = astar.findPath(actor, 1, 1, 9,9);
		
		
		int[][] mat = map.getMap();
		for(int i = 0; i < path.getLength(); i++){
			int x = path.getStep(i).getX();
			int y = path.getStep(i).getY();
			mat[y][x] = 2;
			
			System.out.format(" x: %2d, y: %2d",x,y);
			System.out.println();
		}
		map.setMap(mat);
		map.printMap();
		
		
		
		System.out.println("Tested:");

	}
}

