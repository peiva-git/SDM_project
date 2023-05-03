package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import it.units.sdm.project.core.board.Position;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GuiBoard<P extends StaticTiledMapTile> implements Board<P> {

    private final static int MAX_NUMBER_OF_ROWS = 26;
    private final int numberOfRows;
    private final int numberOfColumns;
    @NotNull
    private TiledMap board = new TiledMap();

    public GuiBoard(int numberOfRows, int numberOfColumns) throws InvalidBoardSizeException {
        if (!isBoardSizeValid(numberOfRows, numberOfColumns)) {
            throw new InvalidBoardSizeException("The size of the board must be at least 1x1 and at most 26x26");
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initBoardWithEmptyCells();
    }

    private boolean isBoardSizeValid(int numberOfRows, int numberOfColumns) {
        return numberOfRows > 0 && numberOfRows <= MAX_NUMBER_OF_ROWS && (numberOfRows == numberOfColumns);
    }

    private void initBoardWithEmptyCells() {
        Pixmap blackSquare = new Pixmap(32, 32, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(32, 32, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, 32, 32);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, 32, 32);
        TextureRegion blackTextureRegion = new TextureRegion(new Texture(blackSquare), 0, 0, 32, 32);
        TextureRegion whiteTextureRegion = new TextureRegion(new Texture(whiteSquare), 0, 0, 32, 32);

        board = new TiledMap();
        MapLayers layers = board.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(numberOfRows, numberOfColumns, 32, 32);
        for (int x = 0; x < numberOfRows; x++) {
            for (int y = 0; y < numberOfColumns; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (y % 2 != 0) {
                    if (x % 2 != 0) {
                        cell.setTile(new StaticTiledMapTile(whiteTextureRegion));
                    } else {
                        cell.setTile(new StaticTiledMapTile(blackTextureRegion));
                    }
                } else {
                    if (x % 2 != 0) {
                        cell.setTile(new StaticTiledMapTile(blackTextureRegion));
                    } else {
                        cell.setTile(new StaticTiledMapTile(whiteTextureRegion));
                    }
                }
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);
        layers.add(new TiledMapTileLayer(numberOfColumns, numberOfColumns, 32, 32));
        board.getLayers().get(0).setName("board");
        board.getLayers().get(1).setName("stones");
    }

    @NotNull
    public TiledMap getTiledMap() {
        return board;
    }

    @Override
    public int getNumberOfRows() {
        return 0;
    }

    @Override
    public int getNumberOfColumns() {
        return 0;
    }

    @Override
    public boolean isCellOccupied(@NotNull Position position) {
        return false;
    }

    @Override
    public void clearCell(@NotNull Position position) {

    }

    @Override
    public void clearBoard() {

    }

    @Override
    public void putPiece(@NotNull P piece, @NotNull Position position) {

    }

    @Override
    public @Nullable P getPiece(@NotNull Position position) {
        return null;
    }

    @Override
    public @NotNull Set<Position> getPositions() {
        return null;
    }

    @Override
    public boolean hasBoardMoreThanOneFreeCell() {
        return false;
    }

    @Override
    public boolean areAdjacentCellsOccupied(Position position) {
        return false;
    }

    @Override
    public @NotNull Set<Position> getAdjacentPositions(Position position) {
        return null;
    }

    public void dispose() {
        board.dispose();
    }
}
