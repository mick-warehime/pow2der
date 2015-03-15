package abilities;

import gameobjects.Broadcaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import actors.Player;
import render.LightningLine;
import render.LineObject;
import render.LineRenderer;
import render.ParticleRenderer;
import render.ShapeRenderer;
import commands.IncrementHPCommand;

public class LightningAbilityObject extends AbilityObject implements Broadcaster {

	private int countDown;
	private int radius= 5;
	private float range = 100f;
	private int damage = 2;
	private boolean shouldRemove;
	private ArrayList<LineObject> lightningBolt;
	private ArrayList<float[]> boltPoints;
	private float[] endPt;
	private float[] startPt;
	private int concavity;

	public LightningAbilityObject(float[] startPt, float[] endPt, Shape shape) throws SlickException, IOException {

		this.startPt = startPt;
		this.endPt = endPt;		

		this.shape = shape;
		canCollide = false;
		shouldRemove = false;

		countDown = 20;

		lightningBolt = new ArrayList<LineObject>();
		boltPoints = new ArrayList<float[]>();

		concavity = 1;
		if(Math.random()>0.5){
			concavity = -1;
		}


		getBoltPoints(startPt,endPt);
		addBolts(2);

		renderer = new LineRenderer(lightningBolt);



	}

	private void addBolts(int numBolts) throws SlickException{

		for(int b = 0; b < numBolts; b++){
			// get a random point in the current bolt
			Random rand = new Random();

			// make sure new bolt is at least midway in the bolt
			int bolt = rand.nextInt(lightningBolt.size());
			while(bolt>lightningBolt.size()/3){
				bolt = rand.nextInt(lightningBolt.size());	
			}

			getBoltPoints(boltPoints.get(bolt),endPt);
		}

	}



	private void getBoltPoints(float[] startPt, float[] endPt) throws SlickException{

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

		float[] prevPoint = startPt;
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
			float[] point  = new float[] {(float) px,(float) py};


			// create a new bolt line and store the points in case we want to add more bolties later
			lightningBolt.add(new LightningLine(point,prevPoint));
			boltPoints.add(new float[] {(float) px,(float) py});


			prevPoint = point;
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

}
