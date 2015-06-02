package se.liu.ida.rubda680.jakbi869.tddc69.tron.Graphics;

import se.liu.ida.rubda680.jakbi869.tddc69.tron.Arena;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.ArenaListener;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.Player;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.PowerUps.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-03 Time: 10:07 To change this template use File | Settings | File
 * Templates.
 */
public class TronComponent extends JComponent implements ArenaListener
{

    private Arena arena;

    //Constructor for TronComponent
    public TronComponent(final Arena arena) {
	this.arena = arena;
	this.addBindnings();


    }

    //The inspector complains about it ignoring its defined method in the superclass
    //But that is what we are supposed to do
    @Override
    public Dimension getPreferredSize() {
	return new Dimension(arena.getColumns(), arena.getRows());
    }


    //This function basically paints everything, such as arena, players and powerups.
    @Override
    protected void paintComponent(final Graphics g) {
	super.paintComponent(g);

	g.setColor(Color.BLACK);
	g.fillRect(1, 1, arena.getColumns() - 1, arena.getRows() - 1);

	for (PowerUp powerUp : arena.getPowerUpsList()) {
	    powerUp.drawPowerUp(g);
	}

	for (Player p : arena.getPlayers()) {
	    for (Point points : p.getPrevPosList()) {
		g.setColor(p.getPlayerColorNow());
		g.fillRect(points.x - 2, points.y - 2, 4, 4);
	    }
	    g.setColor(p.getPlayerColorNow());
	    g.fillRect(p.getPosX() - 2, p.getPosY() - 2, 4, 4);
	}
    }


    @Override
    public void arenaChanged() {
	repaint();
    }


    //Basic keybindings for pause and restart functions.
    private void addBindnings() {
	InputMap bindingsMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);


	bindingsMap.put(KeyStroke.getKeyStroke("P"), "pause");
	bindingsMap.put(KeyStroke.getKeyStroke("R"), "restart");


	Action pause = new AbstractAction()
	{
	    @Override
	    public void actionPerformed(final ActionEvent rL) {
		if (arena.isPause() && arena.isReadyToPlay()) {
		    arena.setPause(false);
		} else {
		    arena.setPause(true);
		}


	    }

	};

	getActionMap().put("pause", pause);


	Action restart = new AbstractAction()
	{
	    @Override
	    public void actionPerformed(final ActionEvent rL) {

		if (!arena.getPlayers().isEmpty() && arena.isReadyToPlay()) {
		    arena.restart();
		}

	    }

	};

	getActionMap().put("restart", restart);
    }

}
