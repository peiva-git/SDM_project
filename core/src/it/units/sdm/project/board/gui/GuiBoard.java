package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

import static it.units.sdm.project.game.gui.FreedomGame.NUMBER_OF_ROWS;

/**
 * This class represents a board object to be used as part of a libGDX scene2d graphical user interface
 * For more information about libGDX, refer to their <a href="https://libgdx.com/wiki/">official page</a>.
 */
public class GuiBoard extends Table implements Board<GuiStone> {

    /**
     * Tile size to be used by the board, in pixels
     */
    public static final int TILE_SIZE = 32;

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
     * Creates a new Board instance to be used in a libGDX scene2d GUI
     * @param skin The skin used by the board, impacting the board's appearance
     * @param numberOfRows Number of rows the board is going to have. Should be equal to the number of columns
     * @param numberOfColumns Number of columns the board is going to have. Should be equal to the number of columns
     */
    public GuiBoard(@NotNull Skin skin, int numberOfRows, int numberOfColumns) {
        super(skin);
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
    public static Position fromTileCoordinatesToBoardPosition(int tileRow, int tileColumn) {
        return Position.fromCoordinates(NUMBER_OF_ROWS - tileRow - 1, tileColumn);
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
     * Sets a listener for the whole board
     * @param clickListener Listener to be set
     */
    @SuppressWarnings("unchecked")
    public void setClickListener(@NotNull ClickListener clickListener) {
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
            if (getPositionFromTile(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                tileAndPiece.setUserObject(null);
                if (tileAndPiece.getChildren().size == 2) {
                    tileAndPiece.removeActorAt(1, false);
                } else {
                    Gdx.app.debug(GUI_BOARD_TAG, "No piece at position " + position + ", already clear");
                }
                return;
            }
        }
        throw new InvalidPositionException("Invalid position, no matching cell found");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putPiece(@NotNull GuiStone piece, @NotNull Position position) throws InvalidPositionException {
        for(Cell<Actor> cell : getCells()) {
            if(getPositionFromTile(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                tileAndPiece.addActor(piece.getActor());
                tileAndPiece.setUserObject(piece.getColor());
                return;
            }
        }
        throw new InvalidPositionException("Invalid position, no matching cell found");
    }

    @NotNull
    private Position getPositionFromTile(@NotNull Cell<Actor> tile) {
        return Position.fromCoordinates(numberOfRows - tile.getRow() - 1, tile.getColumn());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable GuiStone getPiece(@NotNull Position position) throws InvalidPositionException {
        for(Cell<Actor> cell : getCells()) {
            if(getPositionFromTile(cell).equals(position)) {
                Group tileAndPiece = (Group) cell.getActor();
                if (tileAndPiece.getChildren().size < 2) {
                    return null;
                } else {
                    Image image = (Image) tileAndPiece.getChild(1);
                    return new GuiStone((Color) tileAndPiece.getUserObject(), image);
                }
            }
        }
        throw new InvalidPositionException("Invalid position, no matching cell found");
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
