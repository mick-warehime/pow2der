package pathfinding;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

import collisions.PhysicalCollisions;

public class PathHandler {

	private Path path;
	private PhysicalCollisions physicalCollisions;
	private Shape shape;
	
	
	public PathHandler(Shape shape, Path path, PhysicalCollisions physicalCollisions){
		this.path = path;
		this.physicalCollisions = physicalCollisions;
		this.shape = shape;
	}
	
	public float[] getDirection(){
		
		float[] direction = new float[] {};
		for(int p = (path.getLength()-1); p>0; p--){
			//Make a line from centers of player and object
			float x1 = shape.getX();
			float y1 = shape.getY();
			float x2 = path.getX(p);
			float y2 = path.getY(p);

			Line line = new Line(x1, y1, x2, y2);

			
			if (physicalCollisions.isCollidedWithSolids(line)){ 
				continue;
			}else{
				direction = new float[] {x2-x1, y2-y1};
				return direction;
			}

		}

//		System.out.println("DID NOT FIND A PATH");
		
		return direction;
	}
	
	
}
