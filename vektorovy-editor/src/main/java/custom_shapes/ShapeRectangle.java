package custom_shapes;

import controller_components.ControllerScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShapeRectangle extends Rectangle implements IShape{
    private ControllerScene scene;
    private int layer;
    private double originX, originY;
    /*
     *   Inicializace samotného objektu, a získávání všech nutných parametrů např. jeho souřadnice, barva atd.
     *
     * */
    public ShapeRectangle(ControllerScene sc, int l, double ox, double oy, double width, double height, Color stroke, Color fill, int strokeWidth){
        scene = sc;
        layer = l;
        setX(ox);
        setY(oy);
        originX = ox;
        originY = oy;
        setHeight(height);
        setWidth(width);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }

    public ShapeRectangle(){}

/*
   gettry a settry pro módy kreslení
   Layer pro vrsty - získává nebo udává na jaké vrstvě je tvar nakreslen
   Start pro první vykreslení tvaru
   Adjust pro znovu vykreslení a změnění velikosti tvaru - Porovnává vzdálenosti ox/oy (original x) a mx/my (moved x), opět pomocí absolutní hodnoty, vzdálenost
   nesmí být záporná

   Move - přeměňování x a y souřadnic (ox -> Originalx)
   Clone - získání všech parametrů tvaru a znovu nakreslení identického tvaru

*/

    @Override
    public void adjust(double mx, double my){
        if(mx < originX){
            setX(originX + (mx - originX));
            setWidth(Math.abs(getX() - originX));
        } else{
            setX(originX);
            setWidth(Math.abs(mx - originX));
        }
        if(my < originY){
            setY(originY + (my - originY));
            setHeight(Math.abs(getY() - originY));
        } else{
            setY(originY);
            setHeight(Math.abs(my - originY));
        }
        System.out.println("X: " + getX() + "\tY: " + getY() + "\nWidth: " + getWidth() + "\tHeight: " + getHeight() + "\n");
    }

    @Override
    public void move(double ox, double oy){
        originX = ox;
        originY = oy;
        adjust(ox + getWidth(), oy + getHeight());
        System.out.println("Posouvám");
    }

    public double getAdjustX(){
        if(originX > getX()){
            return originX - getWidth();
        } else{
            return originX + getWidth();
        }
    }

    public double getAdjustY(){
        if(originY > getY()){
            return originY - getHeight();
        } else{
            return originY + getHeight();
        }
    }

    public ShapeRectangle clone(){
        ShapeRectangle r = new ShapeRectangle(scene, layer, originX, originY, getWidth(), getHeight(), (Color) getStroke(), (Color) getFill(), (int) getStrokeWidth());
        r.setRotate(getRotate());
        r.setOpacity(getOpacity());
        return r;
    }

    public int getLayer(){
        return layer;
    }
    public void setLayer(int l){
        layer = l;
    }

    public double getCenterX(){
        return getX() + (getWidth() / 2);
    }

    public double getCenterY(){
        return getY() + (getHeight() / 2);
    }

    public double getStartX(){
        return getX();
    }

    public double getStartY(){
        return getY();
    }

    public void setStartX(double x){
        setX(x);
    }

    public void setStartY(double y){
        setY(y);
    }
}


