package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.BoardUtils;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.board.gui.TileClickListener;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.FreedomBoardObserver;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.dialogs.GameOverDialog;
import it.units.sdm.project.game.gui.dialogs.LastMoveDialog;
import it.units.sdm.project.game.gui.screens.GameScreen;
import it.units.sdm.project.game.gui.screens.MainMenuScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class FreedomGame extends Game implements it.units.sdm.project.game.Game {

    public static final int NUMBER_OF_ROWS = 2;
    public static final int NUMBER_OF_COLUMNS = 2;
    public static final Color HIGHLIGHT_DARK_TILE = new Color(105 / 255f, 105 / 255f, 105 / 255f, 255 / 255f);
    public static final Color HIGHLIGHT_LIGHT_TILE = new Color(169 / 255f, 169 / 255f, 169 / 255f, 255 / 255f);
    private Texture blackStoneImage;
    private Texture whiteStoneImage;
    private GuiBoard board;
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    private final Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
    private final Player blackPlayer = new Player(Color.BLACK, "Lollo", "Bianchi");
    private FreedomBoardObserver freedomBoardObserver;

    @Override
    public void create() {
        blackStoneImage = new Texture(Gdx.files.internal("circle2.png"));
        whiteStoneImage = new Texture(Gdx.files.internal("redCircle.png"));
        board = new GuiBoard(new Skin(Gdx.files.internal("uiskin.json")), NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        board.setClickListener(new TileClickListener(this));
        freedomBoardObserver = new FreedomBoardObserver(board);
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void reset() {
        board.clearBoard();
        playersMovesHistory.clear();
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

    public void appendTextToLogArea(@NotNull String textToAppend) {
        if (getScreen().getClass() == GameScreen.class) {
            GameScreen currentScreen = (GameScreen) getScreen();
            currentScreen.getFirstTextArea().appendText(textToAppend);
        } else {
            Gdx.app.error("GAME", "Unable to print to game screen text area");
        }
    }

    @Override
    public @NotNull Board<?> getBoard() {
        return board;
    }

    @Override
    public @NotNull Player getFirstPlayer() {
        return whitePlayer;
    }

    @Override
    public @NotNull Player getSecondPlayer() {
        return blackPlayer;
    }

    @Override
    public @Nullable Move getLastMove() {
        if (playersMovesHistory.isEmpty()) return null;
        return playersMovesHistory.getLast();
    }

    @Override
    public void nextMove(@NotNull Position inputPosition) {
        Position boardPosition = fromTilePositionToBoardPosition(inputPosition.getRow(), inputPosition.getColumn());
        Move currentMove = new Move(nextPlayer(), boardPosition);
        GameStatus currentGameStatus = freedomBoardObserver.getCurrentGameStatus(getLastMove());
        if (!checkUserPositionValidity(currentGameStatus, currentMove.getPosition())) return;

        resetCurrentlyHighlightedCellsIfAny();
        switch (currentGameStatus) {
            case FREEDOM:
            case NO_FREEDOM:
                putStoneOnTheBoard(currentMove);
                highlightValidPositionsForNextNoFreedomMove();
                break;
            case GAME_OVER:
                putStoneOnTheBoard(currentMove);
                GameOverDialog gameOverDialog = new GameOverDialog(this, board.getSkin());
                gameOverDialog.show(board.getStage());
                break;
            case LAST_MOVE:
                LastMoveDialog lastMoveDialog = new LastMoveDialog(this, board.getSkin());
                lastMoveDialog.show(board.getStage());
                break;
        }
    }

    private boolean checkUserPositionValidity(@NotNull GameStatus currentGameStatus, @NotNull Position inputPosition) {
        if (board.isCellOccupied(inputPosition)) return false;
        if (currentGameStatus == GameStatus.NO_FREEDOM)
            return findFreePositionsNearLastPlayedPosition().contains(inputPosition);
        return true;
    }

    @NotNull
    private Set<Position> findFreePositionsNearLastPlayedPosition() {
        return BoardUtils.getAdjacentPositions(board, Objects.requireNonNull(getLastMove()).getPosition()).stream()
                .filter(position -> !board.isCellOccupied(position))
                .collect(Collectors.toSet());
    }

    private void putStoneOnTheBoard(@NotNull Move move) {
        Player currentPlayer = move.getPlayer();
        Position inputPosition = move.getPosition();
        if (currentPlayer.getColor() == Color.WHITE) {
            Image whiteStone = new Image(whiteStoneImage);
            board.putPiece(new GuiStone(Color.WHITE, whiteStone), inputPosition);
            printFormattedMoveOnTheLogAreaForFirstPlayer(inputPosition);
        } else {
            Image blackStone = new Image(blackStoneImage);
            board.putPiece(new GuiStone(Color.BLACK, blackStone), inputPosition);
            appendTextToLogArea("     " + inputPosition + "\n");
        }
        playersMovesHistory.add(move);
    }

    private void printFormattedMoveOnTheLogAreaForFirstPlayer(@NotNull Position inputPosition) {
        long currentStep = playersMovesHistory.stream()
                .filter(move -> move.getPlayer().getColor() == Color.WHITE)
                .count();
        if (currentStep < 10) {
            appendTextToLogArea("  " + currentStep + ". " + inputPosition);
        } else if (currentStep < 100) {
            appendTextToLogArea(" " + currentStep + ". " + inputPosition);
        } else {
            appendTextToLogArea(currentStep + ". " + inputPosition);
        }
    }

    @SuppressWarnings("unchecked")
    private void highlightValidPositionsForNextNoFreedomMove() {
        Set<Position> positionsToHighlight = findFreePositionsNearLastPlayedPosition();
        List<Cell<Actor>> cellsToHighlight = new ArrayList<>();
        for (int i = 0; i < board.getCells().size; i++) {
            Cell<Actor> cell = board.getCells().get(i);
            if (positionsToHighlight.contains(fromTilePositionToBoardPosition(cell.getRow(), cell.getColumn()))) {
                cellsToHighlight.add(cell);
            }
        }
        for (Cell<Actor> cellToHighlight : cellsToHighlight) {
            com.badlogic.gdx.scenes.scene2d.ui.Stack tileAndPiece = (com.badlogic.gdx.scenes.scene2d.ui.Stack) cellToHighlight.getActor();
            Actor tile = tileAndPiece.getChild(0);
            if (isIndexEven(cellToHighlight.getRow())) {
                if (isIndexEven(cellToHighlight.getColumn())) {
                    tile.setColor(HIGHLIGHT_LIGHT_TILE);
                } else {
                    tile.setColor(HIGHLIGHT_DARK_TILE);
                }
            } else {
                if (isIndexEven(cellToHighlight.getColumn())) {
                    tile.setColor(HIGHLIGHT_DARK_TILE);
                } else {
                    tile.setColor(HIGHLIGHT_LIGHT_TILE);
                }
            }
        }
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }

    @SuppressWarnings("unchecked")
    private void resetCurrentlyHighlightedCellsIfAny() {
        for (int i = 0; i < board.getCells().size; i++) {
            Cell<Actor> cell = board.getCells().get(i);
            Group tileAndPiece = (Group) cell.getActor();
            Actor tile = tileAndPiece.getChild(0);
            if (tile.getColor().equals(HIGHLIGHT_DARK_TILE)) {
                tile.setColor(GuiBoard.DARK_TILE);
            } else if (tile.getColor().equals(HIGHLIGHT_LIGHT_TILE)) {
                tile.setColor(GuiBoard.LIGHT_TILE);
            }
        }
    }

    @NotNull
    private Position fromTilePositionToBoardPosition(int tileRow, int tileColumn) {
        return Position.fromCoordinates(NUMBER_OF_ROWS - tileRow - 1, tileColumn);
    }

}
