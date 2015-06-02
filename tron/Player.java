package tron;

import tron.PowerUps.PowerUp;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//import java.awt.Point;
import java.awt.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-21 Time: 18:08 To change this template use File | Settings | File
 * Templates.
 */
public class Player {

    private Directions direction;
    private Directions startDirection;

    private boolean deadPlayer;
    private int left, right, up, down;
    private Color defaultPlayerColor;
    private Color playerColorNow;
    private String playerName;

    private boolean collisionFree = false;
    private int posY, posX, startPosY, startPosX;
    private Point prevPoint = new Point();
    private List<Point> prevPosList = new ArrayList<Point>();

    public boolean isReady() {
	return ready;
    }

    public void setReady(final boolean ready) {
	this.ready = ready;
    }

    private boolean ready = false;

    private HashMap<Integer, List<Integer>> hmPrevPostList = new HashMap<Integer,List<Integer>>();

    private List<PowerUp> playerPowerUpsList = new ArrayList<PowerUp>();

    public Player(int startY, int startX, Directions dir){
	this.left = 0;
	this.right = 0;
	this.up = 0;
	this.down = 0;
	this.posY = startY;
	this.posX = startX;
	this.startPosY = startY;
	this.startPosX = startX;
	this.playerName = "";
	direction = dir;
	startDirection = dir;
	defaultPlayerColor = null;
	playerColorNow = null;
	deadPlayer=false;
    }

    public boolean isDeadPlayer() {
	return deadPlayer;
    }

    public void setDeadPlayer(final boolean deadPlayer) {
	this.deadPlayer = deadPlayer;
    }

    public void setLeft(final int left) {
	this.left = left;
    }

    public void setDown(final int down) {
	this.down = down;
    }

    public void setDefaultPlayerColor(final Color defaultPlayerColor) {
	this.defaultPlayerColor = defaultPlayerColor;
    }

    public void setPlayerName(final String playerName) {
	this.playerName = playerName;
    }

    // The inspector does not complain about the other "set" methods but this one.
    public void setUp(final int up) {
	this.up = up;
    }

    public void setRight(final int right) {
	this.right = right;
    }

    public Color getDefaultPlayerColor() {
	return defaultPlayerColor;
    }

    public Color getPlayerColorNow() {
	return playerColorNow;
    }

    public void setPlayerColorNow(final Color playerColorNow) {
	this.playerColorNow = playerColorNow;
    }

    public String getPlayerName() {
	return playerName;
    }

    public List<PowerUp> getPlayerPowerUpsList() {
        return playerPowerUpsList;
    }


    public void setPlayerPowerUpsList(PowerUp powerUp) {
        this.playerPowerUpsList.add(powerUp);
    }

    public boolean isCollisionFree() {
        return collisionFree;
    }

    public void setCollisionFree(final boolean collisionFree) {
        this.collisionFree = collisionFree;
    }


    //The inspector complaints about a raw usage.
    // But this is the way we want to do it.
    public Map<Integer, List<Integer>> getHmPrevPostList() {
        return hmPrevPostList;
    }

    public void setPosX(final int posX) {
        this.posX = posX;
    }

    public void setPosY(final int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        //System.out.println("Player1 "+posX);
        return posX;

    }

    public int getPosY() {

        return posY;
    }

    public int getStartPosX() {
        return startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions value) {
        this.direction = value;
    }

    public Directions getStartDirection() {
        return startDirection;
    }



    public List<Point> getPrevPosList() {
	return prevPosList;
    }


    // Changes the position of the player and adds all the previous positions to a hashmap.
    public void move(Directions state) {
        prevPoint.setLocation(posX, posY);
        prevPosList.add(new Point(prevPoint));

        if (hmPrevPostList.containsKey(posX)) {
            List<Integer> prevYList = hmPrevPostList.get(posX);
            prevYList.add(posY);

        } else {
            List<Integer> prevYList = new ArrayList<Integer>();
            prevYList.add(posY);
            hmPrevPostList.put(posX, prevYList);
        }

	//Some enum states are not needed anymore in our final program.
	//Therefore some cases are misses and some are basically not needed in this switch.
        switch (state) {
            case NORTH:
                moveUp();
                break;
            case SOUTH:
                moveDown();
                break;
            case EAST:
                moveRight();
                break;
            case WEST:
                moveLeft();
                break;
            default:
                break;

        }

    }

// All of these move methods move a player one step further in a left, right, up and down manner.
    public void moveUp() {
        posY--;
    }

    public void moveDown() {
        posY++;
    }

    public void moveRight() {
        posX++;
    }

    public void moveLeft() {
        posX--;
    }


    // Changes the direction up
    public void dirNorth(){
	if(direction != Directions.SOUTH){
		direction= Directions.NORTH;
	}

    }

    // Changes the direction down
    public void dirSouth(){
	if(direction != Directions.NORTH){
   		direction= Directions.SOUTH;
	}
    }

    // Changes the direction right
    public void dirEast(){
	if(direction != Directions.WEST){
    		direction= Directions.EAST;
	}
    }

    // Changes the direction left
    public void dirWest(){
	if(direction != Directions.EAST){
      		direction= Directions.WEST;
	}
    }

    //Checks the players keys and if it's the correct player, it changes the players direction.
    public void keyPressed(final KeyEvent e) {
	if (e.getKeyCode() == left){
	    dirWest();
	} else if (e.getKeyCode() == right){
	    dirEast();
	} else if (e.getKeyCode() == up){
	    dirNorth();
	} else if (e.getKeyCode() == down){
	    dirSouth();
	}
    }
}