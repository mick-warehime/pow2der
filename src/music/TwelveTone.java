package music;

import jm.JMC;
import jm.music.data.*;

import java.util.ArrayList;
import java.util.Random;


public class TwelveTone implements JMC {
	
	private static final int CN = 0;
	private static final int DF = 1;
	private static final int DN = 2;
	private static final int EF = 3;
	private static final int EN = 4;
	private static final int FN = 5;
	private static final int GF = 6;
	private static final int GN = 7;
	private static final int AF = 8;
	private static final int AN = 9;
	private static final int BF = 10;
	private static final int BN = 11;
	

	private double[][] toneMatrix;

	private int numNotes = 12;

	public TwelveTone(int octave){

		// generate a random tone matrix of a given octave (6 is middle octave);
		generateMatrix(octave);

	}

	public double[][] getToneMatrix(){
		return toneMatrix;
	}
	
	// use the first row of a tone matrix to generate all rows

	private void generateMatrix(int octave){


		// generate a random tone matrix by shuffling the notes	
		//               {C,DF,D,EF,E,F,GF,G,AF,A,BF,B}
		int[] firstRow = {0,1,2,3,4,5,6,7,8,9,10,11};
		shuffleArray(firstRow);		


		int[][] notes = new int[numNotes][numNotes];

		// store first row
		for(int i = 0; i < numNotes; i++ ){
			notes[0][i] = firstRow[i];
		}
	
		// GENERATE TONE MATRIX FROM FIRST ROW USING THE FOLLOWING RULE
		
		//		// to generate jth row add firstRow[0]-first[j] to every element in the first row
		//		// matrix[j][k] = matrix[0][k]+(matrix[0][0]-matrix[0][j])	
		for(int j = 0; j < numNotes; j++){

			int delta = notes[0][0]-notes[0][j];
			for(int k = 0; k < numNotes; k++){
				// calculate new note based on the rule
				int newNote = notes[0][k]+delta;

				//			make sure new note maps to a give note (cyclic)
				if(newNote>=numNotes){
					newNote -= numNotes;
				}else if(newNote<0){
					newNote += numNotes;
				}

				notes[j][k] = newNote;
			}
		}

		
		toneMatrix = new double[numNotes][numNotes];
		for(int i = 0; i<numNotes; i++){
			for(int j = 0; j<numNotes; j++){
				toneMatrix[i][j] = frequency(notes[i][j],octave);
			}
		}


		// PRINT OUT THE FREQUENCIES IN THE TONE MATRIX
		for(int i = 0; i<numNotes; i++){
			for(int j = 0; j<numNotes; j++){
				System.out.format(" %2.4f ",toneMatrix[i][j]);
			}
			System.out.println();

		}




	}

	// Implementing Fisher–Yates shuffle
	private  void shuffleArray(int[] ar){
		Random rnd = new Random();
		for (int i = ar.length - 1; i > -1; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}


	}
	
	public double frequency(double note, int octave){
		
		double keyNumber = note+4 +12*(octave-1);
		
		return Math.pow(2,(keyNumber-49)/12)*440;
		
	}



}
