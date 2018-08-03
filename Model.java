/**
 * CS349 Winter 2014

 */
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.math.*;
import java.awt.geom.Ellipse2D;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model {
	//
	private int conutDisplay;
	private int totalRemoveCount;
	private int deadCount;
	private final double g = -3;
	private final double a = -1;
	private boolean running;
    private Timer timer;
    private long timeElapsed;
    private long timeSeconds;
    private long hh;
    private long mm;
    private long ss;
    private View view;
    private long timeInterval;
    
  // Observer list
  private Vector<ModelListener> views = new Vector();

  // Fruit that we want to display
  private ArrayList<Fruit> shapes = new ArrayList();

  // Constructor
  Model() {
	conutDisplay = 0;
	totalRemoveCount = 0;
	timer = new Timer();
    timeElapsed = 0;
    timeInterval = 0;
    deadCount = 0;
    shapes.clear();
  }

  // MVC methods
  // These likely don't need to change, they're just an implementation of the
  // basic MVC methods to bind view and model together.
  public void addObserver(ModelListener view) {
    views.add(view);
  }

  public void notifyObservers() {
    for (ModelListener v : views) {
      v.update();
    }
  }

  // Model methods
  // You may need to add more methods here, depending on required functionality.
  // For instance, this sample makes to effort to discard fruit from the list.
  public void add(Fruit s) {
    shapes.add(s);
    //notifyObservers();
  }
  
  public void remove(Fruit s) {
	  shapes.remove(s);
  }

  public ArrayList<Fruit> getShapes() {
      return (ArrayList<Fruit>)shapes.clone();
  }
  public int getCount(){
	  return this.conutDisplay;
  }

  public long getTimeHH(){
	  return this.hh;
  }
  public long getTimeMM(){
	  return this.mm;
  }
  public long getTimeSS(){
	  return this.ss;
  }
  public void endGame(){
	  setRunning(false);
	  timer.cancel();
	 
	  //notifyObservers();
}
  
  public void startGame(){
	  setRunning(true);
	  resetAll();
      //startTime = System.currentTimeMillis();
	  
	  timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask(){
      	public void run()
      	{
      		timeElapsed = timeInterval*80;

        	  timeSeconds = timeElapsed / 1000;
        	  hh = timeSeconds /3600;
        	  mm = (timeSeconds - 3600 * hh) / 60;
        	  ss = timeSeconds - 3600 * hh - 60 * mm;  
      		travel();

      		timeInterval ++;
      		
      	  notifyObservers();

      	}
      }, 0, 80);
	  
  }
  
  public void setRunning(boolean running){
	  this.running = running;
  }
  
  public boolean getRunning(){
	  return this.running;
  }
  
  public void travel()
  {
	  
	  //pop-up random number of fruits
	  double popNum = Math.random()*3.0;
	 
//	  System.out.println("xxxxxx:" + popNum);

	  
  	if (this.getShapes().size() < popNum) {
  		
  		//while (popNum > 0){
  		//	popNum --;
	  		Fruit f = new Fruit(new Area(new Ellipse2D.Double(0, 50, 50, 50)));
	  		
	  		//this is a delay
	  		if (Math.random()<0.5){
	  	        f = new Fruit(new Area(new Rectangle2D.Double(0, 50, 50, 50)));
	  		}
	  		
	  		// try to make fruits apart
	  		while (true) {
	  			double x = 20+Math.random()*250;
	  			boolean pass = true;
	  			for (Fruit s : this.getShapes()) {
	  				if (Math.abs(x - s.getTransformedShape().getBounds2D().getX()) < 40)
	  				{
	  					pass = false;
	  					break;
	  				}
	  			}
	  			if (pass)
	  			{
	  				f.translate(x, 400);
	  				break;
	  			}
	  		}
		        this.add(f);
		        
		       // System.out.println("current subcount: " + f.getSplited() );
		      //  int temp = (f.getSplited()) ? 1 : 0;
		      //  countDisplay ++ ;
	  	}
	  	for (ModelListener v : views) {
	       if(v.getViewType() == "View"){
	    	   view = (View)v;
	       }
	      }
	  	Rectangle2D game = this.view.getBounds();
	
	  	for (Fruit s :this.getShapes()) {
	  		double speed = s.getSpeed();
	  		double xspeed = s.getXspeed();
	  		
	  		if (!game.contains(s.getTransformedShape().getBounds2D()) && speed > 0 ) {
	  			//System.out.println("Fruit died!");
	  			//System.out.println(game);
	  	    	//System.out.println(s.getTransformedShape().getBounds2D());
	  			if(s.isSplited() == false){
		  			this.increDead(this.getCount());
	  			}
	  			this.remove(s);
	  			continue;
	  		}if(deadCount >= 5){
	  			this.endGame();
	  		}
	  		
	  		
	  		double var = Math.random()*0.01;
	  		double sign = Math.random()*100;
	  		if(sign > 50  ){
	  			xspeed -=var;
	  		}else{
	  			xspeed +=var;
	  		}
	  		speed -= g;
	  		s.setSpeed(speed);
	  		s.setXspeed(xspeed);
	  		s.translate(0,speed);
	  		s.translate(xspeed,0);
	  	}
  //	}
  	 //endTime = System.currentTimeMillis();
  	  
  }
	  public void increDisplay(){
		  conutDisplay ++;
		//  notifyObservers();
	  }
	  
	  public void increDead(int display){
		  deadCount ++;
		  //notifyObservers();
	  }
	  
	  public int getDeadCount(){
		  return this.deadCount;
	  }
	  public void resetAll(){
		  conutDisplay = 0;
			totalRemoveCount = 0;
		    timeElapsed = 0;
		    timeInterval = 0;
		    deadCount = 0;
		    shapes.clear();
	  }
  }
