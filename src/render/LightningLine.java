package render;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LightningLine implements LineObject{

	protected float[] A;
	protected float[] B;
	protected ArrayList<Image> images;
	protected float angle;
	protected float length;


	public LightningLine(float[] A, float[] B) throws SlickException{		
		this.A = A; 
		this.B = B;


		this.angle = (float) (Math.atan2(A[1]-B[1], A[0]-B[0])*180/Math.PI);
		if (angle<0){ angle = angle + 360;}
		angle -= 180;

		this.length = (float) Math.sqrt(Math.pow(B[1]-A[1], 2)+Math.pow(B[0]-A[0], 2));



		images = new ArrayList<Image>();

		//		Image image1 = new Image("data/lightningHead.png");
		//		image1.setCenterOfRotation(image1.getWidth()/2,image1.getHeight()/2);
		//		image1.setRotation(180+angle);
		//		images.add(image1);
		//		
		Image image2 = new Image("data/lightningBody.png");
		image2.setCenterOfRotation(0,image2.getHeight()/2);
		image2.rotate(angle);
		images.add(image2);
		//		
		//		Image image3 = new Image("data/lightningHead.png");
		//		image3.setCenterOfRotation(image3.getWidth()/2,image3.getHeight()/2);
		//		image3.setRotation(angle);
		//		images.add(image3);
	}

 

	public void render(Graphics g, int offsetX, int offsetY) {

		images.get(0).draw(A[0]-offsetX,A[1]-offsetY,length,20f);

		//		images.get(0).draw(A[0]-offsetX,A[1]-offsetY,images.get(0).getWidth(),20f);

		//		images.get(1).draw(A[0]-offsetX,A[1]-offsetY,length,20f);

		//		images.get(2).draw((float) (A[0]+length*Math.cos(angle*Math.PI/180))-offsetX,
		//				(float) (A[1]+length*Math.sin(angle*Math.PI/180))-offsetY,images.get(0).getWidth(),20f);


	}

}
