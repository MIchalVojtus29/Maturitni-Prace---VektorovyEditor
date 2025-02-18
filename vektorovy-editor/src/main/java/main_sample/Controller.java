package main_sample;

import controller_components.ClickMode;
import controller_components.ControllerInfo;
import controller_components.ControllerScene;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import main_sample.Main;

public class Controller implements Initializable {
    //vytváření prvků pro UI
    @FXML
    private HBox hboxBottom, hboxTop;
    @FXML
    private VBox vboxLeft;
    @FXML
    private MenuBar menu;
    @FXML
    private Button buttonActionInteract, buttonActionLine, buttonActionCircle, buttonActionRectangle, buttonActionEllipse,
            buttonActionPoint, buttonActionMove, buttonAddLayer, buttonAdjust, buttonDelete, buttonPolygon,
            buttonClearAllMatrix, buttonClearLastMatrix, buttonDuplicate, buttonScribble;
    @FXML
    private CheckBox checkboxSnappingStart, checkboxSnappingEnd, checkboxShowPolygonPoints;
    @FXML
    private Label labelAction;

    @FXML
    private ColorPicker colorPickerFill, colorPickerStroke, colorPickerPolygonPoints;
    @FXML
    private BorderPane b;
    @FXML
    private MenuItem menuItemSaveSvg, menuItemSaveJvgf;
    @FXML
    private Spinner spinnerStrokeWidth, spinnerCurrentLayer;

    @FXML
    private ColorPicker colorPickerShapeColor = new ColorPicker();
    @FXML
    private ColorPicker colorPickerStrokeColor = new ColorPicker();
    @FXML
    private TextField tfStartX = new TextField();
    @FXML
    private TextField tfStartY = new TextField();
    @FXML
    private TextField tfLineWidth = new TextField();
    @FXML
    private Spinner spinnerShapeLayer = new Spinner();
    @FXML
    private Slider sliderOpacity = new Slider();
    @FXML
    private ControllerInfo paneRight = new ControllerInfo();
    @FXML
    private ControllerScene scene = new ControllerScene();
    @FXML
    private TextField tfRotation = new TextField();
    @FXML
    private TextArea textAreaPointMatrix = new TextArea();

    //inicializace
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scene.setInfoPane(paneRight);
        paneRight.init(scene, spinnerShapeLayer, colorPickerShapeColor,
                colorPickerStrokeColor, sliderOpacity, tfStartX, tfStartY, tfLineWidth, tfRotation, textAreaPointMatrix);

        colorPickerFill.setOnAction((EventHandler) t -> scene.setFillColor(colorPickerFill.getValue()));
        colorPickerStroke.setOnAction((EventHandler) t -> scene.setStrokeColor(colorPickerStroke.getValue()));

        SpinnerValueFactory<Integer> svfStrokeWidth = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 1);
        spinnerStrokeWidth.setValueFactory(svfStrokeWidth);
        spinnerStrokeWidth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setStrokeWidth((Integer) spinnerStrokeWidth.getValue());
            }
        });

        SpinnerValueFactory<Integer> svfCurrentLayer = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0);
        spinnerCurrentLayer.setValueFactory(svfCurrentLayer);
        spinnerCurrentLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setLayer((Integer) spinnerCurrentLayer.getValue());
            }
        });

        colorPickerPolygonPoints.setOnAction((EventHandler) t -> scene.setPolygonPointsColor(colorPickerPolygonPoints.getValue()));

        textAreaPointMatrix.setEditable(false);
        checkboxShowPolygonPoints.setSelected(true);
    }
// metody pro přepínání režimů
    @FXML
    public void switchActionInteract(){
        scene.switchMode();
        scene.setClickMode(ClickMode.INTERACT);
        labelAction.setText("Režim: Interakce");
    }

    @FXML
    public void switchActionAdjust(){
        scene.switchMode();
        scene.setClickMode(ClickMode.ADJUST);
        labelAction.setText("Režim: Úprava");
    }

    @FXML
    public void switchActionLine(){
        scene.switchMode();
        scene.setClickMode(ClickMode.LINE);
        labelAction.setText("Režim: Přímka");
    }

    @FXML
    public void switchActionScribble(){
        scene.switchMode();
        scene.setClickMode(ClickMode.SCRIBBLE);
        labelAction.setText("Režim: Štětec");
    }

    @FXML
    public void switchActionCircle(){
        scene.switchMode();
        scene.setClickMode(ClickMode.CIRCLE);
        labelAction.setText("Režim: Kruh");
    }

    @FXML
    public void switchActionRectangle(){
        scene.switchMode();
        scene.setClickMode(ClickMode.RECTANGLE);
        labelAction.setText("Režim: Čtyřúhelník");
    }

    @FXML
    public void switchActionEllipse(){
        scene.switchMode();
        scene.setClickMode(ClickMode.ELLIPSE);
        labelAction.setText("Režim: Elipsa");
    }

    @FXML
    public void switchActionPoint(){
        scene.switchMode();
        scene.setClickMode(ClickMode.POINT);
        labelAction.setText("Režim: Bod");
    }

    @FXML
    public void switchActionMove(){
        scene.switchMode();
        scene.setClickMode(ClickMode.MOVE);
        labelAction.setText("Režim: Pohyb");
    }

    @FXML
    public void switchActionDelete(){
        scene.switchMode();
        scene.setClickMode(ClickMode.DELETE);
        labelAction.setText("Režim: Guma");
    }

    @FXML
    public void switchActionDuplicate(){
        scene.switchMode();
        scene.setClickMode(ClickMode.DUPLICATE);
        labelAction.setText("Režim: Duplikace");
    }

    @FXML
    public void addLayer(){
        System.out.println("Vrstva byla přidána");
        scene.addLayer();
        SpinnerValueFactory.IntegerSpinnerValueFactory vf = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerCurrentLayer.getValueFactory();
        vf.setMax(vf.getMax() + 1);
        paneRight.addLayer();
    }
//'snapping' + body pro trojúhelník/mnohoúhelník
    @FXML
    public void changeSnappingStart(){
        scene.changeSnappingStart(checkboxSnappingStart.isSelected());
    }

    @FXML
    public void changeSnappingEnd(){
        scene.changeSnappingEnd(checkboxSnappingEnd.isSelected());
    }

    @FXML
    public void changePolygonPointsVisibility(){
        scene.setPolygonPointsVisibility(checkboxShowPolygonPoints.isSelected());
    }

    @FXML
    public void clearAllMatrix(){
        scene.clearMatrix();
    }

    @FXML
    public void clearLastMatrix(){
        scene.clearLastMatrix();
    }

    @FXML
    public void createPolygon(){
        scene.createPolygon();
    }
//metoda pro uložení do png
    @FXML
    public void save(){

        FileChooser savefile = new FileChooser();
        savefile.setTitle("Uložit");
        File file = savefile.showSaveDialog(Main.stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(1024, 720);
                scene.snapshot(null, writableImage);
                RenderedImage reimg = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(reimg,"png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }

    @FXML
    public void quit(){
        Platform.exit();

    }


}