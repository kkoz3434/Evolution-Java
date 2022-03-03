package lab1;

import java.util.Arrays;

public class GeneMixer {

    public static Genes mixGenes(Animal parent1, Animal parent2) { //create child genes from two parents
        int split1 = (int) ((Math.random() * (Genes.GENES_SIZE - 1)));
        int split2 = (int) ((Math.random() * (Genes.GENES_SIZE - 1 - split1)) + split1);

        int[] genes1 = Arrays.copyOfRange(parent1.getGenes().getGenesTab(), 0, split1);
        int[] genes2 = Arrays.copyOfRange(parent2.getGenes().getGenesTab(), split1, split2);
        int[] genes3 = Arrays.copyOfRange(parent1.getGenes().getGenesTab(), split2, Genes.GENES_SIZE);
        int[] childGenes = new int[32];

        System.arraycopy(genes1, 0, childGenes, 0, genes1.length);
        System.arraycopy(genes2, 0, childGenes, genes1.length, genes2.length);
        System.arraycopy(genes3, 0, childGenes, genes1.length + genes2.length, genes3.length);

        return Genes.ofMixed(childGenes);
    }

    public static Animal animalBreeder(Animal parent1, Animal parent2) { // returns new child

        Animal child = new Animal(parent1.getMap(), fieldNearParent(parent1), Direction.randomDirection(),
                mixGenes(parent1, parent2), childEnergy(parent1, parent2), parent1.getConfiguration());

        parent1.onBreded();
        parent2.onBreded();

        return child;
    }

    public static int childEnergy(Animal parent1, Animal parent2) {
        return (int) (0.25 * parent1.getEnergy() + 0.25 * parent2.getEnergy());
    }

    public static Vector fieldNearParent(Animal animal) { // if empty field around -> return vector; else return random around
        WorldMap map = animal.getMap();
        Direction dir = animal.getDirection();
        Configuration configuration = animal.getConfiguration();

        for (int i = 0; i < 8; i++) {
            if (map.isEmpty(animal.getVector().moveVector(dir, configuration.getMapHeight(), configuration.getMapWidth()))) {
                return animal.getVector().moveVector(dir, configuration.getMapHeight(), configuration.getMapWidth());
            }
        }

        return animal.getVector().moveVector(Direction.randomDirection(), configuration.getMapHeight(), configuration.getMapWidth());
    }

}
