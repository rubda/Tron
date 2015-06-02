package se.liu.ida.rubda680.jakbi869.tddc69.tron;


import se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps.Invincible;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps.PowerUp;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps.RemoveTrail;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps.Teleport;

import java.awt.*;
import java.util.*;
import java.util.List;

import static se.liu.ida.rubda680.jakbi869.tddc69.tron.State.*;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-21 Time: 17:57 To change this template use File | Settings | File
 * Templates.
 */
public class Arena {

    Random generator = new Random();
    private int rows;
    private int columns;
    private State[][] array;

    // playerOne and playerTwo are only needed in the TronTextView.
    // Therefore they are still here.
    //private Player playerOne;
    //private Player playerTwo;
    private List<ArenaListener> arenaList = new ArrayList<ArenaListener>();
    private List<Player> players = new ArrayList<Player>();


    private int powerUpTick = 0;
    private List<PowerUp> powerUpsList = new ArrayList<PowerUp>();
    private PowerUp[] powerUps = new PowerUp[3];

    private String showWinningPlayer = "null";

    final static int TIME_PERIOD_POWER_UP = 500;
    final static int NUM_ROWS = 400;
    final static int NUM_COLUMNS = 601;

    final static int DISTANCE_FROM_LEFT = NUM_COLUMNS / 5;
    final static int DISTANCE_FROM_RIGHT = NUM_COLUMNS - (DISTANCE_FROM_LEFT + 1);
    private int draw = 0;


    private boolean endGame = false;
    private boolean pause = false;


    //Constructor for the arena
    public Arena(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.array = new State[rows][columns];
        this.generateArena();
        notifyListeners();
    }



    //----------------------------------------------------------------------------------------------


    public List<PowerUp> getPowerUpsList() {
         return powerUpsList;
     }

    //Checks if all players are ready to play.
    public boolean isReadyToPlay(){
        for (Player player : players) {
            if(!player.isReady()){
                return false;
            }
        }
        return true;
    }

    public static int getDistanceFromLeft() {
        return DISTANCE_FROM_LEFT;
    }

    public static int getDistanceFromRight() {
        return DISTANCE_FROM_RIGHT;
    }

    public String getShowWinningPlayer() {
        return showWinningPlayer;
    }

    public void setShowWinningPlayer(String showWinningPlayer) {
        this.showWinningPlayer = showWinningPlayer;
    }


    //Idea complains about these getNUM methods names. But it was Idea that named them.
    public static int getNUM_COLUMNS() {
        return NUM_COLUMNS;
    }

    public static int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public boolean isEndGame() {
        return endGame;
    }


    //These are used when playerOne and playerTwo are used. Described above.
    /*
    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
    */

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Player> getPlayers() {
         return players;
     }

    public State returnArenaPos(int row, int column) {
        return this.array[row][column];
    }

//----------------------------------------------------------------------------------------------


      //Creates powerups and puts them in a list.
    public void createPowerUp() {
        powerUps[0] = new Teleport(NUM_ROWS, NUM_COLUMNS, generator.nextInt(NUM_ROWS - 2), generator.nextInt(NUM_COLUMNS - 2));
        powerUps[1] = new RemoveTrail(NUM_ROWS, NUM_COLUMNS, generator.nextInt(NUM_ROWS - 2), generator.nextInt(NUM_COLUMNS - 2));
        powerUps[2] = new Invincible(NUM_ROWS, NUM_COLUMNS, generator.nextInt(NUM_ROWS - 2), generator.nextInt(NUM_COLUMNS - 2));
    }

    //Calls the functions above and puts one of the three powerups on the gaming field
    public void addToPowerUpsList() {
        createPowerUp();
        powerUpsList.add(powerUps[generator.nextInt(powerUps.length)]);
    }


    //This function basically makes the player go back to all of it's starting behaviors.
    public void restart() {
        for (Player player : players) {
            player.getPrevPosList().clear();
            player.getHmPrevPostList().clear();
            player.setDirection(player.getStartDirection());
            player.setPosY(player.getStartPosY());
            player.setPosX(player.getStartPosX());
	    player.getPlayerPowerUpsList().clear();
	    player.setCollisionFree(false);
	    player.setPlayerColorNow(player.getDefaultPlayerColor());
	    player.setDeadPlayer(false);
	}
        powerUpTick = 0;
        powerUpsList.clear();
        draw = 0;
        endGame = false;
        pause = false;
    }


   //Adds the arenalistener
    public void addArenaListener(ArenaListener al) {
        arenaList.add(al);
    }

    //Notifies listeners
    private void notifyListeners() {
        for (ArenaListener arenaListener : arenaList) {
            arenaListener.arenaChanged();
        }
    }

    //Creates the player with a start position and a direction.
    public void createPlayers(int y, int x, Directions dir) {
	this.players.add(new Player(y, x, dir));
    }



    //Checks collision when making players.
    public boolean checkStartColision(int y, int x){

        if (this.returnArenaPos(y, x) == OUTSIDE) {
            return true;
        }
        for (Player p : players) {

            if (x == p.getPosX() && y == p.getPosY()) {
                return true;
            }
        }
        return false;
    }

    //Our collisioncode between players, powerups and borders.
    public boolean isColission(Player player) {

        if (this.returnArenaPos(player.getPosY(), player.getPosX()) == OUTSIDE) {
            return true;
        }

        Iterator<PowerUp> it = powerUpsList.iterator();
        while (it.hasNext()) {
            PowerUp powerUp = it.next();
            for (Point point : powerUp.getSquareOfPowerUp()) {
                if (player.getPosX() == point.x && player.getPosY() == point.y) {
                    player.setPlayerPowerUpsList(powerUp);
                    it.remove();
                }
            }
        }

        for (Player p : players) {

            List<Integer> listY;
            listY = p.getHmPrevPostList().get(player.getPosX());
            if (listY != null) if (!listY.isEmpty()) {
                if (listY.contains(player.getPosY())) {
                    return true;
                }
            }
            if (!player.equals(p) && player.getPosX() == p.getPosX() && player.getPosY() == p.getPosY()) {
                return true;
            }
        }
        return false;
    }

    // This tick does a lot of things. It applies powerups when needed, moves the player and gets the winning player.
    // This function also takes care of the powerup invincible, because the duration is longer than the other
    // and makes the player collisionfree.

    // The inspector complains about it being too complex to analyze,
    // but it is not that hard to analyze to be honest.
    public void tick() {
        draw=players.size();
        powerUpTick++;

        if (powerUpTick % TIME_PERIOD_POWER_UP == 0) {
            addToPowerUpsList();
        }

        String winningPlayer = "no";
        for (Player p : players) {
            Iterator<PowerUp> it = p.getPlayerPowerUpsList().iterator();
            while (it.hasNext()) {
                PowerUp powerUp = it.next();
                powerUp.applyPowerUp(p);
                powerUp.setDuration(powerUp.getDuration() - 1);
               if (powerUp.getDuration() == 0) {
                    it.remove();
                    p.setPlayerColorNow(p.getDefaultPlayerColor());
                    p.setCollisionFree(false);
                }
            }
	    if (!p.isCollisionFree()) {
		if(!p.isDeadPlayer()){
		    if (!isColission(p)) {
			winningPlayer = p.getPlayerName();
			p.move(p.getDirection());
		    } else {
			draw--;
			p.setDeadPlayer(true);
			if(draw<=1) {
			    endGame = true;
			}
		    }
		}else{
		    draw--;
		    p.setDeadPlayer(true);
		    if(draw<=1) {
			endGame = true;
		    }
		}
	    } else {
		winningPlayer = p.getPlayerName();
		p.move(p.getDirection());
	    }
	}
        whoWon(winningPlayer);
    }


    //Announces who won.
    public void whoWon(String winningPlayer) {
        if (endGame) {
            if(players.size()>1){
                if (draw ==0) {
                    System.out.println("Draw");
                    showWinningPlayer = "It's a draw!";
                } else {
                    showWinningPlayer = winningPlayer + " wins! Congratulations " + winningPlayer + "!";
                    System.out.println(winningPlayer + " wins");
                }
            }else{
                System.out.println("You lost");
                showWinningPlayer = "You lost!";
            }
        }
    }

   //Generates the arena.
    public void generateArena() {

        // Constructs the "roof"
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < columns; j++) {
                this.array[i][j] = OUTSIDE;
            }
        }

        // Constructs the "walls"
        for (int i = 1; i < rows - 1; i++) {
            this.array[i][0] = OUTSIDE;
            for (int j = 1; j < columns - 1; j++) {
                if (array[i][j] == null || array[i][j] == EMPTY) {
                    this.array[i][j] = EMPTY;
                    //this.array[i][j] = Directions.values()[generator.nextInt(2)];
                }
            }
            this.array[i][columns - 1] = OUTSIDE;
        }

        // Constructs the "floor"
        for (int i = rows - 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.array[i][j] = OUTSIDE;
            }
        }
        notifyListeners();
    }


}
