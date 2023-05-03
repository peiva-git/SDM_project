package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import it.units.sdm.project.core.board.Stone;
import it.units.sdm.project.core.game.Player;
import org.jetbrains.annotations.NotNull;

public class GameScreen implements Screen {

    private static final int NUMBER_OF_ROWS = 8;
    private static final int NUMBER_OF_COLUMNS = 8;
    @NotNull
    private final OrthographicCamera camera;
    @NotNull
    private final OrthogonalTiledMapRenderer render;
    @NotNull
    private final Stage stage;

    @NotNull
    private final GuiBoard board;

    @NotNull
    private final Texture blackStoneImage = new Texture(Gdx.files.internal("./assets/circle2.png"));
    @NotNull
    private final Texture whiteStoneImage = new Texture(Gdx.files.internal("./assets/redCircle.png"));

    @NotNull
    private final Player whitePlayer = new Player(Stone.Color.WHITE, "Mario", "Rossi");
    @NotNull
    private final Player blackPlayer = new Player(Stone.Color.BLACK, "Loll", "Rossi");
    @NotNull
    private Player currentPlayer = whitePlayer;

    public GameScreen() {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        board = new GuiBoard(NUMBER_OF_ROWS,NUMBER_OF_COLUMNS);

        render = new OrthogonalTiledMapRenderer(board.getTiledMap());
        render.setView(camera);
        camera.position.set(32*4,32*4,0);
        stage = new TiledMapStage(board.getTiledMap());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        render.setView(camera);
        stage.act();
        render.render();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
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
        render.dispose();
        board.dispose();
    }


    private static class TiledMapActor extends Actor {

        @NotNull
        private final TiledMap tiledMap;
        @NotNull
        private final TiledMapTileLayer tiledLayer;
        @NotNull
        private final TiledMapTileLayer.Cell cell;

        public TiledMapActor(@NotNull TiledMap tiledMap, @NotNull TiledMapTileLayer tiledLayer, TiledMapTileLayer.@NotNull Cell cell) {
            this.tiledMap = tiledMap;
            this.tiledLayer = tiledLayer;
            this.cell = cell;
        }
    }

    private class TiledMapClickListener extends ClickListener {

        @NotNull
        private final TiledMapActor actor;

        public TiledMapClickListener(@NotNull TiledMapActor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            for (int i = 0; i < actor.tiledLayer.getWidth(); i++) {
                for (int j = 0; j < actor.tiledLayer.getHeight(); j++) {
                    if (actor.cell.equals(actor.tiledLayer.getCell(i, j))) {
                        TiledMapTileLayer stonesLayer = (TiledMapTileLayer) actor.tiledMap.getLayers().get(1);
                        Sprite stoneImage;
                        if(currentPlayer.getColor() == Stone.Color.BLACK) {
                            stoneImage = new Sprite(blackStoneImage);
                        } else {
                            stoneImage = new Sprite(whiteStoneImage);
                        }
                        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                        cell.setTile(new StaticTiledMapTile(stoneImage));
                        stonesLayer.setCell(i, j, cell);
                        break;
                    }
                }
            }
            nextPlayer();
        }

        private void nextPlayer() {
            if(currentPlayer.getColor() == Stone.Color.BLACK)  {
                currentPlayer = whitePlayer;
            } else {
                currentPlayer = blackPlayer;
            }
        }

    }

    private class TiledMapStage extends Stage {

        private final TiledMap tiledMap;

        public TiledMapStage(TiledMap tiledMap) {
            super.getViewport().setCamera(camera);
            this.tiledMap = tiledMap;
            TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            createActorsForLayer(boardLayer);
        }

        private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
            for (int x = 0; x < tiledLayer.getWidth(); x++) {
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                    TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell);
                    actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                            tiledLayer.getTileHeight());
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor);
                    actor.addListener(eventListener);
                }
            }
        }
    }


}
