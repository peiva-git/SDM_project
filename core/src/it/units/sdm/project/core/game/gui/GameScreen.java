package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;

public class GameScreen implements Screen {

    @NotNull
    private final OrthographicCamera camera;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 100;

    private final Stage stage;

    @NotNull
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer render;
    private static final float MOVEMENT_SPEED = 5f;

    public GameScreen() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        Pixmap blackSquare = new Pixmap(32, 32, Pixmap.Format.RGB565);
        Pixmap whiteSquare = new Pixmap(32, 32, Pixmap.Format.RGB565);
        whiteSquare.setColor(Color.WHITE);
        whiteSquare.fillRectangle(0, 0, 32, 32);
        blackSquare.setColor(Color.BLACK);
        blackSquare.fillRectangle(0, 0, 32, 32);
        TextureRegion blackTextureRegion = new TextureRegion(new Texture(blackSquare), 0, 0, 32, 32);
        TextureRegion whiteTextureRegion = new TextureRegion(new Texture(whiteSquare), 0, 0, 32, 32);

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH, HEIGHT, 32, 32);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
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
        layers.add(new TiledMapTileLayer(WIDTH, HEIGHT, 32, 32));
        map.getLayers().get(0).setName("board");
        map.getLayers().get(1).setName("stones");

        render = new OrthogonalTiledMapRenderer(map);
        render.setView(camera);
        camera.translate((float) Gdx.graphics.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2);
        stage = new TiledMapStage(map);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_TEXTURE10);
        camera.update();
        render.setView(camera);
        stage.act();
        render.render();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-MOVEMENT_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(MOVEMENT_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, MOVEMENT_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -MOVEMENT_SPEED);
        }
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
        map.dispose();
    }


    private static class TiledMapActor extends Actor {

        private final TiledMap tiledMap;
        private final TiledMapTileLayer tiledLayer;

        private final TiledMapTileLayer.Cell cell;

        public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
            this.tiledMap = tiledMap;
            this.tiledLayer = tiledLayer;
            this.cell = cell;
        }
    }

    private static class TiledMapClickListener extends ClickListener {

        private final TiledMapActor actor;

        public TiledMapClickListener(TiledMapActor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            for (int i = 0; i < actor.tiledLayer.getWidth(); i++) {
                for (int j = 0; j < actor.tiledLayer.getHeight(); j++) {
                    if (actor.cell.equals(actor.tiledLayer.getCell(i, j))) {
                        TiledMapTileLayer piecesLayer = (TiledMapTileLayer) actor.tiledMap.getLayers().get(1);
                        Pixmap redSquare = new Pixmap(32, 32, Pixmap.Format.RGB565);
                        redSquare.setColor(Color.RED);
                        redSquare.fillRectangle(0, 0, 32, 32);
                        TextureRegion redTextureRegion = new TextureRegion(new Texture(redSquare), 0, 0, 32, 32);
                        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                        cell.setTile(new StaticTiledMapTile(redTextureRegion));
                        piecesLayer.setCell(i, j, cell);
                    }
                }
            }
        }
    }

    private class TiledMapStage extends Stage {

        private final TiledMap tiledMap;

        public TiledMapStage(TiledMap tiledMap) {
            super.getViewport().setCamera(camera);
            this.tiledMap = tiledMap;
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            createActorsForLayer(tiledLayer);
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
