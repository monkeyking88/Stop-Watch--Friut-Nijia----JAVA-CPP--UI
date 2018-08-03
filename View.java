/**
 * CS349 Winter 2014
 */
import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
/*
 * View of the main play area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class View extends JPanel implements ModelListener {
    private Model model;
    private final MouseDrag drag;
    //private final double g = -3;
    private String type;
    private JButton sbutton;
    private JButton ebutton;
    private JLabel  gameOver;
    
    // Constructor
    View (Model m) {
        model = m;
        model.addObserver(this);
        

        setBackground(Color.WHITE);
        
        this.type = "View";

        // drag represents the last drag performed, which we will need to calculate the angle of the slice
        drag = new MouseDrag();
        // add mouse listener
        addMouseListener(mouseListener);
        
        this.setLayout(null);
        sbutton = new JButton("Start Game");
        this.add(sbutton);
        sbutton.setVisible(true);
        sbutton.setBounds(150, 150, 200, 50);
        sbutton.addActionListener(new ActionListener(){
        	 public void actionPerformed(ActionEvent e) {
                 model.startGame();
                 sbutton.setVisible(false);
        	 }
        });
    }  
    
    // Update fired from model
    @Override
    public void update() {
        
        //System.out.println("is running ?" + model.getRunning());
        if (model.getRunning() == false){
        	this.setLayout(null);
	        	if(gameOver == null && ebutton == null ){
		        	gameOver = new JLabel("You Lose!!!Wanna take a new try?");
		        	this.add(gameOver);
		        	gameOver.setVisible(true);
		        	ebutton = new JButton("Restart Game");
		        	this.add(ebutton);
		        	ebutton.setVisible(true);
		        	ebutton.setBounds(150,100,200,50);
		        	gameOver.setBounds(150,200,300, 50);
		        	ebutton.addActionListener(new ActionListener(){
		           	 public void actionPerformed(ActionEvent e) {
		           		//ebutton.setVisible(false);
		                //gameOver.setVisible(false);
		           		remove(ebutton);
		           		remove(gameOver);
		                model.startGame(); 
		           		//repaint();
		           	 }
		           });	
		        }
	        }else{
	        	ebutton = null;
	        	gameOver = null;

	        }
        this.repaint();
        }
  

    // Panel size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,400);
    }

    // Paint this panel
    @Override
    public void paintComponent(Graphics g) {
        //this.setBounds(0, 35, 500, 400);
        super.paintComponent(g);
        this.setMaximumSize(getPreferredSize());
        Graphics2D g2 = (Graphics2D) g;

        // draw all pieces of fruit
        // note that fruit is responsible for figuring out where and how to draw itself
        for (Fruit s : model.getShapes()) {
            s.draw(g2);
        }
       
        //damage(Rectangle (0,35,500,400));
        
    }

    // Mouse handler
    // This does most of the work: capturing mouse movement, and determining if we intersect a shape
    // Fruit is responsible for determining if it's been sliced and drawing itself, but we still
    // need to figure out what fruit we've intersected.
    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            drag.start(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            drag.stop(e.getPoint());

            // you could do something like this to draw a line for testing
            // not a perfect implementation, but works for 99% of the angles drawn
            
            //int[] x = { (int) drag.getStart().getX(), (int) drag.getEnd().getX(), (int) drag.getEnd().getX(), (int) drag.getStart().getX()};
            //int[] y = { (int) drag.getStart().getY()-1, (int) drag.getEnd().getY()-1, (int) drag.getEnd().getY()+1, (int) drag.getStart().getY()+1};
            //model.add(new Fruit(new Area(new Polygon(x, y, x.length))));

            // find intersected shapes

            for (Fruit s : model.getShapes()) {
                if (s.intersects(drag.getStart(), drag.getEnd())) {
                	//System.out.println("intersect:");
                    s.setFillColor(Color.RED);
                    try {
                    	if(drag.getStart().getX() != drag.getEnd().getX() && drag.getEnd().getY() != drag.getStart().getY()){
                    		Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());
	                    	
	                        if(newFruits.length == 2){
	                        	model.increDisplay();
	                        }
	                     
	                        // add offset so we can see them split - this is used for demo purposes only!
	                        // you should change so that new pieces appear close to the same position as the original piece
	                        for (Fruit f : newFruits) {
	                            f.setSpeed(s.getSpeed());
	                        	model.add(f);
	                            
	                        }
	                        model.remove(s);
                    	 }
                    	} catch (Exception ex) {
	                        System.err.println("Caught error: " + ex.getMessage());
	                    }
	                } 
                else {
                    //s.setFillColor(Color.BLUE);
                }
            }
        }
    };

    /*
     * Track starting and ending positions for the drag operation
     * Needed to calculate angle of the slice
     */
    private class MouseDrag {
        private Point2D start;
        private Point2D end;

        MouseDrag() { }

        protected void start(Point2D start) { this.start = start; }
        protected void stop(Point2D end) { this.end = end; }

        protected Point2D getStart() { return start; }
        protected Point2D getEnd() { return end; }

    }
    public String getViewType(){
  	  return this.type;
    }
}
