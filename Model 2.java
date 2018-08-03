/**
 * CS349 Winter 2014
 */
package com.example.a4;

import android.util.Log;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model extends Observable {
    // List of fruit that we want to display
    private ArrayList<Fruit> shapes = new ArrayList<Fruit>();
    private Vector<Observer> views = new Vector();
    private int lives = 5;
    private int score = 0;
    private long startTime;
    private long endTime;
    private boolean isRunning = true;

    // Constructor
    Model() {
        shapes.clear();
    }

    // Model methods
    // You may need to add more methods here, depending on required functionality.
    // For instance, this sample makes to effort to discard fruit from the list.
    public void add(Fruit s) {
        shapes.add(s);
        setChanged();
        notifyObservers();
    }
    public void remove(Fruit s){
 	   shapes.remove(s);
 	   setChanged();
 	   notifyObservers();
   }


    public ArrayList<Fruit> getShapes() {
        return (ArrayList<Fruit>) shapes.clone();
    }

    // MVC methods
    // Basic MVC methods to bind view and model together.
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    // a helper to make it easier to initialize all observers
    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        setChanged();
        notifyObservers();
    }
    
    public void setLives(int num){
  	  this.lives = num;
  	  setChanged();
  	  notifyObservers();
  	  //views.get(0).invalidate();
    }
    
    public int getLives(){
  	  return lives;
    }
    
    public void setScore(int num){
  	  this.score = num;
  	 // views.get(0).update();
  	notifyObservers();
    }
    
    public int getScore(){
  	  return score;
    }
    
    public void setStart(long s){
    	this.startTime = s;
    }
    
    public long getStart(){
    	return startTime;
    }
    
    public void setEnd(long s){
    	this.endTime = s;
    }
    
    public long getEnd(){
    	return endTime;
    }
    public void endGame(){
    	shapes.clear();
    	setChanged();
    	 notifyObservers();
    }
    
    /*public void setRun (boolean s){
  	  this.isRunning = s;
    }
    
    public boolean getRun (){
  	  return isRunning;
    }*/
}

