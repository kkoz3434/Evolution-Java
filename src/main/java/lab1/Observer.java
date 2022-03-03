package lab1;

public interface Observer {
    /**
     * updates animal position on WorldMap
     * @param animal
     * @param newVector
     * @param newDirection
     */
    void updateAnimalPosition(Animal animal, Vector newVector, Direction newDirection);

}
