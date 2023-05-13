package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.Stone;
import it.units.sdm.project.board.gui.GuiStone;
import it.units.sdm.project.board.terminal.MapBoard;
import it.units.sdm.project.enums.GameStatus;
import it.units.sdm.project.game.FreedomPointsCounter;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.interfaces.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class GameScreen implements Screen {


    private static final int NUMBER_OF_ROWS = 8;
    private static final int NUMBER_OF_COLUMNS = 8;
    public static final int TILE_SIZE = 75;
    @NotNull
    private final Table tableLayout;
    @NotNull
    private GameStatus gameStatus;
    @NotNull
    private final LinkedList<Move> playersMovesHistory = new LinkedList<>();
    @NotNull
    private final Stage stage;
    @NotNull
    private final Board<Stone> board;
    @NotNull
    private final Texture blackStoneImage = new Texture(Gdx.files.internal("circle2.png"));
    @NotNull
    private final Texture whiteStoneImage = new Texture(Gdx.files.internal("redCircle.png"));
    @NotNull
    private final Player whitePlayer = new Player(Color.WHITE, "Mario", "Rossi");
    @NotNull
    private final Player blackPlayer = new Player(Color.BLACK, "Lollo", "Rossi");

    public GameScreen() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        board = new MapBoard<>(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        Pixmap blackSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, TILE_SIZE, TILE_SIZE);
        TextureRegion blackTextureRegion = new TextureRegion(new Texture(blackSquare), 0, 0, TILE_SIZE, TILE_SIZE);
        TextureRegion whiteTextureRegion = new TextureRegion(new Texture(whiteSquare), 0, 0, TILE_SIZE, TILE_SIZE);
        tableLayout = new Table();
        tableLayout.setDebug(true);
        tableLayout.setFillParent(true);
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            tableLayout.row();
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Image tile = new Image(whiteTextureRegion);
                tableLayout.add(tile);
            }
        }
        stage.addActor(tableLayout);
        gameStatus = GameStatus.STARTED;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        switch (gameStatus) {
            case GAME_OVER:
                ScreenUtils.clear(Color.BLACK);
                Player winner = getWinner();
                System.out.println(winner);
                return;
            case LAST_MOVE:
                gameStatus = GameStatus.GAME_OVER;
            default:
        }
    }

    @Nullable
    private Player getWinner() {
        FreedomPointsCounter freedomPointsCounter = new FreedomPointsCounter(board);
        freedomPointsCounter.count();
        if (freedomPointsCounter.getWhitePlayerScore() > freedomPointsCounter.getBlackPlayerScore()) {
            return whitePlayer;
        } else if (freedomPointsCounter.getBlackPlayerScore() > freedomPointsCounter.getWhitePlayerScore()) {
            return blackPlayer;
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


    private class TiledMapClickListener extends ClickListener {

        @NotNull
        private final TiledMapActor actor;

        public TiledMapClickListener(@NotNull TiledMapActor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Player currentPlayer = nextPlayer();
            Position userInputPosition = getUserPosition();
            if (userInputPosition == null || board.isCellOccupied(userInputPosition)) return;

            if (gameStatus == GameStatus.NO_FREEDOM) {
                Set<Position> validPositions;
                validPositions = board.getAdjacentPositions(playersMovesHistory.getLast().getPosition());
                if (!validPositions.contains(userInputPosition)) return;
            }

            Sprite stoneImage;
            if (currentPlayer.getColor() == Color.BLACK) {
                stoneImage = new Sprite(blackStoneImage);
                board.putPiece(new GuiStone(Color.BLACK, new StaticTiledMapTile(stoneImage)), userInputPosition);
                playersMovesHistory.add(new Move(blackPlayer, userInputPosition));
            } else {
                stoneImage = new Sprite(whiteStoneImage);
                board.putPiece(new GuiStone(Color.WHITE, new StaticTiledMapTile(stoneImage)), userInputPosition);
                playersMovesHistory.add(new Move(whitePlayer, userInputPosition));
            }
            updateCurrentGameStatus();
            nextPlayer();
        }

        private @Nullable Position getUserPosition() {
            for (int i = 0; i < actor.getTiledLayer().getWidth(); i++) {
                for (int j = 0; j < actor.getTiledLayer().getHeight(); j++) {
                    if (actor.getCell().equals(actor.getTiledLayer().getCell(i, j))) {
                        return Position.fromCoordinates(i, j);
                    }
                }
            }
            return null;
        }

        private void updateCurrentGameStatus() {
            if (board.hasBoardMoreThanOneFreeCell()) {
                if (playersMovesHistory.isEmpty() || board.areAdjacentCellsOccupied(playersMovesHistory.getLast().getPosition())) {
                    gameStatus = GameStatus.FREEDOM;
                } else {
                    gameStatus = GameStatus.NO_FREEDOM;
                }
            } else {
                gameStatus = GameStatus.LAST_MOVE;
            }
        }

        @NotNull
        private Player nextPlayer() {
            try {
                Player previousPlayer = playersMovesHistory.getLast().getPlayer();
                if (previousPlayer.equals(whitePlayer)) return blackPlayer;
                return whitePlayer;
            } catch (NoSuchElementException exception) {
                return whitePlayer;
            }
        }

    }

    private class BoardTilesStage extends Stage {

        public BoardTilesStage(@NotNull TiledMapTileLayer boardLayer, @NotNull Camera camera) {
            super.getViewport().setCamera(camera);
            createActorsForLayer(boardLayer);
        }

        private void createActorsForLayer(@NotNull TiledMapTileLayer tiledLayer) {
            for (int i = 0; i < tiledLayer.getWidth(); i++) {
                for (int j = 0; j < tiledLayer.getHeight(); j++) {
                    TiledMapTileLayer.Cell cell = tiledLayer.getCell(i, j);
                    TiledMapActor actor = new TiledMapActor(tiledLayer, cell);
                    actor.setBounds(i * tiledLayer.getTileWidth(), j * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                            tiledLayer.getTileHeight());
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor);
                    actor.addListener(eventListener);
                }
            }
        }
    }

}
