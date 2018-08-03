/**
 * CS349 Winter 2014
 */
import javax.swing.*;

import java.awt.*;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends JPanel implements ModelListener {
  private Model model;
  private JLabel title, score,time,dead;
  private int totalCount;
  private int curScore;
  private long timeElapsed;
  private int deadCount;
 
  private long hh;
  private long mm;
  private long ss;
  
  private String type;
  

  // Constructor requires model reference
  TitleView (Model model) {
    // register with model so that we get updates
    this.model = model;
    this.model.addObserver(this);
    this.curScore = model.getCount();
    this.deadCount =model.getDeadCount();

    
    type = "TitleView";
    // draw something
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.YELLOW);
    // You may want a better name for this game!
    title = new JLabel("Super Simplified  & Ugly Fruit Ninja for CS349 A3 De");
    score = new JLabel();
    time = new JLabel();
    dead = new JLabel();

    // use border layout so that we can position labels on the left and right
    this.setLayout(new BorderLayout());
    this.add(title, BorderLayout.NORTH);
    this.add(score, BorderLayout.CENTER);
    this.add(time, BorderLayout.WEST);
    this.add(dead,BorderLayout.EAST);

  }

  // Panel size
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500,35);
  }

  public Dimension getMaximumSize(){
	  return new Dimension(500,35);
  }
  // Update from model
  // This is ONLY really useful for testing that the view notifications work
  // You likely want something more meaningful here.
  @Override
  public void update() {
    //totalCount++;
    paint(getGraphics());
  }

  // Paint method
  @Override
  public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  setBorder(BorderFactory.createLineBorder(Color.black));
	  setBackground(Color.YELLOW);
	    hh = model.getTimeHH();
	    mm = model.getTimeMM();
	    ss = model.getTimeSS();
	    this.curScore = model.getCount();
	    this.deadCount =model.getDeadCount();

   
    score.setText("Count: " + curScore + "    ");
    time.setText("Time Elapsed: " + hh + " : " + mm + " : " + ss +"		");
    dead.setText("Falling Count :" + deadCount + "    ");
   // System.out.println("Maximum bounds: x,"+getPreferredSize()).getX() + ":" + +getPreferredSize()).getY());
    
  }
  public String getViewType(){
	  return this.type;
  }
}
