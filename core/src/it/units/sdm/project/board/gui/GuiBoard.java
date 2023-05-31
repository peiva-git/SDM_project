package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.SnapshotArray;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

public class GuiBoard extends Table implements Board<GuiStone> {

    public static final int TILE_SIZE = 75;
    private final int numberOfRows;
    @NotNull
    private final Color darkTile;
    @NotNull
    private final Color lightTile;
    private final int numberOfColumns;
    @NotNull
    private Texture whiteSquareTexture;

    public GuiBoard(@NotNull Skin skin, int numberOfRows, int numberOfColumns, @NotNull Color darkTile, @NotNull Color lightTile) {
        super(skin);
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.darkTile = darkTile;
        this.lightTile = lightTile;
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
                initBoardColumns(darkTile, lightTile);
            } else {
                initBoardColumns(lightTile, darkTile);
            }
        }
    }

    private void initBoardColumns(Color oddTilesColor, Color evenTilesColor) {
        for (int j = 0; j < numberOfColumns; j++) {
            TextureRegion whiteTextureRegion = new TextureRegion(whiteSquareTexture, 0, 0, TILE_SIZE, TILE_SIZE);
            Actor tile = new Image(whiteTextureRegion);
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
            if (checkCellPosition(position, cell)) {
                Stack stack = (Stack) cell.getActor();
                stack.setUserObject(null);
                Actor child = stack.getChild(1);
                if(child != null) {
                    child.remove();
                }
            }
        }
    }

    private static boolean checkCellPosition(@NotNull Position position, Cell cell) {
        return cell.getRow() == position.getRow() && cell.getColumn() == position.getColumn();
    }

    @Override
    public void clearBoard() {
        for (Cell cell : getCells()) {
            clearCell(Position.fromCoordinates(cell.getRow(), cell.getColumn()));
        }
    }

    @Override
    public void putPiece(@NotNull GuiStone piece, @NotNull Position position) throws InvalidPositionException {
        for(Cell cell : getCells()) {
            if(checkCellPosition(position, cell)) {
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
            if(checkCellPosition(position, cell)) {
                Stack stack = (Stack) cell.getActor();
                SnapshotArray<Actor> actorSnapshotArray = stack.getChildren();
                if(actorSnapshotArray.size == 1) return null;
                Image image = (Image) stack.getChild(1);
                if(image == null) return null;
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
