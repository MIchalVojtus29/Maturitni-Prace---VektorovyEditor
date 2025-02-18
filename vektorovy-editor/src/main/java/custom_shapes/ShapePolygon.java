package custom_shapes;

import controller_components.ControllerScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;


public class ShapePolygon extends Polygon implements IShape {
    private int layer;
    private ControllerScene scene;
    private ArrayList<Double> coordinations;
    /*
     *   Inicializace samotného objektu, a získávání všech nutných parametrů např. jeho souřadnice, barva a u pologonu/mnohoúhelníku jsou koordinace
     *   neboli body jemiž je tvořen
     *
     * */
    public ShapePolygon(ControllerScene sc, ArrayList<Double> coords, Color stroke, Color fill, int strokeWidth, int l){
        scene = sc;
        coordinations = coords;
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
        getPoints().addAll(coords);
        layer = l;
    }

    public ShapePolygon(){}
/*

       gettry a settry pro módy kreslení
       Layer pro vrsty - získává nebo udává na jaké vrstvě je tvar nakreslen
       Start pro první vykreslení bodu - mnohoúhelník je vytvářen skrze body, jejichž souřadnice jsou pak porovnávány v GetCenter
       GetCenter - hledáme "nejvíce x" a "nejvíce y" bod u mnohoúhelníku, přičemž v cyklu porovnáváme poslední maximum s novým bodem
       dokud neprověříme všechny body

       Adjust pro znovu vykreslení a změnění velikosti tvaru
       Move - přeměňování x a y souřadnic (ox -> Originalx)
       Clone - získání všech parametrů tvaru a znovu nakreslení identického tvaru

    */

    @Override
    public void move(double ox, double oy) {
        double relX = ox - coordinations.get(0);
        double relY = oy - coordinations.get(1);

        boolean nextY = false;
        for (int i = 0; i < coordinations.size(); i++) {
            double coord = coordinations.get(i);
            if (!nextY) {
                coordinations.set(i, coord + relX);
            } else {
                coordinations.set(i, coord + relY);
            }
            nextY = !nextY;
        }

        getPoints().setAll(coordinations);


    }

    @Override
    public void adjust(double mx, double my) {
        System.out.println("ERROR");
    }

    @Override
    public double getCenterX() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for(int i = 0; i < getPoints().size() - 1; i += 2){
            double tmp = getPoints().get(i);
            if(tmp > maxX){
                maxX = tmp;
            }
            if(tmp < minX){
                minX = tmp;
            }
        }
        return (minX + maxX) / 2;
    }

    @Override
    public double getCenterY(){
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for(int i = 1; i < getPoints().size() - 1; i += 2){
            double tmp = getPoints().get(i);
            if(tmp > maxY){
                maxY = tmp;
            }
            if(tmp < minY){
                minY = tmp;
            }
        }
        return (minY + maxY) / 2;
    }

    public ShapePolygon clone(){
        System.out.println("PŘIDÁN");
        return new ShapePolygon(scene, coordinations, (Color) getStroke(), (Color) getFill(), (int) getStrokeWidth(), layer);
    }

    @Override
    public double getStartX() {
        return getPoints().get(0);
    }

    @Override
    public double getStartY() {
        return getPoints().get(1);
    }

    @Override
    public void setStartX(double x) {
        getPoints().set(0, x);
    }

    @Override
    public void setStartY(double y) {
        getPoints().set(1, y);
    }

    @Override
    public double getAdjustX() {
        return 0;
    }

    @Override
    public double getAdjustY() {
        return 0;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int i) {
        layer = i;
    }


}
