package custom_shapes;

import controller_components.ControllerScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class ShapeEllipse extends Ellipse implements IShape {
    private int layer;
    private ControllerScene scene;

    /*
     *   Inicializace samotného objektu, a získávání všech nutných parametrů např. jeho souřadnice, barva a u elipsy jsou poloměry dva!
     *
     * */
    public ShapeEllipse(ControllerScene sc, int l, double ox, double oy, double rx, double ry, Color stroke, Color fill, int strokeWidth) {
        scene = sc;
        layer = l;
        setCenterX(ox);
        setCenterY(oy);
        setRadiusX(rx);
        setRadiusY(ry);
        setStroke(stroke);
        setFill(fill);
        setStrokeWidth(strokeWidth);
    }

    public ShapeEllipse(){}
    /*
       gettry a settry pro módy kreslení
       Layer pro vrsty - získává nebo udává na jaké vrstvě je tvar nakreslen
       Start pro první vykreslení tvaru
       Adjust pro znovu vykreslení a změnění velikosti tvaru - elipsa má ještě navíc dva poloměry
       Move - přeměňování x a y souřadnic (ox -> Originalx)
       Clone - získání všech parametrů tvaru a znovu nakreslení identického tvaru

    */
    @Override
    public void move(double ox, double oy) {
        setCenterX(ox);
        setCenterY(oy);
    }

    @Override
    public void adjust(double mx, double my) {
        setRadiusX(Math.abs(getCenterX() - mx));
        setRadiusY(Math.abs(getCenterY() - my));
    }

    public ShapeEllipse clone(){
        ShapeEllipse e = new ShapeEllipse(scene, layer, getCenterX(), getCenterY(), getRadiusX(), getRadiusY(), (Color) getStroke(), (Color) getFill(), (int) getStrokeWidth());
        e.setRotate(getRotate());
        e.setOpacity(getOpacity());
        return e;
    }

    @Override
    public double getStartX() {
        return getCenterX();
    }

    @Override
    public double getStartY() {
        return getCenterY();
    }

    @Override
    public void setStartX(double x) {
        setCenterX(x);
    }

    @Override
    public void setStartY(double y) {
        setCenterY(y);
    }

    @Override
    public double getAdjustX() {
        return getCenterX() + getRadiusX();
    }

    @Override
    public double getAdjustY() {
        return getCenterY() + getRadiusY();
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
