package lab1;

import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.util.List;

public class EngineView {
    private final HBox view = new HBox();
    private final Configuration configuration;
    private final Engine engine;
    private final EngineStatsView statsView;
    private final Canvas canvas;
    private boolean isGenerated = false;
    private boolean isRunning = false;
    private WorldMapDrawer mapDrawer = null;
    private Genes dominantGenes = null;
    private Animal selectedAnimal = null;

    public EngineView(Engine engine, Configuration configuration) {
        this.engine = engine;
        this.configuration = configuration;
        statsView = new EngineStatsView(engine);
        canvas = new Canvas(WorldMapDrawer.calculateWidth(configuration), WorldMapDrawer.calculateHeight(configuration));
    }

    public void setup(String fileName) {
        // ticker for map animation
        FixedStepAnimationTicker.ofStepsPerSecond(200, () -> {
            if (isRunning) {
                engine.step();
            }
            if (isGenerated) {
                mapDrawer.draw(isRunning ? null : dominantGenes, selectedAnimal);
            }
        }).start();

        // ticker for stats animation
        FixedStepAnimationTicker.ofStepsPerSecond(10, () -> {
            if (isRunning) updateGenes();
            EngineStatsHelper.saveEngineStats(engine, dominantGenes, fileName);
        }).start();

        canvas.setOnMouseClicked(event -> {
            if (!isRunning) {
                List<Animal> animals = engine.getMap().getAnimals(WorldMapDrawer.calculateVector(configuration, event));
                setSelectedAnimal(animals != null ? animals.get(0) : null);
            }
        });

        sync();
    }

    private void sync() { // syncs the stats
        view.getChildren().clear();
        view.getChildren().addAll(
                statsView.draw(isGenerated, dominantGenes, selectedAnimal),
                new Separator(Orientation.VERTICAL),
                canvas
        );
        mapDrawer = new WorldMapDrawer(engine.getMap(), canvas.getGraphicsContext2D(), configuration);
    }

    private void updateGenes() {
        setDominantGenes(EngineStatsHelper.findDominantGenes(engine));
    }

    public HBox getView() {
        return view;
    }

    public void markGenerated() {
        isGenerated = true;
        updateGenes();
        setSelectedAnimal(null);
        sync();
    }

    public void setRunning(boolean running) {
        isRunning = running;
        updateGenes();
        sync();
    }

    private void setDominantGenes(Genes genes) {
        dominantGenes = genes;
        sync();
    }

    private void setSelectedAnimal(Animal animal) {
        selectedAnimal = animal;
        engine.setChosenAnimal(animal);
        sync();
    }
}
