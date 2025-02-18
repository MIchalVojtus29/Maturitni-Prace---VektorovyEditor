package custom_shapes;

import controller_components.ControllerScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ShapeLine extends Line implements IShape{
    private ControllerScene scene;
    private int layer;
    /*
     *   Inicializace samotného objektu, a získávání všech nutných parametrů např. jeho souřadnice, barva a čáry jsou dva body - začátek a konec
     *   o co se staraí metody Start a End
     *
     * */
    public ShapeLine(ControllerScene sc, int l, double ox, double oy, Color stroke, int strokeWidth){
        scene = sc;
        layer = l;
        setStartX(ox);
        setStartY(oy);
        setEndX(ox);
        setEndY(oy);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
        System.out.println(scene.getHeight());
    }
    public ShapeLine(){}

  /*
       gettry a settry pro módy kreslení
       Layer pro vrsty - získává nebo udává na jaké vrstvě je tvar nakreslen
       Start pro první vykreslení tvaru
       Adjust pro znovu vykreslení a změnění velikosti čáry
       Move - přeměňování x a y souřadnic (ox -> Originalx)
       Clone - získání všech parametrů tvaru a znovu nakreslení identické čáry

    */

    @Override
    public void adjust(double mx, double my) {
        setEndX(mx);
        setEndY(my);
    }

    @Override
    public void move(double ox, double oy){
        double diffX = getStartX() - getEndX();
        double diffY = getStartY() - getEndY();
        setStartX(ox);
        setStartY(oy);
        setEndX(ox - diffX);
        setEndY(oy - diffY);
    }

    public ShapeLine clone(){
        ShapeLine l = new ShapeLine(scene, layer, getStartX(), getStartY(), (Color) getStroke(), (int) getStrokeWidth());
        l.setEndX(getEndX());
        l.setEndY(getEndY());
        l.setOpacity(getOpacity());
        l.setRotate(getRotate());
        return l;
    }


    public int getLayer(){
        return layer;
    }
    public void setLayer(int l){
        layer = l;
    }
    
    public double getCenterX(){
        double diff = Math.abs(getStartX() - getEndX()) / 2;
        return (getStartX() > getEndX()) ? getEndX() + diff : getStartX() + diff;
    }
    
    public double getCenterY(){
        double diff = Math.abs(getStartY() - getEndY()) / 2 ;
        return (getStartY() > getEndY()) ? getEndY() + diff : getStartY() + diff;
    }

    public double getAdjustX(){
        return getEndX();
    }

    public double getAdjustY(){
        return getEndY();
    }
}
