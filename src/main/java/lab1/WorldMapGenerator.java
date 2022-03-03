package lab1;


import java.util.HashSet;

public class WorldMapGenerator {

    public static HashSet<Vector> emptyJungle(Configuration configuration) { // returns grass vacations in jungle
        HashSet<Vector> grassVacationJungle = new HashSet<>();
        Vector minJungleVector = Vector.getJungleMinVector(configuration);
        Vector maxJungleVector = Vector.getJungleMaxVector(configuration);
        for (int x = minJungleVector.getX(); x < maxJungleVector.getX(); x++) {
            for (int y = minJungleVector.getY(); y < maxJungleVector.getY(); y++) {
                grassVacationJungle.add(new Vector(x, y));
            }
        }
        return grassVacationJungle;
    }

    public static HashSet<Vector> emptySavanna(Configuration configuration) { // returns grass vacations on savanna

        Vector minJungleVector = Vector.getJungleMinVector(configuration);
        Vector maxJungleVector = Vector.getJungleMaxVector(configuration);

        HashSet<Vector> grassVacationSavanna = new HashSet<>();

        // jungle is centered -> savanna 'divided' in four areas

        for (int x = 0; x < minJungleVector.getX(); x++) {
            for (int y = 0; y < configuration.getMapHeight(); y++) {
                grassVacationSavanna.add(new Vector(x, y));
            }
        }

        for (int x = minJungleVector.getX(); x < maxJungleVector.getX(); x++) {
            for (int y = maxJungleVector.getY(); y < configuration.getMapHeight(); y++) {
                grassVacationSavanna.add(new Vector(x, y));
            }
        }

        for (int x = maxJungleVector.getX(); x < configuration.getMapWidth(); x++) {
            for (int y = 0; y < configuration.getMapHeight(); y++) {
                grassVacationSavanna.add(new Vector(x, y));
            }
        }

        for (int x = minJungleVector.getX(); x < maxJungleVector.getX(); x++) {
            for (int y = 0; y < minJungleVector.getY(); y++) {
                grassVacationSavanna.add(new Vector(x, y));
            }
        }
        return grassVacationSavanna;
    }

}
