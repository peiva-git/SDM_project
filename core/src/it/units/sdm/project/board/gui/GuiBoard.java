package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTable;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a {@link Board} object to be used as part of a libGDX scene2d graphical user interface
 * For more information about libGDX, refer to their <a href="https://libgdx.com/wiki/">official page</a>.
 */
public class GuiBoard<P extends GuiStone> extends VisTable implements Board<P> {
    private static final String INVALID_BOARD_POSITION_MESSAGE = "Invalid board position";
    /**
     * Tile size to be used by the {@link Board}, in pixels
     */
    public static final int TILE_SIZE = 64;

    /**
     * Dark tile default {@link Color}
     */
    public static final Color DARK_TILE = new Color(181 / 255f, 136 / 255f, 99 / 255f, 1);

    /**
     * Light tile default {@link Color}
     */
    public static final Color LIGHT_TILE = new Color(240 / 255f, 217 / 255f, 181 / 255f, 1);
    private static final String GUI_BOARD_TAG = "GUI_BOARD";
    private final int numberOfRows;
    private final int numberOfColumns;

    /**
     * Creates a new {@link Board} instance to be used in a libGDX scene2d GUI
     * @param numberOfRows Number of rows the {@link Board} is going to have. Should be equal to the number of columns
     * @param numberOfColumns Number of columns the {@link Board} is going to have. Should be equal to the number of columns
     */
    public GuiBoard(int numberOfRows, int numberOfColumns) {
        if (!isBoardSizeValid(numberOfRows, numberOfColumns)) {
            throw new InvalidBoardSizeException("The size of the board must be at least " + MIN_BOARD_SIZE + "x" + MIN_BOARD_SIZE
                    + " and at most " + MAX_BOARD_SIZE + "x" + MAX_BOARD_SIZE);
        }
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        initBoard();
    }

    /**
     * This method obtains a {@link Position} from the given tile coordinates.
     * Each tile in a {@link Table} layout has its own pair of coordinates.
     * Refer to the <a href="https://libgdx.com/wiki/graphics/2d/scene2d/table">Table documentation</a>
     * to find out how they're specified.
     * @param tileRow The tile row coordinate, starting from index zero
     * @param tileColumn The tile column coordinate, starting from index zero
     * @return The resulting {@link Position}
     */
    @NotNull
    public Position fromTileCoordinatesToBoardPosition(int tileRow, int tileColumn) {
        return Position.fromCoordinates(numberOfRows - tileRow - 1, tileColumn);
    }

    private void initBoard() {
        for (int i = 0; i < numberOfRows; i++) {
            row();
            if (isIndexEven(i)) {
                initBoardColumns(DARK_TILE, LIGHT_TILE);
            } else {
                initBoardColumns(LIGHT_TILE, DARK_TILE);
            }
        }
    }

    private void initBoardColumns(Color oddTilesColor, Color evenTilesColor) {
        for (int j = 0; j < numberOfColumns; j++) {
            Actor tile = new Image(getSkin().get("white_tile", TextureRegion.class));
            if (isIndexEven(j)) {
                tile.setColor(evenTilesColor);
            } else {
                tile.setColor(oddTilesColor);
            }
            Actor tileAndPiece = new Stack(tile);
            add(tileAndPiece).size(TILE_SIZE);
        }
    }

    /**
     * Sets a listener for the whole {@link Board}
     * @param clickListener Listener to be set
     */
    @SuppressWarnings("unchecked")
    public void setTileClickListener(@NotNull ClickListener clickListener) {
        for(Cell<Actor> cell : getCells()) {
            Actor tileAndPiece = cell.getActor();
            tileAndPiece.clearListeners();
            tileAndPiece.addListener(clickListener);
        }
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clearCell(@NotNull Position position) throws InvalidPositionException {
        for (Cell<Actor> cell : getCells()) {
            if (getPositionFromCell(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                if (isCellOccupied(tileAndPiece)) {
                    tileAndPiece.removeActorAt(1, false);
                } else {
                    Gdx.app.debug(GUI_BOARD_TAG, "No piece at position " + position + ", already clear");
                }
                return;
            }
        }
        throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
    }

    private static boolean isCellOccupied(@NotNull Group tileAndPiece) {
        return tileAndPiece.getChildren().size == 2;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putPiece(@NotNull P piece, @NotNull Position position) throws InvalidPositionException {
        for(Cell<Actor> cell : getCells()) {
            if(getPositionFromCell(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                tileAndPiece.addActor(piece);
                return;
            }
        }
        throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
    }

    @NotNull
    private Position getPositionFromCell(@NotNull Cell<Actor> tile) {
        return Position.fromCoordinates(numberOfRows - tile.getRow() - 1, tile.getColumn());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable P getPiece(@NotNull Position position) throws InvalidPositionException {
        for(Cell<Actor> cell : getCells()) {
            if(getPositionFromCell(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                if (!isCellOccupied(tileAndPiece)) {
                    return null;
                } else {
                    return (P) tileAndPiece.getChild(1);
                }
            }
        }
        throw new InvalidPositionException(INVALID_BOARD_POSITION_MESSAGE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Set<Position> getPositions() {
        Set<Position> positions = new TreeSet<>();
        for(Cell<Actor> cell : getCells()) {
            positions.add(Position.fromCoordinates(cell.getRow(), cell.getColumn()));
        }
        return positions;
    }

}
