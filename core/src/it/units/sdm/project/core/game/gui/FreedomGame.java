package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class FreedomGame extends ApplicationAdapter {

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer render;
    private final static int width = 150, height = 100, layercount = 1;
    private TiledMap map;

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w, h);

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
        for (int l = 0; l < layercount; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(width, height, 32,
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
    public void dispose() {
        render.dispose();
        map.dispose();
    }

    private static final float movmentspeed = 5f;

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_TEXTURE10);
        camera.update();
        render.setView(camera);
        render.render();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-movmentspeed, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(movmentspeed, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, movmentspeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -movmentspeed);
        }
    }
}
