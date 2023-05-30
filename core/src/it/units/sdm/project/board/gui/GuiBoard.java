package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

public class GuiBoard extends Table implements Board<GuiStone> {

    public static final int TILE_SIZE = 75;
    public static final Color DARK_TILE = new Color(181 / 255f, 136 / 255f, 99 / 255f, 1);
    public static final Color LIGHT_TILE = new Color(240 / 255f, 217 / 255f, 181 / 255f, 1);
    private final int numberOfRows;
    private final int numberOfColumns;
    @NotNull
    private Texture whiteSquareTexture;

    public GuiBoard(@NotNull Skin skin, int numberOfRows, int numberOfColumns) {
        super(skin);
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        initTextures();
        initBoard();
    }

    private void initTextures() {
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        whiteSquareTexture = new Texture(whiteSquare);
        whiteSquare.dispose();
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
            TextureRegion whiteTextureRegion = new TextureRegion(whiteSquareTexture, 0, 0, TILE_SIZE, TILE_SIZE);
            Image tile = new Image(whiteTextureRegion);
            if (isIndexEven(j)) {
                tile.setColor(evenTilesColor);
            } else {
                tile.setColor(oddTilesColor);
            }
            Stack tileAndPiece = new Stack(tile);
            tileAndPiece.addListener(new TileClickListener(tileAndPiece));
            add(tileAndPiece).size(TILE_SIZE);
        }
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }

    @Override
    public boolean isCellOccupied(@NotNull Position position) throws InvalidPositionException {
        return getPiece(position) != null;
    }

    @Override
    public void clearCell(@NotNull Position position) throws InvalidPositionException {
        for (Cell cell : getCells()) {
            if (cell.getRow() == position.getRow() && cell.getColumn() == position.getColumn()) {
                Stack stack = (Stack) cell.getActor();
                stack.setName(null);
                stack.clearChildren();
            }
        }
    }

    @Override
    public void clearBoard() {
        for (Cell cell : getCells()) {
            Stack stack = (Stack) cell.getActor();
            stack.setName(null);
            stack.clearChildren();
        }
    }

    @Override
    public void putPiece(@NotNull GuiStone piece, @NotNull Position position) throws InvalidPositionException {
        for(Cell cell : getCells()) {
            if(cell.getRow() == position.getRow() && cell.getColumn() == position.getColumn()) {
                Stack stack = (Stack) cell.getActor();
                stack.addActor(piece.getImage());
                stack.setUserObject(piece.getColor());
                break;
            }
        }
    }

    @Override
    public @Nullable GuiStone getPiece(@NotNull Position position) throws InvalidPositionException {
        for(Cell cell : getCells()) {
            if(cell.getRow() == position.getRow() && cell.getColumn() == position.getColumn()) {
                Stack stack = (Stack) cell.getActor();
                Image image = (Image) stack.getChild(0);
                return new GuiStone((Color) stack.getUserObject(), image);
            }
        }
        return null;
    }

    @Override
    public @NotNull Set<Position> getPositions() {
        Set<Position> positions = new TreeSet<>();
        for(Cell cell : getCells()) {
            positions.add(Position.fromCoordinates(cell.getRow(), cell.getColumn()));
        }
        return positions;
    }

}
