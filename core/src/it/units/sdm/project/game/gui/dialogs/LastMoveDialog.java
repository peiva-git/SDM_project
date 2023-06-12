package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * Last move {@link Dialog} for a {@link FreedomGame}.
 */
public class LastMoveDialog extends Dialog {

    private static final String POSITIVE_TEXT = "Yes";
    private static final String NEGATIVE_TEXT = "No";
    private static final int PADDING = 40;
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Skin skin;

    /**
     * Creates a new last move {@link Dialog} to use in a libGDX {@link com.badlogic.gdx.Screen}
     * @param game The {@link FreedomGame} which will use the {@link Dialog}
     * @param skin The {@link Skin} to be used on the {@link Dialog}
     */
    public LastMoveDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        this.skin = skin;
        text("Do you want to put the last stone?");
        pad(PADDING);
        button("Yes", POSITIVE_TEXT);
        button("No", NEGATIVE_TEXT);
        padBottom(PADDING);
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
            GameOverDialog gameOverDialog = new GameOverDialog(game, skin);
            gameOverDialog.show(getStage());
        }
    }
}
