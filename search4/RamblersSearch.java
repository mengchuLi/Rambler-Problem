import java.util.Iterator;

public class RamblersSearch extends Search{
    private Distance distanceStrategy;
    private Coords endCoords;

    public RamblersSearch(Coords endCoords, Distance distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
        this.endCoords = endCoords;
    }

    public Coords getEndCoords() {
        return endCoords;
    }

    public Distance getDistanceStrategy() {
        return distanceStrategy;
    }
}
