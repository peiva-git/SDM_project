package it.units.sdm.project.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;

public class GameOverDialog extends Dialog {
    @NotNull
    private final FreedomGame game;

    public GameOverDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        button("Play again", "Play again");
        button("Quit", "Quit");
        setSize(500, 200);
        Player winner = game.getCurrentWinner();
        if (winner != null) {
            text("The winner is " + winner);
        } else {
            text("Tie!");
        }
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (!(object instanceof String)) {
            return;
        }
        String message = (String) object;
        if (message.equals("Quit")) {
            Gdx.app.exit();
        } else {
            // play again
            game.setScreen(new GameScreen(game));
        }
    }
}
