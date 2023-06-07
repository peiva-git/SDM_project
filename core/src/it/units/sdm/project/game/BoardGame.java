package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Representation of a turn-based game played by two {@link Player}s (white and black) on a {@link Board}.
 */
public interface BoardGame {

    /**
     * Returns the board used by this game
     * @return The board used by this game
     */
    @NotNull Board<?> getBoard();

    /**
     * Returns the white player
     * @return The player who's going first
     */
    @NotNull Player getWhitePlayer();

    /**
     * Returns the black player
     * @return The player who's going second
     */
    @NotNull Player getBlackPlayer();

    /**
     * Returns the last move played in this game
     * @return The last move played in this game
     */
    @Nullable Move getLastMove();

    /**
     * Plays the next move in this game
     * @param position The chosen position for the next move
     */
    void nextMove(@NotNull Position position);

    /**
     * Returns the player who's playing next
     * @return The player who's playing next
     */
    default @NotNull Player getNextPlayer() {
        Player whitePlayer = getWhitePlayer();
        Player blackPlayer = getBlackPlayer();
        if(getLastMove() == null || getLastMove().getPlayer().equals(blackPlayer)) return whitePlayer;
        return blackPlayer;
    }

    /**
     * Reset the game to the default state
     */
    void reset();

}
