public class RunRamblersBB {
    public static void main(String[] args) {
        // the parameter in constructor is the coordination of the end point
        RamblersSearch ramblersSearch = new RamblersSearch(new Coords(14, 9));
        // the Coords parameter in this constructor is the start point
        ramblersSearch.runSearch(new RamblersState(new TerrainMap("tmc.pgm"), new Coords(5, 3), 0), "branchAndBound");
    }
}
