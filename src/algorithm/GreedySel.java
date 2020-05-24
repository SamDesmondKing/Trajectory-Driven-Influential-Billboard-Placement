package algorithm;

import entity.Billboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GreedySel {

	public ArrayList<Billboard> resultList; 
	private ArrayList<Billboard> billboardList;
	private int budget; 

	public GreedySel(int budget, ArrayList<Billboard> billboardList) {
		this.budget = budget;
		this.billboardList = billboardList;
		this.resultList = new ArrayList<>();
	}

	public void generateSolution() {
		int costSoFar = 0;
		int infSoFar = 0;
		
		//Create a sorted collection of billboards ordered (dec) by UMI.
		
		// 1. Create a hashmap of billboards and their UMIs
		HashMap<Billboard, Double> billboardMap = new HashMap<Billboard, Double>();
		double UMI;
		for (Billboard i : billboardList) {
			UMI = (double) i.getInf() / (double) i.getPrice();
			billboardMap.put(i, UMI);
		}
		
		// 2. Convert to ArrayList sorted by UMI
		ArrayList<Map.Entry<Billboard, Double>> sorted_billboards = new ArrayList<Map.Entry<Billboard, Double>>(billboardMap.entrySet());
		
		Collections.sort(sorted_billboards, new Comparator<Map.Entry<Billboard, Double>>() {
			public int compare(Entry<Billboard, Double> o1, Entry<Billboard, Double> o2) {
				return Double.compare(o1.getValue(), o2.getValue());
			}
		} );
		
		// 3. Reverse so that the highest UMI is accessed first.
		Collections.reverse(sorted_billboards);
		
		// (1.3 -> 1.8)
		for (int i = 0; i < sorted_billboards.size(); i++) {
			if (costSoFar + sorted_billboards.get(i).getKey().getPrice() <= this.budget) {
				resultList.add(sorted_billboards.get(i).getKey());
				costSoFar += sorted_billboards.get(i).getKey().getPrice();
				infSoFar += sorted_billboards.get(i).getKey().getInf();
				//System.out.println(sorted_billboards.get(i).getKey().getBillboardID());
			}
		}
		
		// 1.9 - store the best single billboard solution
		Billboard bestSingleSolution = new Billboard("placeholder", 0, 0);
		
		for (int i = 0; i < sorted_billboards.size(); i++) {
			if (sorted_billboards.get(i).getKey().getInf() > bestSingleSolution.getInf() && sorted_billboards.get(i).getKey().getPrice() < this.budget) {
				bestSingleSolution = sorted_billboards.get(i).getKey();
			}
		}
		
		// 1.10 - 1.13
		if (bestSingleSolution.getInf() > infSoFar) {
			this.resultList.clear();
			this.resultList.add(bestSingleSolution);
		}		
	}
	
	public ArrayList<Billboard> getResult() {
		return this.resultList;
	}
	
	// Calculate total influence of a given subset.
	public double calculateSubsetInfluence(ArrayList<Billboard> subset) {
		int influence = 0;
		for (int i = 0; i < subset.size(); i++) {
			influence += subset.get(i).getInf();
		}
		return influence;
	}

	// Calculate total price of a given subset.
	public int calculateSubsetPrice(ArrayList<Billboard> subset) {
		int price = 0;
		for (int i = 0; i < subset.size(); i++) {
			price += subset.get(i).getPrice();
		}
		return price;
	}
}
