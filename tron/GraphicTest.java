package tron;


import tron.Graphics.TronFrame;

/**
 * Created with IntelliJ IDEA. User: jakbi869 Date: 2013-10-03 Time: 10:07 To change this template use File | Settings | File
 * Templates.
 */
public final class GraphicTest {

    private GraphicTest() {}

    //This was used to test the graphics and now the inspector complaints about it.
    public static void main(String[] args) {

        //final TronTextView myPrint = new TronTextView();

        final Arena arena = new Arena(Arena.getNUM_ROWS(), Arena.getNUM_COLUMNS());
        final TronFrame myFrame = new TronFrame(arena);
        myFrame.runTimer();

        //System.out.println(myPrint.convertToText(arena));

    }
}

