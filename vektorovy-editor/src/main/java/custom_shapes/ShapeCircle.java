package custom_shapes;

import controller_components.ControllerScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShapeCircle extends Circle implements IShape {
    private ControllerScene scene;
    private int layer;
/*
*   Inicializace samotného objektu, a získávání všech nutných parametrů např. jeho souřadnice, barva a u kruhu a poloměr
*
* */
    public ShapeCircle(ControllerScene sc, int l, double ox, double oy, double r, Color stroke, Color fill, int strokeWidth) {
        scene = sc;
        layer = l;
        setCenterX(ox);
        setCenterY(oy);
        setRadius(r);
        setStroke(stroke);
        setFill(fill);
        setStrokeWidth(strokeWidth);
    }

    public ShapeCircle() {
    }
/*
   gettry a settry pro módy kreslení
   Layer pro vrsty - získává nebo udává na jaké vrstvě je tvar nakreslen
   Start pro první vykreslení tvaru
   Adjust pro znovu vykreslení a změnění velikosti tvaru - kruh ještě  má navíc poloměr
   Move - přeměňování x a y souřadnic (ox -> Originalx)
   Clone - získání všech parametrů tvaru a znovu nakreslení identického tvaru

*/
    @Override
    public void adjust(double mx, double my) {
        double radius = Math.sqrt(Math.pow(getCenterX() - mx, 2) + Math.pow(getCenterY() - my, 2));
        setRadius(radius);
    }

    @Override
    public void move(double ox, double oy) {
        setCenterX(ox);
        setCenterY(oy);
    }

    public ShapeCircle clone(){
        ShapeCircle c = new ShapeCircle(scene, layer, getCenterX(), getCenterY(), getRadius(), (Color) getStroke(), (Color) getFill(), (int) getStrokeWidth());
        c.setRotate(getRotate());
        c.setOpacity(getOpacity());
        return c;
    }


    public int getLayer() {
        return layer;
    }

    public void setLayer(int l) {
        layer = l;
    }

    public double getStartX() {
        return getCenterX();
    }

    public double getStartY() {
        return getCenterY();
    }

    public void setStartX(double x) {
        setCenterX(x);
    }

    public void setStartY(double y) {
        setCenterY(y);
    }

    public double getAdjustX() {
        return getCenterX() + getRadius();
    }

    public double getAdjustY() {
        return getCenterY();
    }
}
