package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.units.sdm.project.board.BoardUtils;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FreedomGame extends Game {

    public static final int NUMBER_OF_ROWS = 2;
    public static final int NUMBER_OF_COLUMNS = 2;
    private SpriteBatch batch;
    private BitmapFont font;
    private Board<Stone> board;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private GameStatus gameStatus;
    private final Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
    private final Player blackPlayer = new Player(Color.BLACK, "Lollo", "Bianchi");

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        board = new MapBoard<>(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        setScreen(new MainMenuScreen(this));
        gameStatus = GameStatus.FREEDOM;
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

    public void updateCurrentGameStatus() {
        if (gameStatus != GameStatus.GAME_OVER) {
            long numberOfFreeCells = BoardUtils.getNumberOfFreeCells(board);
            if (numberOfFreeCells > 1) {
                if (playersMovesHistory.isEmpty() || BoardUtils.areAdjacentCellsOccupied(board, playersMovesHistory.getLast().getPosition())) {
                    gameStatus = GameStatus.FREEDOM;
                } else {
                    gameStatus = GameStatus.NO_FREEDOM;
                }
            } else if(numberOfFreeCells == 1){
                gameStatus = GameStatus.LAST_MOVE;
            } else {
                gameStatus = GameStatus.GAME_OVER;
            }
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
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        if (playerColor == Color.WHITE) {
            return freedomPointsCounter.getPlayerScore(whitePlayer);
        } else if (playerColor == Color.BLACK) {
            return freedomPointsCounter.getPlayerScore(blackPlayer);
        } else {
            throw new RuntimeException("Invalid player color, must be either black or white");
        }
    }

    public Board<Stone> getBoard() {
        return board;
    }

    public LinkedList<Move> getPlayersMovesHistory() {
        return playersMovesHistory;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
