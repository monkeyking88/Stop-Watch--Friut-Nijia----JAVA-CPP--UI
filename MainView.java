/**
`` * CS349 Winter 2014

 */
package com.example.a4;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/*
 * View of the main game area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class MainView extends View implements Observer {
    private final Model model;
    private final MouseDrag drag = new MouseDrag();
    private Handler customHandler = new Handler();
    static int lives = 5;
    static int score = 0;
    private boolean running;

    // Constructor
    MainView(Context context, Model m) {
        super(context);

        // register this view with the model
        model = m;
        model.addObserver(this);

        startGame();
        
        // TODO END CS349

        // add controller
        // capture touch movement, and determine if we intersect a shape
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Log.d(getResources().getString(R.string.app_name), "Touch down");
                        drag.start(event.getX(), event.getY());
                        break;

                    case MotionEvent.ACTION_UP:
                        // Log.d(getResources().getString(R.string.app_name), "Touch release");
                        drag.stop(event.getX(), event.getY());
                        
                        if (!running){
                        	//running = true;
                        	
                        	startGame();
                        }
                        // find intersected shapes
                        Iterator<Fruit> i = model.getShapes().iterator();
                        while(i.hasNext()) {
                            Fruit s = i.next();
                            if (s.intersects(drag.getStart(), drag.getEnd())) {
                            	//s.setFillColor(Color.GREEN);
                                try {
                                    Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());

                                    // TODO BEGIN CS349
                                    // you may want to place the fruit more carefully than this
                                    newFruits[0].translate(0, -20);
                                    newFruits[1].translate(0, +20);
                                    // TODO END CS349
                                    model.add(newFruits[0]);
                                    model.add(newFruits[1]);

                                } catch (Exception ex) {
                                    Log.e("fruit_ninja", "Error: " + ex.getMessage());
                                }
                                model.remove(s);
                                model.setScore(++score);
                                model.notifyObservers();
                            } else {
                               // s.setFillColor(Color.RED);
                            }
                            invalidate();
                        }
                        break;
                }
                return true;
            }
        });
    }
    
    public void startGame(){
    	running = true;
    	customHandler.postDelayed(updateTimerThread, 50);
    	model.setScore(0);
		model.setLives(5);
		lives = 5;
		score = 0;
        //model.setEnd(0);
        model.notifyObservers();
        invalidate();

    }
    
    private Runnable updateTimerThread = new Runnable() {
    	public int randomIndex;
    	Random generator = new Random();
    	{randomIndex = generator.nextInt(40);}
    	
        public void run() {
        	if (running){
        		
            		for(Fruit s: model.getShapes()){
            	        RectF bounds = new RectF();
            	        s.getTransformedPath().computeBounds(bounds, true);
            			float x = bounds.left;
            			float y = bounds.top;
            			
            			if (!s.getSliceable() &&s.getSide()==0){
                        	s.translate(0,25);
                        	if (y>=580){
                        		model.remove(s);
                        	}
                        	
                        }
                        else if (!s.getSliceable()&&s.getSide()==1){
                        	s.translate(0, 25);
                        	if (y>=580){
                        		model.remove(s);
                        	}
                        }
            			
                        else{
                        	if(s.getState()==0){
                				s.translate((float)s.getWidth(), -5);
                                y-=5;
                                if(y<=s.getHeight()){
                                    s.setState(2);
                                }
                			}
                			else if(s.getState()==1){
                				s.translate((float)s.getWidth()*-1, -5);
                				y-=5;
                				if(y<=s.getHeight()){
                                    s.setState(3);
                                }
                			}

                			else if(s.getState()==2){
                                s.translate((float)s.getWidth(), 5);
                                y+=5;
                                if(y>=580){
                                    model.remove(s);
                                    s.setState(4);
                                    model.setLives(--lives);
                                    if (lives <0){
                                		
                                		//reset();
                                		model.endGame();
                                		running = false;
                                		invalidate();
                                	}
                                }
                            } 
                			else if(s.getState()==3){
                                s.translate((float)s.getWidth()*-1, 5);
                                y+=5;
                                if(y>=580){
                                	model.remove(s);
                                    s.setState(4);
                                    model.setLives(--lives);
                                    if (lives <0){
                                		
                                		//reset();
                                		model.endGame();
                                		running = false;
                                		invalidate();
                                	}
                                }
                            }
                        }
            	
        	}
            		if(randomIndex==0){
                        createFruit();
                        randomIndex = generator.nextInt(40);
                   }
                   else{
                        randomIndex--;
                   }
           		
                   invalidate();
                   customHandler.postDelayed(this, 10);
    		}
    		
        }
        
        
        private void createFruit(){

    		Random fruitGen = new Random();
            int x = fruitGen.nextInt(480);
            int height = fruitGen.nextInt(250);
            Fruit f;
            int shape = fruitGen.nextInt(4);
            if(shape==0)
            	f = new Fruit(new float[] {0, 20, 20, 0, 40, 0, 60, 20, 60, 40, 40, 60, 20, 60, 0, 40});
            else if(shape==1)
            	f = new Fruit(new float[] {0, 20, 20, 0, 40, 0, 60, 20, 60, 40, 40, 60, 20, 60});
            else if (shape==2){
            	f = new Fruit(new float[] {0, 20, 20, 0, 40, 0, 60, 20, 60, 40, 40, 60});
            }
            else{
            	f = new Fruit(new float[] {0, 20, 20, 0, 40, 0, 60, 20, 60, 40});
            }
    		f.setHeight(height);
            f.setFillColor(Color.argb(255, fruitGen.nextInt(230)+40, 
            		fruitGen.nextInt(230)+40, fruitGen.nextInt(230)+40));
            f.translate(x, 600);
            
            if(x<240){
            	f.setState(0);
            	f.setWidth((240-x)/((height+400)/5));
            }
            else{
            	f.setState(1);
                f.setWidth((x-240)/((height+400)/5));
            }
            model.add(f);
    	}
    };

    // inner class to track mouse drag
    // a better solution *might* be to dynamically track touch movement
    // in the controller above
    class MouseDrag {
        private float startx, starty;
        private float endx, endy;

        protected PointF getStart() { return new PointF(startx, starty); }
        protected PointF getEnd() { return new PointF(endx, endy); }

        protected void start(float x, float y) {
            this.startx = x;
            this.starty = y;
        }

        protected void stop(float x, float y) {
            this.endx = x;
            this.endy = y;
        }
    }

    
   
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background
        setBackgroundColor(Color.WHITE);

        // draw all pieces of fruit
        for (Fruit s : model.getShapes()) {
            s.draw(canvas);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        invalidate();
    }
}

