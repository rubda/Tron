package tron.Graphics;

import tron.Arena;
//This import is used when we need to test TronTextView with Player
//import tron.Player;
import tron.State;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-30 Time: 16:31 To change this template use File | Settings | File
 * Templates.
 */
public class TronTextView
{

    public String convertToText(Arena arena) {

	StringBuilder sBuilder = new StringBuilder();

	System.out.println("Tron Arena");

	for (int i = 0; i < arena.getRows(); i++) {
	    for (int j = 0; j < arena.getColumns(); j++) {

		    State colorState = arena.returnArenaPos(i, j);
		    sBuilder.append(getState(colorState));

	    }
	    sBuilder.append("\n");
	}
	return sBuilder.toString();

    }

    //This is our switch to choose what to print.
    public char getState(State state) {
	switch (state) {

	    case EMPTY:
		return '¤';
	    case OUTSIDE:
		return '#';
	    default:
		return '0';

	}
    }

    //This is also used for the TronTextView.
    /*public char getState(String state) {
	if (state.equals("Red")) {
	    return 'R';
	} else if (state.equals("Blue")) {
	    return 'B';
	} else {
	    return '0';
	}
    }*/

}
