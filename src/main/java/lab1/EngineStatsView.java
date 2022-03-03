package lab1;


import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class EngineStatsView {
    private final Engine engine;
    private final VBox view = new VBox(8);

    EngineStatsView(Engine engine) {
        this.engine = engine;
    }


    public ScrollPane draw(boolean isGenerated, Genes dominantGenes, Animal selectedAnimal) { // get parameters from EngineView

        view.setMinWidth(200);
        view.getChildren().clear();

        if (isGenerated) { // basic stats
            view.getChildren().addAll(
                    new Label("Era number:\n" + engine.getEraCounter()),
                    new Label("Animals alive:\n" + engine.getAnimalList().size()),
                    new Label("Grass on map:\n" + engine.getMap().getGrassMap().size()),
                    new Label("Average children per animal:\n" + String.format("%.2f", EngineStatsHelper.averageValues(engine)[0])),
                    new Label("Average energy per animal: \n" + String.format("%.2f", EngineStatsHelper.averageValues(engine)[1])),
                    //new Label("Dominant genes:\n" + (engine.getDominantGenes().get(dominantGenes) > 1 ? EngineStatsHelper.buildGenesString(dominantGenes) : "Not exist")),
                    new Label("Average life duration:\n" + String.format("%.2f", ((float) engine.getDeadAnimalsAge() / engine.getAnimalNumber())))
            );

            if (selectedAnimal != null) { // specified stats for chosen animal
                view.getChildren().addAll(
                        new Separator(Orientation.HORIZONTAL),
                        new Label("Selected animal:"),
                        new Label("Genes:\n" + EngineStatsHelper.buildGenesString(selectedAnimal.getGenes())),
                        new Label("Descendants:\n" + engine.getChosenAnimalDescendants().size()),
                        new Label("Alive descendants:\n" + EngineStatsHelper.countChosenAnimalAliveDescendants(engine)),
                        new Label("Energy:\n" + (selectedAnimal.isAlive() ? selectedAnimal.getEnergy() : "dead")),
                        new Label("Dead era:\n" + (selectedAnimal.isAlive() ? "Still alive" : engine.getChosenAnimalDeadEra()))
                );
            }
        }
        else {
            view.getChildren().add(new Label("Generate map!"));
        }

        ScrollPane scroll = new ScrollPane();
        scroll.setMinWidth(250);
        scroll.setContent(view);
        return scroll;
    }

}
