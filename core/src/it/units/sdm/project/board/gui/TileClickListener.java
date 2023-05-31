package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.units.sdm.project.board.FreedomBoardHelper;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import it.units.sdm.project.game.gui.GameScreen;
import it.units.sdm.project.game.gui.dialogs.GameOverDialog;
import it.units.sdm.project.game.gui.dialogs.LastMoveDialog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

class TileClickListener extends ClickListener {
    public static final Color HIGHLIGHT_DARK_TILE = new Color(105 / 255f, 105 / 255f, 105 / 255f, 255 / 255f);
    public static final Color HIGHLIGHT_LIGHT_TILE = new Color(169 / 255f, 169 / 255f, 169 / 255f, 255 / 255f);
    private final @NotNull TextArea firstTextArea;
    private final @NotNull Table boardLayout;
    private Stack tileAndPiece;
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Skin skin;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Texture blackStoneImage = new Texture(Gdx.files.internal("circle2.png"));
    @NotNull
    private final Texture whiteStoneImage = new Texture(Gdx.files.internal("redCircle.png"));

    public TileClickListener(@NotNull FreedomGame game, @NotNull Skin skin, @NotNull Stage stage, @NotNull Table container) {
        this.game = game;
        this.skin = skin;
        this.stage = stage;
        firstTextArea = (TextArea) container.getChild(0);
        boardLayout = (Table) container.getChild(1);
    }

    @Override
    public void clicked(@NotNull InputEvent event, float x, float y) {
        Player currentPlayer = game.nextPlayer();
        tileAndPiece = (Stack) event.getListenerActor();
        Cell<Actor> clickedTile = boardLayout.getCell(tileAndPiece);
        Position inputPosition = getPositionFromTile(clickedTile);
        if (game.getGameStatus() == GameStatus.FREEDOM) {
            if (game.getBoard().isCellOccupied(inputPosition)) {
                return;
            } else {
                resetCurrentlyHighlightedCells();
                putStoneOnTheBoard(currentPlayer, inputPosition);
                highlightValidPositionsForNextMove();
            }
        } else if (game.getGameStatus() == GameStatus.NO_FREEDOM) {
            Set<Position> allowedPositions = findAllowedPositionsFromLastPlayedPosition();
            if (!allowedPositions.contains(inputPosition)) {
                return;
            } else {
                resetCurrentlyHighlightedCells();
                putStoneOnTheBoard(currentPlayer, inputPosition);
                highlightValidPositionsForNextMove();
            }
        } else if (game.getGameStatus() == GameStatus.PLAY_LAST_MOVE) {
            Set<Position> allowedPositions = findAllowedPositionsFromLastPlayedPosition();
            if (!allowedPositions.contains(inputPosition)) {
                return;
            } else {
                resetCurrentlyHighlightedCells();
                putStoneOnTheBoard(currentPlayer, inputPosition);
                GameOverDialog gameOverDialog = new GameOverDialog(game, skin);
                gameOverDialog.show(stage);
            }
        }

        if (game.getGameStatus() == GameStatus.LAST_MOVE) {
            LastMoveDialog lastMoveDialog = new LastMoveDialog(game, skin);
            lastMoveDialog.show(stage);
        }
        super.clicked(event, x, y);
    }

    @NotNull
    private Set<Position> findAllowedPositionsFromLastPlayedPosition() {
        return FreedomBoardHelper.getAdjacentPositions(game.getBoard(), game.getPlayersMovesHistory().getLast().getPosition()).stream()
                .filter(position -> !game.getBoard().isCellOccupied(position))
                .collect(Collectors.toSet());
    }

    private void putStoneOnTheBoard(@NotNull Player currentPlayer, @NotNull Position inputPosition) {
        if (currentPlayer.getColor() == Color.WHITE) {
            game.getBoard().putPiece(new Stone(Color.WHITE), inputPosition);
            game.getPlayersMovesHistory().add(new Move(game.getWhitePlayer(), inputPosition));
            Actor whiteStone = new Image(whiteStoneImage);
            tileAndPiece.addActor(whiteStone);
            printFormattedMoveOnTheLogArea(inputPosition);
        } else {
            game.getBoard().putPiece(new Stone(Color.BLACK), inputPosition);
            game.getPlayersMovesHistory().add(new Move(game.getBlackPlayer(), inputPosition));
            Actor blackStone = new Image(blackStoneImage);
            tileAndPiece.addActor(blackStone);
            firstTextArea.appendText("     " + inputPosition + "\n");
        }
        game.getStatusHandler().proceedToNextState();
    }

    private void printFormattedMoveOnTheLogArea(@NotNull Position inputPosition) {
        long currentStep = game.getPlayersMovesHistory().stream()
                .filter(move -> move.getPlayer().getColor() == Color.WHITE)
                .count();
        if (currentStep < 10) {
            firstTextArea.appendText("  " + currentStep + ". " + inputPosition);
        } else if (currentStep < 100) {
            firstTextArea.appendText(" " + currentStep + ". " + inputPosition);
        } else {
            firstTextArea.appendText(currentStep + ". " + inputPosition);
        }
    }

    @NotNull
    private Position getPositionFromTile(@NotNull Cell<Actor> tile) {
        return Position.fromCoordinates(NUMBER_OF_ROWS - tile.getRow() - 1, tile.getColumn());
    }

    @SuppressWarnings("unchecked")
    private void highlightValidPositionsForNextMove() {
        Set<Position> positionsToHighlight = findAllowedPositionsFromLastPlayedPosition();
        List<Cell<Actor>> cellsToHighlight = new ArrayList<>();
        for (int i = 0; i < boardLayout.getCells().size; i++) {
            Cell<Actor> cell = boardLayout.getCells().get(i);
            if (positionsToHighlight.contains(getPositionFromTile(cell))) {
                cellsToHighlight.add(cell);
            }
        }
        for (Cell<Actor> cellToHighlight : cellsToHighlight) {
            Stack tileAndPiece = (Stack) cellToHighlight.getActor();
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
    private void resetCurrentlyHighlightedCells() {
        for (int i = 0; i < boardLayout.getCells().size; i++) {
            Cell<Actor> cell = boardLayout.getCells().get(i);
            Stack tileAndPiece = (Stack) cell.getActor();
            Actor tile = tileAndPiece.getChild(0);
            if (tile.getColor().equals(HIGHLIGHT_DARK_TILE)) {
                tile.setColor(GameScreen.DARK_TILE);
            } else if (tile.getColor().equals(HIGHLIGHT_LIGHT_TILE)) {
                tile.setColor(GameScreen.LIGHT_TILE);
            }
        }
    }
}
