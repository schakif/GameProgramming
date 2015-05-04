package edu.moravian.Game;

public class Collision {
	public static boolean checkCollision(int x1, int y1, int x2, int y2) {
		return Math.pow(10.0+10.0, 2) > (Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}

}
