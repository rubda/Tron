package tron.PowerUps;

import tron.Player;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-09 Time: 11:34 To change this template use File | Settings | File
 * Templates.
 */
public class RemoveTrail extends PowerUp {
    public RemoveTrail(final int arenaRows, final int arenaColumns, final int yPos, final int xPos) {
        super(arenaRows, arenaColumns, yPos, xPos);

    }

    // Clears the players previous position list.
    @Override
    public void applyPowerUp(final Player player) {
        player.getHmPrevPostList().clear();
        player.getPrevPosList().clear();
        duration = 1;
    }

    @Override
    public void drawPowerUp(final Graphics g) {
        for (Point point : squareOfPowerUp) {
            g.setColor(Color.CYAN);
            g.drawRect(point.x, point.y, 1, 1);
        }

    }
}
