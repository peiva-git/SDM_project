package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FreedomGame extends Game {

    public static final int NUMBER_OF_ROWS = 2;
    public static final int NUMBER_OF_COLUMNS = 2;
    private SpriteBatch batch;
    private BitmapFont font;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private final Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
    private final Player blackPlayer = new Player(Color.BLACK, "Lollo", "Bianchi");

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @NotNull
    public Player nextPlayer() {
        try {
            Player previousPlayer = playersMovesHistory.getLast().getPlayer();
            if (previousPlayer.equals(whitePlayer)) return blackPlayer;
            return whitePlayer;
        } catch (NoSuchElementException exception) {
            return whitePlayer;
        }
    }

    @Nullable
    public Player getCurrentWinner() {
        int whiteScore = getCurrentScore(Color.WHITE);
        int blackScore = getCurrentScore(Color.BLACK);
        if (whiteScore > blackScore) {
            return getWhitePlayer();
        } else if (blackScore > whiteScore) {
            return getBlackPlayer();
        }
        return null;
    }

    private int getCurrentScore(@NotNull Color playerColor) throws RuntimeException {
        if (getScreen().getClass() == GameScreen.class) {
            GameScreen currentScreen = (GameScreen) getScreen();
            FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(currentScreen.getBoard());
            if (playerColor == Color.WHITE) {
                return freedomPointsCounter.getPlayerScore(Color.WHITE);
            } else if (playerColor == Color.BLACK) {
                return freedomPointsCounter.getPlayerScore(Color.BLACK);
            } else {
                throw new RuntimeException("Invalid player color, must be either black or white");
            }
        } else {
            throw new IllegalStateException("Can't get the current score while on a different screen than the game screen");
        }
    }

    public LinkedList<Move> getPlayersMovesHistory() {
        return playersMovesHistory;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void appendTextToLogArea(@NotNull String textToAppend) {
        if (getScreen().getClass() == GameScreen.class) {
            GameScreen currentScreen = (GameScreen) getScreen();
            currentScreen.getFirstTextArea().appendText(textToAppend);
        }  else {
            Gdx.app.error("GAME", "Unable to print to game screen text area");
        }
    }
}
