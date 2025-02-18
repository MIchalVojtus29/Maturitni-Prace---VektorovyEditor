package controller_components;

import custom_shapes.IShape;
import custom_shapes.ShapeLine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import other.Colors;

public class ControllerInfo extends VBox {
    private ClickMode mode;
    private IShape shape;
    private ControllerScene scene;
    private int maxLayers;

    private Spinner spLayer;
    private ColorPicker cpStroke, cpShape;
    private Slider slOpacity;
    private TextField tfStartX, tfStartY, tfLineWidth, tfRotation;
    private TextArea pointMatrix;

    public ControllerInfo() {
    }
//inicializace
    public void init(ControllerScene vs, Spinner spinnerLayer, ColorPicker colorPickerShape, ColorPicker colorPickerStroke, Slider sliderOpacity,
                     TextField textFieldStartX, TextField textFieldStartY, TextField textFieldLineWidth, TextField textFieldRotation, TextArea pntMatrix) {
        scene = vs;
        maxLayers = 6;
        shape = new ShapeLine();
        tfStartX = textFieldStartX;
        tfStartY = textFieldStartY;
        slOpacity = sliderOpacity;
        cpStroke = colorPickerStroke;
        cpShape = colorPickerShape;
        tfLineWidth = textFieldLineWidth;
        spLayer = spinnerLayer;
        tfRotation = textFieldRotation;
        pointMatrix = pntMatrix;

        tfStartX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Colors.isFloat(newValue)) {
                    shape.setStartX(Double.parseDouble(newValue));
                } else if(newValue.equals("")){
                    shape.setStartX(0);
                    tfStartX.setText("0");
                }  else {
                    tfStartX.setText(oldValue);
                }
            }
        });

        tfStartY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Colors.isFloat(newValue)) {
                    shape.setStartY(Double.parseDouble(newValue));
                } else if(newValue.equals("")){
                    shape.setStartY(0);
                    tfStartY.setText("0");
                } else {
                    tfStartY.setText(oldValue);
                }
            }
        });

        slOpacity.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                shape.setOpacity((double) newValue);
            }
        });

        SpinnerValueFactory<Integer> svfCurrentLayer = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxLayers, 0);
        spLayer.setValueFactory(svfCurrentLayer);
        spLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int oldValue = shape.getLayer();
                shape.setLayer((Integer) spLayer.getValue());
                scene.changeShapeLayer(shape, oldValue);
            }
        });

        tfLineWidth.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Colors.isFloat(newValue)) {
                    shape.setStrokeWidth(Double.parseDouble(newValue));
                } else if(newValue.equals("")){
                    shape.setStrokeWidth(0);
                    tfLineWidth.setText("0");
                } else {
                    tfLineWidth.setText(oldValue);
                }
            }
        });

        cpShape.setOnAction((EventHandler) t -> shape.setFill(cpShape.getValue()));
        cpStroke.setOnAction((EventHandler) t -> shape.setStroke(cpStroke.getValue()));

        tfRotation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Colors.isFloat(newValue)) {
                    shape.setRotate(Double.parseDouble(newValue));
                } else if(newValue.equals("")){
                    shape.setRotate(0);
                    tfRotation.setText("0");
                }  else {
                    tfRotation.setText(oldValue);
                }
            }
        });

    }

    public void setShape(IShape sh) {
        shape = sh;
        refresh();
    }

    public IShape getCurrentShape(){
        return shape;
    }

    public void refresh(){
        tfStartX.setText((int) shape.getStartX() + "");
        tfStartY.setText((int) shape.getStartY() + "");
        slOpacity.setValue(shape.getOpacity());
        spLayer.getValueFactory().valueProperty().setValue(shape.getLayer());
        tfLineWidth.setText((int) shape.getStrokeWidth() + "");
        cpShape.setValue((Color) shape.getFill());
        cpStroke.setValue((Color) shape.getStroke());
        tfRotation.setText((int) shape.getRotate() + "");
    }

    public void addLayer(){
        maxLayers += 1;
        SpinnerValueFactory<Integer> svfCurrentLayer = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxLayers, shape.getLayer());
        spLayer.setValueFactory(svfCurrentLayer);
    }

    public void clearPointMatrix(){
        pointMatrix.setText("");
    }

    public void addCoords(double x, double y){
        pointMatrix.appendText("\nX: " + x + "\tY" + y);
    }

    public void removePointMatrixLine(){
        pointMatrix.setText(pointMatrix.getText().substring(0, pointMatrix.getText().lastIndexOf("\n")));
    }

}
