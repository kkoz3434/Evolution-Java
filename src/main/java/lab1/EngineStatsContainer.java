package lab1;

public class EngineStatsContainer {
    private int eraNumber;
    private int aliveAnimalsNumber;
    private int grassNumber;
    private double averageChildren;
    private double averageEnergy;
    private int[] dominantGenes;
    private float averageLifetime;
    private int[] chosenGenes;
    private int descendants;
    private int aliveDescendants;
    private int energy;
    private int deadEra;

    public EngineStatsContainer(int eraNumber, int aliveAnimalsNumber, int grassNumber,
                                double averageChildren, double averageEnergy, int[] dominantGenes,
                                float averageLifetime,
                                int[] chosenGenes, int descendants, int aliveDescendants,
                                int energy, int deadEra) {
        this.setEraNumber(eraNumber);
        this.setAliveAnimalsNumber(aliveAnimalsNumber);
        this.setGrassNumber(grassNumber);
        this.setAverageChildren(averageChildren);
        this.setAverageEnergy(averageEnergy);
        this.setDominantGenes(dominantGenes);
        this.setAverageLifetime(averageLifetime);
        this.setChosenGenes(chosenGenes);
        this.setDescendants(descendants);
        this.setAliveDescendants(aliveDescendants);
        this.setEnergy(energy);
        this.setDeadEra(deadEra);
    }

    public int getEraNumber() {
        return eraNumber;
    }

    public void setEraNumber(int eraNumber) {
        this.eraNumber = eraNumber;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    public void setGrassNumber(int grassNumber) {
        this.grassNumber = grassNumber;
    }

    public double getAverageChildren() {
        return averageChildren;
    }

    public void setAverageChildren(double averageChildren) {
        this.averageChildren = averageChildren;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public void setAverageEnergy(double averageEnergy) {
        this.averageEnergy = averageEnergy;
    }

    public int[] getDominantGenes() {
        return dominantGenes;
    }

    public void setDominantGenes(int[] dominantGenes) {
        this.dominantGenes = dominantGenes;
    }

    public float getAverageLifetime() {
        return averageLifetime;
    }

    public void setAverageLifetime(float averageLifetime) {
        this.averageLifetime = averageLifetime;
    }

    public int[] getChosenGenes() {
        return chosenGenes;
    }

    public void setChosenGenes(int[] chosenGenes) {
        this.chosenGenes = chosenGenes;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getAliveDescendants() {
        return aliveDescendants;
    }

    public void setAliveDescendants(int aliveDescendants) {
        this.aliveDescendants = aliveDescendants;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getDeadEra() {
        return deadEra;
    }

    public void setDeadEra(int deadEra) {
        this.deadEra = deadEra;
    }

    public int getAliveAnimalsNumber() {
        return aliveAnimalsNumber;
    }

    public void setAliveAnimalsNumber(int aliveAnimalsNumber) {
        this.aliveAnimalsNumber = aliveAnimalsNumber;
    }
}
