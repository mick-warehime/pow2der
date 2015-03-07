package music;


import java.util.ArrayList;

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
public final class CellTest implements JMC{
	public static void main(String[] args){

		// load a random tone matrix in a give octave
		FiveTone penta = new FiveTone(2);
		double[] pentatones = penta.getTones();

		// generate a CA
		int ruleNumber = 165;
		int numNotes = 25;
		int width = 50;

		Automata cell = new Automata(ruleNumber,numNotes,2*width+1);
		
		boolean[][] subCell = cell.getSubCell(50, 16);
		boolean[][] subCell2 = cell.getSubCell(40, 16);
//		boolean[][] subCell2 = cell.getSubCell(0, 250);
		cell.printCell(subCell);
//		cell.setRule(100);
		
		
		// compose a piano piece based on this CA

		Score score = new Score("The PentaTone Drunk"); 
		
		ArrayList<Part> piano = new ArrayList<Part>();
		ArrayList<Part> drums = new ArrayList<Part>();
		ArrayList<Phrase> phrases = new ArrayList<Phrase>();

		
		
		// initialize instruments and phrases
		for(int instr = 0; instr<pentatones.length;instr++){
			drums.add(new Part("sawtooth",jm.constants.Instruments.DRUM, 0));
			piano.add(new Part("sawtooth",jm.constants.Instruments.PIANO, 1));
//			piano.add(new Part("sawtooth",30, 0));
			phrases.add(new Phrase());
		}
		for(int tone = 0; tone < pentatones.length;tone++){
			System.out.println(pentatones[tone]);
		}

		// create a phrase of random durations up to a semibreve in length.
		for(int row=0; row<numNotes; row++){			
			for(int col = 0; col < subCell[0].length; col++){


				if(subCell[row][col]){
					Note note1 = new Note(pentatones[col],  0.25);
					phrases.get(col).addNote(note1);
				} else{
					Note note1 = new Note(1,  0.25);
					phrases.get(col).addNote(note1);
				}

			}
		}

		// add all the phrases
		for(int instr = 0; instr<pentatones.length;instr++){
			piano.get(instr).addPhrase(phrases.get(instr));
			drums.get(instr).addPhrase(phrases.get(instr));
			score.add(drums.get(instr));
			score.add(piano.get(instr));
		}
		

		Play.midi(score);
		Write.midi(score);
	}
}