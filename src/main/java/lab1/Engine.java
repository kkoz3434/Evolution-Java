package lab1;

import java.util.*;

public class Engine {
    private final Configuration configuration;
    private final List<Animal> animalList = new ArrayList<>();
    private final Set<Animal> chosenAnimalDescendants = new HashSet<>();
    private final Map<Genes, Integer> dominantGenes = new HashMap<>();
    private int deadAnimalsAge = 0;
    private int animalNumber;
    private int eraCounter = 0;
    private WorldMap map;
    private Animal chosenAnimal;
    private int chosenAnimalDeadEra;


    public Engine(Configuration configuration) {
        this.configuration = configuration;
        this.animalNumber = configuration.getAnimalAmount();
    }

    private void onAnimalAdded(Animal animal) {
        Genes animalGenes = animal.getGenes();
        if (dominantGenes.containsKey(animalGenes)) {
            Integer val = dominantGenes.get(animalGenes);
            dominantGenes.put(animalGenes, val + 1);
        }
        else {
            dominantGenes.put(animalGenes, 1);
        }
    }

    private void onAnimalRemoved(Animal animal) {
        animal.setAlive(false);
        Genes animalGenes = animal.getGenes();
        Integer val = dominantGenes.get(animalGenes);
        if (val > 1) {
            dominantGenes.put(animalGenes, val - 1);
        }
        else {
            dominantGenes.remove(animalGenes);
        }
    }

    public void initialize() { // function that (re)start engine

        animalList.clear();
        chosenAnimal = null;
        chosenAnimalDescendants.clear();
        map = null;
        eraCounter = 0;
        dominantGenes.clear();
        animalNumber = configuration.getAnimalAmount();
        deadAnimalsAge = 0;
        map = new WorldMap(configuration);


        // add start animals
        int i = 0;
        while (i < configuration.getAnimalAmount()) {
            Animal newAnimal = new Animal(getMap(), Vector.ofRandom(configuration.getMapWidth(), configuration.getMapHeight()),
                    Direction.randomDirection(), Genes.ofRandom(), configuration.getAnimalEnergy(), configuration);

            if (map.isEmpty(newAnimal.getVector())) {
                getAnimalList().add(newAnimal);
                map.addAnimal(newAnimal);
                onAnimalAdded(newAnimal);
                i++;
            }
        }

        // add start grass
        for (int j = 0; j < configuration.getGrassAmount(); j++) {
            addGrass();
        }
    }

    public void step() {
        // every day
        deleteDeadAnimals();
        moveAnimals();
        feedAnimals();
        animalBreeding();
        decrementEnergy();
        addGrass();
        eraCounter++;
    }

    public void addGrass() {
        for(int i=0; i<configuration.getGrassPerEra(); i++) {
            map.addGrass();
        }
    }

    public void animalBreeding() { // love is in the air

        ArrayList<Vector> vectors = new ArrayList(map.getAnimalMap().keySet()); // get keys in map

        for (Vector i : vectors) {

            List<Animal> fieldAnimals = map.getAnimalMap().get(i);

            if (fieldAnimals.size() > 1) { // if more than 2 animals on field -> sort and get 2 strongest
                fieldAnimals.sort(Animal::compareTo);
                Animal parent1 = fieldAnimals.get(fieldAnimals.size() - 1);
                Animal parent2 = fieldAnimals.get(fieldAnimals.size() - 2);

                if (parent1.getEnergy() > 0.5 * configuration.getAnimalEnergy()
                        && parent2.getEnergy() > 0.5 * configuration.getAnimalEnergy()) { // check if animals have enough energy to breed

                    Animal child = GeneMixer.animalBreeder(parent1, parent2);

                    map.addAnimal(child);
                    onAnimalAdded(child);
                    animalList.add(child);
                    animalNumber += 1;
                    // add child to map and list of animals

                    if (
                            parent1 == chosenAnimal || parent2 == chosenAnimal ||
                                    chosenAnimalDescendants.contains(parent1) || chosenAnimalDescendants.contains(parent2)
                    ) {
                        chosenAnimalDescendants.add(child);
                    }
                }
            }
        }
    }

    public void feedAnimals() {

        ArrayList<Vector> vectorArrayList = new ArrayList(map.getAnimalMap().keySet()); // gets keys in map

        for (Vector i :
                vectorArrayList) {

            if (map.getGrass(i) != null) { // if grass on position -> animals eat grass

                List<Animal> fieldList = strongestAnimals(map.getAnimalMap().get(i)); // array of strongest animals

                int energyForAnimal = configuration.getPlantEnergy() / fieldList.size(); //split energy between animals
                map.removeGrass(i);
                map.addVacation(i); // updates grass status

                for (Animal j : fieldList) {
                    j.setEnergy(j.getEnergy() + energyForAnimal); //updates chosen animals energy
                }
            }
        }
    }

    public ArrayList strongestAnimals(List<Animal> arr) { // returns array of strongest animals on position
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (max < arr.get(i).getEnergy()) {
                max = arr.get(i).getEnergy();
            }
        }
        ArrayList<Animal> strongest = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getEnergy() == max) {
                strongest.add(arr.get(i));
            }
        }
        return strongest;
    }

    public int getDeadAnimalsAge() {
        return deadAnimalsAge;
    }

    public int getAnimalNumber() {
        return animalNumber;
    }

    public void moveAnimals() { // moves every animal on map
        for (int i = 0; i < animalList.size(); i++) {
            animalList.get(i).moveForward();
            animalList.get(i).setAge(animalList.get(i).getAge() + 1);
        }
    }

    public void deleteDeadAnimals() { // removes dead animals from map
        ArrayList<Animal> animalToRemove = new ArrayList();

        for (int i = 0; i < animalList.size(); i++) {  //find dead animals
            if (animalList.get(i).getEnergy() < 0) {
                if (animalList.get(i) == chosenAnimal) { // if dead chosen animal change his dead era
                    chosenAnimalDeadEra = eraCounter;
                }
                map.removeAnimal(animalList.get(i));
                animalToRemove.add(animalList.get(i));
            }
        }

        for (int i = 0; i < animalToRemove.size(); i++) {// update animal list
            animalList.remove(animalToRemove.get(i));
            onAnimalRemoved(animalToRemove.get(i));
            deadAnimalsAge += animalToRemove.get(i).getAge();

        }
    }

    public void decrementEnergy() { // of every animal at the end of the day
        for (Animal i : animalList
        ) {
            i.setEnergy(i.getEnergy() - configuration.getMoveEnergy());
        }

    }

    public WorldMap getMap() {
        return map;
    }

    public Map<Genes, Integer> getDominantGenes() {
        if (dominantGenes == null)
            return null;
        return dominantGenes;
    }

    public int getEraCounter() {
        return eraCounter;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public Set<Animal> getChosenAnimalDescendants() {
        return chosenAnimalDescendants;
    }

    public Animal getChosenAnimal() {
        return chosenAnimal;
    }

    public void setChosenAnimal(Animal chosenAnimal) {
        this.chosenAnimal = chosenAnimal;
        chosenAnimalDescendants.clear();
    }

    public int getChosenAnimalDeadEra() {
        return chosenAnimalDeadEra;
    }
}
