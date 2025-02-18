package main_sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("Vektorový Editor");
        root.getStylesheets().add("other/Style.css");



        //alert, který se ptá jestli chceme aplikaci opravdu ukončit
        ButtonType ANO = new ButtonType("Ano");
        ButtonType NE = new ButtonType("Ne");

        primaryStage.setOnCloseRequest(evt -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chcete opravdu zavřít vektorový editor?", ANO, NE);

            // správně by tato část měla zavřít alert při kliknutí na křížek
            ButtonType result = alert.showAndWait().orElse(NE);

            if (NE.equals(result)) {
                evt.consume();
            }
        });

        primaryStage.setScene(new Scene(root, 1480, 860));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

