package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
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
        Label whitePlayerName = new Label("First player's name", VisUI.getSkin());
        Label whitePlayerSurname = new Label("First player's surname", VisUI.getSkin());
        VisValidatableTextField whitePlayerNameText = new VisValidatableTextField(input -> {
            if (input.isBlank() || input.isEmpty()) {
                return false;
            }
            return true;
        });
        SimpleFormValidator form = new SimpleFormValidator(whitePlayerNameText, new Label("test", VisUI.getSkin()));
        Table container = new Table();

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
