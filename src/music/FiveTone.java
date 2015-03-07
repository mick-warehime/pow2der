package music;

import jm.JMC;
import jm.music.data.*;

import java.util.ArrayList;
import java.util.Random;


public class FiveTone implements JMC {

	private double[] tones;

	private int numNotes = 5;

	public FiveTone(int octave){

		// generate a random tone matrix of a given octave (6 is middle octave);
		generateTones(octave);

	}

	public double[] getTones(){
		return tones;
	}

	// use the first row of a tone matrix to generate all rows

	private void generateTones(int octave){

		// generate a random tone matrix by shuffling the notes	
		//				        {C,D,E,G,A}
		int[] notes = new int[] {0,2,4,8,9};
		
		tones = getFrequencies(octave,notes,3); 
	}

	private double[] getFrequencies(int octave, int[] notes, int numScales){

		// get the notes of the pentatonic scale from c(octave) -> c(octave+4)

		double[] frequencies = new double[numScales*numNotes+1];

		int count = 0;
		for(int scale = 0; scale<numScales; scale++){
			for(int note = 0; note<notes.length; note++){
				frequencies[count] = frequency(note,3+octave); 

				count++;
			}
		}
		
		// add last c in 
		frequencies[count]  = frequency(0,octave+3); 


		return frequencies;
	}

	public double frequency(double note, int octave){

		double keyNumber = note+4 +12*(octave-1);

		return Math.pow(2,(keyNumber-49)/12)*440;

	}




}
