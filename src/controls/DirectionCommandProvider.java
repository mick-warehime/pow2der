package controls;


import interfaces.CommandProvider;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;

import commands.FaceDirectionCommand;

//uses mouse (or joystick?) inputs to tell the player's avatar where to face.
public class DirectionCommandProvider implements CommandProvider{

	
	private ArrayList<Command> currentActions = new ArrayList<Command>();
	private Shape playerShape;
	private int[] mousePositionInLevel;
	
	
	
	public DirectionCommandProvider( Shape playerShape, int [] mousePositionInLevel) {
		
		this.playerShape = playerShape;
		this.mousePositionInLevel = mousePositionInLevel;
	}
	
	
	

	public ArrayList<Command> getCommands(){
		
		
		
		currentActions.clear();
		
		float[] newDirection = determineFacingDirection();
		Command cmd = new FaceDirectionCommand(newDirection);
		currentActions.add(cmd);
		
		return currentActions;
	}




	private float[] determineFacingDirection() {	
		
		
		
		float x = playerShape.getCenterX();
		float y = playerShape.getCenterY();
		
		float [] output = new float[] {0f,0f};
		output[0] =(mousePositionInLevel[0] - x);
		output[1] = (mousePositionInLevel[1] - y);
		
		double norm =  Math.sqrt( output[0]*output[0] + output[1]*output[1]);
		
		output[0]= (float) (output[0]/norm);
		output[1] = (float) (output[1]/norm);
		
		
		
		
		return output;
	}


}
