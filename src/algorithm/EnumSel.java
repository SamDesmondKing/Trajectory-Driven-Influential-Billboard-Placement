package algorithm;

import entity.Billboard;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EnumSel {

	public ArrayList<Billboard> resultList; 
	private ArrayList<Billboard> billboardList;
	private int budget;
	private int tau;
	private ArrayList<Billboard> H2;

	public EnumSel(int budget, ArrayList<Billboard> billboardList) {
		this.budget = budget;
		this.billboardList = billboardList;
		this.resultList = new ArrayList<>();
		this.tau = 2;
		this.H2 = new ArrayList<Billboard>();
	}

	public void generateSolution() {
		
		//Enumerate all subsets of size tau
		ArrayList<ArrayList<Billboard>> result = getSubsets(billboardList, this.tau);
		
		//Save best subset result - 2.4
		ArrayList<Billboard> H1 = this.getBestSubset(result);
				
		//Enumerate all subsets of size tau + 1
		ArrayList<ArrayList<Billboard>> resultTwo = getSubsets(billboardList, this.tau + 1);
		
		//Run phase two. 2.6 - 2.9
		this.enumSelPhaseTwo(resultTwo);
		
//		for (int i = 0; i < H2.size(); i++) {
//			this.resultList.add(H2.get(i));
//		}
		
		//Compare and return result. 2.10 - 2.13
		if (this.calculateSubsetInfluence(H1) > this.calculateSubsetInfluence(H2)) {
			System.out.println("Option 1");
			for (int i = 0; i < H1.size(); i++) {
				this.resultList.add(H1.get(i));
			}
		} else {
            System.out.println("Option 2");
			for (int i = 0; i < H2.size(); i++) {
				this.resultList.add(H2.get(i));
			}
		}
	}
	
	public void enumSelPhaseTwo(ArrayList<ArrayList<Billboard>> input) {
		for (int i = 0; i < input.size(); i++) {
			ArrayList<Billboard> subset = input.get(i);
			if (this.calculateSubsetPrice(subset) <= this.budget) {
				int budgetRemaining = this.budget - this.calculateSubsetPrice(subset);
				GreedySel greedySel = new GreedySel(budgetRemaining, this.billboardList);
				greedySel.generateSolution();
				ArrayList<Billboard> greedyResult = greedySel.getResult();
				
				for (int j = 0; j < greedyResult.size(); j++) {
					subset.add(greedyResult.get(j));
				}
				
				if (this.H2.isEmpty()) {
					for (int j = 0; j < subset.size(); j++) {
						this.H2.add(subset.get(j));
					}
				} else if (this.calculateSubsetInfluence(subset) > this.calculateSubsetInfluence(this.H2)) {
					this.H2.clear();
					for (int j = 0; j < subset.size(); j++) {
						this.H2.add(subset.get(j));
					}
				}
			}
		}
	}

	public ArrayList<Billboard> getBestSubset(ArrayList<ArrayList<Billboard>> result) {
		ArrayList<Billboard> bestSubset = new ArrayList<Billboard>();
		for (int i = 0; i < result.size(); i++) {
			if (bestSubset.isEmpty() && this.calculateSubsetPrice(result.get(i)) <= this.budget) {
				for (int j = 0; j < result.get(i).size(); j++) {
					bestSubset.add(result.get(i).get(j));
				}
			} else if (this.calculateSubsetPrice(result.get(i)) <= this.budget && this.calculateSubsetInfluence(result.get(i)) > this.calculateSubsetInfluence(bestSubset)) {
				bestSubset.clear();
				for (int j = 0; j < result.get(i).size(); j++) {
					bestSubset.add(result.get(i).get(j));
				}
			}
		}
		return bestSubset;
	}

	public void getSubsets(ArrayList<Billboard> billboardList, int k, int idx, ArrayList<Billboard> current,
			ArrayList<ArrayList<Billboard>> solution) {
		// successful stop clause
		if (current.size() == k) {
			solution.add(new ArrayList<>(current));
			return;
		}
		// unseccessful stop clause
		if (idx == billboardList.size())
			return;
		Billboard x = billboardList.get(idx);
		current.add(x);
		// "guess" x is in the subset
		getSubsets(billboardList, k, idx + 1, current, solution);
		current.remove(x);
		// "guess" x is not in the subset
		getSubsets(billboardList, k, idx + 1, current, solution);
	}

	public ArrayList<ArrayList<Billboard>> getSubsets(ArrayList<Billboard> billboardList, int k) {
		ArrayList<ArrayList<Billboard>> res = new ArrayList<>();
		getSubsets(billboardList, k, 0, new ArrayList<Billboard>(), res);
		return res;
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
