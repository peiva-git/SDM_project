package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.jetbrains.annotations.NotNull;

public class MainMenuScreen implements Screen {
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Table initialMenu;
    @NotNull
    private final Skin skin;
    @NotNull
    private final Stage stage;

    public MainMenuScreen(final @NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(1200, 640), new SpriteBatch());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        initialMenu = new Table();
        initMenu();
        stage.addActor(initialMenu);
        Gdx.input.setInputProcessor(stage);
    }

    private void initMenu() {
        initialMenu.setFillParent(true);
        Image image = new Image(new Texture(Gdx.files.internal("freedom_logo.png")));
        Label nameLabel = new Label("Tap anywhere to begin!", skin);
        nameLabel.setColor(Color.BLACK);
        nameLabel.setFontScale((float) 1.5);
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
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
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
        stage.dispose();
        stage.getBatch().dispose();
    }
}
