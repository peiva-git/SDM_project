package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_HEIGHT;
import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_WIDTH;

/**
 * {@link FreedomGame}'s menu {@link Screen}. It is displayed before the main {@link GameScreen}.
 */
public class MainMenuScreen implements Screen {
    @NotNull
    private final Table initialMenu;
    @NotNull
    private final Skin skin;
    @NotNull
    private final Stage stage;

    /**
     * Creates an instance of a {@link MainMenuScreen}
     * @param game The {@link FreedomGame} using this {@link Screen}
     */
    public MainMenuScreen(final @NotNull FreedomGame game) {
        stage = new Stage(new FitViewport(GAME_SCREEN_WORLD_WIDTH, GAME_SCREEN_WORLD_HEIGHT), new SpriteBatch());
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freedom.atlas"));
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        skin.addRegions(atlas);
        initialMenu = new Table();
        initMenu();
        stage.addActor(initialMenu);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    private void initMenu() {
        initialMenu.setFillParent(true);
        Actor image = new Image(skin.get("freedom_logo", TextureRegion.class));
        Label nameLabel = new Label("Tap anywhere to begin!", skin);
        nameLabel.setColor(Color.BLACK);
        nameLabel.setFontScale(1.5f);
        initialMenu.add(image);
        initialMenu.row();
        initialMenu.add(nameLabel);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        stage.getBatch().dispose();
        skin.dispose();
    }
}
