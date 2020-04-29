package algorithm;

import entity.Billboard;
import java.util.ArrayList;

public class PartSel {

    private ArrayList<ArrayList<Billboard>> clusterList;
    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private int budget; // the budget constraint


    public PartSel(int budget, ArrayList<ArrayList<Billboard>> clusterList) {
        this.budget = budget;
        this.clusterList = clusterList;
        this.resultList = new ArrayList<>();
    }

    public void generateSolution() {
    }

}
