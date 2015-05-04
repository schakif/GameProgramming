package edu.moravian.Game;

import java.util.ArrayList;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import edu.moravian.Entity.Agent;
import edu.moravian.Entity.Bullet;
import edu.moravian.Entity.Entity;
import edu.moravian.Entity.Tower;
import edu.moravian.math.CoordinateTranslator;
import edu.moravian.math.Point2D;
import org.newdawn.slick.Sound;

public class Game extends BasicGame {

    private static Game instance;

    private final static int screenWidth = 832;
    private final static int screenHeight = 832;
    private GameMap map;
    private double x, y; // Player x and y
    private int delta; // Time between frames
    private CoordinateTranslator coordTran;
    private GameSoundManager gsm;
    private Boolean exit, gameLog, goRight, goLeft, goUp, goDown;
    //private Boolean agentRight, agentLeft, agentUp, agentDown;
    private TiledMap tMap;
    private Sound enterSound, exitSound, firingSound;
    
    private int mouseX, mouseY;
    private ArrayList<Agent> agents;
    private ArrayList<Tower> towers;
    private ArrayList<Bullet> bullets;
    private boolean placeTower;
    private Tower tempTower;
    
    private Boolean laserTower, spiderTower, drillTower;
    private int waveTimer;
    private int lives;
    private Point2D destination;
    private GameLog log;

    private Game(String title) {
        super(title);
    }

    public static Game getInstance() {
        if (instance == null) {
                instance = new Game("Tower Defense");
        }
        return instance;
    }

    @Override
    public void init(GameContainer arg0) throws SlickException {
        File file = new File("res/info.txt");
        try {
            Scanner scanner = new Scanner(file);
            String mapPath = scanner.nextLine();
            tMap = new TiledMap(mapPath);
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
//        coordTran = new CoordinateTranslator(tMap.getWidth(), tMap.getHeight(), 0, 0, screenWidth, screenHeight);
        coordTran = new CoordinateTranslator(832, 832, 0, 0, screenWidth, screenHeight);
        map = new GameMap(tMap);
        agents = new ArrayList<Agent>();
        towers = new ArrayList<Tower>();
        bullets = new ArrayList<Bullet>();
        placeTower = false;
        agents.add(new Agent(400));
        waveTimer = 0;
        lives = 20;
        destination = new Point2D(496, 464);

//        playerRen = new SpriteRenderer();
//        agent1Ren = new SpriteRenderer();
//        agent2Ren = new SpriteRenderer();
//        agent3Ren = new SpriteRenderer();

        gsm = new GameSoundManager();
        exit = false;
        gameLog = false;
        laserTower = true;
        spiderTower = false;
        drillTower = false;
        log = new GameLog(this);
        
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        this.delta = delta;
        
        if (placeTower) {
            // make a new tower with screen coordinates
            if (laserTower) {
                tempTower = new Tower(300.0, 40, new Point((int)(mouseX),(int)(mouseY)));
            }
            else if (spiderTower) {
                tempTower = new Tower(200.0, 25, new Point((int)(mouseX),(int)(mouseY)));
            }
            else if (drillTower) {
                tempTower = new Tower(150.0, 80, new Point((int)(mouseX),(int)(mouseY)));
            }
//            tempTower = new Tower(200.0, 1, new Point((int)(mouseX),(int)(mouseY)));
            boolean isTowerThere = false;

            // Checks to see if tower is already there
            for (Tower t : towers) {
                if (t.getTileLocation().equals(tempTower.getTileLocation())) {
                    isTowerThere = true;
                }
            }

            if (!isTowerThere && tMap.getTileId(mouseX/32, mouseY/32, tMap.getLayerIndex("Path")) == 0
                    && tMap.getTileId(mouseX/32, mouseY/32, tMap.getLayerIndex("Objects")) == 0) {
                towers.add(tempTower);
            }

            placeTower = false;
        }

        if(exit) {
            gc.exit();
        }
        firingSound = new Sound("res/shot.wav");
        for (Tower t : towers) {
            // Find the closest target
            t.setTarget(agents);
            if (t.hasCurrentTarget()) {
                Agent currTarg = t.getCurrentTarget();
                if (currTarg.getHealth() <= 0) {
                    agents.remove(currTarg);
                }
                else {
                    // Attack it
                    t.attack(t.getCurrentTarget(), delta);
//                    firingSound.play();
                }
            }
        }
        
        for (Agent a : agents) {
            a.update(delta);
            
        }
        
        enterSound = new Sound("res/Charizard_Cry.wav");
        
        // Spawn agents with timer
        waveTimer += delta;
        if (waveTimer > 1200) {
            agents.add(new Agent(400));
            enterSound.play();
            waveTimer = 0;
        }
        // Delete agents
        for (int i = 0; i < agents.size(); i++) {
//            if (agents.get(i).getLocation().getX() == destination.getX() && agents.get(i).getLocation().getY() == destination.getY()) {
//                lives -= 1;
//                System.out.println("Lives: " + lives);
//            }
            if (agents.get(i).isDead()) {
                agents.remove(agents.get(i));
            }
        }
        
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).dead) {
                bullets.get(i).killBullet();
                bullets.remove(bullets.get(i));
            }
        }
        
        for (Bullet b : bullets) {
            b.update(delta);
        }
        
        if (lives == 0) {
            exit = true;
        }

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Render the map
        tMap.render(0,0);
        // Render the towers
        for (Tower t : towers) {
            t.render(g);
        }
        // Render the agents
        for (Agent a: agents) {
            a.render(g);
        }
        for (Bullet b : bullets) {
            b.render(g);
        }
        if (gameLog) {
            log.display(g);
        }

    }
    
    public void mouseClicked(int button, int x, int y, int clickCount) {
        mouseX = x;
        mouseY = y;

        placeTower = true;  
      }

    @Override
    public void keyPressed(int key, char c) {
        if(c == 'q' || c == 'Q') {
            exit = true;
        }
        if(key == Input.KEY_TAB) {
            gameLog = gameLog == false;
        }
        if(c == 'l' || c == 'L') {
            laserTower = true;
            spiderTower = false;
            drillTower = false;
        }
        if(c == 's' || c == 'S') {
            spiderTower = true;
            laserTower = false;
            drillTower = false;
        }
        if(c == 'd' || c == 'D') {
            spiderTower = false;
            laserTower = false;
            drillTower = true;
        }
    }
        
    @Override
    public void keyReleased(int key, char c) {
        if(c == 'q' || c == 'Q') {
            exit = false;
        }
    }

    public CoordinateTranslator getCT() {
        return coordTran;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getWorldWidth() {
        return tMap.getWidth();
    }

    public int getWorldHeight() {
        return tMap.getHeight();
    }

    public int getDelta() {
        return delta;
    }
    
    public int getLives() {
        return lives;
    }
    
    public void setLives(int newLives) {
        lives = newLives;
    }
    
    public String getTowerState() {
        if(laserTower) {
            return "Laser";
        }
        else if(spiderTower) {
            return "Spider";
        }
        else if(drillTower) {
            return "Drill";
        }
        return "Laser";
    }

    public GameMap getGameMap() {
        return map;
    }
    
    public void addBullet(Bullet b) {
        bullets.add(b);
    }
}
