package it.units.sdm.project.board.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.board.Board;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.exceptions.InvalidPositionException;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

public class GuiFreedomBoard extends Table implements Board<GuiStone> {

    public static final int TILE_SIZE = 75;
    private final int numberOfRows;
    @NotNull
    private final Color darkTile;
    @NotNull
    private final Color lightTile;
    @NotNull
    private final FreedomGame game;
    private final int numberOfColumns;
    @NotNull
    private Texture whiteSquareTexture;

    public GuiFreedomBoard(@NotNull FreedomGame game, @NotNull Skin skin, int numberOfRows, int numberOfColumns, @NotNull Color darkTile, @NotNull Color lightTile) {
        super(skin);
        this.game = game;
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
            tileAndPiece.addListener(new TileClickListener(game, this));
            add(tileAndPiece).size(TILE_SIZE);
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
                Stack tileAndPiece = (Stack) cell.getActor();
                tileAndPiece.setUserObject(null);
                if (tileAndPiece.getChildren().size == 2) {
                    tileAndPiece.removeActorAt(1, false);
                } else {
                    Gdx.app.debug("GUI_BOARD", "No piece at position " + position + ", already clear");
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
                Stack tileAndPiece = (Stack) cell.getActor();
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
                Stack tileAndPiece = (Stack) cell.getActor();
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
