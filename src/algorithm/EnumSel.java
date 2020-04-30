package algorithm;

import entity.Billboard;
import java.util.ArrayList;

public class EnumSel {

    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private ArrayList<Billboard> billboardList;
    private int budget; // the budget constraint
    private int tau;
    private ArrayList<Billboard> H1;

    public EnumSel(int budget, ArrayList<Billboard> billboardList) {
        this.budget = budget;
        this.billboardList = billboardList;
        this.resultList = new ArrayList<>();
        this.tau = 2;
        this.H1 = new ArrayList<Billboard>();
    }

    public void generateSolution() {
    	
    	// Phase 1
    	
    	// Find all possible billboard combinations of size this.tau that are inside the budget.
    	// Select the one with the highest influence.
    	
    	findBestSubset(this.billboardList);
    	
    	for (int i = 0; i < this.H1.size(); i++) {
    		System.out.println(H1.get(i).getBillboardID());
    	}
    	
    	
    	System.out.println("B: " + this.budget + " SB: " + this.calculateSubsetPrice(this.H1));

    
    }
    
    //Initialise subset size and call recursive createAllSubsets method
    public void findBestSubset(ArrayList<Billboard> set) {
        ArrayList<Billboard> subset = new ArrayList<Billboard>();
        for (int i = 0; i < this.tau; i++) { 
        	subset.add(null);
        }
        enumerateAllSubsets(set, subset, 0, 0);
    }

    //Recursively create all subsets of a given size
    public void enumerateAllSubsets(ArrayList<Billboard> set, ArrayList<Billboard> subset, int subsetSize, int nextIndex) {
        if (subsetSize == this.tau) {
            setBestSubset(subset);
        } else {
            for (int j = nextIndex; j < set.size(); j++) {
                subset.set(subsetSize, set.get(j));
                enumerateAllSubsets(set, subset, subsetSize + 1, j + 1);
            }
        }
    }
    
    //Set best subset as a global variable based on total influence and budget.
    public void setBestSubset(ArrayList<Billboard> subset) {	
    	if (this.H1.isEmpty() && this.calculateSubsetPrice(subset) <= this.budget) {		
    		for (int i = 0; i < subset.size(); i++) {
    			this.H1.add(subset.get(i));
    		}    		
    	} else if (this.calculateSubsetInfluence(subset) > this.calculateSubsetInfluence(this.H1) && this.calculateSubsetPrice(subset) <= this.budget) {
    		this.H1.clear();
    		for (int i = 0; i < subset.size(); i++) {
    			this.H1.add(subset.get(i));
    		}
    	}
    }
    
    //Calculate total influence of a given subset.
    public double calculateSubsetInfluence(ArrayList<Billboard> subset) {
    	int influence = 0;
    	for (int i = 0; i < subset.size(); i++) {
    		influence += subset.get(i).getInf();
    	}
    	return influence;
    }
    
    //Calculate total price of a given subset.
    public double calculateSubsetPrice(ArrayList<Billboard> subset) {
    	int price = 0;
    	for (int i = 0; i < subset.size(); i++) {
    		price += subset.get(i).getPrice();
    	}
    	return price;
    }
}
