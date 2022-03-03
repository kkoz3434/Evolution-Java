package lab1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class EngineStatsHelper {
    public static Genes findDominantGenes(Engine engine) {
        Integer max = 0;
        Genes maxGenes = null;
        for (Map.Entry<Genes, Integer> entry : engine.getDominantGenes().entrySet()) {
            if (max < entry.getValue()) {
                max = entry.getValue();
                maxGenes = entry.getKey();
            }
        }
        return maxGenes;
    }

    public static String buildGenesString(Genes genes) {
        StringBuilder builder = new StringBuilder(); // odkrycie roku <3
        for (int count : genes.genesCounter()) {
            builder.append(count);
            builder.append(' ');
            builder.append('|');
            builder.append(' ');
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }

    public static int countChosenAnimalAliveDescendants(Engine engine) {
        int count = 0;
        for (Animal animal : engine.getChosenAnimalDescendants()) {
            if (animal.isAlive()) count++;
        }
        return count;
    }

    public static double[] averageValues(Engine engine) { // returns average child number and energy
        int childs = 0;
        int energy = 0;
        for (Animal i :
                engine.getAnimalList()) {
            childs += i.getChilds();
            energy += i.getEnergy();
        }
        if (engine.getAnimalList().size() != 0) { // if checking dividing by zero
            return new double[]{(childs / 2.) / engine.getAnimalList().size(), (double) energy / engine.getAnimalList().size()};
        }
        else {
            return new double[2];
        }
    }

    public static void saveEngineStats(Engine engine, Genes dominantGenes, String fileName) {
        EngineStatsContainer engineStatsContainer = createStatsContainer(engine, dominantGenes);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("C:/" + fileName), engineStatsContainer);
        } catch (IOException e) {
            System.out.println("Can't save file");
        }
    }

    public static EngineStatsContainer createStatsContainer(Engine engine, Genes dominantGenes) {
        int eraNumber = engine.getEraCounter();
        int aliveAnimalsNumber = engine.getAnimalNumber();
        int grassNumber = (engine.getMap() != null ? engine.getMap().getGrassMap().size() : 0);
        double averageChildren = EngineStatsHelper.averageValues(engine)[0];
        double averageEnergy = EngineStatsHelper.averageValues(engine)[1];
        int[] dominantGenesTab = ((engine.getDominantGenes().get(dominantGenes) != null && engine.getDominantGenes().get(dominantGenes) > 1)
                ? dominantGenes.getGenesTab() : null);
        float averageLifeTime = (float) engine.getDeadAnimalsAge() / engine.getAnimalNumber();
        int[] chosenGenes = (engine.getChosenAnimal() != null ? engine.getChosenAnimal().getGenes().getGenesTab() : null);
        int descendants = engine.getChosenAnimalDescendants().size();
        int aliveDescendants = (engine.getChosenAnimal() != null ? EngineStatsHelper.countChosenAnimalAliveDescendants(engine) : -1);
        int chosenEnergy = (engine.getChosenAnimal() != null ? engine.getChosenAnimal().getEnergy() : -1);
        int deadEra = (engine.getChosenAnimal() != null ? -1 : engine.getChosenAnimalDeadEra());
        return new EngineStatsContainer(
                eraNumber,
                aliveAnimalsNumber,
                grassNumber,
                averageChildren,
                averageEnergy,
                dominantGenesTab,
                averageLifeTime,
                chosenGenes,
                descendants,
                aliveDescendants,
                chosenEnergy,
                deadEra
        );

    }
}
