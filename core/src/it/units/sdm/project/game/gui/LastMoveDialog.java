package it.units.sdm.project.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.jetbrains.annotations.NotNull;

public class LastMoveDialog extends Dialog {

    @NotNull
    private final FreedomGame game;
    @NotNull
    private final Skin skin;

    public LastMoveDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        this.skin = skin;
        text("Do you want to put the last stone?");
        button("Yes", "Yes");
        button("No", "No");
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
        if (message.equals("Yes")) {
            game.getStatusHandler().proceedToNextState();
            hide();
        } else {
            hide();
            GameOverDialog gameOverDialog = new GameOverDialog(game, skin);
            gameOverDialog.show(getStage());
        }
    }
}
