package edu.moravian.Entity;

import edu.moravian.Game.Game;
import edu.moravian.math.CoordinateTranslator;
import edu.moravian.math.Point2D;
import java.awt.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Bullet extends Entity {
    
    private Point2D nextNode, agentLoc;
    public boolean dead;
    private double velocity, distance, locX, locY, nextX, nextY;
    private Agent agent;
    private CoordinateTranslator coordTrans;
    private Point screenPoint;
    private int towerDamage;
    private int TOL = 10;
    
    public Bullet(Point2D towerLoc, Agent agent, int towerDamage) {
        this.location = towerLoc;
        dead = false;
        velocity = 500;
        this.agent = agent;
        coordTrans = Game.getInstance().getCT();
        this.towerDamage = towerDamage;
    }
    
    public void update(int delta) {
        agentLoc = agent.getLocation();
        if ((Math.abs(location.getX() - agentLoc.getX()) < TOL) && (Math.abs(location.getY() - agentLoc.getY()) < TOL)) {
//        if (location.equals(agentLoc)) {
            dead = true;
        }
        
        distance = (velocity*delta)/1000;
        
        nextX = agentLoc.getX();
        nextY = agentLoc.getY();
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
    
    public void killBullet() {
        agent.takeDamage(towerDamage);
        
    }
    
    public void render(Graphics g) {
        screenPoint = coordTrans.worldToScreen(location);
        g.setColor(Color.red);
        g.fillOval((float)screenPoint.getX(), (float)screenPoint.getY(), 10, 10);
        g.drawOval((float)screenPoint.getX(), (float)screenPoint.getY(), 10, 10);
    }
    
}
