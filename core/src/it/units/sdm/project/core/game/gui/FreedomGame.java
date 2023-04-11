package it.units.sdm.project.core.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public class FreedomGame extends Game {

    @NotNull
    private SpriteBatch batch;
    @NotNull
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    public @NotNull SpriteBatch getBatch() {
        return batch;
    }

    public @NotNull BitmapFont getFont() {
        return font;
    }
}
