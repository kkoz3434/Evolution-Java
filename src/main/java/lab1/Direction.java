package lab1;

public enum Direction {
    N, NE, E, SE, S, SW, W, NW;

    private static Direction[] val = values();

    public static Direction[] getVal() {
        return val;
    }

    // returns random direction (ex. for children)
    public static Direction randomDirection() {
        int random = (int) (Math.random() * 7);
        return Direction.values()[random];
    }

    //returns new direction based on given geneTab
    public Direction getNewDirection(int[] geneTab) {
        int random = (int) (Math.random() * (geneTab.length - 1));
        int gen = geneTab[random];
        return getVal()[(this.ordinal() + gen) % getVal().length];
    }

    public Direction nextDirection() {
        return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
    }

}
