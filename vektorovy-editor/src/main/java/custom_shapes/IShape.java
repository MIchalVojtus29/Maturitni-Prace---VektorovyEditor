package custom_shapes;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface IShape{
/*
  Deklarace všech metod, které používáme při kreslení, editování atd. našich tvarů
 */
    void move(double ox, double oy);

    void adjust(double mx, double my);

    double getCenterX();
    double getCenterY();

    double getStartX();
    double getStartY();

    void setStartX(double x);
    void setStartY(double y);

    double getAdjustX();
    double getAdjustY();

    int getLayer();
    void setLayer(int i);

    void setOpacity(double o);
    double getOpacity();

    void setStrokeWidth(double w);
    double getStrokeWidth();

    void setStroke(Paint p);
    void setFill(Paint p);

    double getRotate();
    void setRotate(double rotation);

    Paint getStroke();
    Paint getFill();
    IShape clone();
}
