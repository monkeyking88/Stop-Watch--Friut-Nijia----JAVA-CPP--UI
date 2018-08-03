/**
 * CS349 Winter 2014
 */
package com.example.a4;
import android.graphics.*;
import android.util.Log;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit {
    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix transform = new Matrix();
    private int state = 0;
    private int height = 0;
    private double width = 0;
    private boolean sliceable = true;
    private int side = -1;


    /**
     * A fruit is represented as Path, typically populated 
     * by a series of points 
     */
    Fruit(float[] points) {
        init();
        this.path.reset();
        this.path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            this.path.lineTo(points[i], points[i + 1]);
        }
        this.path.moveTo(points[0], points[1]);
    }

    Fruit(Region region) {
        init();
        this.path = region.getBoundaryPath();
    }

    Fruit(Path path) {
        init();
        this.path = path;
    }

    private void init() {
        this.paint.setColor(Color.RED);
        this.paint.setStrokeWidth(5);
    }

    /**
     * The color used to paint the interior of the Fruit.
     */
    public int getFillColor() { return paint.getColor(); }
    public void setFillColor(int color) { paint.setColor(color); }

    /**
     * The width of the outline stroke used when painting.
     */
    public double getOutlineWidth() { return paint.getStrokeWidth(); }
    public void setOutlineWidth(float newWidth) { paint.setStrokeWidth(newWidth); }

    /**
     * Concatenates transforms to the Fruit's affine transform
     */
    public void rotate(float theta) { transform.postRotate(theta); }
    public void scale(float x, float y) { transform.postScale(x, y); }
    public void translate(float tx, float ty) { transform.postTranslate(tx, ty); }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public Matrix getTransform() { return transform; }

    /**
     * The path used to describe the fruit shape.
     */
    public Path getTransformedPath() {
        Path originalPath = new Path(path);
        Path transformedPath = new Path();
        originalPath.transform(transform, transformedPath);
        return transformedPath;
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Canvas canvas) {	
        // TODO BEGIN CS349
        // tell the shape to draw itself using the matrix and paint parameters
        // TODO END CS349
    	canvas.drawPath(getTransformedPath(), paint);
    	
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(PointF p1, PointF p2) {
    	if (p1.x==p2.x && p2.y == p1.y)
        {
        	return false;
        }
        if (this.getSliceable()==false){
        	return false;
        }
    	Path line = new Path();
        line.moveTo(p1.x, p1.y);
        line.lineTo(p2.x, p2.y);
        line.lineTo(p1.x+1, p1.y+1);
        
        Region bounds = new Region(0, 0, 480, 800);
        Region fRegion = new Region();
           
        fRegion.setPath(getTransformedPath(), bounds);
        Region lRegion = new Region();
        lRegion.setPath(line, bounds);
        return fRegion.op(lRegion, Region.Op.INTERSECT);
    }
    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(PointF p1) {
        Region region = new Region();
        boolean valid = region.setPath(getTransformedPath(), new Region());
        return valid && region.contains((int) p1.x, (int) p1.y);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(PointF p1, PointF p2) {
    	
        Path topPath = new Path();
        Path bottomPath = new Path();
        Path newPath = new Path(getTransformedPath());

        Region topClip = new Region(-480, -800, 480, 0);  //these are the bounds of the window (480, 800)
        Region bottomClip = new Region(-480, 0, 480, 800);

        Region topRegion = new Region();
        Region bottomRegion = new Region();
        
        //float xShift = p1.x;
        //float yShift = p1.y;
        double radians = -1*Math.atan2((p2.y-p1.y), (p2.x-p1.x));
        
        // Translate
        Matrix translate1 = new Matrix();
        translate1.postTranslate(-1*p1.x, -1*p1.y);
        newPath.transform(translate1, newPath);
        // Then, rotate

        Matrix rotate1 = new Matrix();
        rotate1.postRotate((float)Math.toDegrees(radians));
        newPath.transform(rotate1, newPath);
        
        topRegion.setPath(newPath, topClip);
        bottomRegion.setPath(newPath, bottomClip);
        
        // Reverse the process
        Matrix reverse1 = new Matrix();
        reverse1.postRotate((float)Math.toDegrees(-1*radians));
        topRegion.getBoundaryPath().transform(reverse1, topPath);
        bottomRegion.getBoundaryPath().transform(reverse1, bottomPath);


        Matrix last1 = new Matrix();
        last1.postTranslate(p1.x, p1.y);
        topPath.transform(last1, topPath);
        bottomPath.transform(last1, bottomPath);

    	
        if (topPath != null && bottomPath != null){
        	Fruit left = new Fruit(topPath);
        	Fruit right = new Fruit (bottomPath);
        	left.setSliceable(false);
        	right.setSliceable(false);
        	left.setSide(0);
        	right.setSide(1);
        	
            return new Fruit[] { left, right };
        }
        return new Fruit[0];
        
       
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setState (int s){
        this.state = s;
    }

    public int getState (){
        return state;
    }
    
    public void setWidth(double x){
        this.width = x;
    }
    
    public double getWidth(){
        return width;
    }
    
    public boolean getSliceable(){
    	return sliceable;
    }
    
    public void setSliceable(boolean b){
    	this.sliceable = b;
    }
    
    public void setSide(int s){
    	this.side = s;
    }
    
    public int getSide(){
    	return side;
    }
    
}

