import java.util.Iterator;

public class RamblersSearch extends Search{
    private Coords endCoords;

    public RamblersSearch(Coords endCoords) {
        this.endCoords = endCoords;
    }

    //Selection Strategies
    private void selectNode(String strat) {
        branchAndBound();
    }

    //change from search2
    private void branchAndBound(){

        Iterator i = open.iterator();
        SearchNode minCostNode=(SearchNode) i.next();
        for (;i.hasNext();){
            SearchNode n=(SearchNode) i.next();
            if (n.getGlobalCost()<minCostNode.getGlobalCost()){
                minCostNode=n;};
        }

        currentNode=minCostNode;
        open.remove(minCostNode);
    }

    public Coords getEndCoords() {
        return endCoords;
    }
}
