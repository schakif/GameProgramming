package edu.moravian.PathFinding;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


public class NavGraph implements TileBasedMap {
    private static final float sq2 = (float) Math.sqrt(2.0);
    private TiledMap map;
    
    @Override
    public int getWidthInTiles() {
        return map.getWidth();
    }

    @Override
    public int getHeightInTiles() {
	return map.getHeight();
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
        //
    }

    @Override
    public boolean blocked(PathFindingContext pfc, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getCost(PathFindingContext pfc, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
