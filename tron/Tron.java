package se.liu.ida.rubda680.jakbi869.tddc69.tron;

import se.liu.ida.rubda680.jakbi869.tddc69.tron.Graphics.TronFrame;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-21 Time: 17:59 To change this template use File | Settings | File
 * Templates.
 */
final public class Tron {

    private Tron() {
    }

    // This is not a reundant declaration, since this is our main program.
    public static void main(String[] args) {

        final Arena arena = new Arena(Arena.getNUM_ROWS(), Arena.getNUM_COLUMNS());

        final TronFrame myFrame = new TronFrame(arena);

        myFrame.runTimer();

    }

}
