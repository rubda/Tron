package se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps;

import se.liu.ida.rubda680.jakbi869.tddc69.tron.Player;
//import se.liu.ida.rubda680.jakbi869.tddc69.tron.Directions;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-09 Time: 13:43 To change this template use File | Settings | File
 * Templates.
 */
public class Invincible extends PowerUp {


    public Invincible(final int arenaRows, final int arenaColumns, final int yPos, final int xPos) {
        super(arenaRows, arenaColumns, yPos, xPos);
        duration = 1000;
    }


    //Changes the color of the player to magenta, makes the player collisionfree and can "teleport" between borders.
    @Override
    public void applyPowerUp(final Player player) {
        player.setCollisionFree(true);
        if (duration % 2 == 0) {
            player.setPlayerColorNow(Color.MAGENTA);
        } else {
           player.setPlayerColorNow(player.getDefaultPlayerColor());
        }

        if (player.getPosX() >= arenaColumns) {
            player.setPosX(1);
        } else if (player.getPosX() < 0) {
            player.setPosX(arenaColumns - 2);
        } else if (player.getPosY() >= arenaRows) {
            player.setPosY(1);
        } else if (player.getPosY() < 0) {
            player.setPosY(arenaRows - 2);
        }
    }

    @Override
    public void drawPowerUp(final Graphics g) {
        for (Point point : squareOfPowerUp) {
            g.setColor(Color.GREEN);
            g.drawRect(point.x, point.y, 1, 1);
        }
    }
}
