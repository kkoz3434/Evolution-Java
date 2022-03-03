package lab1;

public class Vector {
    private int x;
    private int y;

    Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector ofRandom(int mapWidth, int mapHeight) {    //returns random vector scaled to map size
        int x = (int) ((Math.random() * (mapWidth)));
        int y = (int) ((Math.random() * (mapHeight)));
        return new Vector(x, y);
    }

    public Vector addVector(Vector vector, int height, int width) { // function adding two vectors and making coordinates module to fit in map
        return new Vector((x + vector.getX() + width) % width, (y + vector.getY() + height) % height);
    }

    public Vector moveVector(Direction direction, int height, int width) {  // return Vector to change animal position
        Vector vector;
        switch (direction) {
            case N -> vector = new Vector(0, 1);
            case NE -> vector = new Vector(1, 1);
            case E -> vector = new Vector(1, 0);
            case SE -> vector = new Vector(1, -1);
            case S -> vector = new Vector(0, -1);
            case SW -> vector = new Vector(-1, -1);
            case W -> vector = new Vector(-1, 0);
            case NW -> vector = new Vector(-1, 1);
            default -> vector = new Vector(0, 0);
        }
        return this.addVector(vector, height, width);
    }

    public boolean betweenVector(Vector vectorMin, Vector vectorMax) {   //returns true if position is in square maded by two vectors ( needs to get properly ordered vectors)
        if (x > vectorMin.getX() && x < vectorMax.getX()) {
            if (y > vectorMin.getY() && y < vectorMax.getY()) {
                return true;
            }
        }
        return false;
    }

    public static Vector getJungleMinVector(Configuration configuration) {
        int minX = (int) ((configuration.getMapWidth() - (configuration.getMapWidth() * configuration.getJungleRatio())) / 2);
        int minY = (int) ((configuration.getMapHeight() - (configuration.getMapHeight() * configuration.getJungleRatio())) / 2);
        return new Vector(minX, minY);
    }

    public static Vector getJungleMaxVector(Configuration configuration) {
        int maxX = (int) (((configuration.getMapWidth() - (configuration.getMapWidth() * configuration.getJungleRatio() )) / 2)
                + (configuration.getMapWidth() * configuration.getJungleRatio() ));

        int maxY = (int) (((configuration.getMapHeight() - (configuration.getMapHeight() * configuration.getJungleRatio())) / 2
                + (configuration.getMapHeight() * configuration.getJungleRatio() )));
        return new Vector(maxX, maxY);
    }

    @Override
    public boolean equals(Object o) {
        Vector vector = (Vector) o;
        if (vector == null) return false;
        if (x == vector.getX() && y == vector.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 131071 * x + 8191 * y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
