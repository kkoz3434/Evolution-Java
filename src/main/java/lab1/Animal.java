package lab1;

import java.util.HashSet;
import java.util.Set;

public class Animal implements Comparable<Animal> {
    private final Configuration configuration;
    private final WorldMap map;
    private final Genes animalGenes;

    private Vector vector;
    private Direction direction;
    private int energy;
    private int childs = 0;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age=0;

    private boolean isAlive = true;

    private Set<Observer> observers = new HashSet<>();

    public Configuration getConfiguration() {
        return configuration;
    }

    Animal(WorldMap map, Vector vector, Direction direction, Genes genes, int energy, Configuration configuration) {
        this.vector = vector;
        this.direction = direction;
        this.animalGenes = genes;
        this.map = map;
        this.energy = energy;
        this.configuration=configuration;
        addObserver(map);
    }

    public void moveForward() {
        // move forward and set new direction
        Vector newVector = this.getVector().moveVector(this.getDirection(), configuration.getMapHeight(), configuration.getMapWidth());
        Direction newDirection = this.getDirection().getNewDirection(this.animalGenes.getGenesTab());   //create direction from genes and new vector

        for (Observer observer : observers
        ) {
            observer.updateAnimalPosition(this, newVector, newDirection);
        }


    }

    @Override
    public int compareTo(Animal o) {
        if (o.getEnergy() == this.getEnergy()) {
            return 0;
        }
        if (o.getEnergy() < this.getEnergy()) {
            return -1;
        }
        else return 1;
    }

    public void onBreded(){
        this.setChilds(childs+1);
        this.setEnergy((int)(energy*0.5));
    }

    public Genes getGenes() {
        return animalGenes;
    }

    public Vector getVector() {
        return this.vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getChilds() {
        return this.childs;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public WorldMap getMap() {
        return this.map;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void addObserver(WorldMap map){
        this.observers.add(map);
    }
}

