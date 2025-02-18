package custom_shapes;

/*
public class MyScribble extends Scribble implements IShape {
    private VectorScene scene;
    private int layer;


    private GraphicsContext graphicsContext;
    private ColorPicker cpLine;

    private double startX, startY, endX, endY;

    private ArrayList<Double> xValues = new ArrayList<>();
    private ArrayList<Double> yValues = new ArrayList<>();

    public MyScribble(VectorScene sc, int l, double ox, double oy, Color stroke, int strokeWidth){

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
    public MyScribble(){}


    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker){
        cpLine = colorPicker;
    }

    public void setStartPoint(double startX, double startY){
        this.startX = startX;
        this.startY= startY;
    }

    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void addPoint(double x, double y){
        xValues.add(x);
        yValues.add(y);
    }

    public boolean containsPoint(Point2D point){
        for(int i = 0; i < xValues.size(); i++){
            if(xValues.get(i) == point.getX() && yValues.get(i) == point.getY()){
                return true;
            }
        }

        return false;
    }

    public double getStartX(){
        return startX;
    }

    public double getStartY(){
        return startY;
    }

    public double getEndX(){
        return endX;
    }

    public double getEndY(){
        return endY;
    }

    public double[] getAllXValues(){
        double[] xVals = new double[xValues.size()];

        for(int i = 0; i < xValues.size(); i++){
            xVals[i] = xValues.get(i);
        }

        return xVals;
    }

    public double[] getAllYValues(){
        double[] yVals = new double[yValues.size()];

        for(int i = 0; i < yValues.size(); i++){
            yVals[i] = yValues.get(i);
        }

        return yVals;
    }

    public ColorPicker getColor(){
        return cpLine;
    }

    public void draw(){
        graphicsContext.setStroke(getColor().getValue());
        graphicsContext.beginPath();
        graphicsContext.lineTo(getStartX(), getStartY());

        for(int i = 0; i < xValues.size(); i++){
            graphicsContext.lineTo(xValues.get(i), yValues.get(i));
            graphicsContext.stroke();
        }

        graphicsContext.lineTo(getEndX(), getEndY());
        graphicsContext.stroke();

        graphicsContext.closePath();
    }


 // Getters
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

}*/