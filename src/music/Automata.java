package music;

import java.util.ArrayList;

public class Automata {

	private boolean[][] cell;
	private int ruleNumber;
	private int evolutions;
	private ArrayList<Boolean> rule;
	private int cellWidth;


	public Automata(int ruleNumber, int evolutions, int cellWidth){
		this.ruleNumber = ruleNumber;
		this.evolutions = evolutions;
		this.cellWidth = cellWidth;

		generateRule();
		evolve();

//		printRule();
//		printCell(cell);
//		printCell(getSubCell(7,9));

	}
	
	public void setRule(int ruleNumber){
		this.ruleNumber = ruleNumber;
		generateRule();
	}
	
	public void evolveCell(){
		evolve();
	}

	public boolean[][] getCell(){
		return cell;
	}

	public boolean[][] getSubCell(int startingColumn, int width){
		
		if((startingColumn+width)>cellWidth){
			System.out.println("desired width too large. pick smaller width");
		}
		
		boolean[][] subCell = new boolean[evolutions][width];

		// pick out a swath of the cell
		for(int row = 0; row<evolutions;row++){
			// apply the rule to each element using the previous rows values
			// using cyclic boundary conditions cell[1][-1] = cell[1][cellWidth-1]
			int subCellCol = 0;
			for(int col = startingColumn; col < (startingColumn+width); col++){
				
				subCell[row][subCellCol] = cell[row][col];
				subCellCol ++;
			}

		}

		return subCell;
	}



	private void evolve(){
		cell = new boolean[evolutions][cellWidth];

		int middle = Math.floorDiv(cellWidth, 2);

		// the seed for the first row is a single value in the middle of the cell
		for(int col = 0; col<cellWidth; col++){
			if(col==middle){cell[0][col] = true;}
			else{cell[0][col] = false;}
		}


		// evolve the matrix
		for(int row = 1; row<evolutions;row++){
			// apply the rule to each element using the previous rows values
			// using cyclic boundary conditions cell[1][-1] = cell[1][cellWidth-1]
			for(int col = 0; col < cellWidth; col++){
				int left = col-1;
				int right = col+1;

				if(col==0){
					left = cellWidth-1;					
				}else if(col == (cellWidth-1)){
					right = 0;
				}
				cell[row][col] = applyRule(cell[row-1][left],cell[row-1][col],cell[row-1][right]);

			}
		}


	}

	private boolean applyRule(boolean left, boolean center, boolean right){

		// determine the rule that should be evaluated
		int subRule = 0;
		if(right){subRule +=1;}
		if(center){ subRule+=2;}
		if(left){subRule+=4;}

		return rule.get(subRule);
	}


	private void generateRule(){

		// assumes only nearest neighbors affect rule
		rule = new ArrayList<Boolean>();

		String binaryString = Integer.toString(ruleNumber,2);
		int len = binaryString.length();

		for(int i = 0; i < 9; i++){
			if(i<len){
				char c = binaryString.charAt(len-i-1);
				rule.add(Character.getNumericValue(c)==0);
			}else{
				rule.add(true);
			}
		}

	}
	////////////////
	//
	//
	//   PRINTING METHODS
	//
	//
	////////////////

	public void printCell(boolean[][] cell){
		int evolutions = cell.length;
		int cellWidth = cell[0].length;
		
		for(int row = 0; row < evolutions; row++){
			for(int col = 0; col < cellWidth; col++){
				System.out.print(ruleString(cell[row][col]));
			}
			System.out.println();
		}
	}

	public void printRule(){
		for(int i = 0; i<2; i++){
			for(int j = 0; j<2; j++){
				for(int k = 0; k<2; k++){
					String prevStr = ruleString(i==0)+ruleString(j==0)+ruleString(k==0);
					String curStr = ruleString(applyRule(i==0,j==0,k==0));

					System.out.println(prevStr);
					System.out.println(" "+curStr+" ");
					System.out.println();
				}
			}
		}
	}

	private String ruleString(boolean cellValue){

		// determine the rule that should be evaluated
		if(cellValue){
			return "@";
		}else{
			return " ";
		}


	}






}
