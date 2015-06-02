package se.liu.ida.rubda680.jakbi869.tddc69.tron;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-23 Time: 16:29 To change this template use File | Settings | File
 * Templates.
 */
//States for the players.
//A player only carries one state, else the directions would collide with eachother.
//In theory you could carry more states, but this is not how we intended the code to be.
public enum Directions {

    //Some states are only used in TronTextView.
    //Therefore the inspector complaints about declaration redundancy.

    /**
     * NORTH -direction North. Player have a direction variable
     * and if it's equals NORTH it means that player will go to the North.
     */
     NORTH,
    /**
     * SOUTH  -direction South. Player have a direction variable
     * and if it's equals SOUTH it means that player will go to the South.
     */
    SOUTH,
    /**
     * EAST  -direction East. Player have a direction variable
     * and if it's equals EAST it means that player will go to the East.
     */
    EAST,
    /**
     * WEST -direction West. Player have a direction variable
     * and if it's equals WEST it means that player will go to the West.
     */
    WEST





}
