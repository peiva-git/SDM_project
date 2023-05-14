package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GameScreen implements Screen {


    public static final int NUMBER_OF_ROWS = 8;
    public static final int NUMBER_OF_COLUMNS = 8;
    public static final int TILE_SIZE = 75;
    @NotNull
    private final Table tableLayout;
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Stage stage;
    @NotNull
    private final Texture blackStoneImage = new Texture(Gdx.files.internal("circle2.png"));
    @NotNull
    private final Texture whiteStoneImage = new Texture(Gdx.files.internal("redCircle.png"));

    public GameScreen(@NotNull FreedomGame game) {
        this.game = game;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        Pixmap blackSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        TextureRegion blackTextureRegion = new TextureRegion(new Texture(blackSquare), 0, 0, TILE_SIZE, TILE_SIZE);
        TextureRegion whiteTextureRegion = new TextureRegion(new Texture(whiteSquare), 0, 0, TILE_SIZE, TILE_SIZE);
        tableLayout = new Table();
        tableLayout.setFillParent(true);
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            tableLayout.row();
            if (isIndexEven(i)) {
                for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    Image tile;
                    if (isIndexEven(j)) {
                        tile = new Image(whiteTextureRegion);
                    } else {
                        tile = new Image(blackTextureRegion);
                    }
                    tile.addListener(new TileClickListener(tile));
                    tableLayout.add(tile);
                }
            } else {
                for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    Image tile;
                    if (isIndexEven(j)) {
                        tile = new Image(blackTextureRegion);
                    } else {
                        tile = new Image(whiteTextureRegion);
                    }
                    tile.addListener(new TileClickListener(tile));
                    tableLayout.add(tile);
                }
            }
        }
        stage.addActor(tableLayout);
        Gdx.input.setInputProcessor(stage);
    }

    private static boolean isIndexEven(int i) {
        return i % 2 == 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        switch (game.getGameStatus()) {
            case GAME_OVER:
                ScreenUtils.clear(Color.BLACK);
                Player winner = getWinner();
                System.out.println(winner);
                return;
            case LAST_MOVE:
                game.setGameStatus(GameStatus.GAME_OVER);
            default:
        }
    }

    @Nullable
    private Player getWinner() {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(game.getBoard());
        freedomPointsCounter.count();
        if (freedomPointsCounter.getWhitePlayerScore() > freedomPointsCounter.getBlackPlayerScore()) {
            return game.getWhitePlayer();
        } else if (freedomPointsCounter.getBlackPlayerScore() > freedomPointsCounter.getWhitePlayerScore()) {
            return game.getBlackPlayer();
        }
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        stage.getBatch().dispose();
    }

    private class TileClickListener extends ClickListener {
        @NotNull
        private final Image tile;

        public TileClickListener(@NotNull Image tile) {
            this.tile = tile;
        };

        @Override
        public void clicked(@NotNull InputEvent event, float x, float y) {
            Player currentPlayer = game.nextPlayer();
            Actor clickedActor = event.getListenerActor();
            Cell<Actor> clickedTile = tableLayout.getCell(clickedActor);
            System.out.println("Row: " + clickedTile.getRow());
            System.out.println("Column: " + clickedTile.getColumn());
            Position inputPosition = Position.fromCoordinates(NUMBER_OF_ROWS - clickedTile.getRow(), clickedTile.getColumn());
            System.out.println(inputPosition);
            if (game.getGameStatus() == GameStatus.NO_FREEDOM) {
                Set<Position> validPositions = game.getBoard().getAdjacentPositions(game.getPlayersMovesHistory().getLast().getPosition());
                if (!validPositions.contains(inputPosition)) return;
            }

            Sprite stoneImage;
            if (currentPlayer.getColor() == Color.BLACK) {
                stoneImage = new Sprite(blackStoneImage);
                game.getBoard().putPiece(new Stone(Color.BLACK), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getBlackPlayer(), inputPosition));
            } else {
                stoneImage = new Sprite(whiteStoneImage);
                game.getBoard().putPiece(new Stone(Color.WHITE), inputPosition);
                game.getPlayersMovesHistory().add(new Move(game.getWhitePlayer(), inputPosition));
            }
            game.updateCurrentGameStatus();
            System.out.println(game.getBoard());
            super.clicked(event, x, y);
        }
    }
}
