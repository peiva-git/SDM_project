package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * {@link FreedomGame}'s splash {@link Screen}. It is the first displayed {@link Screen}.
 */
public class SplashScreen implements Screen {
    @NotNull
    private final VisTable initialMenu;
    @NotNull
    private final Stage stage;
    @NotNull
    private final TextureAtlas atlas;
    private static final int MAIN_MENU_SCREEN_WORLD_WIDTH = 800;
    private static final int MAIN_MENU_SCREEN_WORLD_HEIGHT = 600;

    /**
     * Creates an instance of a {@link SplashScreen}
     * @param game The {@link FreedomGame} using this {@link Screen}
     */
    public SplashScreen(final @NotNull FreedomGame game) {
        stage = new Stage(new FitViewport(MAIN_MENU_SCREEN_WORLD_WIDTH, MAIN_MENU_SCREEN_WORLD_HEIGHT), new SpriteBatch());
        atlas = new TextureAtlas("freedom.atlas");
        initialMenu = new VisTable();
        initMenu();
        stage.addActor(initialMenu);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayersNamesFormScreen(game));
                dispose();
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    private void initMenu() {
        initialMenu.setFillParent(true);
        Actor image = new Image(atlas.findRegion("freedom_logo"));
        VisLabel nameLabel = new VisLabel("Tap anywhere to begin!");
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
        stage.getBatch().dispose();
        atlas.dispose();
    }
}
