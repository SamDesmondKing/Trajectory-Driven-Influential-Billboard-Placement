package algorithm;

import entity.Billboard;
import java.util.ArrayList;

public class EnumSel {

    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private ArrayList<Billboard> billboardList;
    private int budget; // the budget constraint

    public EnumSel(int budget, ArrayList<Billboard> billboardList) {
        this.budget = budget;
        this.billboardList = billboardList;
        this.resultList = new ArrayList<>();
    }

    public void generateSolution() {
    }
}
