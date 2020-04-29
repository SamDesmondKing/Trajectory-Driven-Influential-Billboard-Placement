package algorithm;

import entity.Billboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GreedySel {

	public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not
											// change or remove it!
	private ArrayList<Billboard> billboardList;
	private int budget; // the budget constraint

	public GreedySel(int budget, ArrayList<Billboard> billboardList) {
		this.budget = budget;
		this.billboardList = billboardList;
		this.resultList = new ArrayList<>();
	}

	public void generateSolution() {

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
		
		for (int i = 0; i < sorted_billboards.size(); i++) {
			System.out.println("ID: " + sorted_billboards.get(i).getKey().getBillboardID() + " UMI: " + sorted_billboards.get(i).getValue());
		}
	}
}
