/**
 * CS349 Winter 2014
 */
import java.awt.*;
import java.awt.geom.*;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit implements FruitInterface {
    private Area            fruitShape   = null;
    private Color           fillColor    = Color.RED;
    private Color           outlineColor = Color.BLACK;
    private AffineTransform transform    = new AffineTransform();
    private double          outlineWidth = 5;
    private double			speed        = -35 - Math.random()*10.0;
    private double 			xspeed 		 =  Math.random()*10.0;
    private boolean			splited 	 = false;
    //private int 			subCount;
    /**
     * A fruit is represented using any arbitrary geometric shape.
     */
    Fruit (Area fruitShape) {
        this.fruitShape = (Area)fruitShape.clone();
        //subCount = 0;
    }
    
    Fruit (Area fruitShape, boolean splited) {
        this.fruitShape = (Area)fruitShape.clone();
        this.splited = splited;
    }
    
    public double getSpeed()
    {
    	return speed;
    }
    
    public void setSpeed(double speed)
    {
    	this.speed = speed;
    }
    
    public double getXspeed()
    {
    	return xspeed;
    }
    
    public void setXspeed(double xspeed)
    {
    	this.xspeed = xspeed;
    }
    
    /**
     * The color used to paint the interior of the Fruit.
     */
    public Color getFillColor() {
        return fillColor;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public void setFillColor(Color color) {
        fillColor = color;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public Color getOutlineColor() {
        return outlineColor;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public void setOutlineColor(Color color) {
        outlineColor = color;
    }
    
    /**
     * Gets the width of the outline stroke used when painting.
     */
    public double getOutlineWidth() {
        return outlineWidth;
    }

    /**
     * Sets the width of the outline stroke used when painting.
     */
    public void setOutlineWidth(double newWidth) {
        outlineWidth = newWidth;
    }

    /**
     * Concatenates a rotation transform to the Fruit's affine transform
     */
    public void rotate(double theta) {
        transform.rotate(theta);
    }

    /**
     * Concatenates a scale transform to the Fruit's affine transform
     */
    public void scale(double x, double y) {
        transform.scale(x, y);
    }

    /**
     * Concatenates a translation transform to the Fruit's affine transform
     */
    public void translate(double tx, double ty) {
        transform.translate(tx, ty);
    }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public AffineTransform getTransform() {
        return (AffineTransform)transform.clone();
    }

    /**
     * Creates a transformed version of the fruit. Used for painting
     * and intersection testing.
     */
    public Area getTransformedShape() {
        return fruitShape.createTransformedArea(transform);
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Graphics2D g2) {
    	AffineTransform oldTransform = g2.getTransform();
    	g2.setTransform(this.transform);
    	g2.setColor(this.fillColor);
    	g2.fill(this.fruitShape);
    	g2.setColor(this.outlineColor);
    	g2.draw(this.fruitShape);
    	g2.setTransform(oldTransform);
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2) {
    	Line2D.Double line = new Line2D.Double(p1,p2); 
        return line.intersects(this.getTransformedShape().getBounds2D());
    }

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(Point2D p1) {
        return this.getTransformedShape().contains(p1);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(Point2D p1, Point2D p2) throws NoninvertibleTransformException {

        // TODO BEGIN CS349
        // Rotate shape to align slice with x-axis
        // Bisect shape above/below x-axis (look at intersection methods!)
        // TODO END CS349
    	Area topArea = null;
    	Area bottomArea = null;
    	double slope;
    	double radian;
    	double offset = 100;
    	
    	if( this.splited == false){
	    	Line2D.Double line = new Line2D.Double(p1,p2); 
	    	/*System.out.println("point1 .y = "+ line.getY1() );
	    	System.out.println("point2 .y = "+ line.getY2() );
	    	System.out.println("point1 .x = "+ line.getX1() );
	    	System.out.println("point2 .x = "+ line.getX2() );*/
	    	
	    	if(line.getY1() - line.getY2()!= 0 && line.getX1() - line.getX2()!=0){
		    	 slope = (line.getY1() - line.getY2()) / (line.getX1() - line.getX2());
		    	radian = Math.atan(slope);
		    	Rectangle2D area = this.fruitShape.getBounds2D();
				
		    	// Shift to center to zero
		    	AffineTransform transform = new AffineTransform();
		    	double shiftX = 0-area.getX()-area.getWidth()/2;
		    	double shiftY = 0-area.getY()-area.getHeight()/2;
		        transform.translate(shiftX, shiftY );
		    	
		        topArea = (Area) this.fruitShape.createTransformedArea(transform).clone();
		        bottomArea = (Area) this.fruitShape.createTransformedArea(transform).clone();
		        
		        // rotate
		        transform = new AffineTransform();
		        transform.rotate(0-radian);
		        topArea.transform(transform);
		        bottomArea.transform(transform);
		        
		        double x = topArea.getBounds2D().getX();
		        double y = topArea.getBounds2D().getY();
		        double width = topArea.getBounds2D().getWidth();
		        double height = topArea.getBounds2D().getHeight()/2;
		        
		        
		        Rectangle2D topMask = new Rectangle2D.Double(x, y, width, height);
		        Rectangle2D bottomMask = new Rectangle2D.Double(x, y+height,width,height);
		        
		        // mask off
		        topArea.subtract(new Area(bottomMask));
		        bottomArea.subtract(new Area(topMask));
		       
		        // rotate back
		        transform = new AffineTransform();
		        transform.rotate(radian);
		        topArea.transform(transform);
		        bottomArea.transform(transform);
		        
		        // shift, offset a bit
		        offset = 10;
		        if (slope > 0)
		        	offset = 0 - offset;
		        
		        transform = new AffineTransform();
		        transform.translate(0-shiftX-offset,0-shiftY-offset);
		        topArea.transform(transform);
		        transform = new AffineTransform();
		        transform.translate(0-shiftX+offset,0-shiftY+offset);
		        bottomArea.transform(transform);
		        
		        // shift
		        topArea.transform(this.transform);
		        bottomArea.transform(this.transform);
		        	
	    		}
	    	else {
	    		topArea = (Area) this.fruitShape.createTransformedArea(transform).clone();
	    		bottomArea = (Area) this.fruitShape.createTransformedArea(transform).clone();

	    	
	    		}
    		}
		    	
	    		//avoid to cut same fruit twice
	    		else if (this.splited == true){

	    			topArea = (Area) this.fruitShape.createTransformedArea(transform).clone();
	    	        bottomArea = (Area) this.fruitShape.createTransformedArea(transform).clone();
	    		}
	        if (topArea != null && bottomArea != null){
	            return new Fruit[] {new Fruit(topArea,true) ,new Fruit(bottomArea,true) };
	        }
	        return new Fruit[0];
     }
    public boolean isSplited (){
    	return this.splited;
    }
}
