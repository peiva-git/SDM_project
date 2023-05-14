package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

public class MainMenuScreen implements Screen {

    private static final int VIEWPORT_WIDTH = 800;
    private static final int VIEWPORT_HEIGHT = 400;
    @NotNull
    private final OrthographicCamera camera;
    @NotNull
    private final FreedomGame game;

    public MainMenuScreen(final @NotNull FreedomGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Welcome to th Freedom game!", 100, 150);
        game.getFont().draw(game.getBatch(), "Tap anywhere to begin!", 100, 100);
        game.getBatch().end();

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
    }
}
