package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import it.units.sdm.project.game.gui.screens.GameScreen;
import org.jetbrains.annotations.NotNull;

/**
 * Game over dialog for a {@link FreedomGame}.
 */
public class GameOverDialog extends Dialog {
    private static final String NEGATIVE_TEXT = "Quit";
    private static final String POSITIVE_TEXT = "Play again";
    private static final int PADDING = 40;
    @NotNull
    private final FreedomGame game;

    /**
     * Creates a new game over dialog to use in a libGDX screen
     * @param game The game which will use the dialog
     * @param skin The skin to be used on the dialog
     */
    public GameOverDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        FreedomBoardStatusObserver statusObserver = new FreedomBoardStatusObserver((GuiBoard) game.getBoard());
        button("Play again", POSITIVE_TEXT);
        button("Quit", NEGATIVE_TEXT);
        pad(PADDING);
        Player winner = statusObserver.getCurrentWinner(game.getWhitePlayer(), game.getBlackPlayer());
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
