package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.form.FormInputValidator;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_HEIGHT;
import static it.units.sdm.project.game.gui.screens.GameScreen.GAME_SCREEN_WORLD_WIDTH;

public class PlayersFormScreen implements Screen {

    private static final int MAX_NAME_LENGTH = 20;
    static final int FORM_SCREEN_WORLD_WIDTH = GAME_SCREEN_WORLD_WIDTH + 200;
    static final int FORM_SCREEN_WORLD_HEIGHT = GAME_SCREEN_WORLD_HEIGHT + 200;

    @NotNull
    private final Stage stage;
    @NotNull
    private final FreedomGame game;

    public PlayersFormScreen(@NotNull FreedomGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(FORM_SCREEN_WORLD_WIDTH, FORM_SCREEN_WORLD_HEIGHT));
        VisUI.load(VisUI.SkinScale.X2);

        VisTextButton continueButton = new VisTextButton("Let's play!");
        VisValidatableTextField whitePlayerName = new VisValidatableTextField();
        VisValidatableTextField whitePlayerSurname = new VisValidatableTextField();
        VisValidatableTextField blackPlayerName = new VisValidatableTextField();
        VisValidatableTextField blackPlayerSurname = new VisValidatableTextField();
        whitePlayerName.setMaxLength(MAX_NAME_LENGTH + 1);
        whitePlayerSurname.setMaxLength(MAX_NAME_LENGTH + 1);
        blackPlayerName.setMaxLength(MAX_NAME_LENGTH + 1);
        blackPlayerSurname.setMaxLength(MAX_NAME_LENGTH + 1);
        VisLabel errorMessage = new VisLabel();
        errorMessage.setColor(Color.RED);
        errorMessage.setWrap(true);

        VisTable container = new VisTable(true);
        container.setFillParent(true);
        container.padLeft(10).padRight(10);
        container.columnDefaults(0).left();
        container.add(new VisLabel("First player's name: "));
        container.add(whitePlayerName).expand().fill();
        container.row();
        container.add(new VisLabel("First player's surname: "));
        container.add(whitePlayerSurname).expand().fill();
        container.row();
        container.add(new VisLabel("Second player's name: "));
        container.add(blackPlayerName).expand().fill();
        container.row();
        container.add(new VisLabel("Second player's surname: "));
        container.add(blackPlayerSurname).expand().fill();
        container.row();
        container.add(errorMessage).expand().fill().colspan(2);
        container.row();
        container.add(continueButton).fill().expand().colspan(2).padBottom(3);

        SimpleFormValidator validator = new SimpleFormValidator(continueButton, errorMessage);
        validator.setSuccessMessage("All good!");
        validator.notEmpty(whitePlayerName, "First player's name can't be empty");
        validator.custom(whitePlayerName, new FormInputValidator("First player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(whitePlayerSurname, "First player's surname can't be empty");
        validator.custom(whitePlayerSurname, new FormInputValidator("First player's surname length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(blackPlayerName, "Second player's name can't be empty");
        validator.custom(blackPlayerName, new FormInputValidator("Second player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(blackPlayerSurname, "Second player's surname can't be empty");
        validator.custom(blackPlayerSurname, new FormInputValidator("Second player's surname length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO set obtained player names here
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
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
    }
}
