package it.units.sdm.project.game;

import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Representation of a turn-based game played by two {@link Player}s
 * ({@link com.badlogic.gdx.graphics.Color#WHITE} and {@link com.badlogic.gdx.graphics.Color#BLACK}) on a {@link Board}.
 */
public interface BoardGame {

    /**
     * Returns the {@link Board} used by this {@link BoardGame}
     * @return The {@link Board} used by this {@link BoardGame}
     */
    @NotNull Board<? extends Stone> getBoard();

    /**
     * Returns the {@link com.badlogic.gdx.graphics.Color#WHITE} {@link Player}
     * @return The {@link Player} who's going first
     */
    @NotNull Player getWhitePlayer();

    /**
     * Returns the {@link com.badlogic.gdx.graphics.Color#BLACK} {@link Player}
     * @return The {@link Player} who's going second
     */
    @NotNull Player getBlackPlayer();

    /**
     * Returns the last {@link Move} played in this {@link BoardGame}
     * @return The last {@link Move} played in this {@link BoardGame}
     */
    @Nullable Move getLastMove();

    /**
     * Plays the next {@link Move} in this {@link BoardGame}
     * @param position The chosen {@link Position} for the next {@link Move}
     */
    void nextMove(@NotNull Position position);

    /**
     * Returns the {@link Player} who's playing next
     * @return The {@link Player} who's playing next
     */
    default @NotNull Player getNextPlayer() {
        Player whitePlayer = getWhitePlayer();
        Player blackPlayer = getBlackPlayer();
        if(getLastMove() == null || getLastMove().getPlayer().equals(blackPlayer)) return whitePlayer;
        return blackPlayer;
    }

    /**
     * Reset the {@link BoardGame} to the default {@link it.units.sdm.project.game.FreedomBoardStatusObserver.GameStatus} state
     */
    void reset();

}
