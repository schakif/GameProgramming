package edu.moravian.math;
import java.awt.Point;

public class CoordinateTranslator {
	
	private double worldHeight;
	private double worldWidth ;
	private int screenHeight ;
	private int screenWidth ;
	private double viewedWllx ;
	private double viewedWlly ;
	
	public CoordinateTranslator(double worldWidth, double worldHeight, double viewedWllx, double viewedWlly, int screenWidth, int screenHeight) {
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.viewedWllx = viewedWllx;
		this.viewedWlly = viewedWlly;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;	
	}
	
	public Point2D screenToWorld(int sx, int sy) {
		Point2D worldPoint = new Point2D();
		
		worldPoint.setX(((worldWidth/screenWidth)*sx) + viewedWllx);
		worldPoint.setY(-((worldHeight*sy)/screenHeight) + viewedWlly + worldHeight);
		
		
		return worldPoint;
	}
	
	public Point2D screenToWorld(Point screenPoint ) {
		Point2D worldPoint = new Point2D();
		
		worldPoint.setX(((worldWidth*screenPoint.getX())/screenWidth) + viewedWllx);
		worldPoint.setY(-((worldHeight*screenPoint.getY())/screenHeight) - viewedWlly + worldHeight);
		
		return worldPoint;
	}
	
	public Point worldToScreen(double wx, double wy) {
		Point screenPoint = new Point();
		
		int sx = (int) ((wx - viewedWllx) * (screenWidth/worldWidth)); 
		int sy = (int) ((worldHeight - wy - viewedWlly) * (screenHeight/worldHeight));
		
		screenPoint.setLocation(sx, sy);
		
		return screenPoint;
	}
	
	public Point worldToScreen(Point2D worldPoint) {
		Point screenPoint  = new Point();
		
		int sx = (int) ((worldPoint.getX() - viewedWllx) * (screenWidth/worldWidth)); 
		int sy = (int) ((worldHeight - worldPoint.getY() + viewedWlly) * (screenHeight/worldHeight));
		
		screenPoint.setLocation(sx, sy);
		
		return screenPoint;
	}
	
	public double getWorldWidth() {
		return worldWidth;
	}
	
	public double getWorldHeight() {
		return worldHeight;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
}
