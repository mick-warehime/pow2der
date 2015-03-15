package particleTests;
import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class ParticleState extends BasicGameState {
	protected ParticleSystemObject systemObject;
	private int ID;

	public ParticleState(int ID){
		this.ID = ID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		checkInputs(gc, sbg);
		
		for(int s = 0; s < systemObject.getSystemCount(); s++){
			systemObject.getParticleSystem(s).update(delta);
		}

	}
	
	public void checkInputs(GameContainer gc, StateBasedGame sbg){
		//switch states
		if(gc.getInput().isKeyPressed(Input.KEY_LEFT)){
			int state = sbg.getCurrentStateID();
			if(state>1){
				state--;
				
			}else{
				state = sbg.getStateCount();
			}
			
			sbg.enterState(state);
		}else if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)){
			int state = sbg.getCurrentStateID();
			if(state<sbg.getStateCount()){
				state++;
			}else{
				state = 1;
			}			
			sbg.enterState(state);
		}
	}


	public void restart() throws SlickException{
		systemObject.restartSystem();
	}
	
	public void keyPressed(int key, char code){

		if(key == Input.KEY_SPACE){
			try {
				restart();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		//escape means, close the program
		if(key == Input.KEY_ESCAPE){
			System.exit(0);
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		for(int s = 0; s < systemObject.getSystemCount(); s++){
			systemObject.getParticleSystem(s).render();
		}
	}

	public int getID() {
		return ID;
	}

	public class ParticleSystemObject{

		private ArrayList<ParticleSystem> systemList;
		private ArrayList<String> imageFileNamesList;
		private ArrayList<String> xmlFileNamesList;
		private ArrayList<Integer> xPositionsList;
		private ArrayList<Integer> yPositionsList;


		public ParticleSystemObject(String imageFileName, String xmlFileName, int xPos, int yPos) throws SlickException{
			systemList = new ArrayList<ParticleSystem>();
			imageFileNamesList = new ArrayList<String>();
			xmlFileNamesList = new ArrayList<String>();
			xPositionsList = new ArrayList<Integer>();
			yPositionsList = new ArrayList<Integer>();


			addSystem(imageFileName,xmlFileName,xPos,yPos);

		}

		public void addSystem(String imageFileName, String xmlFileName, int xPos, int yPos) throws SlickException{

			// store the data for each system added that way we can reinitialize later if desired

			imageFileNamesList.add(imageFileName);
			xmlFileNamesList.add(xmlFileName);
			xPositionsList.add(xPos);
			yPositionsList.add(yPos);

			addSystem(imageFileName, xmlFileName, xPos, yPos, true);
		}

		public void addSystem(String imageFileName, String xmlFileName, int xPos, int yPos, boolean restart) throws SlickException{		
			
			//load the plus particle and create a particle system with a limit of 1500 particles
			Image image = new Image(imageFileName, false);
			ParticleSystem system = new ParticleSystem(image,1500);

			try {
				//load the xml file and add the emitter to the system
				File xmlFile = new File(xmlFileName);
				ConfigurableEmitter emitter = ParticleIO.loadEmitter(xmlFile);
				emitter.setPosition(xPos,yPos);
				system.addEmitter(emitter);
			} catch (Exception e) {
				System.out.println("Exception: " +e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}

			system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);	

			systemList.add(system);
		}

		public void restartSystem() throws SlickException{
			systemList = new ArrayList<ParticleSystem>();

			for(int sys = 0; sys < imageFileNamesList.size(); sys++){
				addSystem(imageFileNamesList.get(sys),xmlFileNamesList.get(sys),
						xPositionsList.get(sys),yPositionsList.get(sys),true);	
			}


		}

		public ParticleSystem getParticleSystem(int index){
			return systemList.get(index);
		}

		public int getSystemCount(){
			return systemList.size();
		}

	}


}


