package tron.PowerUps;

import tron.Player;

import java.util.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-08 Time: 09:13 To change this template use File | Settings | File
 * Templates.
 */
public abstract class PowerUp {
    int yPos;
    int xPos;
    int duration;
    int arenaRows;
    int arenaColumns;
    int powerUpSize;
    List<Point> squareOfPowerUp = new ArrayList<Point>();
    Point points = new Point();


    //This is our abstract class for all our powerups.

    protected PowerUp(int arenaRows, int arenaColumns, int yPos, int xPos) {
          this.arenaRows = arenaRows;
          this.arenaColumns = arenaColumns;
          this.yPos = yPos;
          this.xPos = xPos;
          this.powerUpSize = 8;
          this.squareOfPowerUp = createSquareOfPowerUp();
      }

    public List<Point> getSquareOfPowerUp() {
        return squareOfPowerUp;
    }

    public abstract void applyPowerUp(Player player);



    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }



    // Creates the size of the power up.
    public List<Point> createSquareOfPowerUp() {

        for (int i = 0; i < powerUpSize - 1; i++) {
            for (int j = 0; j < powerUpSize - 1; j++) {

                points.setLocation(xPos + i, yPos + j);
                squareOfPowerUp.add(new Point(points));
            }


        }
        return squareOfPowerUp;
    }


    // Draws the powerup on the gaming field.
    public abstract void drawPowerUp(Graphics g);


}
