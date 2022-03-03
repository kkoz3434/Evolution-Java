package lab1;

public class Configuration {
    private float jungleRatio;
    private int moveEnergy;
    private int grassAmount;
    private int grassPerEra;
    private int plantEnergy;
    private int animalEnergy;
    private int animalAmount;
    private int mapWidth;
    private int mapHeight;

    private Configuration() {}  // constructor for jackson

    public Configuration(float jungleRatio, int moveEnergy, int grassAmount, int plantEnergy,
                         int animalEnergy, int animalAmount,int grassPerEra, int mapWidth, int mapHeight) {
        this.jungleRatio = jungleRatio;
        this.moveEnergy = moveEnergy;
        this.grassAmount = grassAmount;
        this.grassPerEra = grassPerEra;
        this.plantEnergy = plantEnergy;
        this.animalEnergy = animalEnergy;
        this.animalAmount = animalAmount;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public int getGrassPerEra() {
        return grassPerEra;
    }

    public float getJungleRatio() {
        return jungleRatio;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getGrassAmount() {
        return grassAmount;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getAnimalEnergy() {
        return animalEnergy;
    }

    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
