package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.game.FreedomBoardObserver;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import it.units.sdm.project.game.gui.screens.GameScreen;
import org.jetbrains.annotations.NotNull;

public class GameOverDialog extends Dialog {
    public static final String NEGATIVE_TEXT = "Quit";
    public static final String POSITIVE_TEXT = "Play again";
    @NotNull
    private final FreedomGame game;

    public GameOverDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        FreedomBoardObserver freedomBoardObserver = new FreedomBoardObserver((GuiBoard) game.getBoard());
        button("Play again", POSITIVE_TEXT);
        button("Quit", NEGATIVE_TEXT);
        setSize(500, 200);
        Player winner = freedomBoardObserver.getCurrentWinner(game.getFirstPlayer(), game.getSecondPlayer());
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
        if (message.equals(NEGATIVE_TEXT)) {
            Gdx.app.exit();
        } else if (message.equals(POSITIVE_TEXT)){
            game.setScreen(new GameScreen(game));
        }
    }
}
