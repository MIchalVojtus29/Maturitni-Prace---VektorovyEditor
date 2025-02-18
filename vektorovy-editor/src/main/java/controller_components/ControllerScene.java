package controller_components;

import custom_shapes.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import other.ShapePoint;
import main_sample.Main;

import java.util.ArrayList;

public class ControllerScene extends Pane {
    private ClickMode action;
    private ArrayList<ArrayList<IShape>> content = new ArrayList<>();
    private Color fillColor;
    private Color strokeColor;
    private int strokeWidth;

    private boolean isDrawing = false;
    private boolean isMoving = false;
    private boolean isAdjusting = false;
    private boolean snappingStart = false;
    private boolean snappingEnd = false;
    private double xTmp, yTmp;
    private IShape currentShape;
    private int layer;
    private static final String checkMovable = "package custom_shapes";
    private ControllerInfo infoPane;

    private ArrayList<Double> pointMatrix = new ArrayList<>();
    private ArrayList<ShapePoint> pointList = new ArrayList<>();
    public ControllerScene(){
        //velikost plátna
        setClip(new Rectangle(1024, 720));

        action = ClickMode.INTERACT;
        fillColor = Color.WHITE;
        strokeColor = Color.BLACK;
        strokeWidth = 1;
        xTmp = yTmp = 0;


        for(int i = 0; i < 7; i++){
            content.add(new ArrayList<IShape>());
        }

        setOnMouseClicked(new EventHandler<MouseEvent>(){
            //handler pro MouseEvent
            @Override
            public void handle(MouseEvent m) {
                double x = m.getX();
                double y = m.getY();
                if(snappingStart && m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                    IShape snapTo = (IShape) m.getPickResult().getIntersectedNode();
                    x = snapTo.getCenterX();
                    y = snapTo.getCenterY();
                }
                if(action == ClickMode.INTERACT){
                    if(m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                        currentShape = (IShape) m.getPickResult().getIntersectedNode();
                        infoPane.setShape(currentShape);
                    }
                } else if(action == ClickMode.POINT && m.getButton() == MouseButton.PRIMARY){
                    pointMatrix.add(m.getX());
                    pointMatrix.add(m.getY());
                    pointList.add(new ShapePoint(m.getX(), m.getY()));
                    renderPolygonPoints();
                    infoPane.addCoords(m.getX(), m.getY());
                } else if(action == ClickMode.MOVE){
                    if(m.getButton() == MouseButton.PRIMARY && !isMoving){
                        if(m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                            currentShape = (IShape) m.getPickResult().getIntersectedNode();
                            infoPane.setShape(currentShape);
                            xTmp = currentShape.getStartX();
                            yTmp = currentShape.getStartY();
                            System.out.println("začátek pohybu");
                            isMoving = true;
                        }
                    } else if(m.getButton() == MouseButton.PRIMARY && isMoving){
                        System.out.println("konec pohybu");
                        isMoving = false;
                    } else if(m.getButton() == MouseButton.SECONDARY && isMoving){
                        stopMovingShape();
                    }
                } else if(action == ClickMode.ADJUST){
                        if(m.getButton() == MouseButton.PRIMARY && !isAdjusting){
                            if(m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                                System.out.println("začátek úpravy");
                                currentShape = (IShape) m.getPickResult().getIntersectedNode();
                                infoPane.setShape(currentShape);
                                xTmp = currentShape.getAdjustX();
                                yTmp = currentShape.getAdjustY();
                                isAdjusting = true;
                            }
                        } else if(m.getButton() == MouseButton.PRIMARY && isAdjusting){
                            System.out.println("konec úpravy");
                            isAdjusting = false;
                        } else if(m.getButton() == MouseButton.SECONDARY && isAdjusting){
                            stopAdjustingShape();
                        }
                } else if(action == ClickMode.DELETE){
                    if(m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                        IShape del = (IShape) m.getPickResult().getIntersectedNode();
                        removeShape(del);
                    }
                } else if(action == ClickMode.DUPLICATE){
                    System.out.println("duplikace");
                    if(m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                        IShape toDuplicate = (IShape) m.getPickResult().getIntersectedNode();
                        newShape(toDuplicate.clone());
                    }
                } else {
                    if(m.getButton() == MouseButton.PRIMARY && !isDrawing) {
                        System.out.println("začátek kresby");
                        isDrawing = true;
                        switch (action) {
                            case LINE:
                                newShape(new ShapeLine(ControllerScene.this, layer, x, y, strokeColor, strokeWidth));
                                break;
                           /* case SCRIBBLE:
                                newShape(new MyScribble(VectorScene.this, layer, x, y, strokeColor, strokeWidth));
                                break;*/
                            case CIRCLE:
                                newShape(new ShapeCircle(ControllerScene.this, layer, x, y, 0, strokeColor, fillColor, strokeWidth));
                                break;
                            case RECTANGLE:
                                newShape(new ShapeRectangle(ControllerScene.this, layer, x, y, 0, 0, strokeColor, fillColor, strokeWidth));
                                break;
                            case ELLIPSE:
                                newShape(new ShapeEllipse(ControllerScene.this, layer, x, y, 0, 0, strokeColor, fillColor, strokeWidth));
                                break;
                        }
                    } else if(m.getButton() == MouseButton.PRIMARY && isDrawing){
                        System.out.println("konec kresby");
                        isDrawing = false;
                        currentShape = null;
                    }
                    if(m.getButton() == MouseButton.SECONDARY && isDrawing){
                        System.out.println("přerušení kresby");
                        removeUnfinishedShape();
                    }
                }
            }
        });

        setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m) {

                double x = m.getX();
                double y = m.getY();
                if(snappingEnd && m.getPickResult().getIntersectedNode().getClass().getPackage().toString().equals(checkMovable)){
                    IShape snapTo = (IShape) m.getPickResult().getIntersectedNode();
                    if(snapTo != currentShape){
                        x = snapTo.getCenterX();
                        y = snapTo.getCenterY();
                    }
                }

                if(action == ClickMode.INTERACT){

                } else if(action == ClickMode.POINT || action == ClickMode.DUPLICATE){

                } else if(action == ClickMode.MOVE && isMoving){
                    currentShape.move(x, y);
                    infoPane.refresh();
                } else if(action == ClickMode.ADJUST && isAdjusting){
                    currentShape.adjust(x, y);
                    infoPane.refresh();
                }  else{
                    if(isDrawing){
                        currentShape.adjust(x, y);
                    }
                }
            }
        });
    }

    private void newShape(IShape shape){
        currentShape = shape;
        content.get(layer).add(shape);
        renderContent();
    }

    private void renderContent(){
        getChildren().clear();
        for(ArrayList<IShape> arr : content){
            for(IShape shape : arr){
                if(!getChildren().contains(shape)){
                    getChildren().add((Shape) shape);
                }
            }
        }
    }

    private void removeShape(IShape shape){
        for(ArrayList<IShape> layer : content){
            layer.remove(shape);
        }
        System.out.println(getChildren().remove(shape));
    }

    public void changeShapeLayer(IShape shape, int oldLayer){

        if(content.get(oldLayer).remove(shape)){
            content.get(shape.getLayer()).add(shape);
            renderContent();
        }
    }

    public void switchMode(){
        if(isDrawing)
            removeUnfinishedShape();
        if(isAdjusting)
            stopAdjustingShape();
        if(isMoving)
            stopMovingShape();
    }

    private void removeUnfinishedShape(){
        isDrawing = false;
        content.get(currentShape.getLayer()).remove(currentShape);
        getChildren().remove(currentShape);
        currentShape = null;
    }

    private void stopAdjustingShape(){
        currentShape.adjust(xTmp, yTmp);
        currentShape = null;
        isAdjusting = false;
    }

    private void stopMovingShape(){
        currentShape.move(xTmp, yTmp);
        currentShape = null;
        isMoving = false;
    }


    public void addLayer(){
        content.add(new ArrayList<IShape>());
    }

    public void clearMatrix(){
        pointMatrix.clear();
        removePolygonPoints();
        infoPane.clearPointMatrix();
    }

    public void clearLastMatrix(){
        if(pointMatrix.size() >= 4){
            pointMatrix.remove(pointMatrix.size() - 1);
            pointMatrix.remove(pointMatrix.size() - 1);
            ShapePoint tmp = pointList.get(pointList.size() - 1);
            getChildren().remove(tmp);
            pointList.remove(tmp);
            infoPane.removePointMatrixLine();
        }
    }

    public void createPolygon(){
        newShape(new ShapePolygon(ControllerScene.this, pointMatrix, strokeColor, fillColor, strokeWidth, layer));
        clearMatrix();
        removePolygonPoints();
    }

    private void renderPolygonPoints(){
        for(ShapePoint point : pointList){
            if(!getChildren().contains(point)){
                getChildren().add(point);
            }
        }
    }

    private void removePolygonPoints(){
        for(ShapePoint point : pointList){
            getChildren().remove(point);
        }
        pointList.clear();
    }

    public void setPolygonPointsVisibility(boolean visibility){
        for(ShapePoint point : pointList){
            point.setVisible(visibility);
        }
        ShapePoint.setDefaultVisibility(visibility);
    }

    public void setPolygonPointsColor(Color c){
        for(ShapePoint point: pointList){
            point.setFill(c);
        }
        ShapePoint.setDefaultFill(c);
    }

    public void clearAll(){
        getChildren().removeAll();
        for(ArrayList<IShape> arr : content){
            arr.clear();
        }
        switchMode();
    }

    public void setClickMode(ClickMode act){
        action = act;
    }

    public ClickMode getClickMode(){
        return action;
    }

    public void setFillColor(Color c){
        fillColor = c;
    }

    public void setStrokeColor(Color c){
        strokeColor = c;
    }

    public ArrayList<ArrayList<IShape>> getContent(){
        return content;
    }

    public void setStrokeWidth(int sw){
        strokeWidth = sw;
    }

    public void setLayer(int l){
        layer = l;
        System.out.println(layer);
    }

    public int getLayer(){
        return content.size();
    }

    public void changeSnappingStart(boolean value){
        snappingStart = value;
    }

    public void changeSnappingEnd(boolean value){
        snappingEnd = value;
    }

    public void setInfoPane(ControllerInfo ip){
        infoPane = ip;
    }

    public void readLine(String[] params){
        ShapeLine currentLine = new ShapeLine();
        newShape(currentLine);
        currentLine.setStartX(Double.parseDouble(params[2]));
        currentLine.setStartY(Double.parseDouble(params[4]));
        currentLine.setEndX(Double.parseDouble(params[6]));
        currentLine.setEndY(Double.parseDouble(params[8]));
        currentLine.setOpacity(Double.parseDouble(params[10]));
        currentLine.setStrokeWidth(Double.parseDouble(params[12]));
        currentLine.setStroke(Color.valueOf(params[14]));
        currentLine.setLayer(Integer.parseInt(params[16]));
        currentLine.setRotate(Double.parseDouble(params[18]));
        currentShape = null;
    }
/*
    public void readScribble(String[] params){
        MyScribble currentScribble = new MyScribble();
        newShape(currentScribble);
        currentScribble.setStartX(Double.parseDouble(params[2]));
        currentScribble.setStartY(Double.parseDouble(params[4]));
        currentScribble.setEndX(Double.parseDouble(params[6]));
        currentScribble.setEndY(Double.parseDouble(params[8]));
        currentScribble.setOpacity(Double.parseDouble(params[10]));
        currentScribble.setStrokeWidth(Double.parseDouble(params[12]));
        currentScribble.setStroke(Color.valueOf(params[14]));
        currentScribble.setLayer(Integer.parseInt(params[16]));
        currentScribble.setRotate(Double.parseDouble(params[18]));
        currentShape = null;
    }
*/
    public void readRectangle(String[] params){
        ShapeRectangle currentRectangle = new ShapeRectangle();
        newShape(currentRectangle);
        currentRectangle.setX(Double.parseDouble(params[2]));
        currentRectangle.setY(Double.parseDouble(params[4]));
        currentRectangle.setWidth(Double.parseDouble(params[6]));
        currentRectangle.setHeight(Double.parseDouble(params[8]));
        currentRectangle.setOpacity(Double.parseDouble(params[10]));
        currentRectangle.setStrokeWidth(Double.parseDouble(params[12]));
        currentRectangle.setStroke(Color.valueOf(params[14]));
        currentRectangle.setFill(Color.valueOf(params[16]));
        currentRectangle.setLayer(Integer.parseInt(params[18]));
        currentRectangle.setRotate(Double.parseDouble(params[20]));
        currentShape = null;
    }

    public void readCircle(String[] params){
        ShapeCircle currentCircle = new ShapeCircle();
        newShape(currentCircle);
        currentCircle.setCenterX(Double.parseDouble(params[2]));
        currentCircle.setCenterY(Double.parseDouble(params[4]));
        currentCircle.setRadius(Double.parseDouble(params[6]));
        currentCircle.setOpacity(Double.parseDouble(params[8]));
        currentCircle.setStrokeWidth(Double.parseDouble(params[10]));
        currentCircle.setStroke(Color.valueOf(params[12]));
        currentCircle.setFill(Color.valueOf(params[14]));
        currentCircle.setLayer(Integer.parseInt(params[16]));
        currentCircle.setRotate(Double.parseDouble(params[18]));
        currentShape = null;
    }

    public void readEllipse(String[] params){
        ShapeEllipse currentEllipse = new ShapeEllipse();
        newShape(currentEllipse);
        currentEllipse.setCenterX(Double.parseDouble(params[2]));
        currentEllipse.setCenterY(Double.parseDouble(params[4]));
        currentEllipse.setRadiusX(Double.parseDouble(params[6]));
        currentEllipse.setRadiusY(Double.parseDouble(params[8]));
        currentEllipse.setOpacity(Double.parseDouble(params[10]));
        currentEllipse.setStrokeWidth(Double.parseDouble(params[12]));
        currentEllipse.setStroke(Color.valueOf(params[14]));
        currentEllipse.setFill(Color.valueOf(params[16]));
        currentEllipse.setLayer(Integer.parseInt(params[18]));
        currentEllipse.setRotate(Double.parseDouble(params[20]));
        currentShape = null;
    }

    public void readPolygon(String[] params){
        ShapePolygon currentPolygon = new ShapePolygon();
        newShape(currentPolygon);
        ArrayList<Double> points = new ArrayList<>();
        String pointsString = params[2];
        while(pointsString.length() != 0){
            System.out.println(pointsString.substring(0, pointsString.indexOf(',')));
            points.add(Double.parseDouble(pointsString.substring(0, pointsString.indexOf(','))));
            pointsString = pointsString.substring(pointsString.indexOf(',') + 1);
        }
        currentPolygon.getPoints().addAll(points);
        currentPolygon.setOpacity(Double.parseDouble(params[4]));
        currentPolygon.setStrokeWidth(Double.parseDouble(params[6]));
        currentPolygon.setStroke(Color.valueOf(params[8]));
        currentPolygon.setFill(Color.valueOf(params[10]));
        currentPolygon.setLayer(Integer.parseInt(params[12]));
        currentPolygon.setRotate(Double.parseDouble(params[14]));
    }
}

