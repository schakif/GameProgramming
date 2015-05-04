package edu.moravian.Game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class TowerDefense {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Game play = Game.getInstance();
            AppGameContainer agc = new AppGameContainer(play, play.getScreenWidth(), play.getScreenHeight(), false);
            agc.setTargetFrameRate(200);
            agc.start();
        }
        catch (SlickException ex)
        {
            System.out.println("Error starting game");
        }
    }
    
}
