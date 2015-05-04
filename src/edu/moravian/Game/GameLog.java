package edu.moravian.Game;

import edu.moravian.Game.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class GameLog  {
    private final Game game;  
    
    public GameLog(Game game) {
        this.game = game;
    }
    
    public void display(Graphics g) {
        g.setColor(Color.white);
//        g.drawString("Player's map location: (" + game.getEntity(0).getEntitySx() + "," + game.getEntity(0).getEntitySy() +")", 10, game.getScreenHeight() - 70);
        g.drawString("Lives Remaining: " + game.getLives(), 10, game.getScreenHeight() - 70);
        g.drawString("(" + game.getLives() + ")", 461, game.getScreenHeight() - 475);
    }
}