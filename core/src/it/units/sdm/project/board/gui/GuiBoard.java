package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidBoardSizeException;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class GuiBoard implements Board<GuiStone> {

    private final static int MAX_NUMBER_OF_ROWS = 26;
    private final int numberOfRows;
    private final int numberOfColumns;
    @NotNull
    private TiledMap tiledMap = new TiledMap();

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

        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(numberOfRows, numberOfColumns, 32, 32);
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (j % 2 != 0) {
                    if (i % 2 != 0) {
                        cell.setTile(new StaticTiledMapTile(whiteTextureRegion));
                    } else {
                        cell.setTile(new StaticTiledMapTile(blackTextureRegion));
                    }
                } else {
                    if (i % 2 != 0) {
                        cell.setTile(new StaticTiledMapTile(blackTextureRegion));
                    } else {
                        cell.setTile(new StaticTiledMapTile(whiteTextureRegion));
                    }
                }
                layer.setCell(i, j, cell);
            }
        }
        layers.add(layer);
        layers.add(new TiledMapTileLayer(numberOfColumns, numberOfColumns, 32, 32));
        tiledMap.getLayers().get(0).setName("board");
        tiledMap.getLayers().get(1).setName("stones");
    }

    @NotNull
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    @Override
    public int getNumberOfRows() {
        return numberOfRows;
    }

    @Override
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public boolean isCellOccupied(@NotNull Position position) {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        TiledMapTileLayer.Cell cell = stonesLayer.getCell(position.getRow(), position.getColumn());
        return cell != null;
    }

    @Override
    public void clearCell(@NotNull Position position) {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        stonesLayer.setCell(position.getRow(), position.getColumn(), null);
    }

    @Override
    public void clearBoard() {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        for (int i = 0; i < stonesLayer.getWidth(); i++) {
            for (int j = 0; j < stonesLayer.getHeight(); j++) {
                clearCell(Position.fromCoordinates(i, j));
            }
        }
    }

    @Override
    public void putPiece(@NotNull GuiStone piece, @NotNull Position position) {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(piece.getStaticTiledMapTile());
        stonesLayer.setCell(position.getRow(), position.getColumn(), cell);
    }

    @Override
    public @Nullable GuiStone getPiece(@NotNull Position position) {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        TiledMapTileLayer.Cell cell = stonesLayer.getCell(position.getRow(), position.getColumn());
        if(cell == null) return null;
        StaticTiledMapTile piece = (StaticTiledMapTile) cell.getTile();
        Color pieceColor = (Color) piece.getProperties().get("color");
        return new GuiStone(pieceColor,piece);
    }


    @Override
    public @NotNull Set<Position> getPositions() {
        Set<Position> positions =new TreeSet<>();
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                positions.add(Position.fromCoordinates(i,j));
            }
        }
        return positions;
    }

    @Override
    public boolean hasBoardMoreThanOneFreeCell() {
        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("stones");
        int emptyCellsCounter = 0;
        for (int i = 0; i < stonesLayer.getWidth(); i++) {
            for (int j = 0; j < stonesLayer.getHeight(); j++) {
                if(!isCellOccupied(Position.fromCoordinates(i,j))) emptyCellsCounter++;
            }
        }
        return emptyCellsCounter > 1;
    }

    @Override
    public boolean areAdjacentCellsOccupied(Position position) {
        return getAdjacentPositions(position).stream()
                .allMatch(this::isCellOccupied);
    }

    @Override
    public @NotNull Set<Position> getAdjacentPositions(Position position) {
        if (isPositionOutOfBoardBounds(position)) {
            throw new InvalidPositionException("The specified position is outside the board");
        }
        Set<Position> adjacentPositions = new HashSet<>(8);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (i == 0 && j == 0) continue;
                    if (position.getRow() + i <= numberOfRows && position.getColumn() + j <= numberOfColumns) {
                        adjacentPositions.add(Position.fromCoordinates(position.getRow() + i, position.getColumn() + j));
                    }
                } catch (InvalidPositionException ignored) {
                }
            }
        }
        return adjacentPositions;
    }

    private boolean isPositionOutOfBoardBounds(@NotNull Position position) {
        return position.getRow() > numberOfRows || position.getColumn() > numberOfColumns;
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
