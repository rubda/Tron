package tron.PowerUps;

import tron.Player;

import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-08 Time: 09:27 To change this template use File | Settings | File
 * Templates.
 */
public class Teleport extends PowerUp {
    Random generator = new Random();


    public Teleport(final int arenaRows, final int arenaColumns, final int yPos, final int xPos) {
        super(arenaRows, arenaColumns, yPos, xPos);
        duration = 1;


    }

    // Randoms a x and y coordinate for the player.
    @Override
    public void applyPowerUp(Player player) {
        player.setPosX(generator.nextInt(arenaColumns - 2));
        player.setPosY(generator.nextInt(arenaRows - 2));

    }


    @Override
    public void drawPowerUp(final Graphics g) {
        for (Point point : squareOfPowerUp) {
            g.setColor(Color.ORANGE);
            g.drawRect(point.x, point.y, 1, 1);
        }


    }


}
