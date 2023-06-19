package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kotcrab.vis.ui.widget.VisDialog;
import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.Player;
import it.units.sdm.project.game.gui.FreedomGame;
import it.units.sdm.project.game.gui.screens.PlayersNamesFormScreen;
import org.jetbrains.annotations.NotNull;

/**
 * Game over dialog for a {@link FreedomGame}.
 */
public class GameOverDialog extends VisDialog {
    private static final String NEGATIVE_TEXT = "Quit";
    private static final String POSITIVE_TEXT = "Play again";
    private static final float PADDING = 20;
    @NotNull
    private final FreedomGame game;

    /**
     * Creates a new game over {@link VisDialog} to use in a libGDX {@link com.badlogic.gdx.Screen}
     * @param game The {@link FreedomGame} which will use the {@link VisDialog}
     */
    public GameOverDialog(@NotNull FreedomGame game) {
        super("GAME OVER");
        this.game = game;
        FreedomBoardStatusObserver statusObserver = new FreedomBoardStatusObserver(game.getBoard());
        button(POSITIVE_TEXT, POSITIVE_TEXT);
        button(NEGATIVE_TEXT, NEGATIVE_TEXT);
        Player winner = statusObserver.getCurrentWinner(game.getWhitePlayer(), game.getBlackPlayer());
        if (winner != null) {
            text("The winner is " + winner);
        } else {
            text("Tie!");
        }
        getContentTable().pad(PADDING);
        getButtonsTable().pad(PADDING);
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
            game.reset();
            Screen previousScreen = game.getScreen();
            game.setScreen(new PlayersNamesFormScreen(game));
            previousScreen.dispose();
        }
    }
}
