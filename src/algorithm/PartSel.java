package algorithm;

import entity.Billboard;
import java.util.ArrayList;

public class PartSel {

    private ArrayList<ArrayList<Billboard>> clusterList;
    public ArrayList<Billboard> resultList; 
    private int budget;
    
    private double maxResult;
    private int q;

    public PartSel(int budget, ArrayList<ArrayList<Billboard>> clusterList) {
        this.budget = budget;
        this.clusterList = clusterList;
        this.resultList = new ArrayList<>();
    }

    public void generateSolution() {
    	
    	//3.4
    	int m = clusterList.size();
    	
    	//3.3 - Matrixs for holding influence values
    	double[][] I = new double[m + 1][this.budget + 1];
    	double[][] S = new double[m + 1][this.budget + 1];	
    	
    	//Matrixes for holding actual billboards
    	@SuppressWarnings("unchecked")
		ArrayList<Billboard>[][] I2 = new ArrayList[m + 1][this.budget + 1];
    	@SuppressWarnings("unchecked")
    	ArrayList<Billboard>[][] S2 = new ArrayList[m + 1][this.budget + 1];
    	
    	//3.5 + 3.6
    	for (int i = 1; i <= m; i++) {
    		for (int l = 1; l <= this.budget; l++) {
    			//3.7
    			EnumSel enumSel = new EnumSel(l, this.clusterList.get(i - 1));
    			enumSel.generateSolution();

    			//Store influence value of this local solution
    			S[i][l] = this.calculateSubsetInfluence(enumSel.resultList); 
    			//Store Billboard objects of this local solution
    			S2[i][l] = enumSel.resultList;
    			
    			//3.8
    			this.maxResult = 0;
    			this.q = 0;
    			//Find best value of q
    			for (int q = 0; q <= l; q++) {	
    				double resultQ = I[i-1][l-q] + S[i][q];
    				if (resultQ >= this.maxResult) {
    					this.q = q;
    					this.maxResult = resultQ;
    				}		
    			}
    
    			//3.9
    			//Store influence value of this global solution
    			I[i][l] = I[i-1][l-this.q] + S[i][this.q];
    			
    			//Store Billboard objects of this global solution
    			ArrayList<Billboard> globalPart = I2[i-1][l-this.q];
    			ArrayList<Billboard> localPart = S2[i][this.q];
    			ArrayList<Billboard> result = new ArrayList<Billboard>();
    			
    			if (globalPart != null && localPart != null) {
    				result.addAll(localPart);
    				result.addAll(globalPart);
    				I2[i][l] = result;
    			} else if (globalPart == null && localPart != null) {
    				I2[i][l] = localPart;
    			} else if (globalPart != null && localPart == null) {
    				I2[i][l] = globalPart;
    			} else {
    				I2[i][l] = null;
    			}
    		}
    	}
    	
    	//Obtain billboard list of best solution (I[m][L])
    	this.resultList = I2[m][this.budget]; 		
    }
    
	// Calculate total influence of a given subset.
	public double calculateSubsetInfluence(ArrayList<Billboard> subset) {
		int influence = 0;
		for (int i = 0; i < subset.size(); i++) {
			influence += subset.get(i).getInf();
		}
		return influence;
	}
}
