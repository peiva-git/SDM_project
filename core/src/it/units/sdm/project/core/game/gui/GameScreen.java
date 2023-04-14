package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import org.jetbrains.annotations.NotNull;

public class GameScreen implements Screen {

    @NotNull
    private final Game game;
    @NotNull
    private final OrthographicCamera camera;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 100;
    private static final int LAYER_COUNT = 1;
    @NotNull
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer render;
    private static final float MOVEMENT_SPEED = 5f;

    public GameScreen(@NotNull final Game game) {
        this.game = game;
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
        TextureRegion blackTextureRegion = new TextureRegion(new Texture(blackSquare),0,0,32,32);
        TextureRegion whiteTextureRegion = new TextureRegion(new Texture(whiteSquare),0,0,32,32);

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        for (int l = 0; l < LAYER_COUNT; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH, HEIGHT, 32,
                    32);
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
        }
        render = new OrthogonalTiledMapRenderer(map);
        render.setView(camera);
        camera.translate((float) Gdx.graphics.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2);
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
}
