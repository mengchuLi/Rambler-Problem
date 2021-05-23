import java.util.ArrayList;

public class RamblersState extends SearchState{
    private TerrainMap terrainMap;
    private Coords coords;

    /**
     * constructor
     * @param terrainMap
     * @param coords the start point
     * @param localCost
     */
    public RamblersState(TerrainMap terrainMap, Coords coords, int localCost, int estRemCost) {
        this.terrainMap = terrainMap;
        this.coords = coords;
        this.localCost = localCost;
        this.estRemCost = estRemCost;
    }

    @Override
    boolean goalPredicate(Search searcher) {
        RamblersState ramblersState = (RamblersState) searcher.currentNode.get_State();
        if (ramblersState.getCoords().getx() == ((RamblersSearch)searcher).getEndCoords().getx()
                && ramblersState.getCoords().gety() == ((RamblersSearch)searcher).getEndCoords().gety()) {
            for (SearchNode node: searcher.open) {
                if (node.getestTotalCost() < searcher.currentNode.getestTotalCost()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    ArrayList<SearchState> getSuccessors(Search searcher) {
        SearchNode node = ((RamblersSearch) searcher).currentNode;
        ArrayList<SearchState> successors = new ArrayList<>();
        // up
        if (coords.getx() > 0) {
            successors.add(new RamblersState(terrainMap, new Coords(coords.gety(), coords.getx() - 1), calculateLocalCost(coords.getx() - 1, coords.gety()), calculateEstRemCost(coords.getx() - 1, coords.gety(), searcher)));
        }

        // down
        if (coords.getx() < terrainMap.getWidth() - 1) {
            successors.add(new RamblersState(terrainMap, new Coords(coords.gety(), coords.getx() + 1), calculateLocalCost(coords.getx() + 1, coords.gety()), calculateEstRemCost(coords.getx() + 1, coords.gety(), searcher)));
        }

        // left
        if (coords.gety() > 0) {
            successors.add(new RamblersState(terrainMap, new Coords(coords.gety() - 1, coords.getx()), calculateLocalCost(coords.getx(), coords.gety() - 1), calculateEstRemCost(coords.getx(), coords.gety() - 1, searcher)));
        }

        // right
        if (coords.gety() < terrainMap.getDepth() - 1) {
            successors.add(new RamblersState(terrainMap, new Coords(coords.gety() + 1, coords.getx()), calculateLocalCost(coords.getx(), coords.gety() + 1), calculateEstRemCost(coords.getx(), coords.gety() + 1, searcher)));
        }
        return successors;
    }

    @Override
    boolean sameState(SearchState n2) {
        if (((RamblersState)n2).getCoords().getx() == coords.getx() && ((RamblersState)n2).getCoords().gety() == coords.gety()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "coordinate (x, y): (" + coords.getx() + ", " + coords.gety() + ")";
    }

    /**
     * This method will return the local cost
     * @param x
     * @param y
     * @return
     */
    private int calculateLocalCost(int x, int y) {
        int[][] tmap = terrainMap.getTmap();
        if (tmap[x][y] <= tmap[coords.getx()][coords.gety()]) {
            return 1;
        }
        else {
            return 1 + tmap[x][y] - tmap[coords.getx()][coords.gety()];
        }
    }

    /**
     * This method will calculate the estRemCost according to the strategy
     * @param x
     * @param y
     * @param searcher
     * @return
     */
    private int calculateEstRemCost(int x, int y, Search searcher) {
        Coords endCoords = ((RamblersSearch) searcher).getEndCoords();
        if (((RamblersSearch) searcher).getDistanceStrategy() == Distance.MANHATTAN) {
            return Math.abs(endCoords.getx() - x) + Math.abs(endCoords.gety() - y);
        }
        else if (((RamblersSearch) searcher).getDistanceStrategy() == Distance.EUCLIDEAN) {
            return (int) Math.sqrt(Math.pow(endCoords.getx() - x, 2) + Math.pow(endCoords.gety() - y, 2));
        }
        else if (((RamblersSearch) searcher).getDistanceStrategy() == Distance.HEIGHT_DIFFERENCE) {
            return terrainMap.getTmap()[endCoords.getx()][endCoords.gety()] - terrainMap.getTmap()[x][y];
        }
        return 0;
    }

    /**
     * The method will judge the successor has existed in open or closed list
     * @param searcher
     * @param x
     * @param y
     * @return true if it exists
     */
    private boolean isDuplicated(Search searcher, int x, int y) {
        for (SearchNode node: searcher.open) {
            Coords coords = ((RamblersState)node.get_State()).getCoords();
            if (coords.gety() == y && coords.getx() == x) {
                return true;
            }
        }
        for (SearchNode node: searcher.closed) {
            Coords coords = ((RamblersState)node.get_State()).getCoords();
            if (coords.gety() == y && coords.getx() == x) {
                return true;
            }
        }
        return false;
    }

    public Coords getCoords() {
        return coords;
    }
}
