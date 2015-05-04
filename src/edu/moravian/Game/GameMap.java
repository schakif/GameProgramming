package edu.moravian.Game;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import edu.moravian.math.CoordinateTranslator;
import edu.moravian.math.Point2D;

public class GameMap {

	private double x, y;
	private int tx, ty; // Distance from the Upper Left Corner to the X and Y axis of the tile it is currently in
	private int plotX, plotY; // How far in the negative direcoordTranion (up and to the left) from the corner point to reach the tile's axes
	private int sx, sy; // What tile do we start rendering at
	private int dsx, dsy; // Number of Tiles to render from upper left corner
	private final int tileWidth;
	private final int tileHeight;
	private final CoordinateTranslator coordTran;
	private final TiledMap map;
	
	public GameMap(TiledMap map) throws SlickException {
            coordTran = Game.getInstance().getCT();
            this.map = map;
            tileWidth = map.getTileWidth();
            tileHeight = map.getTileHeight();
	}
	
        public Iterable<Point2D> getNeighbors(Point2D p) {
            ArrayList<Point2D> ret = new ArrayList<Point2D>();
            int x = (int)p.getX();
            int y = (int)p.getY();
            int objectLayer = map.getLayerIndex("Objects");

            //search a specific (x,y) position to determine if there is an object present,
            //	if there is NOT then add it to the list of neighbors.
            //for each neighbor v of p

            for(int i = x - 1; i < x + 2; i++) {
                for(int j = y - 1; j < y + 2; j++) {
                    if (i >= 0 && i < map.getWidth()) {
                        if (j >= 0 && j < map.getHeight()) {
                            if (i!=x || j!=y) {
                                if (map.getTileId(i, j, objectLayer) == 0) { //causes to fail // NEED TO PASS p THROUGH CT TO GET THE CORRECT TILE COORDINATE
                                    ret.add(new Point2D(i,j));
                                }
                            }
                        }
                    }
                }
            }

            return ret;
	}
        
	public void checkPlayerWrap(double x, double y) {
            this.x = x;
            this.y = y;
            Point screenUpperLeft = coordTran.worldToScreen(x, y);
            sx = screenUpperLeft.x;
            sy = screenUpperLeft.y - coordTran.getScreenHeight();
            tx = sx % tileWidth;
            ty = sy % tileHeight;
            plotX = -1 * tx;
            plotY = -1 * ty;
            dsx = sx/tileWidth;
            dsy = sy/tileHeight;
		
            if(sx < -coordTran.getScreenWidth()/2) {
                this.x = ((coordTran.getWorldWidth()*32 - coordTran.getScreenWidth()/2)*coordTran.getWorldWidth())/(double)coordTran.getScreenWidth();
            }
            if(sx > coordTran.getWorldWidth()*32 - coordTran.getScreenWidth()/2) {
                this.x = -(double)coordTran.getWorldWidth()/2;
            }
            if(sy < -coordTran.getScreenHeight()/2) {
                this.y = (coordTran.getWorldHeight()*32 - coordTran.getScreenHeight()/2)*coordTran.getWorldHeight()/-(double)coordTran.getScreenHeight();
            }
            if(sy > coordTran.getWorldHeight()*32 - coordTran.getScreenHeight()/2) {
                this.y = (coordTran.getScreenHeight()/2*coordTran.getWorldHeight())/coordTran.getScreenHeight();
            }
        }
	
	public void render() {
            map.render((int) plotX, (int) plotY, (int) dsx,(int) dsy, coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            if(sx > map.getWidth()*32 - coordTran.getScreenWidth()) {
                map.render((int) plotX, (int) plotY, (int) (dsx - map.getWidth())%(map.getWidth()), (int) dsy, coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sx < 0) {
                map.render((int) plotX - 32, (int) plotY, (int) (dsx + map.getWidth() - 1)%(map.getWidth()), (int) dsy, coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sy < 0) {
                map.render((int) plotX, (int) plotY - 32, (int) dsx, (int) (dsy + map.getHeight() - 1)%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sy > map.getHeight()*32 - coordTran.getScreenHeight()) {
                map.render((int) plotX, (int) plotY, (int) dsx, (int) (dsy - map.getHeight())%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sx < 0 && sy < 0) {
                map.render((int) plotX - 32, (int) plotY - 32, (int) (dsx + map.getWidth() - 1)%(map.getWidth()), (int) (dsy + map.getHeight() - 1)%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sx < 0 && sy > map.getHeight()*32 - coordTran.getScreenHeight()) {
                map.render((int) plotX - 32, (int) plotY, (int) (dsx + map.getWidth() - 1)%(map.getWidth()), (int) (dsy - map.getHeight())%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sx > map.getWidth()*32 - coordTran.getScreenWidth() && sy < 0) {
                map.render((int) plotX, (int) plotY - 32, (int) (dsx - map.getWidth())%(map.getWidth()), (int) (dsy + map.getHeight() - 1)%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
            if(sx > map.getWidth()*32 - coordTran.getScreenWidth() && sy > map.getHeight()*32 - coordTran.getScreenHeight()) {
                map.render((int) plotX, (int) plotY, (int) (dsx - map.getWidth())%(map.getWidth()), (int) (dsy - map.getHeight())%(map.getHeight()), coordTran.getScreenWidth()/32 + 2, coordTran.getScreenHeight()/32 + 2);
            }
        }

	public double getX() {
            return x;
	}
	
	public double getY() {
            return y;
	}
	
	public int getSx() {
            return sx;
	}
	
	public int getSy() {
            return sy;
	}
	
	public double getWidth() {
		return map.getWidth();
	}
	
	public double getHeight() {
		return map.getHeight();
	}
        
        public TiledMap getTMap() {
            return map;
        }
        
        public Boolean isTileOpen(double x, double y) {
            int xTile = (int)x/32;
            int yTile = (int)y/32;
            int objectLayer = map.getLayerIndex("Objects");
            
            if (map.getTileId(xTile, yTile, objectLayer) == 0) {
                return true;
            }
            return false;
        }
        
        public Point getUpperLeft() {
            return (new Point(coordTran.worldToScreen(x, y)));
        }

}
