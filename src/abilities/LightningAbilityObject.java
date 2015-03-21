package abilities;

import interfaces.Broadcaster;
import interfaces.CollidesWithSolids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Shape;

import collisions.PhysicalCollisions;
import actors.Player;
import render.LightningLine;
import render.LineObject;
import render.LineRenderer;
import commands.IncrementHPCommand;

public class LightningAbilityObject extends AbilityObject implements Broadcaster, CollidesWithSolids {

	private int countDown;
	private int damage = 2;
	private boolean shouldRemove;
	private ArrayList<LineObject> boltLines;
	private ArrayList<float[]> boltPoints;
	private float[] endPt;
	private int concavity;

	public LightningAbilityObject(float[] startPt, float[] endPt) throws SlickException, IOException {

		this.endPt = endPt;		

		canCollide = false;
		shouldRemove = false;

		countDown = 20;

		boltLines = new ArrayList<LineObject>();
		boltPoints = new ArrayList<float[]>();

		concavity = 1;
		if(Math.random()>0.5){
			concavity = -1;
		}


		defineBoltPoints(startPt,endPt);
		
	
//		addBolts(2);

		renderer = new LineRenderer(boltLines);



	}

	private void makeShapeFromBoltPoints(PhysicalCollisions detector) throws SlickException {
		
		
		
		int len = boltPoints.size();
		
		assert len>1 : "Tried to make a liightning bolt with no points!!!";
		
		float[] bp = boltPoints.get(0);
		
		shape = new Path(bp[0],bp[1]);
		
		float[] bplast = bp;
		
		for (int i = 1; i<len; i++){
			bp = boltPoints.get(i);
			
			Line testLine = new Line(bplast[0],bplast[1],bp[0],bp[1]);
			if (detector.isCollidedWithSolids(testLine)){
				break;
			}
			((Path) shape).lineTo(bp[0],bp[1]);
			boltLines.add(new LightningLine(bplast,bp));
			bplast = bp;
		}
		
	}

	@SuppressWarnings("unused")
	private void addBolts(int numBolts) throws SlickException{

		for(int b = 0; b < numBolts; b++){
			// get a random point in the current bolt
			Random rand = new Random();

			// make sure new bolt is at least midway in the bolt
			int bolt = rand.nextInt(boltLines.size());
			while(bolt>boltLines.size()/3){
				bolt = rand.nextInt(boltLines.size());	
			}

			defineBoltPoints(boltPoints.get(bolt),endPt);
		}

	}



	private void defineBoltPoints(float[] startPt, float[] endPt) throws SlickException{

		// this algorithm is stolen from 
		// http://gamedevelopment.tutsplus.com/tutorials/how-to-generate-shockingly-good-2d-lightning-effects--gamedev-2681


		float[] tangent = new float[] {endPt[0]-startPt[0], endPt[1]-startPt[1]};
		float length =(float) Math.sqrt(Math.pow(tangent[0],2)+Math.pow(tangent[1],2));
		float[] normal = new float[] {tangent[1]/length, -tangent[0]/length};



		// set of random sorted points between (0,1) 
		ArrayList<Double> positions = new ArrayList<Double>();
		positions.add(0.0);
		positions.add(0.01);
		for(int i = 0; i < length/8; i++){
			positions.add(Math.random());
		}
		positions.add(1.0);
		Collections.sort(positions);

		float Sway = 40f;
		float Jaggedness = 1 / Sway;

		double prevDisplacement = 0;
		for(int p = 1; p < positions.size(); p++){

			double pos = positions.get(p);

			// used to prevent sharp angles by ensuring very close positions also have small perpendicular variation.			
			double scale = (length*Jaggedness)*( pos - positions.get(p-1));

			// defines an envelope. Points near the middle of the bolt can be further from the central line.
			double envelope = 1.0;
			if(pos > 0.95f){
				envelope = 20.0*(1.0-pos);
			}

			double displacement = 2.0*Sway*(Math.random()-1.0);
			displacement -= (displacement - prevDisplacement)*(1 - scale);
			displacement = displacement*envelope;

			double px = (startPt[0] + pos*tangent[0] +concavity*displacement*normal[0]);
			double py = (startPt[1] + pos*tangent[1] + concavity*displacement*normal[1]);
			


			// create a new bolt line and store the points in case we want to add more bolties later
			
			boltPoints.add(new float[] {(float) px,(float) py});


			prevDisplacement = displacement;

		}


	}





	@Override
	public boolean shouldRemove() {
		return shouldRemove;
	}

	@Override
	public void update() {

		countDown -=1;

		if (countDown<0){
			shouldRemove = true;
		}

	}



	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {


//		shouldRemove = true;			

	}



	@Override
	public ArrayList<Command> onCollisionBroadcast(
			Class<?> collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> output = new ArrayList<Command>();

		if(!(collidingObjectClass.equals(Player.class))){			
			output.add( new IncrementHPCommand(-damage));
		}

	
	return output;
}

	@Override
	public Shape getInteractionRange() {
		// TODO Auto-generated method stub
		return shape;
	}

	@Override
	public void onRemoveDo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignCollisionDetector(PhysicalCollisions detector) throws SlickException {
		makeShapeFromBoltPoints(detector);
		
		
		
		
		
	}

}
