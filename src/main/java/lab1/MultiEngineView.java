package lab1;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MultiEngineView {
    private final Engine engine1, engine2;
    private final EngineView view1, view2;
    private final VBox view = new VBox(8);

    private boolean isGenerated = false;
    private boolean isRunning = false;

    public MultiEngineView(Engine engine1, Engine engine2, Configuration configuration) {
        this.engine1 = engine1;
        this.engine2 = engine2;
        view1 = new EngineView(engine1, configuration);
        view2 = new EngineView(engine2, configuration);
    }

    public void setup() {
        view1.setup("firstStats.json");
        view2.setup("secondStats.json");
    }

    public VBox draw(){
        view.getChildren().clear();
        HBox buttonsRow = new HBox(160);
        buttonsRow.alignmentProperty().setValue(Pos.CENTER);

        if (!isRunning) buttonsRow.getChildren().add(buildGenerateButton());
        if (!isRunning && isGenerated) buttonsRow.getChildren().add(buildStartButton("Start", true));
        if (isRunning) buttonsRow.getChildren().add(buildStartButton("Pause", false));

        view.getChildren().add(buttonsRow);
        view.getChildren().add(new Separator(Orientation.HORIZONTAL));
        HBox engineViewsRow = new HBox();
        engineViewsRow.getChildren().addAll(view1.getView(), new Separator(Orientation.VERTICAL), view2.getView());
        view.getChildren().add(engineViewsRow);

        return view;
    }

    private void markGenerated() {
        this.isGenerated = true;
        view1.markGenerated();
        view2.markGenerated();
        draw();
    }

    private void setRunning(boolean running) {
        this.isRunning = running;
        view1.setRunning(running);
        view2.setRunning(running);
        draw();
    }

    private Button buildStartButton(String start, boolean targetIsRunning) {
        Button startButton = new Button(start);
        startButton.setOnAction(event -> setRunning(targetIsRunning));
        return startButton;
    }

    private Button buildGenerateButton() {
        Button generateButton = new Button("Generate");
        generateButton.setOnAction(event -> {
            engine1.initialize();
            engine2.initialize();
            markGenerated();
        });
        return generateButton;
    }
}
