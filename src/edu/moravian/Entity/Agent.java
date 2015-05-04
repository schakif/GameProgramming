package edu.moravian.Entity;

import edu.moravian.Game.Game;
import edu.moravian.View.SpriteRenderer;
import java.awt.Point;
import java.util.ArrayList;

import edu.moravian.math.Point2D;
import java.util.Iterator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Agent extends Entity {
    private final int maxHealth;
    private int health;
    private ArrayList<Point2D> path;
    private Iterator<Point2D> pathIterator;
    private Point2D nextNode;
    private int velocity;
    private double distance;
    private double locX, locY, nextX, nextY;
    private Boolean dead;
    private String currentState;
    private ArrayList<String> states;
    private Iterator<String> stateIterator;
    private SpriteRenderer ren;
    private int TOL = 10;
    private Sound exitSound;

    public Agent(int maxHealth) throws SlickException {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.location = location;
        distance = 0;
        velocity = 300;
        dead = false;
        path = new ArrayList<Point2D>();
        path.add(new Point2D(112, 816));
        path.add(new Point2D(144, 144));
        path.add(new Point2D(624, 144));
        path.add(new Point2D(624, 656));
        path.add(new Point2D(304, 656));
        path.add(new Point2D(304, 304));
        path.add(new Point2D(496, 304));
        path.add(new Point2D(496, 464));
        
        pathIterator = path.iterator();
        
        states = new ArrayList<String>();
        states.add("down");
        states.add("right");
        states.add("up");
        states.add("left");
        states.add("down");
        states.add("right");
        states.add("up");
        states.add("up");
        
        stateIterator = states.iterator();
        
        this.location = nextNode = pathIterator.next();
        locX = nextX = location.getX();
        locY = nextY = location.getY();
        
        ren = new SpriteRenderer();
    }
    
    public void update(int delta) throws SlickException {
        exitSound = new Sound("res/pokemonItem.wav");
        if ((Math.abs(nextNode.getX() - location.getX()) < TOL) && (Math.abs(nextNode.getY() - location.getY()) < TOL)) {
//        if (nextNode.equals(location)) {
            if(pathIterator.hasNext() && stateIterator.hasNext()) {
                nextNode = pathIterator.next();
                currentState = stateIterator.next();
            }
            else {
                dead = true;
                Game.getInstance().setLives(Game.getInstance().getLives()-1);
                exitSound.play();
            }
        }
        
        distance = (velocity*delta)/1000;
        
        nextX = nextNode.getX();
        nextY = nextNode.getY();
        locX = location.getX();
        locY = location.getY();
        
        if (locX < nextX) {
            locX += distance;
        }
        else if (locX > nextX) {
            locX -= distance;
        }
        
        if (locY < nextY) {
            locY += distance;
        }
        else if (locY > nextY) {
            locY -= distance;
        }
        location = new Point2D(locX, locY);
        
    }
    
    public void render(Graphics g) {
//        Point screenLocation = Game.getInstance().getCT().worldToScreen(location);
//        g.drawImage(image, (float)screenLocation.x, (float)screenLocation.y);
        ren.render(this, location);
    }

    public void move() {

    }

    public void takeDamage(int damage) {
        if ((health - damage) < 0) {
            health = 0;
        }
        else {
            this.health -= damage;
        }
    }

    public int getHealth() {
            return health;
    }

    public int getMaxHealth() {
            return maxHealth;
    }
    
    public Boolean isDead() {
        return dead;
    }
    
    public String getState() {
        return currentState;
    }
    
    public void setState(String state) {
        this.currentState = state;
    }
    
    public Point2D getLocation() {
        return location;
    }

}
