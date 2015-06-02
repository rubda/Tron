package tron;

/**
 * Created with IntelliJ IDEA.
 * User: RubenDas
 * Date: 2013-12-06
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
public enum State {
    /**
     * EMPTY -empty position in arena. Almost all position in arena.
     * We wanted to have somthing to compare with when we do collision check.
     */
    EMPTY,
    /**
     * OUTSIDE -frame around arena. We have used it in collision check.
     * If player is on outside position it means that he lost.
     */
    OUTSIDE
}
