package edu.moravian.Entity;

import edu.moravian.Game.Game;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tower extends Entity {

    private Double range;
    private int power;
    private Agent currentTarget;
    private HashMap<Agent, Double> map;
    private Point tileLocation;
    private Image image;
    private int shotTimer;
    
    public Tower(Double range, int power, Point tileLocation) throws SlickException {
        this.range = range;
        this.power = power;
        // Convert the screen coordinates to tile coordinates
        this.tileLocation = new Point((int)tileLocation.x/32, (int)tileLocation.y/32);
        // Convert the screen coordinates to world coordinates
        this.location = Game.getInstance().getCT().screenToWorld(new Point(this.tileLocation.x*32+16, this.tileLocation.y*32+16));
        this.currentTarget = null;
        shotTimer = 1000;
        if(Game.getInstance().getTowerState() == "Laser") {
            image = new Image("res/towerSprite1.png");
        }
        else if(Game.getInstance().getTowerState() == "Spider") {
            image = new Image("res/towerSprite2.png");
        }
        else if(Game.getInstance().getTowerState() == "Drill") {
            image = new Image("res/towerSprite3.png");
        }
        //image = new Image("res/towerSprite1.png"); //tower sprite
    }
    
    public void render(Graphics g) {
        g.drawImage(image, (float)(tileLocation.x*32), (float)(tileLocation.y*32));
    }
    
    public void setTarget(ArrayList<Agent> agents) {

        map = new HashMap<Agent, Double>();

        if (agents.size() == 0) {
            this.currentTarget = null;
            return;
        }

        for (Agent a : agents) {
            // Tower's Location component - Agent's component
            double x = this.getLocation().getX() - a.getLocation().getX();
            double y = this.getLocation().getX() - a.getLocation().getY();
            double r = range*range;
            double d = x*x + y*y;

            // If it's in range, add it to the map
            if (r > d) {
                map.put(a, d);
            }
        }

        if (map.size() == 0) {
            this.currentTarget = null;
            return;
        }
        Double minValueInMap=(Collections.min(map.values()));
            
        for (Entry<Agent, Double> entry : map.entrySet()) {
            if (entry.getValue()==minValueInMap) {
                this.currentTarget = entry.getKey();
            }
        }

            // Get the first key, which should be the lowest
            //this.currentTarget = (Agent) sortedMap.keySet().toArray()[0];
    }
    
    public void attack(Agent target, int delta) {
        if (shotTimer >= 1000) {
            if (target == currentTarget) {
                Bullet b = new Bullet(location, target, power);
                Game.getInstance().addBullet(b);
//                target.takeDamage(power);
            }
            else {
                return;
            }
            shotTimer = 0;
        }
        else {
            shotTimer += delta;
        }
    }
	
    public Agent getCurrentTarget() {
        return currentTarget;
    }

    public Point getTileLocation() {
        return tileLocation;
    }
	
    public Boolean hasCurrentTarget() {
        if (currentTarget != null) {
            return true;
        }
        return false;
    }
    
//    public void towerSelection(String tower){
//        towerState = tower;
//    }
    
}
