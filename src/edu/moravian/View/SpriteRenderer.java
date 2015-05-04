package edu.moravian.View;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import edu.moravian.Entity.Agent;
import edu.moravian.Entity.Bullet;
import edu.moravian.Entity.Entity;
import edu.moravian.Game.Game;
import edu.moravian.Game.GameMap;
import edu.moravian.math.CoordinateTranslator;
import edu.moravian.math.Point2D;

public class SpriteRenderer {
	
    private Image spriteSheetImage1 = null;
    private Image spriteSheetImage2 = null;
    private SpriteSheet spriteSheet1, spriteSheet2;
    private Animation charizardUp, charizardDown, charizardLeft, charizardRight, charizardStill;

    private int spriteWidth1, spriteWidth2;
    private int spriteHeight1, spriteHeight2;
    private float spriteSheetWidth1, spriteSheetWidth2;
    private float spriteSheetHeight1, spriteSheetHeight2;
    private int spritesPerRow1 = 3;
    private int spritesPerColumn1 = 4;
    private int spritesPerRow2 = 4;
    private int spritesPerColumn2 = 4;

    private int duration = 200; // Time to display each sprite
    private final CoordinateTranslator coordTran;

    public SpriteRenderer() throws SlickException {

        spriteSheetImage2 = new Image("res/charizard_sprite.png");
        spriteSheetWidth2 = spriteSheetImage2.getWidth();
        spriteSheetHeight2 = spriteSheetImage2.getHeight();
            
        // Get width of sprite based on width of the sheet and how many sprites are in it
        spriteWidth2 = (int)(spriteSheetWidth2/spritesPerRow2);

        // Get height similarly
        spriteHeight2 = (int)(spriteSheetHeight2/spritesPerColumn2);

        // Now create a SpriteSheet objecoordTran with the new SpriteSheet
        spriteSheet2 = new SpriteSheet(spriteSheetImage2, spriteWidth2, spriteHeight2); 
            
        // SpriteSheet, Start Column, Start Row, End Column, End Row, Scan Horizontally, How long per Image, Continually Cycle 

        charizardUp    = new Animation(spriteSheet2, 0, 3, 3, 3, true, duration, true);
        charizardRight = new Animation(spriteSheet2, 0, 2, 3, 2, true, duration, true);
        charizardDown  = new Animation(spriteSheet2, 0, 0, 3, 0, true, duration, true);
        charizardLeft  = new Animation(spriteSheet2, 0, 1, 3, 1, true, duration, true);
            
        charizardStill  = new Animation(spriteSheet2, 0, 0, 0, 0, true, duration, true);

        coordTran = Game.getInstance().getCT();
    }
	
	
    public void render(Entity e, Point2D location) {
        if (e instanceof Agent) {
                renderAgent((Agent)e, location);
        }
        else if (e instanceof Bullet) {
                renderBullet((Bullet)e, location);
        }
        else {
                throw new RuntimeException("Could not render");
        }
    }
	
    private void renderAgent(Agent agent, Point2D location) {

        if (agent.getState() == "up") {
                charizardUp.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth2*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight2*0.8)));
        }
        if (agent.getState() == "down") {
                charizardDown.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth2*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight2*0.8)));
        }
        if (agent.getState() == "left") {
                charizardLeft.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth2*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight2*0.8)));
        }
        if (agent.getState() == "right") {
                charizardRight.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth2*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight2*0.8)));
        }
        
//        if (agent.getState() == "still") {
//                charizardStill.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth2*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight2*0.8)));
//        }
    }
    
    private void renderBullet(Bullet b, Point2D location) {
        
    }
	
}
