package lab1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.Map;

public class WorldMapDrawer {
    private static final double TILE_SIZE = 8;
    private final WorldMap map;
    private final GraphicsContext context;
    private final Configuration configuration;

    public WorldMapDrawer(WorldMap map, GraphicsContext context, Configuration configuration) {
        this.map = map;
        this.context = context;
        this.configuration = configuration;
    }

    public void draw(Genes dominantGenes, Animal selectedAnimal) {
        if (map != null) {
            context.clearRect(0, 0, calculateWidth(configuration), calculateHeight(configuration));

            // draw grass on map
            context.setFill(Color.GREEN);
            for (Vector vector : map.getGrassMap()) {
                drawTileRect(vector);
            }

            // draw animals on map with  colors
            for (Map.Entry<Vector, List<Animal>> entry : map.getAnimalMap().entrySet()) {
                Paint paint;
                if (selectedAnimal != null && entry.getValue().contains(selectedAnimal)) {
                    paint = Color.RED;
                }
                else if (dominantGenes != null) {
                    boolean hasDominantGenes = false;
                    for (Animal animal : entry.getValue()) {
                        if (animal.getGenes().equals(dominantGenes)) {
                            hasDominantGenes = true;
                            break;
                        }
                    }
                    if (hasDominantGenes) {
                        paint = Color.ORANGE;
                    }
                    else {
                        paint =Color.BLUE;
                    }
                }
                else {
                    paint = Color.BLUE;
                }
                context.setFill(paint);
                drawTileOval(entry.getKey());
            }
        }
    }

    private void drawTileOval(Vector vector) {
        context.fillOval(vector.getX() * TILE_SIZE, (configuration.getMapHeight() - vector.getY() - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public void drawTileRect(Vector vector) {
        context.fillRect(vector.getX() * TILE_SIZE, (configuration.getMapHeight() - vector.getY() - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public static int calculateWidth(Configuration configuration) {
        return (int) (configuration.getMapWidth() * TILE_SIZE);
    }

    public static int calculateHeight(Configuration configuration) {
        return (int) (configuration.getMapHeight() * TILE_SIZE);
    }

    public static Vector calculateVector(Configuration configuration, MouseEvent event) {
        int vectorX = (int) (event.getX() / TILE_SIZE);
        int vectorY = configuration.getMapHeight() - (int) (event.getY() / TILE_SIZE) - 1;
        return new Vector(vectorX, vectorY);
    }
}
