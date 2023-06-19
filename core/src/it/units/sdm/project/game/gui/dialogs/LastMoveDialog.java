package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.kotcrab.vis.ui.widget.VisDialog;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * Last move {@link Dialog} for a {@link FreedomGame}.
 */
public class LastMoveDialog extends VisDialog {

    private static final String POSITIVE_TEXT = "Yes";
    private static final String NEGATIVE_TEXT = "No";
    private static final float PADDING = 20f;
    @NotNull
    private final FreedomGame game;

    /**
     * Creates a new last move {@link VisDialog} to use in a libGDX {@link com.badlogic.gdx.Screen}
     * @param game The {@link FreedomGame} which will use the {@link VisDialog}
     */
    public LastMoveDialog(@NotNull FreedomGame game) {
        super("LAST MOVE");
        this.game = game;
        text("Do you want to put the last stone?");
        button(POSITIVE_TEXT, POSITIVE_TEXT);
        button(NEGATIVE_TEXT, NEGATIVE_TEXT);
        getContentTable().pad(PADDING);
        getButtonsTable().pad(PADDING);
    }
    @Override
    protected void result(Object object) {
        super.result(object);
        if(!(object instanceof String)) {
            return;
        }
        String message = (String) object;
        hide();
        if (message.equals(NEGATIVE_TEXT)) {
            GameOverDialog gameOverDialog = new GameOverDialog(game);
            gameOverDialog.show(getStage());
        }
    }
}
