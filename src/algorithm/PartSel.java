package algorithm;

import entity.Billboard;
import java.util.ArrayList;

public class PartSel {

    private ArrayList<ArrayList<Billboard>> clusterList;
    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private int budget; // the budget constraint
    
    private int maxResult;
    private int q;

    public PartSel(int budget, ArrayList<ArrayList<Billboard>> clusterList) {
        this.budget = budget;
        this.clusterList = clusterList;
        this.resultList = new ArrayList<>();
    }

    public void generateSolution() {
    	
    	//3.4
    	int m = clusterList.size();
    	
    	//3.3
    	double[][] I = new double[m + 1][this.budget + 1];
    	double[][] S = new double[m + 1][this.budget + 1];	
    	
    	//3.5 + 3.6
    	for (int i = 1; i <= m; i++) {
    		for (int l = 1; l <= this.budget; l++) {
    			//3.7
    			EnumSel enumSel = new EnumSel(l, this.clusterList.get(i - 1));
    			enumSel.generateSolution();
    			//System.out.println(l);
    			//System.out.println(this.clusterList.get(i - 1));
    			//System.out.println(enumSel.resultList); 
    			S[i][l] = this.calculateSubsetInfluence(enumSel.resultList); 
    			//3.8
    			this.maxResult = 0;
    			this.q = 0;
    			
    			for (int q = 0; q <= l; q++) {	
    				//TODO q is behaving unexpectedly. 
    				double result = I[i-1][l-q] + S[i][q];
    				if (result >= this.maxResult) {
    					this.q = q;
    				}		
    			}
    
    			//3.9
    			I[i][l] = I[i-1][l-this.q] + S[i][this.q]; 
 
    		}
    	}
    	
    	String print = "";
    	for (int i = 1; i <= m; i++) {
    		for (int l = 1; l <= budget; l++) {
    			print += S[i][l] + " ";
    		}
    		print += "\n";
    	}
    	
    	System.out.println(print);
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
