package music;


import instruments.ChiffInst;
import instruments.TriangleInst;
import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.*;
import jm.music.tools.ca.CellularAutomata;
import jm.util.Play;
import jm.util.Write;


/**
 * A short example which generates a random snare drum rhythm
 * and writes to a MIDI file called randomRhythm.mid
 * @author Andrew Brown
 */
public final class Test implements JMC{
	public static void main(String[] args){
		
		CellularAutomata CA = new CellularAutomata(100, 100, (int) 0.2, false);
		for(int j=0; j<100;j++){
			CA.evolve();
			System.out.println(CA.getState(j,12));
		}
		
		
		Score score = new Score("The 12 Tone Drunk"); 
		Part piano1 = new Part("piano",2, 0);
		Part piano2 = new Part("piano",0, 0);

		Phrase phr1 = new Phrase();
		Phrase phr2 = new Phrase();

		int numNotes = 12;

		// load a random tone matrix in a give octave
		int octave = 2;


//		FiveTone penta = new FiveTone(octave);
//		double[] pentatones = penta.getTones();
//
//		// create a phrase of random durations up to a semibreve in length.
//		for(int i=0; i<numNotes; i++){
//
//			Note note1 = new Note(pentatones[i],  0.5);
//			Note note2 = new Note(pentatones[i],  0.5);
//
//			phr1.addNote(note1);
//			phr2.addNote(note2);
//		}
//
//		piano1.add(phr1);
//		piano2.add(phr2);
//
//		score.add(piano1);
//		score.add(piano2);
//		// create a MIDI file of the score
//		//        Write.midi(score, "randomRhythm.mid");
//		Play.midi(score);
	}
}