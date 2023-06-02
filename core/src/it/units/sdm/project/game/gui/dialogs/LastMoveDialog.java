package it.units.sdm.project.game.gui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

public class LastMoveDialog extends Dialog {

    public static final String POSITIVE_TEXT = "Yes";
    public static final String NEGATIVE_TEXT = "No";
    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Skin skin;

    /**
     * Creates a new last move dialog to use in a libgdx screen
     * @param game The game which will use the dialog
     * @param skin The skin to be used on the dialog
     */
    public LastMoveDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        this.skin = skin;
        text("Do you want to put the last stone?");
        button("Yes", POSITIVE_TEXT);
        button("No", NEGATIVE_TEXT);
        setSize(500, 200);
        padBottom(40f);
    }
    @Override
    protected void result(Object object) {
        super.result(object);
        if(!(object instanceof String)) {
            return;
        }
        String message = (String) object;
        if (message.equals(POSITIVE_TEXT)) {
            hide();
        } else {
            hide();
            GameOverDialog gameOverDialog = new GameOverDialog(game, skin);
            gameOverDialog.show(getStage());
        }
    }
}
