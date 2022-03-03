package lab1;

import java.util.*;

//KK

public class WorldMap implements Observer {

    private final HashMap<Vector, List<Animal>> animalMap = new LinkedHashMap<>();
    private final Set<Vector> grassVacationJungle;
    private final Set<Vector> grassVacationSavanna;
    private final Set<Vector> grassMap = new HashSet<>();
    private final Configuration configuration;

    WorldMap(Configuration configuration) {
        grassVacationJungle = WorldMapGenerator.emptyJungle(configuration);
        grassVacationSavanna = WorldMapGenerator.emptySavanna(configuration);
        this.configuration = configuration;
    }


    @Override
    public void updateAnimalPosition(Animal animal, Vector newVector, Direction newDirection) {
        removeAnimal(animal);  //removes animal from map

        if (getGrass(animal.getVector()) == null) {
            addVacation(animal.getVector()); // updates grass vacancy
        }

        animal.setVector(newVector);
        animal.setDirection(newDirection);

        addAnimal(animal); // add changed

        if (getGrass(newVector) == null) {
            removeVacation(newVector); // updates grass vacancy
        }

    }

    public void addAnimal(Animal animal) {
        if (animalMap.containsKey(animal.getVector())) { // if ArrayList exist -> just add animal
            animalMap.get(animal.getVector()).add(animal);
        }
        else {
            animalMap.put(animal.getVector(), new ArrayList<>());  // if the field was empty at the beginning
            animalMap.get(animal.getVector()).add(animal);
        }
    }

    public void removeAnimal(Animal animal1) {
        List positionArrAnimal = animalMap.get(animal1.getVector());

        //if (positionArrAnimal == null) { // quick debug
         //   System.out.println("NULL");
        //}

        if (positionArrAnimal.size() == 1) { // if ArrayList is empty -> delete in HashMap
            positionArrAnimal.remove(animal1);
            animalMap.remove(animal1.getVector());
        }
        positionArrAnimal.remove(animal1);

    }

    public void addGrass() {

        Vector grassInJungle = freeVectorInJungle();
        Vector grassOnSavanna = randomGrassSavanna();

        if (grassInJungle != null) { // check if can add grass
            grassMap.add(grassInJungle);
            grassVacationJungle.remove(grassInJungle);
        }

        if (grassOnSavanna != null) { // check if can add grass
            grassMap.add(grassOnSavanna);
            grassVacationSavanna.remove(grassOnSavanna);
        }
    }

    public void addVacation(Vector vector) { // adds vacation to grass vacation
        if (vector.betweenVector(Vector.getJungleMinVector(configuration), Vector.getJungleMaxVector(configuration))) {
            grassVacationJungle.add(vector);
        }
        grassVacationSavanna.add(vector);
    }

    public void removeVacation(Vector vector) { // removes vacation on given vector
        if (vector.betweenVector(Vector.getJungleMinVector(configuration), Vector.getJungleMaxVector(configuration))) {
            grassVacationJungle.remove(vector);
        }
        grassVacationSavanna.remove(vector);
    }

    public boolean isEmpty(Vector vector) { // checks if field is empty on map
        return !grassMap.contains(vector) && !animalMap.containsKey(vector);
    }

    public void removeGrass(Vector vector) {
        grassMap.remove(vector);
    }

    public List<Animal> getAnimals(Vector vector) {
        return animalMap.get(vector);
    }

    public Vector getGrass(Vector vector) {
        if (grassMap.contains(vector)) {
            return vector;
        }
        return null;
    }

    public Set<Vector> getGrassMap() {
        return grassMap;
    }

    public HashMap<Vector, List<Animal>> getAnimalMap() {
        return animalMap;
    }

    public boolean isInJungle(Vector vector) {
        return vector.betweenVector(Vector.getJungleMinVector(configuration), Vector.getJungleMaxVector(configuration));
    }

    public Vector freeVectorInJungle() {   // returns free vector in jungle
        Vector minVector = Vector.getJungleMaxVector(configuration);
        Vector maxVector = Vector.getJungleMinVector(configuration);

        int iterator = 0;

        // check free grass spaces
        if (grassVacationJungle.size() == 0)
            return null;

        while (iterator < 2 * configuration.getMapWidth() * configuration.getMapHeight()) {

            int randomX = (int) (Math.random() * (maxVector.getX() - minVector.getX()) + minVector.getX());
            int randomY = (int) (Math.random() * (maxVector.getY() - minVector.getY()) + minVector.getY());
            Vector randomVector = new Vector(randomX, randomY);

            if (this.isEmpty(randomVector)) {
                return randomVector;
            }
            iterator++;
        }
        return null;
    }

    public Vector randomGrassSavanna() {

        // check free grass spaces
        if (grassVacationSavanna.size() == 0)
            return null;

        int iterator = 0;
        while (iterator < 2 * configuration.getMapWidth() * configuration.getMapHeight()) {

            int randomX = (int) (Math.random() * (configuration.getMapWidth()));
            int randomY = (int) (Math.random() * (configuration.getMapHeight()));  //random grass position
            Vector randomVector = new Vector(randomX, randomY);

            if (!isInJungle(randomVector)) { // if vector not in jungle
                if (this.getGrass(randomVector) == null) {
                    if (this.getAnimals(randomVector) == null || this.getAnimals(randomVector).size() == 0) {
                        return randomVector;
                    }
                }
            }
            iterator++;
        }
        return null;
    }

}
