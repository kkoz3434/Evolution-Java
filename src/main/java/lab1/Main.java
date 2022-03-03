package lab1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** Jakub Koźlak, grupa: czwartek 17:50
     *
     * są chyba wszystkie funkcje
     *
     * json zapisywany w "C:/"
     *
     * dane wczytywane z pliku parameters.json
     *
     * zwierze wybierane kliknięciem po zatrzymaniu symulacji, osobno dla każdej mapy
     *
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Stage) {

        Stage.setTitle("Ewolucja");
        Stage.setResizable(false);

        Configuration configuration = ConfigurationProvider.load();

        Engine engine1 = new Engine(configuration);
        Engine engine2 = new Engine(configuration);

        MultiEngineView multiEngineView = new MultiEngineView(engine1, engine2, configuration);
        multiEngineView.setup();

        Stage.setScene(new Scene(multiEngineView.draw()));

        Stage.show();
    }
}
