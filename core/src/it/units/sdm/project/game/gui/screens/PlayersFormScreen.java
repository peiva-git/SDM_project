package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_HEIGHT;
import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_WIDTH;

public class PlayersFormScreen implements Screen {

    @NotNull
    private final Stage stage;
    @NotNull
    private final Skin skin;

    public PlayersFormScreen() {
        stage = new Stage(new FitViewport(GAME_SCREEN_WORLD_WIDTH, GAME_SCREEN_WORLD_HEIGHT));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        skin.addRegions(atlas);
        VisUI.load(skin);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        VisUI.dispose();
        skin.dispose();
    }
}
