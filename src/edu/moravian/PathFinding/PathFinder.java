package edu.moravian.PathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;

import edu.moravian.Game.GameMap;
import edu.moravian.math.Point2D;
import java.util.Collections;

public class PathFinder {

    private GameMap map;
    private ArrayList<Point2D> path;
    private Set<Edge> consideredEdges;

    public PathFinder(GameMap map) {
        this.map = map;
        consideredEdges = new HashSet<Edge>();
    }
	
    //A* here
    public void generatePath(Point2D start, final Point2D end) {
        Point2D u = new Point2D();

        Map<Point2D, Double> d = new HashMap<Point2D, Double>();
        Map<Point2D, Point2D> pred = new HashMap<Point2D, Point2D>();
	
        //set first node's dist. to 0
        d.put(start, 0.0);
        //PQ with comparator that will figure out ordering for the minimum distance to end
        PriorityQueue<Point2D> PQ = new PriorityQueue<Point2D>(100, new Comparator<Point2D>(){
        
        @Override
        public int compare(Point2D p1, Point2D p2) {
            double w1 = w(end, p1);
            double w2 = w(end, p2);
            return (int)(w1-w2);
        }
        });

        //add start node.point to Q
        PQ.add(start);
        while (!PQ.isEmpty()) {
            //Take the vertex in PQ with the minimum distance
            u = PQ.poll();
            //System.out.println("Node looking at: " + u);

            if (u.equals(end)) {
                constructPath(pred, start, u);
                return;
            }

            Iterable<Point2D> adjacent = map.getNeighbors(u);

            for (Point2D v : adjacent) {
                //Double dist = d.get(u) + w(u,v);
                if (!d.containsKey(v)) {
//                    d.put(v, Double.POSITIVE_INFINITY);
//                }
//                if (!isInPQ(PQ, v) || (dist < d.get(v)) ) { //FIXME check if distance is INFINITY or if the pred. is NULL
                    d.put(v, d.get(u) + w(u,v));

                    if (!pred.containsKey(v)) {
                        pred.put(v, u);
                    }
                    PQ.add(v);
                    consideredEdges.add(new Edge(u, v));
                }
            }
        }
    }

    private void constructPath(Map<Point2D,Point2D> pred, Point2D start, Point2D current){
        ArrayList<Point2D> totalPath = new ArrayList<Point2D>();
        totalPath.add(current);
        Point2D previous;
        while (current != start) {
            previous = pred.get(current);
            totalPath.add(previous);
            current = previous;
        }
        path = totalPath;
        Collections.reverse(path);
    }
    
    //Returns distance from point a to point b; equivalent to weight
    public double w(Point2D a, Point2D b) {
        return Math.sqrt( (a.getX()-b.getX()) * (a.getX()-b.getX()) + 
                    (a.getY()-b.getY()) * (a.getY()-b.getY()) );
    }
	
    public ArrayList<Point2D> getPath() {
        return path;
    }

    public Set<Edge> getConsideredEdges() {
        return consideredEdges;
    }
    
    public Boolean isInPQ(PriorityQueue<Point2D> frontier, Point2D node) {
        //Point2D[] array = (Point2D[]) frontier.toArray();
        for (Point2D currentPoint : frontier) {
            if (currentPoint.equals(node)) {
                return true;
            }
        }
        return false;
    }
}