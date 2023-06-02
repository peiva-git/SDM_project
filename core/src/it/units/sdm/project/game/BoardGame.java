package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BoardGame {

    /**
     *
     * @return The board used by the game
     */
    @NotNull Board<?> getBoard();

    /**
     *
     * @return The player who's going first. In a game of chess, that would probably be the white player
     */
    @NotNull Player getFirstPlayer();

    /**
     *
     * @return The player who's going second. In a game of chess, that would probably be the black player
     */
    @NotNull Player getSecondPlayer();

    /**
     *
     * @return The last move played in this game
     */
    @Nullable Move getLastMove();

    /**
     * Play the move in this game
     * @param position The chosen position for the next move
     */
    void nextMove(@NotNull Position position);

    /**
     *
     * @return The player who should play next
     */
    default @NotNull Player getNextPlayer() {
        Player firstPlayer = getFirstPlayer();
        Player secondPlayer = getSecondPlayer();
        if(getLastMove() == null || getLastMove().getPlayer().equals(secondPlayer)) return firstPlayer;
        return secondPlayer;
    }

    /**
     * Reset the game to the default state
     */
    void reset();

}
